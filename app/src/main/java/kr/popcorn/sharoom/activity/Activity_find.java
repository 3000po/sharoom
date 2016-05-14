package kr.popcorn.sharoom.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.appevents.AppEventsLogger;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import kr.popcorn.sharoom.R;
import kr.popcorn.sharoom.helper.Helper_checker;
import kr.popcorn.sharoom.helper.Helper_find;
import kr.popcorn.sharoom.helper.Helper_server;

/**
 * Created by Administrator on 2016-05-14.
 */
public class Activity_find extends Activity {

    String email = "whrltkd789@naver.com";

    private Button btn_id_find;
    private Button btn_password_find;
    private EditText et_find_id_email;
    private EditText et_find_password_id;
    private EditText et_find_password_email;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_find); // 항상 제공되는

        et_find_id_email = (EditText) findViewById(R.id.et_find_id_email);
        et_find_password_id = (EditText) findViewById(R.id.et_find_password_id);
        et_find_password_email = (EditText) findViewById(R.id.et_find_password_email);


        btn_id_find = (Button) findViewById(R.id.btn_find_id);
        btn_id_find.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {

                RequestParams emailParams = new RequestParams();
                final String email = et_find_id_email.getText().toString();

                emailParams.put("email", email);

                Helper_server.post("findId.php", emailParams, new JsonHttpResponseHandler() {
                    @Override

                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        Log.i("Msg", "success");
                        String data = "";
                        String str = "";
                        try {
                            data = response.get("id").toString();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("findId", "" + data);
                        if (data.equals("no")) {
                            str = "이메일에 일치하는 아이디가 존재하지 않습니다.";
                        } else {
                            str = "이메일에 일치하는 아이디는 [ "+data+" ] 입니다.";
                        }
                        id_Alert(str);
                    }
                });

            }
        });


        btn_password_find = (Button) findViewById(R.id.btn_find_password);


    }
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }

    public void id_Alert(String str) {
        AlertDialog.Builder alert_id = new AlertDialog.Builder(this);
        alert_id.setTitle("아이디 찾기 결과")
                .setMessage(str)
                .setCancelable(true)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        et_find_id_email.clearComposingText();;
                    }
                })
                .show();

        AlertDialog alert = alert_id.create();
        alert.show();
    }




    public void password_Alert() {
        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(Activity_find.this);
        alert_confirm.setMessage("이메일인증을 하시겠습니까?").setCancelable(false).setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Helper_find help= new Helper_find(email);
                    }
                }).setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 'No'
                        return;
                    }
                });
        AlertDialog alert = alert_confirm.create();
        alert.show();
    }

    public void Helper_find(String id){






    }

}
