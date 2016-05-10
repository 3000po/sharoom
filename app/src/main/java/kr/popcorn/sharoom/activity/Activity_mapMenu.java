package kr.popcorn.sharoom.activity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import kr.popcorn.sharoom.R;
import kr.popcorn.sharoom.activity.Fragment.User.Activity_group_view;
import kr.popcorn.sharoom.helper.GpsInfo;

public class Activity_mapMenu extends FragmentActivity implements GoogleMap.OnMapClickListener {

    private GoogleMap mGoogleMap;
    private ImageView homeBtn;
    //private ArrayList<MyMarker> mMyMarkersArray = new ArrayList<MyMarker>();
    //private HashMap<Marker, MyMarker> mMarkersHashMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_menu);

        homeBtn = (ImageView) findViewById(R.id.homeMenu);
        homeBtn.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_mapMenu.this, Activity_group_view.class);
                startActivity(intent);
                finish();
            }
        });

        init();
    }

    /** Map 클릭시 터치 이벤트 */
    public void onMapClick(LatLng point) {

        // 현재 위도와 경도에서 화면 포인트를 알려준다
        Point screenPt = mGoogleMap.getProjection().toScreenLocation(point);

        // 현재 화면에 찍힌 포인트로 부터 위도와 경도를 알려준다.
        LatLng latLng = mGoogleMap.getProjection().fromScreenLocation(screenPt);

        Log.d("맵좌표", "좌표: 위도(" + String.valueOf(point.latitude) + "), 경도("
                + String.valueOf(point.longitude) + ")");
        Log.d("화면좌표", "화면좌표: X(" + String.valueOf(screenPt.x) + "), Y("
                + String.valueOf(screenPt.y) + ")");
    }

    /**
     * 초기화
     * @author
     */
    private void init() {

        GooglePlayServicesUtil.isGooglePlayServicesAvailable(Activity_mapMenu.this);
        mGoogleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapView)).getMap();

        // mMarkersHashMap = new HashMap<Marker, MyMarker>();

        // 맵의 이동
        //mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15));

        GpsInfo gps = new GpsInfo(Activity_mapMenu.this);
        // GPS 사용유무 가져오기
        if (gps.isGetLocation()) {
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            // Creating a LatLng object for the current location
            LatLng latLng = new LatLng(latitude, longitude);

            /*mMyMarkersArray.add(new MyMarker("Brasil", "icon1", Double.parseDouble("-28.5971788"), Double.parseDouble("-52.7309824")));
            mMyMarkersArray.add(new MyMarker("United States", "icon2", Double.parseDouble("33.7266622"), Double.parseDouble("-87.1469829")));
            mMyMarkersArray.add(new MyMarker("Canada", "icon3", Double.parseDouble("51.8917773"), Double.parseDouble("-86.0922954")));
            mMyMarkersArray.add(new MyMarker("England", "icon4", Double.parseDouble("52.4435047"), Double.parseDouble("-3.4199249")));
            */
            // Showing the current location in Google Map
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

            // Map 을 zoom 합니다.
            mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(15));

            // 마커 설정.
            MarkerOptions optFirst = new MarkerOptions();
            optFirst.position(latLng);// 위도 • 경도
            optFirst.title("Current Position");// 제목 미리보기
            optFirst.snippet("Snippet");
            optFirst.icon(BitmapDescriptorFactory.fromResource(R.drawable.mapmaker));
            mGoogleMap.addMarker(optFirst).showInfoWindow();

        }
    }

  /*  private void plotMarkers(ArrayList<MyMarker> markers)
    {
        if(markers.size() > 0)
        {
            for (MyMarker myMarker : markers)
            {

                // Create user marker with custom icon and other options
                MarkerOptions markerOption = new MarkerOptions().position(new LatLng(myMarker.getmLatitude(), myMarker.getmLongitude()));
                markerOption.icon(BitmapDescriptorFactory.fromResource(R.drawable.mapmaker));

                Marker currentMarker = mGoogleMap.addMarker(markerOption);
                mMarkersHashMap.put(currentMarker, myMarker);

                //mGoogleMap.setInfoWindowAdapter(new MarkerInfoWindowAdapter());
            }
        }
    }*/
}
