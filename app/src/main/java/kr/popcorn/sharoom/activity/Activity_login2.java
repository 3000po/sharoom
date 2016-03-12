package kr.popcorn.sharoom.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.ibm.mobilefirstplatform.clientsdk.android.security.api.AuthorizationManager;
import com.loopj.android.http.*;

import cz.msebera.android.httpclient.Header;
import kr.popcorn.sharoom.R;
import kr.popcorn.sharoom.helper.Helper_server;

/**
 * Created by Administrator on 2016-03-11.
 */
public class Activity_login2 extends Activity {
    private String login_id = "";

    EditText et_id;
    EditText et_password;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login2); // 항상 제공되는
        // activity_layout.xml을

        // 만듦
        et_id = (EditText)findViewById(R.id.et_login_id);
        et_password = (EditText)findViewById(R.id.et_login_password);

        AsyncHttpClient client = Helper_server.getInstance();
        PersistentCookieStore myCookieStore = new PersistentCookieStore(this);
        client.setCookieStore(myCookieStore);


        et_password.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //Enter key Action
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(et_password.getWindowToken(), 0);    //hide keyboard

                    //login source
                    RequestParams params = new RequestParams();


                    return true;
                }
                return false;
            }
        });


        Button btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new Button.OnClickListener() {
                                         public void onClick(View v) {
                                             RequestParams params = new RequestParams();
                                             String id = et_id.getText().toString();
                                             String password = et_password.getText().toString();

                                             Log.i("Msg", "id : " + id + "pwd : " + password);
                                             params.put("id", id);
                                             params.put("password", password);


                                             Helper_server.get("", params, new AsyncHttpResponseHandler() {
                                                 @Override

                                                 public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                                     Log.i("Msg", "success");
                                                 }

                                                 @Override
                                                 public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                                     Log.i("Msg", "fali");
                                                 }
                                             });
                                     }


        });
    }
}