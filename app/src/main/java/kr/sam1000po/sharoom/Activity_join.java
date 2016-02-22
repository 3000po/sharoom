package kr.sam1000po.sharoom;

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

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Administrator on 2016-02-22.
 */
public class Activity_join extends Activity {

    private form_basic form_basic;
    class form_basic {
        EditText et_id;
        EditText et_password;
        EditText et_name;
        EditText et_phoneNumber;
        EditText et_email;

        Button btn_submit;

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

        form_basic.et_id.setNextFocusDownId(R.id.et_join_password);
        form_basic.et_password.setNextFocusDownId(R.id.et_join_name);
        form_basic.et_name.setNextFocusDownId(R.id.et_join_phoneNumber);
        form_basic.et_phoneNumber.setNextFocusDownId(R.id.et_join_email);

        form_basic.btn_submit = (Button) findViewById(R.id.btn_join_submit);
        //this.overridePendingTransition( R.anim.anim_slide_in_left, R.anim.anim_slide_in_right);
        View.OnClickListener downloadListener = new View.OnClickListener() {
            public void onClick(View v) {
                if (isNetworkAvailable()) {
                    String id = form_basic.et_id.getText().toString();
                    String password = form_basic.et_password.getText().toString();
                    String name = form_basic.et_name.getText().toString();
                    String phoneNumber = form_basic.et_phoneNumber.getText().toString();
                    String email = form_basic.et_email.getText().toString();

                    id=urlEncodeUTF8(id);
                    password = urlEncodeUTF8(password);
                    name = urlEncodeUTF8(name);
                    phoneNumber = urlEncodeUTF8(phoneNumber);
                    email = urlEncodeUTF8(email);

                    String url = "http://14.63.223.92/member_Insert.php?id="+id+"&password="+password+"&name="+name+"&phoneNumber="+phoneNumber+"&email="+email;

                    phpTask phpTask = new phpTask();
                    phpTask.execute(url);
                }
            }
            // Codes.InitApp(this); // *
        };
        form_basic.btn_submit.setOnClickListener(downloadListener);

        //email 입력후 엔터눌렀을때 회원가입 처리
        form_basic.et_email.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //Enter key Action
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow( form_basic.et_email.getWindowToken(), 0);    //hide keyboard

                    if (isNetworkAvailable()) {
                        String id = form_basic.et_id.getText().toString();
                        String password = form_basic.et_password.getText().toString();
                        String name = form_basic.et_name.getText().toString();
                        String phoneNumber = form_basic.et_phoneNumber.getText().toString();
                        String email = form_basic.et_email.getText().toString();

                        id=urlEncodeUTF8(id);
                        password = urlEncodeUTF8(password);
                        name = urlEncodeUTF8(name);
                        phoneNumber = urlEncodeUTF8(phoneNumber);
                        email = urlEncodeUTF8(email);

                        String url = "http://14.63.223.92/member_Insert.php?id="+id+"&password="+password+"&name="+name+"&phoneNumber="+phoneNumber+"&email="+email;

                        phpTask phpTask = new phpTask();
                        phpTask.execute(url);
                    }
                    return true;
                }
                return false;
            }
        });
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
        try {
            URL url = new URL(php);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            InputStream iStream = urlConnection.getInputStream();
            //if (iStream == null)
            //show.setText("ono");
            //else
            //show.setText("two");

        } catch(Exception e){
            Log.d("Exception ", e.toString());

        }
    }

    private class phpTask extends AsyncTask<String, Integer, String> {
        protected String doInBackground(String... url){

            try{
                Log.d("url",url[0].toString());
                downloadUrl(url[0]);
            }
            catch(Exception e){
                Log.d("Background Task", e.toString());
            }
            return null;
        }

        protected void onPostExecute(String result){
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
    }
    public static String urlEncodeUTF8(String url) {
        try {
            return URLEncoder.encode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedOperationException(e);
        }
    }

}
