package kr.popcorn.sharoom.activity.TabView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;

import kr.popcorn.sharoom.R;
import kr.popcorn.sharoom.activity.Activity_intro;
import kr.popcorn.sharoom.activity.Activity_login;
import kr.popcorn.sharoom.activity.Fragment.Host.Activity_host_view;
import kr.popcorn.sharoom.activity.Fragment.User.Activity_user_view;
import kr.popcorn.sharoom.helper.Helper_server;

/**
 * Created by Administrator on 2016-05-11.
 */
public class TabView_myself extends LinearLayout {

    ImageView myFace;
    ImageView logout_btn;
    Button chage_btn;
    int check_host_user = 0;

    public TabView_myself(Context context) {
        super(context);
        init();

    }

    private void init(){

        final Activity_user_view aActivity = (Activity_user_view) Activity_user_view.AActivty;

        Bitmap face = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.myself_50x50);

        final View view = LayoutInflater.from(getContext()).inflate(R.layout.activity_myself,null);
        view.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));

        myFace = (ImageView) view.findViewById(R.id.myface);
        logout_btn = (ImageView) view.findViewById(R.id.logout);
        chage_btn = (Button) view.findViewById(R.id.chage_btn);

        myFace.setImageBitmap(getCircleBitmap(face));

        logout_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (v.getId() == R.id.logout) {
                    // Set an EditText view to get user input
                    //로그아웃파트.
                    joinAlert();
                    //여기에 로그아웃 됬다는 말과 함께 로그인 화면으로 이동시켜 주어야 함.
                }
            }
        });

        chage_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (v.getId() == R.id.chage_btn) {

                   // Log.e("check :", "0=" + getContext().getClass());
                    String str = ""+getContext().getClass();
                    if(str.contains("Activity_user_view")) {
                        Intent intent = new Intent(getContext(), Activity_User_to_Host_animation.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getContext().startActivity(intent);
                    }
                    else if(str.contains("Activity_host_view")) {
                        Intent intent = new Intent(getContext(), Activity_Host_to_User_animation.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getContext().startActivity(intent);
                    }

                }
            }
        });


        this.addView(view);
    }

    public Bitmap getCircleBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        int size = (bitmap.getWidth()/2);
        canvas.drawCircle(size, size, size, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    public void joinAlert() {
        android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(getContext());
        alert.setTitle("로그아웃");
        alert.setMessage("로그아웃 하겠습니까?");
        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                AsyncHttpClient client = Helper_server.getInstance();
                final PersistentCookieStore myCookieStore = new PersistentCookieStore(getContext()); //이부분 Context 확인해야함. Activity context로.
                Helper_server.logout(myCookieStore, getContext());
                client.setCookieStore(myCookieStore);

                Intent intent = new Intent(getContext(), Activity_login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                getContext().startActivity(intent);
            }
        });
        alert.show();

    }

    public void setCheck_host_user(int n){
        check_host_user = n;
    }

}
