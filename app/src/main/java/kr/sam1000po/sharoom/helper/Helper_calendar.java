package kr.sam1000po.sharoom.helper;

/**
 * Created by user on 16. 2. 15.
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import com.ibm.icu.util.ChineseCalendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by user on 16. 1. 10.
 */
public class Helper_calendar {

    public static ChineseCalendar cc;
    public static Calendar cal;

    public static int chkYun(int year) {  //윤년체크
        int isYun = 0;

        if ((0 == (year % 4) && 0 != (year % 100)) || 0 == year % 400) {
            isYun = 1;
        } else {
            isYun = 0;
        }
        return isYun;
    }
    public static String afterDays(String yyyymmdd) {  //다음날 리턴
        String date = "";
        Calendar cal = Calendar.getInstance();
        cal.set(Integer.parseInt(yyyymmdd.substring(0, 4)), Integer.parseInt(yyyymmdd.substring(4, 6)) - 1, Integer.parseInt(yyyymmdd.substring(6, 8)));
        cal.add(Calendar.DATE, +1);

        SimpleDateFormat formatter = new SimpleDateFormat("MMdd", Locale.KOREA);
        date = formatter.format(cal.getTime());

        return date;
    }
    public static String preDays(String yyyymmdd) {  //이전날 리턴
        String date = "";
        Calendar cal = Calendar.getInstance();
        cal.set(Integer.parseInt(yyyymmdd.substring(0, 4)), Integer.parseInt(yyyymmdd.substring(4, 6)) - 1, Integer.parseInt(yyyymmdd.substring(6, 8)));
        cal.add(Calendar.DATE, -1);

        SimpleDateFormat formatter = new SimpleDateFormat("MMdd", Locale.KOREA);
        date = formatter.format(cal.getTime());

        return date;
    }

    public static String getNextDay(String date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try{
            c.setTime(sdf.parse(date));
        }catch(ParseException e){
            e.getErrorOffset();
        }
        c.add(Calendar.DATE, 1);  //하루를 더해준다.
        date = sdf.format(c.getTime());  // dt는 하루를 더한 날짜
        return date;
    }

    //요일 구하기
    public static String getDateDay(String date){
        String day = "" ;

        Calendar cal = Calendar.getInstance() ;
        cal.set(Integer.parseInt(date.substring(0, 4)), Integer.parseInt(date.substring(4, 6)) - 1, Integer.parseInt(date.substring(6, 8)));

        int dayNum = cal.get(Calendar.DAY_OF_WEEK) ;
        switch (dayNum) {
            case 1:
                day = "SUN";
                break;
            case 2:
                day = "MON";
                break ;
            case 3:
                day = "TUE";
                break ;
            case 4:
                day = "WED";
                break;
            case 5:
                day = "THU";
                break ;
            case 6:
                day = "FRI";
                break ;
            case 7:
                day = "SAT";
                break ;
        }
        return day ;
    }
    //휴일 구하기
    public static boolean isHoliday(String yyyymmdd){
        // 검사년도
        int yyyy = Integer.parseInt(yyyymmdd.substring(0, 4));
        int isYun = chkYun(yyyy);  //윤년체크
        try {
            // 음력 공휴일을 양력으로 바꾸어서 입력
            String tmp01 = fromLunar(yyyy + "0101");// 음력설날
            String tmp02 = fromLunar(yyyy + "0815");// 음력추석
            String tmp03 = fromLunar( yyyy + "0408");// 석가탄신일

            String[] holidays = {
                    yyyy+preDays(tmp01), tmp01,yyyy+afterDays(tmp01), //설날전날,설날,설날다음날
                    yyyy+preDays(tmp02), tmp02,yyyy+afterDays(tmp02),  //추석전날,추석,추석다음날
                    tmp03,
                    yyyy + "0101",  // 양력설날
                    yyyy + "0301",  // 삼일절
                    yyyy + "0405",  // 식목일
                    yyyy + "0505",  // 어린이날
                    yyyy + "0606",  // 현충일
                    yyyy + "0815",  // 광복절
                    yyyy + "1003",  // 개천절
                    yyyy + "1009",  // 한글날
                    yyyy + "1225",  // 성탄절
            };

            for ( int ii = 0 ; ii < holidays.length ; ++ii ) {
                if ( yyyymmdd.equals(holidays[ii])  ) {
                    return true ;
                }
            }
            if( getDateDay(yyyymmdd).equals("SUN") || getDateDay(yyyymmdd).equals("SAT") )
                return true;

        } catch(Exception ex) {
            throw ex;
        }
        return false;
    }


    public static String fromLunar(String yyyymmdd) {
        cal = Calendar.getInstance();
        cc = new ChineseCalendar();

        if (yyyymmdd == null)
            return "";

        String date = yyyymmdd.trim();
        if (date.length() != 8) {
            if (date.length() == 4)
                date = date + "0101";
            else if (date.length() == 6)
                date = date + "01";
            else if (date.length() > 8)
                date = date.substring(0, 8);
            else
                return "";
        }

        cc.set(ChineseCalendar.EXTENDED_YEAR, Integer.parseInt(date.substring(
                0, 4)) + 2637);
        cc.set(ChineseCalendar.MONTH,
                Integer.parseInt(date.substring(4, 6)) - 1);
        cc.set(ChineseCalendar.DAY_OF_MONTH, Integer
                .parseInt(date.substring(6)));

        cal.setTimeInMillis(cc.getTimeInMillis());

        int y = cal.get(Calendar.YEAR);
        int m = cal.get(Calendar.MONTH) + 1;
        int d = cal.get(Calendar.DAY_OF_MONTH);

        StringBuffer ret = new StringBuffer();
        if (y < 1000)
            ret.append("0");
        else if (y < 100)
            ret.append("00");
        else if (y < 10)
            ret.append("000");
        ret.append(y);

        if (m < 10)
            ret.append("0");
        ret.append(m);

        if (d < 10)
            ret.append("0");
        ret.append(d);

        return ret.toString();
    }
}
