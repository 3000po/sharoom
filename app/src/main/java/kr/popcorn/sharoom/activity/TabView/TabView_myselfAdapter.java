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

    ImageView myface;

    private Context mContext;
    public ArrayList<Helper_userData> list;
    private LinearLayoutManager linearLayoutManager;

    public ArrayList<Helper_userData> getContactsList() {
        return list;
    }

    public TabView_myselfAdapter(Context context, ArrayList<Helper_userData> _dataSet, LinearLayoutManager linearLayoutManager) {
        mContext = context;
        list = _dataSet;
        this.linearLayoutManager = linearLayoutManager;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext)
                .inflate(R.layout.activity_myself_adapter, parent, false);
/*
        String id = Helper_server.isLogIn(mContext);
        final RequestParams idParams = new RequestParams("fbid", id);

        Log.i("myself", id);

        Helper_server.post("getProfile.php", idParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i("myself", "success");
                String data = "";
                try {
                    data = response.get("userID").toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("myself", "" + data);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.d("Failed: ", "myself " + statusCode);
                Log.d("Error : ", "myself " + throwable);
            }
        });
        */

        return new ViewHolder(v);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {

        Bitmap face = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.myself_50x50);
        //myface.setImageBitmap(getCircleBitmap(face));

        //holder.myface.setImageResource(R.drawable.ic_action_mapview_m);
        holder.myface.setImageBitmap(getCircleBitmap(face));
        //holder.myname.setText((CharSequence) list.get(position));
        //holder.text.setText(tmp.substring(0,4));
    }

    @Override
    public int getItemCount() {
        if (list == null) return 0;
        return list.size();
    }

    public void add(Helper_userData song, int position) {
        list.add(position, song);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView myface;
        public TextView myname;
        public Button logout;

        public ViewHolder(View itemView) {
            super(itemView);
            //album = (ImageView) itemView.findViewById(R.id.album_art1);
            //text = (TextView) itemView.findViewById(R.id.year);

            myface = (ImageView) itemView.findViewById(R.id.myface);
            myname = (TextView) itemView.findViewById(R.id.myname);

            itemView.setClickable(true);
            itemView.setOnClickListener(this);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    // song is selected
                    return true;
                }
            });
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

                        Log.i("kisang", "logout");
                    }
                }});
        }

    }
    void init(){

        //myface= (ImageView)findViewById(R.id.myface);
       // myface.getDrawable();
       // init();
       // Bitmap face = BitmapFactory.decodeResource(getBaseContext().getResources(),R.drawable.myself_50x50);

        //myface.setImageBitmap(getCircleBitmap(face));

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
