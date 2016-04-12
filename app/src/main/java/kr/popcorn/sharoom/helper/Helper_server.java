package kr.popcorn.sharoom.helper;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.loopj.android.http.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import cz.msebera.android.httpclient.cookie.Cookie;
import kr.popcorn.sharoom.R;
import kr.popcorn.sharoom.activity.Fragment.Activity_group_view;

/**
 * Created by Administrator on 2016-03-11.
 */
public class Helper_server {

    private static final String TAG_RESULTS="result";
    private static final String TAG_USERID = "userID";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_NUM ="phoneNumber";
    private static final String TAG_EMAIL ="email";
    private static final String TAG_SEX ="sex";
    private static final String TAG_RATE ="rate";
    private static final String TAG_SCHOOL ="school";
    private static final String TAG_FACEBOOK ="facebook";

    public static String myJSON;
    public static JSONArray peoples = null;
    public static Helper_userData userData;

    private static final String BASE_URL = "http://14.63.227.200/";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static AsyncHttpClient getInstance() {
        return Helper_server.client;
    }

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler){
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler){
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    public static boolean login(PersistentCookieStore myCookieStore){
        List<Cookie> cookieList = myCookieStore.getCookies();
        if (!cookieList.isEmpty()) {
            for (int i = 0; i < cookieList.size(); i++) {
                // cookie = cookies.get(i);
                String cookieString = cookieList.get(i).getName() + "="
                        + cookieList.get(i).getValue();
                Log.e("surosuro", cookieString);

                if(cookieList.get(i).getName().equals("login_cookie")){
                     return true;
                }
            }
        }
        return false;
    }

    public static void logout(PersistentCookieStore myCookieStore, Context mContext){
        myCookieStore.clear();
        client.setCookieStore(myCookieStore);

        FacebookSdk.sdkInitialize(mContext);
        Profile profile = Profile.getCurrentProfile();
        if (profile != null) {
            LoginManager loginManager = LoginManager.getInstance();
            loginManager.logOut();
        }
    }

    //로그인해서 정보 얻을때
    public static String isLogIn(Context mContext){
        //TODO 회원가입할때 아이디는 숫자만으로 가입 불가능하게 만들어야한다!


        //페이스북 로그인 정보 확인 Id를 리턴한다.
        FacebookSdk.sdkInitialize(mContext);
        Profile profile = Profile.getCurrentProfile();
        if (profile != null) {
            return profile.getId();
        }

        //쿠키로 로그인 정보 확인 찾으면 아이디를 리턴


        //리턴한 값으로 디비에 페이스북 아이디와 그냥 유저아이디에서 검색을 때려 둘중 하나가 나올테니 그걸로 한다.

        //못 찾으면 null 리턴
        return null;
    }


    public static String getAbsoluteUrl(String relativeUrl){
        return BASE_URL + relativeUrl;
    }

    protected void showList(){
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);

            for(int i=0;i<peoples.length();i++){
                JSONObject c = peoples.getJSONObject(i);
                int userID = c.getInt(TAG_USERID);
                String id = c.getString(TAG_ID);
                String name = c.getString(TAG_NAME);
                String phoneNumber = c.getString(TAG_NUM);
                String email = c.getString(TAG_EMAIL);
                int sex = c.getInt(TAG_SEX);
                int rate = c.getInt(TAG_RATE);
                String school = c.getString(TAG_SCHOOL);
                String facebook = c.getString(TAG_FACEBOOK);

                userData = new Helper_userData(userID,id,name,phoneNumber,email,sex,rate, school, facebook);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void getData(String url, String id){

        final RequestParams idParams = new RequestParams("fbid", id);

        class GetDataJSON extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];

                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while((json = bufferedReader.readLine())!= null){
                        sb.append(json+"\n");
                    }

                    return sb.toString().trim();

                }catch(Exception e){
                    return null;
                }



            }

            @Override
            protected void onPostExecute(String result){
                myJSON=result;
                showList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }
}
