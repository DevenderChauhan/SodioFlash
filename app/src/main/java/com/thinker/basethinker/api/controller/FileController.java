package com.thinker.basethinker.api.controller;

import android.content.Context;
import android.graphics.Bitmap;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;
import com.thinker.basethinker.api.CommonController;
import com.thinker.basethinker.api.FileUtils;

import java.io.File;

import rx.Observable;
import rx.functions.Func1;
import vc.thinker.colours.client.api.FileuploadcontrollerApi;
import vc.thinker.colours.client.model.SingleResponseOfRequestResult;

/**
 * Created by huan on 2016/1/28.
 */
public class FileController extends CommonController {
    private FileuploadcontrollerApi mFileuploadcontrollerApi;

    public FileController(FileuploadcontrollerApi fileuploadcontrollerApi) {
        mFileuploadcontrollerApi = fileuploadcontrollerApi;
    }
    //上传单个
    public Observable<String> postFile(String filePath) {
        File file = new File(filePath);
        String fileTypeDesc = "IMAGE";
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);

        return mFileuploadcontrollerApi.uploadFileV2UsingPOST(fileTypeDesc, requestBody)
                .map(new Func1<SingleResponseOfRequestResult, String>() {
                    @Override
                    public String call(SingleResponseOfRequestResult singleResponseOfRequestResult) {
                        if (singleResponseOfRequestResult.getSuccess()) {
                            return singleResponseOfRequestResult.getItem().getUrl();
                        }

                        return null;
                    }
                });
    }
    //上传一组
   /* public Observable<List<String>> postFiles(List<String> filePaths) {
        Map<String, RequestBody> partMap = new HashMap<>();
        String fileTypeDesc = "IMAGE";
        if (filePaths != null) {
            for (String filePath : filePaths) {
                File file = new File(filePath);
                RequestBody fileBody = RequestBody.create(MediaType.parse("image*//**//*"), file);
                partMap.put("file\"; filename=\""+file.getName()+"\"", fileBody);
            }
        }
        return mFileuploadcontrollerApi.uploadFileBatchV2UsingPOST(fileTypeDesc, partMap)
                .map(new Func1<ListResponseOfRequestResult, List<String>>() {
                    @Override
                    public List<String> call(ListResponseOfRequestResult listResponseOfRequestResult) {
                        List<RequestResult> list = listResponseOfRequestResult.getItems();
                        List<String> result = new ArrayList<String>();
                        if (list != null && list.size() > 0)
                        {
                            for (RequestResult rr: list)
                            {
                                result.add(rr.getUrl());
                            }
                        }
                        return result;
                    }
                });
    }*/

    public Observable<String> postFile(Context context, Bitmap bitmap) {
        if (bitmap != null) {
            String fileName = FileUtils.getDiskCacheDir(context) + "/" + String.valueOf(System.currentTimeMillis()) + ".jpg";
            String filePath = FileUtils.saveBitmap(bitmap, fileName);

            return postFile(filePath).map(new Func1<String, String>() {
                @Override
                public String call(String s) {
                    return s;
                }
            });
        }

        return Observable.error(new Exception("post failed"));
    }

   /* public Observable<List<String>> postFiles(Context context, List<Bitmap> bitmaps) {
        List<String> pathList = new ArrayList<>();
        if (bitmaps != null && bitmaps.size() > 0) {
            for (Bitmap bitmap : bitmaps)
            {
                String fileName = FileUtils.getDiskCacheDir(context) + "/" + String.valueOf(System.currentTimeMillis()) + ".jpg";
                String filePath = FileUtils.saveBitmap(bitmap, fileName);
                pathList.add(filePath);
            }

            return postFiles(pathList).map(new Func1<List<String>, List<String>>() {
                @Override
                public List<String> call(List<String> strings) {
                    return strings;
                }
            });

        }

        return Observable.error(new Exception("post failed"));
    }*/
}
