package kr.popcorn.sharoom.helper;

import com.loopj.android.http.*;

/**
 * Created by Administrator on 2016-03-11.
 */
public class Helper_server {

    private static final String BASE_URL = "http://192.168.168.111:52273/";

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


    public static String getAbsoluteUrl(String relativeUrl){
        return BASE_URL + relativeUrl;
    }

}
