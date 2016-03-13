package kr.popcorn.sharoom.helper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import kr.popcorn.sharoom.R;
import me.yokeyword.imagepicker.ImagePickerActivity;
import me.yokeyword.imagepicker.adapter.GlideFragmentAdapter;
import me.yokeyword.imagepicker.fragments.PreviewFragment;

/**
 * Created by user on 16. 3. 13.
 */

public class Helper_roomPicPreview extends FragmentActivity {

    private int position;

    private ViewPager viewPager;
    private TextView tvCount, tvBtnYes, tvPickPicCount;

    private ArrayList<String> imgList = new ArrayList<>();
    private GlideFragmentAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        imgList = (ArrayList<String>) getIntent().getSerializableExtra("list");
        position = getIntent().getIntExtra("idx",0);

        initView();
        initListener();
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tvCount = (TextView) findViewById(R.id.tv_count);
        tvBtnYes = (TextView) findViewById(R.id.tv_btn_yes);
        tvPickPicCount = (TextView) findViewById(R.id.tv_pick_pic_count);

        tvPickPicCount.setText(String.format(getString(R.string.yo_select_pic_count), imgList.size()));

        if (imgList.size() > 1) {
            tvCount.setText( position + "/" + imgList.size());
        } else {
            tvCount.setText("");
        }

        adapter = new GlideFragmentAdapter( getSupportFragmentManager(), imgList);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position);
    }

    private void initListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tvCount.setText(position + 1 + "/" + imgList.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tvBtnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
