package kr.popcorn.sharoom.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import kr.popcorn.sharoom.R;
import me.yokeyword.imagepicker.adapter.GlideFragmentAdapter;


public class Activity_roomInfo extends FragmentActivity {

    public static Activity_roomInfo rActivity;
    private GoogleMap googleMap;
    private MarkerOptions markerOptions;
    private LatLng latLng;
    public static final int SEARCH_RADIUS = 8000;
    private String url = "http://i.imgur.com/DvpvklR.png";

    private ViewPager viewPager;
    private Button btn_find;

    private int[] imgList = new int[] {
            R.drawable.room1, R.drawable.room2, R.drawable.room3, R.drawable.roomimg
    };
    private ArrayList<String> facillitiesList = new ArrayList<>();

    //private ArrayList<String> imgList = new ArrayList<>();
    //private ArrayList<Integer> imgList = new ArrayList<>();

    private final static Integer[] imageResIds = new Integer[] {
            R.drawable.room1, R.drawable.room2, R.drawable.room3, R.drawable.roomimg};


    private GlideFragmentAdapter listAdapter;
    private ImageAdapter adapter;

    private TextView tvCount;
    private int position;
    //public ImageView cFacillities;
    private Button cFacillities, reservationBtn;
    private Activity_FacillitiesInfo customDialog;
    private ViewGroup layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_info);
        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.mapView);

        // Getting a reference to the map
        googleMap = supportMapFragment.getMap();

        // Getting reference to btn_find of the layout activity_main
        btn_find = (Button) findViewById(R.id.map_button);

        //imageview(view pager)
        viewPager = (ViewPager)findViewById(R.id.pager);
        tvCount = (TextView) findViewById(R.id.tv_count);
        position = getIntent().getIntExtra("idx",1);

        if (imgList.length > 1) {
            //if(imgList.size() > 1)
            //tvCount.setText(position + "/" + imgList.size());
            tvCount.setText(position + " /" + imageResIds.length);
        } else {
            tvCount.setText("");
        }

        //listAdapter = new GlideFragmentAdapter( getSupportFragmentManager(), facillitiesList);
        adapter = new ImageAdapter(this);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //tvCount.setText(position + 1 + "/" + imgList.size());
                tvCount.setText(position + 1 + " /" + imgList.length);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // Defining button click event listener for the find button
        final OnClickListener findClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Getting reference to EditText to get the user input location
                EditText etLocation = (EditText) findViewById(R.id.et_location);
                etLocation.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(etLocation, InputMethodManager.SHOW_FORCED);

                // Getting user input location
                String location = etLocation.getText().toString();
                MyApplication myApp = (MyApplication) getApplication();
                myApp.setGlobalString(location);


                if(location!=null && !location.equals("")){
                    new GeocoderTask().execute(location);
                }
            }
        };

        // Setting button click event listener for the find button


        btn_find.setOnClickListener(findClickListener);
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng arg0) {
                Intent mapIntent = new Intent(Activity_roomInfo.this, Activity_largeMap.class);
                startActivity(mapIntent);
            }
        });

        cFacillities = (Button)findViewById(R.id.facillitiesIcon);
        cFacillities.setBackgroundResource(R.drawable.selector_facilitiesbtn);
        cFacillities.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                switch (arg0.getId()) {
                    case R.id.facillitiesIcon:
                        customDialog = new Activity_FacillitiesInfo(Activity_roomInfo.this, cancelListener);
                        customDialog.setCanceledOnTouchOutside(true);
                        customDialog.show();

                        break;

                }

            }

        });

        layout = (ViewGroup) findViewById(R.id.reservationBar);
        layout.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //Intent intent = new Intent(this, SubActivity.class);
                //startActivity(intent);
                switch (v.getId()) {
                    case R.id.reservationBar:
                        //Toast.makeText(MainActivity.this, "예약버튼이 눌렸습니다.", Toast.LENGTH_SHORT).show();
                        Intent reservationIntent = new Intent(Activity_roomInfo.this, Activity_Reservation.class);
                        startActivity(reservationIntent);
                        finish();
                }
            }
        });
    }

    public boolean onTouchEvent(MotionEvent event)
    {
        if(event.getAction() == MotionEvent.ACTION_OUTSIDE){
            customDialog.dismiss();
        }

        return false;
    }


    private View.OnClickListener cancelListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.cancelBtn:
                    customDialog.dismiss();
                    break;
            }
        }
    };

    public class ImageAdapter extends PagerAdapter {
        Context context;

        ImageAdapter(Context context){
            this.context=context;
        }
        @Override
        public int getCount() {
            return imgList.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((ImageView) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int p) {

            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            //imageView.setImageBitmap((decodeSampledBitmapFromResource(getResources(), imgList[p], 100, 100)));
            Glide.with(context).load(url).into(imageView);


            ((ViewPager) container).addView(imageView, 0);

            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((ImageView) object);
        }
    }


    // An AsyncTask class for accessing the GeoCoding Web Service
    private class GeocoderTask extends AsyncTask<String, Void, List<Address>>{

        @Override
        protected List<Address> doInBackground(String... locationName) {
            // Creating an instance of Geocoder class
            Geocoder geocoder = new Geocoder(getBaseContext());
            List<Address> addresses = null;

            try {
                // Getting a maximum of 3 Address that matches the input text
                addresses = geocoder.getFromLocationName(locationName[0], 3);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return addresses;
        }


        @Override
        protected void onPostExecute(List<Address> addresses) {

            if(addresses==null || addresses.size()==0){
                Toast.makeText(getBaseContext(), "No Location found", Toast.LENGTH_SHORT).show();
            }

            // Clears all the existing markers on the map
            googleMap.clear();

            // Adding Markers on Google Map for each matching address
            for(int i=0;i<addresses.size();i++){

                Address address = (Address) addresses.get(i);

                // Creating an instance of GeoPoint, to display in Google Map
                latLng = new LatLng(address.getLatitude(), address.getLongitude());
                String addressText = String.format("%s, %s",
                        address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "",
                        address.getCountryName());

                markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title(addressText);
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.mapmaker));

                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                googleMap.addMarker(markerOptions);
                googleMap.addCircle(new CircleOptions()
                        .center(latLng)
                        .radius(SEARCH_RADIUS * 1.2)
                        .strokeColor(Color.RED)
                        .strokeWidth(4));

                // Locate the first location
                if(i==0)
                    googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            }


        }
    }

    // image resize!!!!!!
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    // image resize!!!!!!
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }
}