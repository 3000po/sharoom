package kr.popcorn.sharoom.helper;

import android.content.Intent;
import android.util.Log;

import com.loopj.android.http.*;

import java.util.List;

import cz.msebera.android.httpclient.cookie.Cookie;
import kr.popcorn.sharoom.activity.Fragment.Activity_group_view;

/**
 * Created by Administrator on 2016-03-11.
 */
public class Helper_server {

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

    public static void logout(PersistentCookieStore myCookieStore){
        myCookieStore.clear();
        client.setCookieStore(myCookieStore);
    }

    public static String getAbsoluteUrl(String relativeUrl){
        return BASE_URL + relativeUrl;
    }

}
