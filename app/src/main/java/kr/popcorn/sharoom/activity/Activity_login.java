package kr.popcorn.sharoom.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

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
import com.kakao.auth.AuthType;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;
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
import kr.popcorn.sharoom.activity.Fragment.User.Activity_user_view;
import kr.popcorn.sharoom.helper.Helper_server;
import kr.popcorn.sharoom.helper.Helper_userData;

public class Activity_login extends Activity {
    private String login_id = "";

    EditText et_id;
    EditText et_password;

    //페이스북 로그인, 콜백
    private LoginButton loginButton;
    private CallbackManager callbackManager;

    //카카오톡 관련
    private ImageView kakaoButton;
    private SessionCallback mKakaocallback; //카카오톡 로그인 콜백
    private String userName;
    private String userId;
    private RelativeLayout layoutIdPassword;

    public PermissionListener permissionlistener;
    public TedPermission ted;
    public String phoneNum;

    public void openActivity(){
        Intent intent = new Intent(Activity_login.this, Activity_user_view.class);
        Activity_mainIntro activity = (Activity_mainIntro) Activity_mainIntro.mActivity;
        Helper_server.userData = Helper_userData.getInstance(getApplicationContext());

        startActivity(intent);
        finish();
        activity.finish();
    }

    public void getPermission(){
        ted = new TedPermission(getApplication());

        ted.setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.READ_PHONE_STATE)
                .check();
    }

    //카카오톡 세션콜백
    private class SessionCallback implements ISessionCallback {
        @Override
        public void onSessionOpened() {
            Log.d("TAG", "세션 오픈됨");
            // 사용자 정보를 가져옴, 회원가입 미가입시 자동가입 시킴
            KakaorequestMe();
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if (exception != null) {
                Log.d("TAG", exception.getMessage());
            }
        }
    }

    //카카오톡 요청
    protected void KakaorequestMe() {
        UserManagement.requestMe(new MeResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                int ErrorCode = errorResult.getErrorCode();
                int ClientErrorCode = -777;

                if (ErrorCode == ClientErrorCode) {
                    Toast.makeText(getApplicationContext(), "카카오톡 서버의 네트워크가 불안정합니다. 잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("TAG", "오류로 카카오로그인 실패 ");
                }
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Log.d("TAG", "오류로 카카오로그인 실패 ");
            }

            @Override
            public void onSuccess(UserProfile userProfile) {
                getPermission();
                userId = String.valueOf(userProfile.getId());
                userName = userProfile.getNickname();

                if( phoneNum == null ) return ;

                final RequestParams idParams = new RequestParams("ktid", userId);
                idParams.put("name", userName);
                idParams.put("phone", phoneNum);
                Helper_server.post("ktCheck.php", idParams, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        Log.i("abde", "success");
                        String data = "";
                        try {
                            data = response.get("ok").toString();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (data.equals("true")) {  //카카오톡 가입이 안되있을경우
                            Helper_server.post("kakaotalk.php", idParams, new AsyncHttpResponseHandler() {
                                @Override

                                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                    openActivity();
                                }

                                @Override
                                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                    Log.i("abde", "fail");
                                }
                            });
                            return;

                        } else {
                            openActivity();
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
            public void onNotSignedUp() {
                // 자동가입이 아닐경우 동의창
            }
        });
    }

    //카카오톡 로그인
    private void isKakaoLogin() {
        // 카카오 세션을 오픈한다
        mKakaocallback = new SessionCallback();
        Session.getCurrentSession().addCallback(mKakaocallback);
        Session.getCurrentSession().checkAndImplicitOpen();
        Session.getCurrentSession().open(AuthType.KAKAO_TALK_EXCLUDE_NATIVE_LOGIN, Activity_login.this);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //페이스북 로그인 SDK초기화 SetContentView전에 해줘야한다.
        //더불어 콜백 매니저도 생성한다.
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_login); // 항상 제공되는
        // activity_layout.xml을

        loginButton = (LoginButton) findViewById(R.id.login_button);
        kakaoButton = (ImageView) findViewById(R.id.kakao_login);
        kakaoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 카카오 로그인 요청
                isKakaoLogin();
            }
        });

        permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                TelephonyManager tMgr = (TelephonyManager)getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
                phoneNum = tMgr.getLine1Number();
            }
            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Toast.makeText(Activity_login.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };

        //
        //loginButton.setPublishPermissions(Arrays.asList("public_profile", "user_friends", "email"));
        //LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile","user_friends","email"));
        loginButton.setReadPermissions(Arrays.asList("public_profile", "user_friends", "email"));
        loginButton.setBackgroundResource(R.drawable.facebookbtn);
        loginButton.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        loginButton.setText("");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                getPermission();
                //TODO 전화번호 인증모듈 띄우기
                final String id = loginResult.getAccessToken().getUserId();
                final RequestParams idParams = new RequestParams("fbid", id);

                if( phoneNum == null ) return ;

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
                                                Log.e("JSON", response.toString());
                                                JSONObject data = response.getJSONObject();

                                                String id = data.getString("id");
                                                String name = data.getString("name");
                                                String email = data.getString("email");
                                                String gender = data.getString("gender");
                                                String phone = phoneNum;

                                                RequestParams params = new RequestParams();
                                                params.put("id", id);
                                                params.put("name", name);
                                                params.put("email", email);
                                                if (gender.equals("male") == true) {
                                                    params.put("gender", 1);
                                                } else {
                                                    params.put("gender", 2);
                                                }
                                                params.put("phone",phone);

                                                Helper_server.post("facebook.php", params, new AsyncHttpResponseHandler() {
                                                    @Override

                                                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                                        openActivity();
                                                    }

                                                    @Override
                                                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                                        Log.i("abde", "fail");
                                                    }
                                                });

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                            ).executeAsync();

                            return;
                        } else {
                            openActivity();
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
                Log.i("bhc :", "Login attempt canceled.");
            }

            @Override
            public void onError(FacebookException e) {
                Log.i("bhc :", "Login attempt failed.");
            }
        });

        // 만듦
        et_id = (EditText) findViewById(R.id.et_login_id);
        et_password = (EditText) findViewById(R.id.et_login_password);
        layoutIdPassword = (RelativeLayout) findViewById(R.id.layout_idpassword);


        AsyncHttpClient client = Helper_server.getInstance();
        final PersistentCookieStore myCookieStore = new PersistentCookieStore(this);
        client.setCookieStore(myCookieStore);

        //자동 로그인 파트.
        if (Helper_server.login(myCookieStore)) {
            Log.i("abde", "what the!! ");
            openActivity();
        }else if( Session.getCurrentSession().isOpened() ) {
            openActivity();
        }else
        { //페이스북 자동로그인 파트
            AccessToken accessToken = AccessToken.getCurrentAccessToken();
            if (accessToken == null) {
                Log.d("abde", ">>>" + "Signed Out");
            } else {
                Log.d("abde", ">>>" + "Signed In");
                openActivity();
            }
        }

        et_id.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                                           @Override
                                           public void onFocusChange(View v, boolean hasFocus) {
                                               if (hasFocus) {
                                                   et_id.setHint("");
                                                   InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                                                   imm.showSoftInput(et_id, InputMethodManager.SHOW_IMPLICIT);
                                               } else
                                                   et_id.setHint("ID 혹은 이메일 입력");
                                           }
                                       }
        );

        et_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    et_password.setHint("");
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(et_password, InputMethodManager.SHOW_IMPLICIT);

                } else
                    et_password.setHint("비밀번호 입력");
            }
        });
        et_id.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(et_id.getWindowToken(), 0);    //hide keyboard
                    return true;
                }
                return false;
            }
        });

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

        layoutIdPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {

                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(et_password.getWindowToken(), 0);    //hide keyboard
                    return true;
                }
                return false;
            }
        });

        final CheckBox check = (CheckBox) findViewById(R.id.ck_autoLogin);

        //login buton click

        ImageView btn_login = (ImageView) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new ImageView.OnClickListener()

                                     {
                                         public void onClick(View v) {
                                             RequestParams params = new RequestParams();
                                             final String id = et_id.getText().toString();
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
                                                         data = response.get("phpsession").toString();
                                                     } catch(JSONException e){
                                                         e.printStackTrace();
                                                     }
                                                     Log.d("phpsession", "" + data);
                                                     if(!data.equals("no")){

                                                         BasicClientCookie newCookie = new BasicClientCookie("login_session", data);
                                                         newCookie.setVersion(1);
                                                         newCookie.setDomain("14.63.227.200");
                                                         newCookie.setPath("/");
                                                         myCookieStore.addCookie(newCookie);
                                                         if(check.isChecked()){
                                                             newCookie = new BasicClientCookie("isLogin", "true");
                                                             newCookie.setVersion(1);
                                                             newCookie.setDomain("14.63.227.200");
                                                             newCookie.setPath("/");
                                                             myCookieStore.addCookie(newCookie);
                                                         }

                                                         Activity_mainIntro activity = (Activity_mainIntro) Activity_mainIntro.mActivity;
                                                         Intent intent = new Intent(Activity_login.this, Activity_user_view.class);
                                                         Helper_userData user = new Helper_userData();
                                                         user.getInstance(id, getApplicationContext());

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


      /*  Button btn_join = (Button) findViewById(R.id.btn_join);
        btn_join.setOnClickListener(new Button.OnClickListener(){
                                        public void onClick(View v) {

                                            Intent intent = new Intent(Activity_login.this, Activity_join.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                            startActivity(intent);

                                        }
                                    }

        );*/
        ImageView joinBtn = (ImageView) findViewById(R.id.iv_join);
        joinBtn.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent joinIntent = new Intent(Activity_login.this, Activity_join.class);
                startActivity(joinIntent);

            }
        });


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

    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //페이스북 로그인
        callbackManager.onActivityResult(requestCode, resultCode, data);

        //카카오톡 로그인
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}