package com.example.customandroidmapstylesdarkmodeandcameras;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.PermissionRequest;
import android.widget.Button;

import com.here.sdk.core.GeoBox;
import com.here.sdk.core.GeoCoordinates;
import com.here.sdk.mapview.MapCamera;
import com.here.sdk.mapview.MapError;
import com.here.sdk.mapview.MapScene;
import com.here.sdk.mapview.MapScheme;
import com.here.sdk.mapview.MapView;

public class MainActivity extends AppCompatActivity {
    Button btnCamera, btnCustom , btnStyle ;
    private MapView mapView;
    // ChangeStyle
    private int styleCounter = 0 ;
    private MapScheme scheme = MapScheme.NORMAL_DAY;

    // Change Camera
    private int cameraCounter = 0 ;
    private GeoCoordinates cameraCoordinates ;
    private double bearingInDegrees ;
    private double tilInDegrees ;
    private MapCamera.OrientationUpdate cameraOrientation ;
    private double distanceInMeters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnCamera = findViewById(R.id.btnCamare);
        btnCustom = findViewById(R.id.btnCustom);
        btnStyle = findViewById(R.id.btnStyle);
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState); // Phai co create neu khong bi loi
        loadMap();
        btnStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeStyle(v);
            }
        });
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeCamera(v);
            }
        });
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void loadMap()
    {
        GeoCoordinates geoCoordinates = new GeoCoordinates(10.46171 , 105.64354);
     //   GeoCoordinates geoCoordinates = new GeoCoordinates(location.getLatitude() , location.getLongitude());
        mapView.getMapScene().loadScene(MapScheme.NORMAL_DAY, new MapScene.LoadSceneCallback() {
            @Override
            public void onLoadScene(@Nullable MapError mapError) {
                if (mapError == null) {
                    double distanceInMeters = 1000;
                    mapView.getCamera().lookAt(
                            //        new GeoCoordinates(10.46384,  105.6441), distanceInMeters);
                            geoCoordinates,distanceInMeters);
                } else {
                    Log.d("Log", "Loading map failed: mapError: " + mapError.name());
                }
            }
        });
    }
    private void changeStyle(View view)
    {
        styleCounter ++ ;
        switch ((styleCounter))
        {
            case 0 :
                scheme = MapScheme.NORMAL_DAY;
                break;
            case 1:
                scheme = MapScheme.SATELLITE;
                break;
            case 2:
                scheme = MapScheme.NORMAL_NIGHT;
                break;
            case 3:
                scheme = MapScheme.GREY_DAY;
                break;
            case 4:
                styleCounter = 0 ;
        }
        mapView.getMapScene().loadScene(scheme, new MapScene.LoadSceneCallback() {
            @Override
            public void onLoadScene(@Nullable MapError mapError) {
              if(mapError==null)
              {

              }
              else {

              }
            }
        });
    }
    private void changeCamera(View view)
    {
        cameraCounter ++ ;
        if (cameraCounter == 4) cameraCounter = 0 ;
        cameraCoordinates = new GeoCoordinates(10.46171 , 105.64354);
        bearingInDegrees = 0 ;
        tilInDegrees = 0 ;
        cameraOrientation = new MapCamera.OrientationUpdate(bearingInDegrees , tilInDegrees);
        distanceInMeters = 1000;
        Log.e("Log", "Change Camera !!!");
        switch (cameraCounter)
        {
            case 0 :
                mapView.getCamera().lookAt(cameraCoordinates, cameraOrientation, distanceInMeters);
                Log.e("Log", "0");
                break;
            case 1:
                bearingInDegrees = 180 ;
                cameraOrientation = new MapCamera.OrientationUpdate(bearingInDegrees, tilInDegrees);
                mapView.getCamera().lookAt(cameraCoordinates, cameraOrientation, distanceInMeters);
                Log.e("Log", "1");
                break;
            case 2:
                bearingInDegrees = 270 ;
                distanceInMeters = 2000;
                cameraOrientation = new MapCamera.OrientationUpdate(bearingInDegrees, tilInDegrees);
                mapView.getCamera().lookAt(cameraCoordinates, cameraOrientation, distanceInMeters);
                Log.e("Log", "2");
                break;
            case 3:
                GeoBox cameraBox  = new GeoBox(new GeoCoordinates(10.46171 , 105.64354), new GeoCoordinates(10.46171 , 105.64570));
                mapView.getCamera().lookAt(cameraBox, cameraOrientation);
                Log.e("Log", "3");
                break;

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
}