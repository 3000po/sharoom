package kr.popcorn.sharoom.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import kr.popcorn.sharoom.R;
import kr.popcorn.sharoom.helper.Helper_roomPicListAdapter;
import me.yokeyword.imagepicker.ImagePicker;
import me.yokeyword.imagepicker.callback.CallbackForCamera;
import me.yokeyword.imagepicker.callback.CallbackForImagePicker;

/**
 * Created by user on 16. 3. 13.
 */

//TODO 뒤로가기시 저장, 액티비티종료시 저장, 리스트 전
public class Activity_editRoom_roomPic extends Activity {

    private RecyclerView recyclerView;
    private Helper_roomPicListAdapter listAdapter;

    private  ArrayList<String> list;
    private ImagePicker mImagePicker;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        list = (ArrayList<String>) getIntent().getSerializableExtra("list");
        mImagePicker = new ImagePicker(this);


        recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).
                color(Color.LTGRAY).sizeResId(R.dimen.divider).marginResId(R.dimen.leftmargin, R.dimen.rightmargin).build());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
달
        listAdapter = new Helper_roomPicListAdapter(this,
                list, (LinearLayoutManager) recyclerView.getLayoutManager());
        recyclerView.setAdapter(listAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflater함수를 이용해서 menu 리소스를 menu로 변환.
        // 두 줄 코드
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity_editroom, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.camera:
                // camera 이 눌렸을 경우 이벤트 발생
                mImagePicker.openCamera(new CallbackForCamera() {
                    @Override
                    public void onError(Exception error) {

                    }
                    @Override
                    public void onComplete(String imagePath) {
                        list.add(imagePath);
                    }

                    @Override
                    public void onCancel(String imagePath) {
                        Toast.makeText(getApplicationContext(), "실패..", Toast.LENGTH_SHORT).show();

                        File tempFile = new File(imagePath);
                        if (tempFile.exists()) {
                            tempFile.delete();
                        }
                    }
                });
                return true;

            case R.id.gallery:
                // gallery 이 눌렸을 경우 이벤트 발생
                mImagePicker.openImagePiker(true, new CallbackForImagePicker() {
                    @Override
                    public void onError(Exception error) {

                    }
                    @Override
                    public void onComplete(List<String> imagePath) {
                        list.addAll(imagePath);
                    }
                });
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
