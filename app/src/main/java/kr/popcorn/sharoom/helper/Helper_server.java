package kr.popcorn.sharoom.helper;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import com.facebook.AccessToken;
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

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken == null) {
            Log.d("abde", ">>>" + "Signed Out");
        } else {
            LoginManager loginManager = LoginManager.getInstance();
            loginManager.logOut();
        }
    }



    //로그인해서 정보 얻을때
    public static String isLogIn(Context mContext){
        //TODO 회원가입할때 아이디는 숫자만으로 가입 불가능하게 만들어야한다!

        //페이스북 로그인 정보 확인 Id를 리턴한다.
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken == null) {
            Log.d("abde", ">>>" + "Signed Out");
        } else {
            return accessToken.getUserId();
        }

        //쿠키로 로그인 정보 확인 찾으면 아이디를 리턴


        //리턴한 값으로 디비에 페이스북 아이디와 그냥 유저아이디에서 검색을 때려 둘중 하나가 나올테니 그걸로 한다.

        //못 찾으면 null 리턴
        return null;
    }


    public static String getAbsoluteUrl(String relativeUrl){
        return BASE_URL + relativeUrl;
    }
}
