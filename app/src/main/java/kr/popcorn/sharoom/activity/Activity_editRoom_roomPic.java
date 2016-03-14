package kr.popcorn.sharoom.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import kr.popcorn.sharoom.R;
import kr.popcorn.sharoom.helper.Helper_adapterCommunication;
import kr.popcorn.sharoom.helper.Helper_roomPicListAdapter;
import kr.popcorn.sharoom.helper.Helper_roomPicPreview;
import me.yokeyword.imagepicker.ImagePicker;
import me.yokeyword.imagepicker.callback.CallbackForCamera;
import me.yokeyword.imagepicker.callback.CallbackForImagePicker;

/**
 * Created by user on 16. 3. 13.
 */


public class Activity_editRoom_roomPic extends Activity {

    private RecyclerView recyclerView;
    private Helper_roomPicListAdapter listAdapter;

    private  ArrayList<String> list;
    private ImagePicker mImagePicker;

    public void updateTitle(){
        getActionBar().setTitle("사진 "+list.size()+"장");
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        list = (ArrayList<String>) getIntent().getSerializableExtra("list");
        mImagePicker = new ImagePicker(this);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        updateTitle();

        recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).
                color(Color.LTGRAY).sizeResId(R.dimen.divider).marginResId(R.dimen.leftmargin, R.dimen.rightmargin).build());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        listAdapter = new Helper_roomPicListAdapter(this,
                list, (LinearLayoutManager) recyclerView.getLayoutManager());

        listAdapter.setOnClickListener(new Helper_adapterCommunication(){
            public void removeItem(int position){
                list.remove(position);
                saveData();
                updateTitle();
                listAdapter.notifyDataSetChanged();
            }
            public void longClickItem(int position){
                openPreview(position);
            }
        });
        recyclerView.setAdapter(listAdapter);
    }

    public void openPreview(int position){
        Intent it = new Intent(this, Helper_roomPicPreview.class);
        it.putExtra("list", list);
        it.putExtra("idx", position);
        startActivity(it);
    }

    @Override
    public void onBackPressed(){
        Intent it = getIntent();
        it.putExtra("list", list);
        setResult(1, it);
        finish();
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
            case android.R.id.home:
                Intent it = getIntent();
                it.putExtra("list", list);
                setResult(1, it);
                finish();
                return true;

            case R.id.camera:
                // camera 이 눌렸을 경우 이벤트 발생
                mImagePicker.openCamera(new CallbackForCam());
                return true;

            case R.id.gallery:
                // gallery 이 눌렸을 경우 이벤트 발생
                mImagePicker.openImagePiker(true, new CallbackForGal());
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class CallbackForCam implements CallbackForCamera{
        @Override
        public void onError(Exception error) {

        }
        @Override
        public void onComplete(String imagePath) {
            list.add(imagePath);
            listAdapter.setList(list);
            saveData();
            listAdapter.notifyDataSetChanged();
            updateTitle();
        }
        @Override
        public void onCancel(String imagePath) {
            Toast.makeText(getApplicationContext(), "실패..", Toast.LENGTH_SHORT).show();

            File tempFile = new File(imagePath);
            if (tempFile.exists()) {
                tempFile.delete();
            }
        }
    }
    public class CallbackForGal implements CallbackForImagePicker{
        @Override
        public void onError(Exception error) {
        }
        @Override
        public void onComplete(List<String> imagePath) {
            list.addAll(imagePath);
            listAdapter.setList(list);
            saveData();
            listAdapter.notifyDataSetChanged();
            updateTitle();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mImagePicker.delegateActivityResult(requestCode, resultCode, data);
    }

    private void saveData(){
        Log.i("aab","saved");
        // 특정번호의 공유저장소를 편집가능 상태로 불러온다.
        SharedPreferences.Editor edt = getSharedPreferences("room", 0).edit();

        // 저장
        edt.putInt("picCount", list.size());
        for(int i=0; i<list.size(); i++){
            edt.putString("pic"+i, list.get(i));
        }

        // 수행
        edt.commit();
    }

}
