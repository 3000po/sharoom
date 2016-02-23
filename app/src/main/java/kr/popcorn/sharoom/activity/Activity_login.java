package kr.popcorn.sharoom.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import kr.popcorn.sharoom.R;

/**
 * Created by Administrator on 2016-02-22.
 */

//TODO 입력이 제대로 됐는지 체크

public class Activity_login extends Activity{
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

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.i("bhc :",
                        "User ID: "
                                + loginResult.getAccessToken().getUserId()
                                + "\n" +
                                "Auth Token: "
                                + loginResult.getAccessToken().getToken()
                );
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

        et_password.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //Enter key Action
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow( et_password.getWindowToken(), 0);    //hide keyboard

                    if (isNetworkAvailable()) {
                        et_id = (EditText)v.findViewById(R.id.et_login_id);
                        et_password = (EditText)v.findViewById(R.id.et_login_password);

                        String id = et_id.getText().toString();
                        String password = et_password.getText().toString();
                        login_id = id;
                        String url = "http://14.63.223.92/login.php?id=" + id + "&password=" + password; //서버전송쿼리
                        phpTask phpTask = new phpTask();
                        phpTask.execute(url);

                        et_id.setText("");
                        et_password.setText("");
                    }
                    return true;
                }
                return false;
            }
        });


        Button btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if (isNetworkAvailable()) {
                    String id = et_id.getText().toString();
                    String password = et_password.getText().toString();
                    login_id = id;
                    String url = "http://14.63.223.92/login.php?id=" + id + "&password=" + password; //서버전송쿼리
                    phpTask phpTask = new phpTask();
                    phpTask.execute(url);

                }
            }
        });

        Button btn_join = (Button) findViewById(R.id.btn_join);
        btn_join.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_login.this, Activity_join.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private boolean isNetworkAvailable(){
        boolean available = false;
        ConnectivityManager connMgr= (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if(networkInfo != null & networkInfo.isAvailable())
            available = true;

        return available;
    }

    private String downloadUrl(String php) throws IOException {
        StringBuilder jsonHtml = new StringBuilder();
        InputStream iStream = null;
        String line="";
        String ok = "";
        BufferedReader br = null;

        try {
            //연결 url설정
            URL url = new URL(php);
            //connection 객체 설정
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();

            iStream = urlConnection.getInputStream();
            //urlConnection.setConnectTimeout(10000);
            //urlConnection.setUseCaches(false);
            //연결되었음 코드가 리턴되면
            br = new BufferedReader(new InputStreamReader(iStream));
            while((line=br.readLine()) != null){
                jsonHtml.append(line + "\n");
            }

            br.close();
            ok = jsonHtml.toString();
            iStream.close();
            urlConnection.disconnect();

        } catch(Exception e){
            Log.d("Exception ", e.toString());
        }

        return ok;

    }

    private class phpTask extends AsyncTask<String, Integer, String> {

        protected String doInBackground(String... url){

            String ok = "";
            try{
                Log.d("url",url[0].toString());
                ok = downloadUrl(url[0]);
            }
            catch(Exception e){
                Log.d("Background Task", e.toString());
            }
            return ok;
        }

        protected void onPostExecute(String result){

            String ok="";


            try{

                JSONObject root = new JSONObject(result);
                //JSONArray json_arr = root.getJSONArray("ok");

                ok = root.getString("ok");
                Log.d("aaa", ok);
                //for(int i=0; i<json_arr.length(); i++){
                //    JSONObject sub = json_arr.getJSONObject(i);
                //    ok = sub.getString("imgurl");
                //}


                AlertDialog.Builder alert = new AlertDialog.Builder(Activity_login.this);
                if(ok.equals("true")) {
                    alert.setTitle("로그인");
                    alert.setMessage(ok);
                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Activity_login.this, Activity_calendar.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                            startActivity(intent);
                            finish();
                        }
                    });
                    alert.show();
                }
                else{
                    alert.setTitle("로그인");
                    alert.setMessage(ok);
                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alert.show();

                }

            }catch(JSONException e){
                e.printStackTrace();
            }

        }
    }


    @Override
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
}
