package com.thinker.basethinker.feedback;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.car.qijia.thinker.photo.view.ImageSelectorActivity;
import com.google.gson.Gson;
import com.thinker.basethinker.MainActivity;
import com.thinker.basethinker.R;
import com.thinker.basethinker.feedback.adapter.ChoosePhotoAdapter;
import com.thinker.basethinker.feedback.adapter.QuestionAdapter;
import com.thinker.basethinker.feedback.bean.FeedBackUpLoadBean;
import com.thinker.basethinker.feedback.bean.FeedbackTypeListBean;
import com.thinker.basethinker.feedback.bean.FeedbackTypeListData;
import com.thinker.basethinker.mvp.MvpActivity;
import com.thinker.basethinker.scan.ScanActivity;
import com.thinker.basethinker.scan.SelectTypeActivity;
import com.thinker.basethinker.utils.LogUtils;
import com.thinker.basethinker.utils.Utils;
import com.thinker.basethinker.views.MyGridView;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by farley on 17/5/22.
 * description:
 */

public class FeedBackActivity extends MvpActivity<FeedBackPresenter, IFeedBackView> implements IFeedBackView, View.OnClickListener {
    private FeedBackPresenter presenter;
    private ImageView head_left, head_right;
    private TextView head_title;
    private MyGridView gridview_item;
    private ImageView scan_code;
    private EditText syscode;
    private EditText edt_desc;
    private RecyclerView mRecyclerView;
    private QuestionAdapter adapter;
    private List<FeedbackTypeListData> questionStrList = new ArrayList<>();
    private int chooseOne = 0;
    private ChoosePhotoAdapter chooseAdapter;
    private List<String> imgArray = new ArrayList<>();//照片
    private String typeQuestion = "1";//默认是一般问题
    private String codeQuestion;//车辆的code
    private Button complete;
    private Long questionCode = -1L;
    private String tripId = null;
    private String desc;
    private TextView feeedback_desc_tips;
    private FeedBackUpLoadBean bean = new FeedBackUpLoadBean();

    @Override
    protected FeedBackPresenter CreatePresenter() {
        return presenter = new FeedBackPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        typeQuestion = getIntent().getStringExtra("TYPE");
        tripId = getIntent().getStringExtra("TRIPID");
        initView();
        LogUtils.e("typeQuestion=" + typeQuestion);
        if (!TextUtils.isEmpty(typeQuestion)) {//1 一般问题 2 行程中 3 已完成用户
            presenter.getFeedBackTypeList(typeQuestion);//获取问题列表
        } else {
            presenter.getFeedBackTypeList("1");//获取问题列表
        }
    }

    public List<Bitmap> getImageList() {
        List<Bitmap> bitmaps = new ArrayList<>();
        if (imgArray.size() > 0) {
            for (String url : imgArray) {
                File file = new File(url);
                Bitmap bitmap = Utils.getBitmapFromFile(file, 1000, 1000);
                if (bitmap != null) {
                    bitmaps.add(bitmap);
                }
            }
            return bitmaps;
        }
        return null;
    }

    public void initData(FeedbackTypeListBean feedbackTypeListBean) {
        if("3".equals(typeQuestion)){
            syscode.setText(tripId);
//            syscode.setEnabled(false);
//            scan_code.setEnabled(false);
        }
        questionStrList.clear();
        if (feedbackTypeListBean != null) {
            questionStrList.addAll(feedbackTypeListBean.getDatas());
        }
        setQuestionList();
        setPhoto();
    }

    private void initView() {
        complete = (Button) findViewById(R.id.complete);
        scan_code = (ImageView) findViewById(R.id.scan_code);
        edt_desc = (EditText) findViewById(R.id.edt_desc);
        syscode = (EditText) findViewById(R.id.syscode);
        gridview_item = (MyGridView) findViewById(R.id.gridview_item);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_receycleview);
        head_title = (TextView) findViewById(R.id.head_title);
        head_left = (ImageView) findViewById(R.id.head_left);
        head_right = (ImageView) findViewById(R.id.head_right);
        feeedback_desc_tips = (TextView)findViewById(R.id.feeedback_desc_tips);
        head_title.setText(Utils.object2String(getString(R.string.feedback_title)));
        head_right.setVisibility(View.GONE);
        head_left.setOnClickListener(this);
        scan_code.setOnClickListener(this);
        complete.setOnClickListener(this);
        edt_desc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                desc = s.toString();
                if (s.length() > 0 && !TextUtils.isEmpty(syscode.getText().toString().trim()) && questionCode > -1) {
                    complete.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        if (typeQuestion.equals("2")){
            feeedback_desc_tips.setVisibility(View.VISIBLE);
        }else{
            feeedback_desc_tips.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_left:
                finish();
                break;
            case R.id.scan_code:
                Intent bindPhone = new Intent(this, ScanActivity.class);
                startActivityForResult(bindPhone, 5);
                break;
            case R.id.complete:
                if (TextUtils.isEmpty(desc)) {
                    Toast.makeText(this, getString(R.string.feedback_toast1), Toast.LENGTH_SHORT).show();
                } /*else if (TextUtils.isEmpty(syscode.getText().toString().trim())) {
                    Toast.makeText(this,getString(R.string.feedback_toast2),Toast.LENGTH_SHORT).show();
                }*/ else if (questionCode < 0) {
                    Toast.makeText(this, getString(R.string.feedback_toast3), Toast.LENGTH_SHORT).show();
                } else {
                    bean.setOrderCode(tripId);
                    bean.setFeedDesc(desc);
                    bean.setSysCode(syscode.getText().toString().trim());
                    bean.setTypeId(String.valueOf(questionCode));
                    if (imgArray.size() > 0) {
                        bean.setImgUrl1(imgArray.get(0));
                    } else if (imgArray.size() > 1) {
                        bean.setImgUrl1(imgArray.get(0));
                        bean.setImgUrl2(imgArray.get(1));
                    } else if (imgArray.size() > 2) {
                        bean.setImgUrl1(imgArray.get(0));
                        bean.setImgUrl2(imgArray.get(1));
                        bean.setImgUrl3(imgArray.get(2));
                    } else if (imgArray.size() > 3) {
                        bean.setImgUrl1(imgArray.get(0));
                        bean.setImgUrl2(imgArray.get(1));
                        bean.setImgUrl3(imgArray.get(2));
                        bean.setImgUrl4(imgArray.get(3));
                    }
                    presenter.feedback(bean);
                }
                break;
            default:
                break;
        }
    }

    //设置问题类型
    private void setQuestionList() {
        if (questionStrList.size() > 5) {
            FeedbackTypeListData data = new FeedbackTypeListData();
            data.setTypeName(getString(R.string.feedback_qusetion_item6));
            questionStrList.add(5, data);
        }
        adapter = new QuestionAdapter(this, questionStrList);
        gridview_item.setAdapter(adapter);
        if (questionStrList.size() > 0 && !TextUtils.isEmpty(codeQuestion)) {
            adapter.setSelected(0);
            questionCode = questionStrList.get(0).getId();
        }
        adapter.setOnMySelectedListener(new QuestionAdapter.OnMySeletcedListener() {
            @Override
            public void onSelected(int position) {
                adapter.setSelected(position);
                questionCode = questionStrList.get(position).getId();
                if (questionStrList.size() > 5 && position == 5) {
                    Gson gson = new Gson();
                    FeedbackTypeListBean myBean = new FeedbackTypeListBean(questionStrList);

                    String strList = gson.toJson(myBean);
                    Intent it = new Intent(FeedBackActivity.this, ChooseMoreQuestionActivity.class);
                    it.putExtra("POSITION", chooseOne);
                    it.putExtra("STRLIST", strList);
                    startActivityForResult(it, 199);
                }
            }
        });
    }

    //设置照片
    private void setPhoto() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        chooseAdapter = new ChoosePhotoAdapter(this, imgArray);
        chooseAdapter.setOnItemClickLitener(new ChoosePhotoAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(int position) {
                if (position == imgArray.size()) {
                    ImageSelectorActivity activty = new ImageSelectorActivity();
                    activty.start(FeedBackActivity.this, 4 - imgArray.size(), ImageSelectorActivity.MODE_MULTIPLE, true, true, false);
                } else {
                   /* Intent review = new Intent(AssignUpActivity.this, PhotoReviewActivity.class);
                    review.putStringArrayListExtra("IMGLIST", (ArrayList<String>) imgArray);
                    review.putExtra("POSITION",  position);
                    startActivityForResult(review,1000);*/
                }
            }
        });
        mRecyclerView.setAdapter(chooseAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 199) {
            if (data != null) {
                Long str = data.getLongExtra("MOREQUESTION", -1L);
                int posSelected = data.getIntExtra("MOREPOSITION", 0);
                chooseOne = posSelected;
                questionCode = str;
            }
        }
        if (resultCode == RESULT_OK && requestCode == ImageSelectorActivity.REQUEST_IMAGE) {
            ArrayList<String> images = (ArrayList<String>) data.getSerializableExtra(ImageSelectorActivity.REQUEST_OUTPUT);
            if (images.size() > 0) {
                imgArray.addAll(images);
                if (chooseAdapter != null) {
                    chooseAdapter.notifyDataSetChanged();
                }
            }
        }
        if (requestCode == 5//扫描后返回
                && resultCode == Activity.RESULT_OK) {
            String result = data.getStringExtra(CodeUtils.RESULT_STRING);
            LogUtils.e("code=" + result);
            String[] codes = result.split("=");
            if (codes.length < 2){
                Toast.makeText(this, getString(R.string.feedback_toast4), Toast.LENGTH_SHORT).show();
                return;
            }else{
                codeQuestion  = codes[1];
                syscode.setText(codeQuestion);
            }
        }


    }
}
