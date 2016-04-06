package kr.popcorn.sharoom.helper;


import android.widget.ImageView;

import java.util.ArrayList;

import kr.popcorn.sharoom.R;

/**
 * Created by user on 16. 3. 2.
 */

//TODO 방정보를 저장할 자료형 클래스
public class Helper_roomData {

    public String roomname;
    public int roomimage;


    public ArrayList<Integer> list;

    public Helper_roomData(){
        //list = new ArrayList<Integer>();
       // list.add(0, R.drawable.facebook_gray);
        roomname = "방 화면 예시";
        roomimage = R.drawable.room1;
    }
}
