package kr.popcorn.sharoom.activity.View;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Calendar;
import java.util.GregorianCalendar;

import kr.popcorn.sharoom.R;
import kr.popcorn.sharoom.activity.Activity_profileView;

/**
 * Created by parknature on 16. 5. 6..
 */
public class Activity_Reservation_check extends Activity {

    private ViewPager viewPager;
    private ViewGroup requestBtn;
    private RelativeLayout sureBtn;
    private ImageAdapter adapter;
    private TextView tvCount, startDate, endDate;
    private int mYear, mMonth, mDay;

    private String url = "http://i.imgur.com/DvpvklR.png";

    private int position;
    private Paint p;
    private Spinner peopleNum;
    private Activity_profileView customDialog;


    private int[] imgList = new int[] {
            R.drawable.room1, R.drawable.room2, R.drawable.room3, R.drawable.roomimg
    };
    private final static Integer[] imageResIds = new Integer[] {
            R.drawable.room1, R.drawable.room2, R.drawable.room3, R.drawable.roomimg};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_check);

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
        //startDate.setHint("년/월/일");


//        startDate.setText("2016/2/14");


        endDate = (TextView) findViewById(R.id.endDate);

        Calendar cal = new GregorianCalendar();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);
        startDate.setText(String.format("%d/%d/%d", mYear, mMonth+1, mDay));
        endDate.setText(String.format("%d/%d/%d", mYear, mMonth+1, mDay));

        //달력 입력을 받기 위한 다이얼로그
        startDate.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()){
                    case R.id.startDate:
                        new DatePickerDialog(Activity_Reservation_check.this, mDateSetListener1, mYear, mMonth, mDay).show();
                        break;

                }
            }
        });

        endDate.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()){
                    case R.id.endDate:
                        new DatePickerDialog(Activity_Reservation_check.this, mDateSetListener2, mYear, mMonth, mDay).show();
                        break;

                }
            }
        });


        /*peopleNum = (Spinner)findViewById(R.id.peopleNum);
        List<String> list = new ArrayList<String>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");

        ArrayAdapter<String> mMyadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, list);
        mMyadapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        peopleNum.setAdapter(mMyadapter);
        */
        sureBtn = (RelativeLayout)findViewById(R.id.sure);

        sureBtn.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                switch (arg0.getId()) {
                    case R.id.sure:
                        finish();
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
                        customDialog = new Activity_profileView(Activity_Reservation_check.this);
                        customDialog.setCanceledOnTouchOutside(true);
                        customDialog.show();

                        break;
                }
            }
        });

    }

    DatePickerDialog.OnDateSetListener mDateSetListener1 =
            new DatePickerDialog.OnDateSetListener(){

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;

                    startDate.setText(String.format("%d/%d/%d", mYear, mMonth+1, mDay));
                }
            };

    DatePickerDialog.OnDateSetListener mDateSetListener2 =
            new DatePickerDialog.OnDateSetListener(){

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;

                    endDate.setText(String.format("%d/%d/%d", mYear, mMonth+1, mDay));
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
        public Object instantiateItem(ViewGroup container, int position) {

            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            //imageView.setImageResource(imgList[position]);

            Glide.with(context).load(url).into(imageView);

            ((ViewPager) container).addView(imageView, 0);

            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((ImageView) object);
        }
    }

}
