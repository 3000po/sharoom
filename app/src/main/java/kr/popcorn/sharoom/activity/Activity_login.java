package kr.popcorn.sharoom.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.impl.cookie.BasicClientCookie;
import kr.popcorn.sharoom.R;
import kr.popcorn.sharoom.activity.Fragment.User.Activity_group_view;
import kr.popcorn.sharoom.helper.Helper_server;
import kr.popcorn.sharoom.helper.Helper_userData;

/**
 * Created by Administrator on 2016-03-11.
 */
public class Activity_login extends Activity {
    private String login_id = "";

    EditText et_id;
    EditText et_password;

    private LoginButton loginButton;
    private CallbackManager callbackManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_login); // 항상 제공되는
        // activity_layout.xml을

        loginButton = (LoginButton)findViewById(R.id.login_button);

        //loginButton.setPublishPermissions(Arrays.asList("public_profile", "user_friends", "email"));
        //LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile","user_friends","email"));
        loginButton.setReadPermissions(Arrays.asList("public_profile", "user_friends","email"));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {

                //TODO 전화번호 인증모듈 띄우기
                final String id = loginResult.getAccessToken().getUserId();
                final RequestParams idParams = new RequestParams("fbid", id);

                Helper_server.post("fbCheck.php", idParams, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        Log.i("abde", "success");
                        String data = "";
                        try {
                            data = response.get("ok").toString();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("ok", "" + data);
                        if (data.equals("true")) {  //페북 가입이 안되있을경우
                            Bundle params = new Bundle();
                            params.putString("fields", "id,name,email,gender");
                            new GraphRequest(
                                    AccessToken.getCurrentAccessToken(), //loginResult.getAccessToken(),
                                    "/me",
                                    params,
                                    HttpMethod.GET,
                                    new GraphRequest.Callback() {
                                        public void onCompleted(GraphResponse response) {
                                            try {
                                                Log.e("JSON",response.toString());
                                                JSONObject data = response.getJSONObject();

                                                String id = data.getString("id");
                                                String name = data.getString("name");
                                                String email = data.getString("email");
                                                String gender = data.getString("gender");

                                                RequestParams params = new RequestParams();
                                                params.put("id", id);
                                                params.put("name", name);
                                                params.put("email", email);
                                                if( gender.equals("male") == true ){
                                                    params.put("gender", 1);
                                                }else{
                                                    params.put("gender", 2);
                                                }

                                                Helper_server.post("facebook.php", params, new AsyncHttpResponseHandler() {
                                                    @Override

                                                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                                        Intent intent = new Intent(Activity_login.this, Activity_group_view.class);

                                                        Helper_server.userData = Helper_userData.getInstance(getApplicationContext());

                                                        startActivity(intent);
                                                        finish();
                                                    }

                                                    @Override
                                                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                                        Log.i("abde", "fail");
                                                    }
                                                });

                                            } catch (Exception e){
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                            ).executeAsync();

                            return;
                        } else {
                            Intent intent = new Intent(Activity_login.this, Activity_group_view.class);

                            Helper_server.userData = Helper_userData.getInstance(getApplicationContext());

                            startActivity(intent);
                            finish();
                            return;
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                        Log.d("Failed: ", "" + statusCode);
                        Log.d("Error : ", "" + throwable);
                    }
                });
            }

            @Override
            public void onCancel() {
                Log.i("bhc :","Login attempt canceled.");
            }

            @Override
            public void onError(FacebookException e) {
                Log.i("bhc :", "Login attempt failed.");
            }
        });

        // 만듦
        et_id = (EditText)findViewById(R.id.et_login_id);
        et_password = (EditText)findViewById(R.id.et_login_password);

        AsyncHttpClient client = Helper_server.getInstance();
        final PersistentCookieStore myCookieStore = new PersistentCookieStore(this);
        client.setCookieStore(myCookieStore);

        //자동 로그인 파트.
        if (Helper_server.login(myCookieStore)) {
            Log.i("abde", "what the!! ");
            Intent intent = new Intent(Activity_login.this, Activity_group_view.class);

            Helper_server.userData = Helper_userData.getInstance(getApplicationContext());

            startActivity(intent);
            finish();
        }else{ //페이스북 자동로그인 파트
            AccessToken accessToken = AccessToken.getCurrentAccessToken();
            if (accessToken == null) {
                Log.d("abde", ">>>" + "Signed Out");
            } else {
                Log.d("abde", ">>>" + "Signed In");
                Intent intent = new Intent(Activity_login.this, Activity_group_view.class);

                Helper_server.userData = Helper_userData.getInstance(getApplicationContext());

                startActivity(intent);
                finish();
            }
        }

        et_password.setOnKeyListener(new View.OnKeyListener() {

                                         @Override
                                         public boolean onKey(View v, int keyCode, KeyEvent event) {
                                             //Enter key Action
                                             if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                                                 InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                                                 imm.hideSoftInputFromWindow(et_password.getWindowToken(), 0);    //hide keyboard
                                                 return true;
                                             }
                                             return false;
                                         }
                                     }

        );

        //login buton click
        Button btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new Button.OnClickListener()

                                     {
                                         public void onClick(View v) {
                                             RequestParams params = new RequestParams();
                                             String id = et_id.getText().toString();
                                             String password = et_password.getText().toString();

                                             //put params
                                             params.put("id", id);
                                             params.put("password", password);
                                             //server connect
                                             Helper_server.post("login.php", params,  new JsonHttpResponseHandler() {
                                                 @Override

                                                 public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                                     Log.i("abde", "success");
                                                     String data="";
                                                     try{
                                                         data = response.get("ok").toString();
                                                     } catch(JSONException e){
                                                         e.printStackTrace();
                                                     }
                                                     Log.d("ok", "" + data);
                                                     if(data.equals("true")){

                                                         BasicClientCookie newCookie = new BasicClientCookie("login_cookie", "id");
                                                         newCookie.setVersion(1);
                                                         newCookie.setDomain("14.63.227.200");
                                                         newCookie.setPath("/");
                                                         myCookieStore.addCookie(newCookie);

                                                         Intent intent = new Intent(Activity_login.this, Activity_group_view.class);
                                                         startActivity(intent);
                                                         finish();

                                                     }
                                                     else{
                                                         loginAlert();
                                                     }
                                                 }

                                                 @Override
                                                 public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                                     super.onFailure(statusCode, headers, responseString, throwable);
                                                     Log.d("Failed: ", ""+statusCode);
                                                     Log.d("Error : ", "" + throwable);
                                                 }
                                             });
                                         }


                                     }
        );

        Button btn_join = (Button) findViewById(R.id.btn_join);
        btn_join.setOnClickListener(new Button.OnClickListener(){
                                        public void onClick(View v) {

                                            Intent intent = new Intent(Activity_login.this, Activity_join.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                            startActivity(intent);

                                        }
                                    }

        );
    }//onCreateEnd

    public void loginAlert() {
        AlertDialog.Builder alert = new AlertDialog.Builder(Activity_login.this);
        alert.setTitle("로그인 실패");
        alert.setMessage("아이디 혹은 비밀번호가 확인해주세요 ");
        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                et_id.setText(null);
                et_password.setText(null);
            }
        });
        alert.show();
    }

    public void onBackPressed(){
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);

        super.onActivityResult(requestCode, resultCode, data);
    }
}