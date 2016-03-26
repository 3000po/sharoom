package kr.popcorn.sharoom.helper;

import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Administrator on 2016-03-26.
 */
public class Helper_emailCertification {

    private int random_number;

    public Helper_emailCertification(String email) {

        randomNumber_create();

        RequestParams emailParams = new RequestParams();
        emailParams.put("email", email);
        emailParams.put("webmailKey", random_number);

        Helper_server.post("email.php", emailParams, new AsyncHttpResponseHandler() {
            @Override

            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.i("Msg", "success:" + random_number);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.i("Msg", "fali");
            }

        });

    }

    private void randomNumber_create() {
        Random rand = new Random(System.currentTimeMillis()); // seed값을 배정하여 생성
        System.out.println(Math.abs(rand.nextInt(10)));                //0~10사이의 난수값생성
        this.random_number = Math.abs(rand.nextInt(899999) + 100000);
    }

    private int randomNumber_get(){
        return random_number;
    }


}