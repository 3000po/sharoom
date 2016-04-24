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
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import kr.popcorn.sharoom.R;
import kr.popcorn.sharoom.activity.Activity_join;
import kr.popcorn.sharoom.activity.Activity_login;
import kr.popcorn.sharoom.activity.Fragment.Activity_group_view;
import kr.popcorn.sharoom.activity.Fragment.TestFragment;
import kr.popcorn.sharoom.helper.Helper_adapterCommunication;
import kr.popcorn.sharoom.helper.Helper_server;
import kr.popcorn.sharoom.helper.Helper_userData;

public class TabView_myselfAdapter extends RecyclerView.Adapter<TabView_myselfAdapter.ViewHolder> {

    ImageView myFace;

    private Context mContext;
    public Helper_userData data;
    private LinearLayoutManager linearLayoutManager;

    public TabView_myselfAdapter(Context context, Helper_userData _dataSet, LinearLayoutManager linearLayoutManager) {
        mContext = context;
        data = _dataSet;
        this.linearLayoutManager = linearLayoutManager;
        loadInfo();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext)
                .inflate(R.layout.activity_myself_adapter, parent, false);

        return new ViewHolder(v);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {

        Bitmap face = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.myself_50x50);
        //myFace.setImageBitmap(getCircleBitmap(face));

        //holder.myFace.setImageResource(R.drawable.ic_action_mapview_m);
        holder.myFace.setImageBitmap(getCircleBitmap(face));
        //holder.myname.setText((CharSequence) list.get(position));
        //holder.text.setText(tmp.substring(0,4));

        holder.loadData();
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    private void loadInfo(){
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
                    userID = Integer.parseInt(response.get("userID").toString());
                    id = response.get("id").toString();
                    name = response.get("name").toString();
                    phoneNumber = response.get("phoneNumber").toString();
                    email = response.get("email").toString();
                    sex = Integer.parseInt(response.get("sex").toString());
                    rate = Integer.parseInt(response.get("rate").toString());
                    school = response.get("school").toString();
                    facebook = response.get("facebook").toString();

                    Log.i("myself", id+", "+name+","+facebook);

                    data = new Helper_userData(userID,id,name,phoneNumber,email,sex,rate,school,facebook);
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView myFace;
        public TextView myName;
        public RatingBar myRating;
        public ImageView confirm1;
        public ImageView confirm2;
        public ImageView confirm3;
        public ImageView confirm4;
        public ImageView confirm5;
        public ImageView confirm6;

        public boolean facebookID;
        public boolean phoneID;
        public boolean emailID;
        
        public Button logout;

        public ViewHolder(View itemView) {
            super(itemView);
            //album = (ImageView) itemView.findViewById(R.id.album_art1);
            //text = (TextView) itemView.findViewById(R.id.year);

            myFace = (ImageView) itemView.findViewById(R.id.myface);
            myName = (TextView) itemView.findViewById(R.id.myname);
            myRating = (RatingBar) itemView.findViewById(R.id.myrating);
            confirm1 = (ImageView) itemView.findViewById(R.id.confirm1);
            confirm2 = (ImageView) itemView.findViewById(R.id.confirm2);
            confirm3 = (ImageView) itemView.findViewById(R.id.confirm3);
            confirm4 = (ImageView) itemView.findViewById(R.id.confirm4);
            confirm5 = (ImageView) itemView.findViewById(R.id.confirm5);
            confirm6 = (ImageView) itemView.findViewById(R.id.confirm6);


            itemView.setClickable(true);
            itemView.setOnClickListener(this);
            
            if( data == null ){
                loadInfo();
            }
            loadData();


            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    // song is selected
                    return true;
                }
            });
        }

        public void loadData(){

            myName.setText(data.name);
            //내 사진 로드
            myRating.setRating(data.rate);

            if(data.facebook == null){
                confirm1.setImageResource(R.drawable.facebook_gray);
                facebookID=false;
            }else{
                confirm1.setImageResource(R.drawable.facebook_green);
                facebookID=true;
            }

            if(data.phoneNumber == null){
                confirm2.setImageResource(R.drawable.phone_gray);
                phoneID=false;
            }else{
                confirm2.setImageResource(R.drawable.phone_green);
                phoneID=true;
            }
        }

        @Override
        public void onClick(View v) {
            ImageView logout_btn;
            logout_btn = (ImageView) v.findViewById(R.id.logout);

            logout_btn.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v2) {
                    if (v2.getId() == R.id.logout) {
                        // Set an EditText view to get user input
                        //로그아웃파트.
                        joinAlert();
                        //여기에 로그아웃 됬다는 말과 함께 로그인 화면으로 이동시켜 주어야 함.
                    }
                }});
        }

    }
    void init(){

        //myFace= (ImageView)findViewById(R.id.myFace);
       // myFace.getDrawable();
       // init();
       // Bitmap face = BitmapFactory.decodeResource(getBaseContext().getResources(),R.drawable.myself_50x50);

        //myFace.setImageBitmap(getCircleBitmap(face));

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
        android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(mContext);
        alert.setTitle("로그아웃");
        alert.setMessage("로그아웃 하겠습니까?");
        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                AsyncHttpClient client = Helper_server.getInstance();
                final PersistentCookieStore myCookieStore = new PersistentCookieStore(mContext); //이부분 Context 확인해야함. Activity context로.
                Helper_server.logout(myCookieStore,mContext);
                client.setCookieStore(myCookieStore);

                Intent intent = new Intent(mContext, Activity_login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                mContext.startActivity(intent);
            }
        });
        alert.show();
    }
}
