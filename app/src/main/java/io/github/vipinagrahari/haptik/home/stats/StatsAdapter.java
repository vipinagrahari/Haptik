package io.github.vipinagrahari.haptik.home.stats;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import io.github.vipinagrahari.haptik.R;
import io.github.vipinagrahari.haptik.data.db.DbContract.MessageEntry;


/**
 * Created by vipin on 19/11/16.
 */
public class StatsAdapter extends RecyclerView.Adapter<StatsAdapter.StatsView> {

    Cursor cursor;
    Context mContext;

    public StatsAdapter(Cursor cursor) {
        this.cursor = cursor;
    }


    @Override
    public StatsView onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new StatsView(LayoutInflater.from(mContext).inflate(R.layout.element_stat, parent, false));
    }

    @Override
    public void onBindViewHolder(StatsView holder, int position) {
        cursor.moveToPosition(position);
        String name=cursor.getString(cursor.getColumnIndex(MessageEntry.COLUMN_NAME));
        String imgUrl=cursor.getString(cursor.getColumnIndex(MessageEntry.COLUMN_IMG_URL));
        int totalMessages=cursor.getInt(cursor.getColumnIndex("total"));
        int favMessages=cursor.getInt(cursor.getColumnIndex("favourite"));

        if (!imgUrl.isEmpty())
            Picasso.with(mContext)
                    .load(imgUrl)
                    .into(holder.imgAvatar);
        holder.tvName.setText(name);
        holder.tvCount.setText(String.valueOf(totalMessages).concat(" Messages "));
        holder.tvFavourite.setText(String.valueOf(favMessages));

    }

    public Cursor swapCursor(Cursor cursor) {
        if (this.cursor == cursor) {
            return null;
        }
        Cursor oldCursor = this.cursor;
        this.cursor = cursor;
        if (cursor != null) {
            this.notifyDataSetChanged();
        }
        return oldCursor;
    }

    @Override
    public int getItemCount() {
        return (cursor == null) ? 0 : cursor.getCount();
    }

    class StatsView extends RecyclerView.ViewHolder  {

        ImageView imgAvatar;
        TextView tvName, tvCount ,tvFavourite;


        StatsView(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvCount = (TextView) itemView.findViewById(R.id.tv_count);
            imgAvatar = (ImageView) itemView.findViewById(R.id.img_avatar);
            tvFavourite=(TextView)itemView.findViewById(R.id.tv_fav);

        }


    }


}

