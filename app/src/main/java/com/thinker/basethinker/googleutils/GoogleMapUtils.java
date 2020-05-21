package com.thinker.basethinker.googleutils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.thinker.basethinker.MainActivity;
import com.thinker.basethinker.R;
import com.thinker.basethinker.bean.AllSellerBean;
import com.thinker.basethinker.bean.OrderBean;
import com.thinker.basethinker.bean.SellerBean;
import com.thinker.basethinker.eventbus.MainMsgEvent;
import com.thinker.basethinker.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by thinker on 17/12/18.
 */

public class GoogleMapUtils {
    private Activity activity;
    private GoogleMap googleMap;
    private int duration = 1000;//过度时间 秒
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest mLocationRequest;
//    private boolean mRequestingLocationUpdates = false;//控制是否打开定位更新
    private List<Marker> mMarkerRainbow = new ArrayList<>();//所有商户的标记
    private Marker startMarker;//起点的坐标
    public static CameraPosition mMyCurrentLocaton =
            new CameraPosition.Builder().target(new LatLng(-33.87365, 151.20689))
                    .zoom(15.5f)
                    .bearing(0)
                    .tilt(25)
                    .build();//用户当前位置

    public GoogleMapUtils(Activity activity, GoogleMap googleMap) {
        this.activity = activity;
        this.googleMap = googleMap;
    }

    /**
     * When the map is not ready the CameraUpdateFactory cannot be used. This should be called on
     * all entry points that call methods on the Google Maps API.
     */
    public boolean checkReady() {
        if (googleMap == null) {
            Toast.makeText(activity, R.string.map_not_ready, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * Change the camera position by moving or animating the camera depending on the state of the
     * animate toggle button.
     */
    public void changeCamera(boolean haveAnimal, CameraUpdate update, GoogleMap.CancelableCallback callback) {
        if (haveAnimal) {
            // The duration must be strictly positive so we make it at least 1.
            googleMap.animateCamera(update, Math.max(duration, 1), callback);
        } else {
            googleMap.moveCamera(update);
        }
    }

    //移动到我当前的位置
    public void moveToMyLocation() {
        changeCamera(true, CameraUpdateFactory.newCameraPosition(mMyCurrentLocaton), null);
    }

    @SuppressLint("MissingPermission")
    public void setMyUiSetting() {
        if (!checkReady()) {
            return;
        }
        UiSettings settings = googleMap.getUiSettings();
        settings.setMyLocationButtonEnabled(false);//去掉地图自己的定位图标功能
        settings.setTiltGesturesEnabled(false);//禁用地图倾斜功能
        googleMap.setMyLocationEnabled(true);
        googleMap.setOnMarkerClickListener(mOnMarkerClickListener);
        googleMap.setInfoWindowAdapter(new MyInfoWindow());
        googleMap.setOnCameraIdleListener(mOnCameraIdleListener);//注册地图移动户的回调
        googleMap.setOnMapClickListener(mOnMapClickListener);//注册地图点击事件
    }

    public void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        SettingsClient client = LocationServices.getSettingsClient(activity);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
        task.addOnSuccessListener(activity, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                LogUtils.e("mLocationRequestonSuccess");
            }
        });

        task.addOnFailureListener(activity, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                LogUtils.e("mLocationRequestonFailure");
//                if (e instanceof ResolvableApiException) {
//                    // Location settings are not satisfied, but this can be fixed
//                    // by showing the user a dialog.
//                    try {
//                        // Show the dialog by calling startResolutionForResult(),
//                        // and check the result in onActivityResult().
//                        ResolvableApiException resolvable = (ResolvableApiException) e;
//                        resolvable.startResolutionForResult(MainActivity.this,
//                                REQUEST_CHECK_SETTINGS);
//                    } catch (IntentSender.SendIntentException sendEx) {
//                        // Ignore the error.
//                    }
//                }
            }
        });

    }
    private GoogleApiClient mGoogleApiClient;;
    @SuppressLint("MissingPermission")
    public void setMyLocationClient() {
       /* if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(activity)
                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                        @Override
                        public void onConnected(@Nullable Bundle bundle) {

                           Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                            LogUtils.e("已连接+"+mLastLocation);
                        }

                        @Override
                        public void onConnectionSuspended(int i) {
                            LogUtils.e("已连接");
                        }
                    })
                    .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                            LogUtils.e("连接失败");
                        }
                    })
                    .addApi(LocationServices.API)
                    .build();
            mGoogleApiClient.connect();

        }*/
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);
        mFusedLocationClient.getLastLocation().addOnSuccessListener(activity, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                LogUtils.e("定位自己的位置="+location);
                if (location != null) {
                    mMyCurrentLocaton = new CameraPosition.Builder().target(new LatLng(location.getLatitude(), location.getLongitude()))
                            .zoom(15.5f)
                            .bearing(0)
                            .tilt(25)
                            .build();
                    changeCamera(true, CameraUpdateFactory.newCameraPosition(mMyCurrentLocaton), null);
                    EventBus.getDefault().post(new MainMsgEvent(2, mMyCurrentLocaton.target));//获取到自己的定位后 发给主界面
                }
            }
        });
    }

    //开始定位
    public void startLocationUpdates() {
        LogUtils.e("开始定位");
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                ) {
            Toast.makeText(activity,"没有权限,请手动开启定位权限",Toast.LENGTH_SHORT).show();
            // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义）
            ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 126);
        }else{
            mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                    mLocationCallback,
                    null);
        }
    }

    //停止定位
    public void stopLocationUpdates() {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }

    //5秒一次更新定位
    LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            LogUtils.e("location data==="+locationResult);
            for (Location location : locationResult.getLocations()) {
                // Update UI with location data
                // ...

//                mRequestingLocationUpdates = true;
                mMyCurrentLocaton = new CameraPosition.Builder().target(new LatLng(location.getLatitude(), location.getLongitude()))
                        .zoom(15.5f)
                        .bearing(0)
                        .tilt(25)
                        .build();//更新当前位置

            }
        }

        ;
    };

    //添加标记
    public void addMarkersToMap(AllSellerBean allSellerBean) {
        if (allSellerBean.getDataList() == null) {
            return;
        }
        if (allSellerBean.getDataList().size() == 0) {
            return;
        }
        int numMarkersInRainbow = allSellerBean.getDataList().size();
        for (int i = 0; i < numMarkersInRainbow; i++) {
            SellerBean sellerBean = allSellerBean.getDataList().get(i);
            Marker marker = googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(sellerBean.getLocationLat(), sellerBean.getLocationLon()))
                    .title(sellerBean.getSellerName())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.battery_position_n))
            );
            LogUtils.e("添加新的标记");
            mMarkerRainbow.add(marker);
        }
    }

    //添加开始和结束的标识
    public void addMarkersInOrderDetail(OrderBean orderBean) {
        if (orderBean == null) {
            return;
        }

        if (orderBean.getEndLocationLat() != null &&
                orderBean.getEndLocationLon() != null) {
            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(orderBean.getEndLocationLat(), orderBean.getEndLocationLon()))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.end_point))
            );
        }
        if (orderBean.getBeginLocationLat() != null &&
                orderBean.getBeginLocationLon() != null){
            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(orderBean.getBeginLocationLat(), orderBean.getBeginLocationLon()))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.starting_point))
            );
        }
    }
    //移动到地图中间
    public void moveToOrderCenter(OrderBean orderBean) {
        LatLngBounds latLngBounds = null;
        if (orderBean == null) {
            return;
        }
        if (orderBean.getEndLocationLat() != null &&
                orderBean.getEndLocationLon() != null){
            latLngBounds = new LatLngBounds(new LatLng(orderBean.getEndLocationLat(), orderBean.getEndLocationLon())
                    ,new LatLng(orderBean.getEndLocationLat(), orderBean.getEndLocationLon()));
        }
        if (orderBean.getBeginLocationLat() != null &&
                orderBean.getBeginLocationLon() != null){
            latLngBounds = new LatLngBounds(new LatLng(orderBean.getBeginLocationLat(), orderBean.getBeginLocationLon())
                    ,new LatLng(orderBean.getBeginLocationLat(), orderBean.getBeginLocationLon()));
        }
        if (orderBean.getEndLocationLat() != null &&
                orderBean.getEndLocationLon() != null && orderBean.getBeginLocationLat() != null &&
                orderBean.getBeginLocationLon() != null){
            latLngBounds = new LatLngBounds(new LatLng(orderBean.getBeginLocationLat(), orderBean.getBeginLocationLon()),
                    new LatLng(orderBean.getEndLocationLat(), orderBean.getEndLocationLon()));
        }
        if (latLngBounds == null){
            return;
        }
        changeCamera(true, CameraUpdateFactory.newLatLngBounds(latLngBounds,180), null);
    }
    //添加起点的大头针
    public void addStartPointerMarkers(LatLng startLanlng) {
        startMarker = googleMap.addMarker(new MarkerOptions()
                .position(startLanlng)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.needle))
        );
    }

    //删除起点的大头针
    public void deleteStartPointMarkers() {
        if (startMarker == null) {
            return;
        }
        startMarker.remove();
        setLastRecovery();
    }

    //删除标记
    public void deleteMarkersToMap() {
        for (Marker marker : mMarkerRainbow) {
            marker.remove();
            LogUtils.e("清楚原来的标记");
        }
        mMarkerRainbow.clear();
    }

    int id = -1;//标记集合中的指针
    //标记点击事件
    GoogleMap.OnMarkerClickListener mOnMarkerClickListener = new GoogleMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            id = mMarkerRainbow.indexOf(marker);
            EventBus.getDefault().post(new MainMsgEvent(1, marker, id));//发送给主界面
            return true;
        }
    };

    //把上个点击的标记复原
    private void setLastRecovery() {
    }

    //地图移动结束后的回调
    GoogleMap.OnCameraIdleListener mOnCameraIdleListener = new GoogleMap.OnCameraIdleListener() {
        @Override
        public void onCameraIdle() {
            LogUtils.e("地图移动结束后的回调");
            EventBus.getDefault().post(new MainMsgEvent(0, googleMap.getCameraPosition().target));//发送给主界面,地图中心位置
        }
    };
    //地图点击的回调
    GoogleMap.OnMapClickListener mOnMapClickListener = new GoogleMap.OnMapClickListener() {
        @Override
        public void onMapClick(LatLng latLng) {
            LogUtils.e("地图点击后的回调");
            EventBus.getDefault().post(new MainMsgEvent(3, googleMap.getCameraPosition().target));//发送给主界面,地图中心位置
        }
    };

    public static CameraPosition getmMyCurrentLocaton() {
        return mMyCurrentLocaton;
    }

    public static void setmMyCurrentLocaton(CameraPosition mMyCurrentLocaton) {
        GoogleMapUtils.mMyCurrentLocaton = mMyCurrentLocaton;
    }
}
