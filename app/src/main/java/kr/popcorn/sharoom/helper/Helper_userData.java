package kr.popcorn.sharoom.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import kr.popcorn.sharoom.activity.Fragment.User.Activity_user_view;

/**
 * Created by Administrator on 2016-03-19.
 */

//TODO 유저정보 저장하는 클래스

public class Helper_userData {

    private static Helper_userData user;

    public int userID;
    public String id;
    public String name;
    public String phoneNumber;
    public String email;
    public int sex;
    public int rate;
    public String school;
    public String facebook;

    public Helper_userData(int userID, String id, String name, String phoneNumber, String email, int sex, int rate, String school, String facebook) {

        this.userID = userID;
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.sex = sex;
        this.rate = rate;
        this.school = school;
        this.facebook = facebook;
    }

    public Helper_userData(){

    }
    public static Helper_userData getInstance(Context mContext) {
        if( user == null ) {
            String id = Helper_server.isLogIn(mContext);
            final RequestParams idParams = new RequestParams("fbid", id);

            Helper_server.post("getProfile.php", idParams, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    Log.i("myself", "success");
                    int userID;
                    String id;
                    String name;
                    String phoneNumber;
                    String email;
                    int sex;
                    int rate;
                    String school;
                    String facebook;

                    try {
                        userID = isNull_Int(response.get("userID"));
                        id = isNull_String(response.get("id"));
                        name = isNull_String(response.get("name"));
                        phoneNumber = isNull_String(response.get("phoneNumber"));;
                        email = isNull_String(response.get("email"));
                        sex = isNull_Int(response.get("sex"));
                        rate = isNull_Int(response.get("rate"));
                        school = isNull_String(response.get("school"));
                        facebook = isNull_String(response.get("facebook"));

                        Log.i("myself", id + ", " + name + "," + facebook);

                        user = new Helper_userData(userID, id, name, phoneNumber, email, sex, rate, school, facebook);

                        //list.add(new Helper_userData(userID,id,name,phoneNumber,email,sex,rate,school,facebook));
                        //notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                    Log.d("Failed: ", "myself " + statusCode);
                    Log.d("Error : ", "myself " + throwable);
                }
            });
        }
        return user;
    }

    public static void login_GetData(String id, final Context mContext) {
        if( user == null ) {
            final RequestParams idParams = new RequestParams("id", id);

            Helper_server.post("getProfile_Id.php", idParams, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    Log.i("myself", "success");
                    int userID;
                    String id;
                    String name;
                    String phoneNumber;
                    String email;
                    int sex;
                    int rate;
                    String school;
                    String facebook;

                    try {
                        System.out.println("ohohohohoh" + response.get("sex"));
                        userID = isNull_Int(response.get("userID"));
                        id = isNull_String(response.get("id"));
                        name = isNull_String(response.get("name"));
                        phoneNumber = isNull_String(response.get("phoneNumber"));
                        ;
                        email = isNull_String(response.get("email"));
                        sex = isNull_Int(response.get("sex"));
                        rate = isNull_Int(response.get("rate"));
                        school = isNull_String(response.get("school"));
                        facebook = isNull_String(response.get("facebook"));

                        Log.d("userData", id);
                        Log.d("userData", name);
                        Log.d("userData", email);
                        Log.d("userData", school);

                        user = new Helper_userData(userID, id, name, phoneNumber, email, sex, rate, school, facebook);

                        System.out.println("aaaaa : " + user.getId() + " + " + user.getEmail());
                        System.out.println("aaaaa : " + user);

                        Intent intent = new Intent(mContext, Activity_user_view.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                    Log.d("Failed: ", "myself " + statusCode);
                    Log.d("Error : ", "myself " + throwable);
                }
            });
        }
        else{
            Intent intent = new Intent(mContext, Activity_user_view.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        }
    }

   public static Helper_userData getInstance(){
       return user;
   }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public static String isNull_String(Object response){
        if(response == null | response.equals(null)) return "";
        else return response.toString().trim();
    }
    public static int isNull_Int(Object response){
        if(response == null || response.equals(null)) return -1;
        else return Integer.parseInt(response.toString().trim());

    }

}
