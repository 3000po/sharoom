package kr.popcorn.sharoom.activity;


import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import kr.popcorn.sharoom.R;
import me.yokeyword.imagepicker.ImagePicker;
import me.yokeyword.imagepicker.callback.CallbackForCamera;
import me.yokeyword.imagepicker.callback.CallbackForImagePicker;

/**
 * Created by user on 16. 3. 12.
 */
public class Activity_editRoom extends Activity  implements View.OnClickListener{

    public final int PICK_THE_ALBUM=1;

    private List<String> list;
    private ImagePicker mImagePicker;

    private Dialog dialog;

    private ImageButton picButton;
    private ImageButton dialogCam;
    private ImageButton dialogGal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editroom);

        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_editroom_dialog);
        picButton = (ImageButton) findViewById(R.id.picture);
        dialogCam = (ImageButton) dialog.findViewById(R.id.camera);
        dialogGal = (ImageButton) dialog.findViewById(R.id.gallery);

        picButton.setOnClickListener(this);
        dialogCam.setOnClickListener(this);
        dialogGal.setOnClickListener(this);

        mImagePicker = new ImagePicker(this);
        list = new ArrayList<>();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.picture:
                if(list.size() == 0 ) dialog.show();
                else{
                    //TODO 새액티비티 띄워서 거기서 리스트보여주고 삭제가능하게하고 사진 추가(카메라,앨범) 가능하게해야함
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

                    }

                    @Override
                    public void onComplete(List<String> imagePath) {
                        list.addAll(imagePath);
                    }
                });
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mImagePicker.delegateActivityResult(requestCode, resultCode, data);
    }
}

