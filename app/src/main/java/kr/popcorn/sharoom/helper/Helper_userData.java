package kr.popcorn.sharoom.helper;

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

        user.userID = userID;
        user.id = id;
        user.name = name;
        user.phoneNumber = phoneNumber;
        user.email = email;
        user.sex = sex;
        user.rate = rate;
        user.school = school;
        user.facebook = facebook;
    }

    public Helper_userData(){

    }
    public static synchronized Helper_userData getInstance() {
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


}
