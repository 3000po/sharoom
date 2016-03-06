package kr.popcorn.sharoom.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.appevents.AppEventsLogger;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import kr.popcorn.sharoom.activity.Activity_login;
import kr.popcorn.sharoom.R;
import kr.popcorn.sharoom.helper.Helper_checker;
/**
 * Created by Administrator on 2016-02-22.
 */


//TODO 입력이 제대로 됐는지 체크
public class Activity_join extends Activity {

    boolean id_check_ok = false;
    boolean join_flag = false;

    private form_basic form_basic;
    class form_basic {
        EditText et_id;
        EditText et_password;
        EditText et_name;
        EditText et_phoneNumber;
        EditText et_email;

        Button btn_submit;
        Button btn_idcheck;

    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_join); // 항상 제공되는
        // activity_layout.xml을 복사해서
        // 만듦
        final form_basic form_basic = new form_basic();

        form_basic.et_id = (EditText) findViewById(R.id.et_join_id);
        form_basic.et_password = (EditText) findViewById(R.id.et_join_password);
        form_basic.et_name = (EditText) findViewById(R.id.et_join_name);
        form_basic.et_phoneNumber = (EditText) findViewById(R.id.et_join_phoneNumber);
        form_basic.et_email = (EditText) findViewById(R.id.et_join_email);

        form_basic.btn_submit = (Button) findViewById(R.id.btn_join_submit);
        form_basic.btn_idcheck = (Button) findViewById(R.id.btn_join_idcheck);
        //this.overridePendingTransition( R.anim.anim_slide_in_left, R.anim.anim_slide_in_right);

        form_basic.et_id.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                id_check_ok = false;
                Log.d("check","false");
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


            View.OnClickListener downloadListener = new View.OnClickListener() {
                public void onClick(View v) {
                    if (isNetworkAvailable()) {
                        String id = form_basic.et_id.getText().toString();
                        String password = form_basic.et_password.getText().toString();
                        String name = form_basic.et_name.getText().toString();
                        String phoneNumber = form_basic.et_phoneNumber.getText().toString();
                        String email = form_basic.et_email.getText().toString();

                        if (!Helper_checker.validJoin(Activity_join.this, email, name, id, password)) {
                            return;
                        }

                        if (!Helper_checker.id_check_ok(Activity_join.this, id_check_ok)) {
                            return;
                        }

                        id = urlEncodeUTF8(id);
                        password = urlEncodeUTF8(password);
                        name = urlEncodeUTF8(name);
                        phoneNumber = urlEncodeUTF8(phoneNumber);
                        email = urlEncodeUTF8(email);

                        String url = "http://14.63.223.92/member_Insert.php?id=" + id + "&password=" + password + "&name=" + name + "&phoneNumber=" + phoneNumber + "&email=" + email;
                        join_flag = true;
                        phpTask phpTask = new phpTask();
                        phpTask.execute(url);
                    }
                }
                // Codes.InitApp(this); // *
            };

            View.OnClickListener idCheck = new View.OnClickListener() {
                public void onClick(View v) {
                    if (isNetworkAvailable()) {
                        String id = form_basic.et_id.getText().toString();

                        if (!Helper_checker.validId_context(Activity_join.this, id)) {
                            return;
                        }

                        id = urlEncodeUTF8(id);

                        String url = "http://14.63.223.92/member_idcheck.php?id=" + id;
                        phpTask phpTask = new phpTask();
                        phpTask.execute(url);
                    }
                }
            };


            form_basic.btn_submit.setOnClickListener(downloadListener);
            form_basic.btn_idcheck.setOnClickListener(idCheck);

            //email 입력후 엔터눌렀을때 회원가입 처리
            form_basic.et_email.setOnKeyListener(new View.OnKeyListener()

            {
                @Override
                public boolean onKey (View v,int keyCode, KeyEvent event){
                //Enter key Action
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(form_basic.et_email.getWindowToken(), 0);    //hide keyboard

                    if (isNetworkAvailable()) {
                        String id = form_basic.et_id.getText().toString();
                        String password = form_basic.et_password.getText().toString();
                        String name = form_basic.et_name.getText().toString();
                        String phoneNumber = form_basic.et_phoneNumber.getText().toString();
                        String email = form_basic.et_email.getText().toString();

                        if (!Helper_checker.validJoin(Activity_join.this, email, name, id, password)) {
                            return false;
                        }

                        if (!Helper_checker.id_check_ok(Activity_join.this, id_check_ok)) {
                            return false;
                        }

                        id = urlEncodeUTF8(id);
                        password = urlEncodeUTF8(password);
                        name = urlEncodeUTF8(name);
                        phoneNumber = urlEncodeUTF8(phoneNumber);
                        email = urlEncodeUTF8(email);

                        String url = "http://14.63.223.92/member_Insert.php?id=" + id + "&password=" + password + "&name=" + name + "&phoneNumber=" + phoneNumber + "&email=" + email;
                        join_flag = true;
                        phpTask phpTask = new phpTask();
                        phpTask.execute(url);
                    }
                    return true;
                }
                return false;
            }
            }

            );
        }

    private boolean isNetworkAvailable(){
        boolean available = false;
        ConnectivityManager connMgr= (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if(networkInfo != null & networkInfo.isAvailable())
            available = true;

        return available;
    }

    private void downloadUrl(String php) throws IOException {
        StringBuilder jsonHtml = new StringBuilder();
        InputStream iStream = null;
        String line="";
        String id = "";
        BufferedReader br = null;
        Boolean ok = false;

        try {
            URL url = new URL(php);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            iStream = urlConnection.getInputStream();

            if(id_check_ok == false) {
                br = new BufferedReader(new InputStreamReader(iStream));
                while ((line = br.readLine()) != null) {
                    jsonHtml.append(line + "\n");
                }

                br.close();
                id = jsonHtml.toString();
                iStream.close();
                urlConnection.disconnect();
                JSONObject root = new JSONObject(id);
                Log.d("a2222",id);
                id = root.getString("id");

                if(id.equals("valid")) {
                    id_check_ok = true;
                    Log.d("aaaaa",id);
                }
                else if(id.equals("invalid")){
                    id_check_ok = false;
                    Log.d("aaaaa", id);
                }
            }
        } catch(Exception e){
            Log.d("Exception ", e.toString());

        }
    }

    private class phpTask extends AsyncTask<String, Integer, String> {
        protected String doInBackground(String... url) {
            try {
                Log.d("url", url[0].toString());
                downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return null;
        }

        protected void onPostExecute(String result) {

            if (join_flag == true && id_check_ok == true) {
                AlertDialog.Builder alert = new AlertDialog.Builder(Activity_join.this);
                alert.setTitle("성공");
                alert.setMessage("가입이 완료되셨습니다.");
                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Activity_join.this, Activity_login.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(intent);
                        finish();
                    }
                });
                alert.show();
            }
            else if(join_flag == true && id_check_ok == false){
                AlertDialog.Builder alert = new AlertDialog.Builder(Activity_join.this);
                alert.setTitle("가입");
                alert.setMessage("아이디 중복검사를 하지 않았습니다.");
                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                alert.show();
            }

            else if(id_check_ok == false){
                AlertDialog.Builder alert = new AlertDialog.Builder(Activity_join.this);
                alert.setTitle("ID검사");
                alert.setMessage("사용할수 없는 아이디입니다.");
                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.show();
            }
            else if(id_check_ok == true){
                AlertDialog.Builder alert = new AlertDialog.Builder(Activity_join.this);
                alert.setTitle("ID검사");
                alert.setMessage("사용할수 있는 아이디입니다.");
                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.show();
            }


            join_flag = false;
        }
    }
    public static String urlEncodeUTF8(String url) {
        try {
            return URLEncoder.encode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedOperationException(e);
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
