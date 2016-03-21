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


//Acitivity_editRoom에서 방사진목록을 따로 편집하기위한 액티비티
public class Activity_editRoom_roomPic extends Activity {

    public final int MAX_SIZE=7;

    private RecyclerView recyclerView;
    private Helper_roomPicListAdapter listAdapter;

    private  ArrayList<String> list;
    private ImagePicker mImagePicker;

    //방 사진 개수에 변동사항이있을떄 호출되서 현재 방사진이 몇개 올라왔는지 수정해준다.
    public void updateTitle(){
        getActionBar().setTitle("사진 "+list.size()+"장");
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //Activity_editRoom_roomPic에서 전달한 리스트를 불러온다.
        list = (ArrayList<String>) getIntent().getSerializableExtra("list");
        mImagePicker = new ImagePicker(this);
        saveData(); //Activity_editRoom_roomPic에서 전달한 리스트를 저장한다. (강제종료됐을경우 리스트를 불러오기 위함)

        getActionBar().setDisplayHomeAsUpEnabled(true);     //액션바에 뒤로가기 버튼 추가
        updateTitle();                                      //액션바 타이틀 수정

        //RecyclerView(ListView)를 초기화해줌.
        recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).
                color(Color.LTGRAY).sizeResId(R.dimen.divider).marginResId(R.dimen.leftmargin, R.dimen.rightmargin).build());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        listAdapter = new Helper_roomPicListAdapter(this,
                list, (LinearLayoutManager) recyclerView.getLayoutManager());

        //RecyclerView 각 원소에 버튼이 눌렸을때 처리
        listAdapter.setOnClickListener(new Helper_adapterCommunication(){

            //삭제버튼이 눌렸을경우 지워주고 그 리스트를 저장한다.
            public void removeItem(int position){
                list.remove(position);
                saveData();
                updateTitle();
                listAdapter.notifyDataSetChanged();
            }

            //원소가 길게 클릭될경우 해당사진을 크게보여준다.
            public void longClickItem(int position){
                openPreview(position);
            }
        });
        recyclerView.setAdapter(listAdapter);
    }

    //롱클릭시 해당 사진부터 현재 업로드된 사진을 크게 보여준다.
    public void openPreview(int position){
        Intent it = new Intent(this, Helper_roomPicPreview.class);
        it.putExtra("list", list);
        it.putExtra("idx", position);
        startActivity(it);
    }

    //뒤로가기버튼시 현재 리스트를 Activity_editRoom에 리턴해준다.
    @Override
    public void onBackPressed(){
        Intent it = getIntent();
        it.putExtra("list", list);
        setResult(1, it);
        finish();
    }


    //메뉴에 사진찍기버튼과 갤러리에서 불러오기버튼을 추가해준다.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflater함수를 이용해서 menu 리소스를 menu로 변환.
        // 두 줄 코드
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity_editroom, menu);

        return super.onCreateOptionsMenu(menu);
    }


    //메뉴의 버튼 클릭시 처리
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //뒤로가기버튼시 현재 리스트를 Activity_editRoom에 리턴해준다.
            case android.R.id.home:
                Intent it = getIntent();
                it.putExtra("list", list);
                setResult(1, it);
                finish();
                return true;

            //카메라버튼을 눌렀을때 카메라를 오픈해 사진을 찍을수 있게해준다.
            case R.id.camera:
                // camera 이 눌렸을 경우 이벤트 발생
                if( list.size() > MAX_SIZE ){
                    Toast.makeText(Activity_editRoom_roomPic.this, "사진을 8개이상 등록 할 수 없습니다.", Toast.LENGTH_SHORT).show();
                }else {
                    mImagePicker.openCamera(new CallbackForCam());
                }
                return true;

            //갤러리버튼을 눌렀을때 갤러리를 불러와 사진을 선택할수있게해준다.
            case R.id.gallery:
                // gallery 이 눌렸을 경우 이벤트 발생
                if( list.size() > MAX_SIZE ){
                    Toast.makeText(Activity_editRoom_roomPic.this, "사진을 8개이상 등록 할 수 없습니다.", Toast.LENGTH_SHORT).show();
                }else {
                    mImagePicker.openImagePiker(true, new CallbackForGal());
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //카메라로 사진 찍었을 경우 호출
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

    //갤러리에서 사진 선택시 호출
    public class CallbackForGal implements CallbackForImagePicker{
        @Override
        public void onError(Exception error) {
        }
        @Override
        public void onComplete(List<String> imagePath) {
            list.addAll(imagePath);
            for(int i=MAX_SIZE; i<list.size(); i++){
                list.remove(i);
            }
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

    //저장을 안하고 종료했을경우 현재 상태를 저장한다.
    private void saveData(){
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
