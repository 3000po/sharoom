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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2016-02-22.
 */
public class Activity_login extends Activity {

    private String login_id = "";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_login); // 항상 제공되는
        // activity_layout.xml을

        // 만듦
        final EditText et_id = (EditText)findViewById(R.id.et_login_id);
        final EditText et_password = (EditText)findViewById(R.id.et_login_password);


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
                Intent intent = new Intent(Activity_login.this, Activity_group.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);

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


}
