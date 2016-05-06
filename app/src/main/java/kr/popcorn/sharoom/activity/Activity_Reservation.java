package kr.popcorn.sharoom.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kr.popcorn.sharoom.R;
import me.yokeyword.imagepicker.adapter.GlideFragmentAdapter;

/**
 * Created by parknature on 16. 5. 6..
 */
public class Activity_Reservation extends Activity {
    private ViewPager viewPager;
    private ViewGroup reservationBtn, requestBtn;
    private GlideFragmentAdapter listAdapter;
    private ImageAdapter adapter;
    private TextView tvCount, startDate, endDate;
    private int position;
    private Paint p;
    private Spinner peopleNum;
    private Activity_profileView customDialog;
    public static Activity_Reservation rActivity;

    private int[] imgList = new int[] {
            R.drawable.room1, R.drawable.room2, R.drawable.room3, R.drawable.roomimg
    };
    private final static Integer[] imageResIds = new Integer[] {
            R.drawable.room1, R.drawable.room2, R.drawable.room3, R.drawable.roomimg};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        rActivity = Activity_Reservation.this;

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
        p = new Paint();
        p.setColor(Color.rgb(32, 197, 137));

        startDate = (TextView) findViewById(R.id.startDate);
        //SpannableString content = new SpannableString("2016/2/14");
        //content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        startDate.setPaintFlags(p.getColor());
        startDate.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        startDate.setText("2016/2/14");


        endDate = (TextView) findViewById(R.id.endDate);
        endDate.setPaintFlags(p.getColor());
        endDate.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        endDate.setText("2016/2/16");

        peopleNum = (Spinner)findViewById(R.id.peopleNum);
        List<String> list = new ArrayList<String>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");

        ArrayAdapter<String> mMyadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, list);
        mMyadapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        peopleNum.setAdapter(mMyadapter);

        reservationBtn = (ViewGroup)findViewById(R.id.reservationBtn);
        reservationBtn.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                switch (arg0.getId()) {
                    case R.id.reservationBtn:
                        Intent finishReservIntent = new Intent(Activity_Reservation.this, Activity_FinishReserv.class);
                        startActivity(finishReservIntent);
                        break;

                }

            }

        });

        requestBtn = (ViewGroup) findViewById(R.id.requestInfo);
        requestBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId())
                {
                    case R.id.requestInfo:
                        //Toast.makeText(Activity_Reservation.this, "문의요청버튼 누름.", Toast.LENGTH_LONG).show();
                        customDialog = new Activity_profileView(Activity_Reservation.this);
                        customDialog.setCanceledOnTouchOutside(true);
                        customDialog.show();

                        break;
                }
            }
        });

    }

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
        public Object instantiateItem(ViewGroup container, int position) {

            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(imgList[position]);

            ((ViewPager) container).addView(imageView, 0);

            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((ImageView) object);
        }
    }
}
