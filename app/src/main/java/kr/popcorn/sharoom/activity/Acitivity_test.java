package kr.popcorn.sharoom.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import cz.msebera.android.httpclient.Header;
import kr.popcorn.sharoom.R;
import kr.popcorn.sharoom.helper.Helper_server;

/**
 * Created by Administrator on 2016-04-02.
 */
public class Acitivity_test extends Activity{

    private ImageView img;
    private static final String BASE_URL = "http://14.63.227.200/image/image.png";
    AsyncHttpClient client = Helper_server.getInstance();

    FileOutputStream outputStream;
    File file = new File("/sdcard/image.jpg");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tester);

        img  = (ImageView)findViewById(R.id.testImg);
        client.get(BASE_URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                // TODO Auto-generated method stub
                try {
                    outputStream = new FileOutputStream(file);
                    outputStream.write(arg2);
                    outputStream.close();
                    Log.e("TAGGG", " " + file.getFreeSpace());
                    Message msg = new Message();
                    Bundle d = new Bundle();
                    String path = file.getPath();
                    d.putString("imagePath", path);
                    d.putString("Date", arg1[0].toString());
                    d.putString("Type", arg1[1].toString());
                    d.putString("Size", arg1[2].toString());
                    msg.setData(d);
                    handler.sendMessage(msg);
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                                  Throwable arg3) {
                // TODO Auto-generated method stub
            }
        });
    }
    private Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            Bitmap bm = BitmapFactory.decodeFile(msg.getData().getString("imagePath"));
            img.setImageBitmap(bm);
        };
    };
}