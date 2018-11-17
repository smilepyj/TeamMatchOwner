package com.yanggle.teammatch.owner;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.yanggle.teammatch.owner.network.Service;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

public class GroundLocationActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    Context mContext;
    ApplicationTM mApplicationTM;

    private Service mService;

    Toolbar toolbar;

    String ground_name;
    double ground_loc_lat, ground_loc_lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ground_location);

        mContext = this;
        mApplicationTM = (ApplicationTM) getApplication();

        mService = new Service(mContext);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        ground_name = getIntent().getStringExtra("ground_name");
        ground_loc_lat = getIntent().getDoubleExtra("ground_loc_lat", 0.0);
        ground_loc_lon = getIntent().getDoubleExtra("ground_loc_lon", 0.0);

        MapView mapView = new MapView(this);
        mapView.setDaumMapApiKey(getString(R.string.kakao_app_key));
        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.rl_ground_location);
        MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(ground_loc_lat, ground_loc_lon);
        mapView.setMapCenterPoint(mapPoint, false);
        //true면 앱 실행 시 애니메이션 효과가 나오고 false면 애니메이션이 나오지않음.
        mapViewContainer.addView(mapView);

        MapPOIItem marker = new MapPOIItem();
        marker.setItemName(ground_name);
        marker.setTag(0);
        marker.setMapPoint(mapPoint);
        // 기본으로 제공하는 BluePin 마커 모양.
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
        // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
        mapView.addPOIItem(marker);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home :
                finish();
                return true;
            default :
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
