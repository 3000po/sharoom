package kr.popcorn.sharoom.activity.View.Host;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import kr.popcorn.sharoom.R;
import kr.popcorn.sharoom.helper.Helper_server;
import me.yokeyword.imagepicker.ImagePicker;
import me.yokeyword.imagepicker.callback.CallbackForCamera;
import me.yokeyword.imagepicker.callback.CallbackForImagePicker;

/**
 * Created by user on 16. 3. 12.
 */


//방을 등록하기 위한 액티비티
public class Activity_host_registerRoom extends Activity  implements View.OnClickListener{
    public final int MAX_SIZE=7;
    public final int PICK_THE_ALBUM=1;

    private ArrayList<String> list;
    private ImagePicker mImagePicker;

    private Dialog dialog;

    private ImageButton picButton;
    private ImageButton dialogCam;
    private ImageButton dialogGal;
    public TextView tv_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editroom);


        //사진 클릭시 카메라와 갤러리에서 고를수있게 지원해주는 다이얼로그
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_editroom_dialog);

        picButton = (ImageButton) findViewById(R.id.picture);

        //다이얼로그의 카메라와 갤러리 버튼
        dialogCam = (ImageButton) dialog.findViewById(R.id.camera);
        dialogGal = (ImageButton) dialog.findViewById(R.id.gallery);

        picButton.setOnClickListener(this);
        dialogCam.setOnClickListener(this);
        dialogGal.setOnClickListener(this);

        mImagePicker = new ImagePicker(this);
        loadData();

        tv_register = (TextView) findViewById(R.id.bottomtext);
        tv_register.setOnClickListener(new TextView.OnClickListener(){
            public void onClick(View v) {
                Log.d("buttonClick", "okokokokok");
                for(int i=0; i<list.size(); i++){
                    Log.d("buttonList", list.get(i));
                }
                    postImage(list);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            //방 사진을 눌렀을때 처리
            case R.id.picture:
                //방에 사진이 하나도 없을경우 다이얼로그를 띄워서 카메라와 갤러리를 고를수있게해줌
                if(list.size() == 0 ) dialog.show();
                else{   //사진이 하나이상 있을경우 사진편집 액티비티를 띄워서 그곳에서 방사진을 편집하게끔한다.
                    openActivity();
                }
                break;
            case R.id.camera:
                dialog.dismiss();
                mImagePicker.openCamera(new CallbackForCamera() {
                    @Override
                    public void onError(Exception error) {

                    }
                    @Override
                    public void onComplete(String imagePath) {
                        list.add(imagePath);
                        openActivity();
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
                break;
            case R.id.gallery:
                dialog.dismiss();
                mImagePicker.openImagePiker(true, new CallbackForImagePicker() {
                    @Override
                    public void onError(Exception error) {
                        Log.i("aab", "error");
                    }
                    @Override
                    public void onComplete(List<String> imagePath) {
                        list.addAll(imagePath);
                        for(int i=MAX_SIZE; i<list.size(); i++){
                            list.remove(i);
                        }
                        openActivity();
                    }
                });
                break;
        }
    }
    public void openActivity(){
        Intent it = new Intent(this, Activity_host_registerRoom_roomPic.class);
        it.putExtra("list", list);
        startActivityForResult(it, PICK_THE_ALBUM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode){
            case PICK_THE_ALBUM:
                list = data.getStringArrayListExtra("list");
                saveData();
                break;
        }

        mImagePicker.delegateActivityResult(requestCode, resultCode, data);
    }



    @Override
    public void onResume(){
        super.onResume();
        loadData();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveData();
    }

    private void saveData(){
        // 특정번호의 공유저장소를 편집가능 상태로 불러온다.
        SharedPreferences.Editor edt = getSharedPreferences("room",0).edit();

        // 저장
        edt.putInt("picCount", list.size());
        for(int i=0; i<list.size(); i++){
            edt.putString("pic" + i, list.get(i));
        }

        // 수행
        edt.commit();
    }

    private void loadData(){
        // 저장소 객체를 생성
        SharedPreferences prefs = getSharedPreferences("room", 0);

        // 로드
        int size = prefs.getInt("picCount",0);
        ArrayList<String> arrayList = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            arrayList.add(prefs.getString("pic" + i, null));
        }

        list = arrayList;

        if( list.size() > 0 ) {
            picButton.setImageURI( Uri.fromFile( new File(list.get(0))));
        }else{
            picButton.setImageResource(R.drawable.roompicture);
        }
    }


    public static void postImage(ArrayList<String> list){
        RequestParams params = new RequestParams();
            params.put("size", list.size());
            for (int i = 0; i < list.size(); i++) {
                System.out.println("sibalbalblabl_imageLink : " + list.get(i));
                String imagePath =list.get(i);
                File f = new File(imagePath);
                System.out.println("sibalbalImagePath : " + imagePath);

            try{
                params.put("file" + i, f);
                //params.put("path", "aaa");
               }
                catch(FileNotFoundException e){
                    System.out.println("sibalbal fileNotFound");
                }
            }

        Helper_server.post("image/save1.php", params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                System.out.println("statusCode "+statusCode);//statusCode 200
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("sibalbalblabl_onFailure");
            }
        });
    }
}

