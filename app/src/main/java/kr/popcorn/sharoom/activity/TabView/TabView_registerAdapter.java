package kr.popcorn.sharoom.activity.TabView;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import kr.popcorn.sharoom.R;
import kr.popcorn.sharoom.helper.Helper_roomData;

/**
 * Created by Administrator on 2016-03-25.
 */
public class TabView_registerAdapter extends RecyclerView.Adapter<TabView_registerAdapter.ViewHolder> {

    private Context mContext;
    public ArrayList<Helper_roomData> list;
    private LinearLayoutManager linearLayoutManager;

    public ArrayList<Helper_roomData> getContactsList() {
        return list;
    }

    public TabView_registerAdapter(Context context, ArrayList<Helper_roomData> _dataSet, LinearLayoutManager linearLayoutManager) {
        mContext = context;
        list = _dataSet;
        this.linearLayoutManager = linearLayoutManager;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext)
                .inflate(R.layout.list_item_register_room, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //Picasso.with(mContext).load(new File(list.get(position).path)).into(holder.album);

        //holder.roomimage.setImageResource(R.drawable.ic_action_mapview_m);
        //holder.rating.setText(list.get(position).roomname);
        //holder.text.setText(tmp.substring(0,4));
    }

    @Override
    public int getItemCount() {
        if (list == null) return 0;
        return list.size();
    }

    public void remove(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void add(Helper_roomData song, int position) {
        list.add(position, song);
        notifyItemInserted(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView roomimage;
        public TextView rating;

        public ViewHolder(View itemView) {
            super(itemView);
            //album = (ImageView) itemView.findViewById(R.id.album_art1);
            //text = (TextView) itemView.findViewById(R.id.year);

            roomimage = (ImageView) itemView.findViewById(R.id.roomimage);
            rating = (TextView) itemView.findViewById(R.id.roomrating);

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
        }

    }
}
