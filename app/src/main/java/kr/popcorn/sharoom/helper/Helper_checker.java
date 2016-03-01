package kr.popcorn.sharoom.Helper;

import android.content.Context;
import android.widget.Toast;

import java.util.regex.Pattern;

/**
 * Created by user on 16. 2. 22.
 */

//패키지를 나누기 위해서 일단 만들었음
public class Helper_checker {
    public static final int MIN=5;
    public static final int MAX=20;

    public static boolean isEmail(String email) {
        if (email==null) return false;
        boolean b = Pattern.matches("[\\w\\~\\-\\.]+@[\\w\\~\\-]+(\\.[\\w\\~\\-]+)+", email.trim());
        return b;
    }
    public static boolean validName(String name){
        if( name == null ) return false;
        if( name.length() < MIN || name.length()>MAX) return false;
        return true;
    }
    public static boolean validId(String id){
        //TODO 아이디 중복 검사
        if( id == null ) return false;
        if( id.length() < MIN || id.length() > MAX ) return false;
        return true;
    }
    public static boolean validPassword(String pw){
        if( pw == null ) return false;
        if( pw.length() < MIN ) return false;
        return true;
    }

    public static boolean validJoin(Context context, String email, String name, String id, String pw){
        if( !validId(id) ){
            Toast.makeText(context, "아이디는 5글자이상 20글자 이하여야합니다. ", Toast.LENGTH_LONG).show();
            return false;
        }
        if( !validPassword(pw) ){
            Toast.makeText(context, "비밀번호는 5글자 이상이어야합니다. ", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!validName(name)) {
            Toast.makeText(context, "이름은 5글자이상 20글자 이하여야합니다. ", Toast.LENGTH_LONG).show();
             return false;
        }
        if( !isEmail(email) ){
            Toast.makeText(context, "이메일 형식이 잘못되었습니다. ", Toast.LENGTH_LONG).show();
            return false;
        }


        return true;
    }
}