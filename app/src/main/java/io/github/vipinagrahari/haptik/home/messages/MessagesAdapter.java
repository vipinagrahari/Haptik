package io.github.vipinagrahari.haptik.home.messages;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import io.github.vipinagrahari.haptik.R;
import io.github.vipinagrahari.haptik.data.db.DbContract;
import io.github.vipinagrahari.haptik.data.model.Message;
import io.github.vipinagrahari.haptik.home.stats.StatsContract;

/**
 * Created by vipin on 19/11/16.
 */
public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessageView> {

    Cursor mCursor;
    Context mContext;

    MessagesContract.Presenter mPresenter;

    public MessagesAdapter(Cursor cursor, Context context, MessagesContract.Presenter presenter) {
        mCursor = cursor;
        mContext = context;
        mPresenter =presenter;
    }

    @Override
    public MessageView onCreateViewHolder(ViewGroup parent, int viewType) {
        View messageView = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_message, parent, false);
        return new MessageView(messageView);
    }

    @Override
    public void onBindViewHolder(MessageView holder,int position) {
        mCursor.moveToPosition(position);

        String name=mCursor.getString(mCursor.getColumnIndex(DbContract.MessageEntry.COLUMN_NAME));
        String imgUrl=mCursor.getString(mCursor.getColumnIndex(DbContract.MessageEntry.COLUMN_IMG_URL));
        String messageBody= mCursor.getString(mCursor.getColumnIndex(DbContract.MessageEntry.COLUMN_BODY));
        String time=mCursor.getString(mCursor.getColumnIndex(DbContract.MessageEntry.COLUMN_TIME_STAMP));
        int favourite=mCursor.getInt(mCursor.getColumnIndex(DbContract.MessageEntry.COLUMN_IS_FAVOURITE));

        if (!imgUrl.isEmpty())
            Picasso.with(mContext)
                    .load(imgUrl)
                    .into(holder.imgAvatar);
        holder.tvName.setText(name);
        holder.tvMessage.setText(messageBody);
        holder.tvTime.setText(getRelativeTime(time));

        /**
         *
         * If the  message is a favourite then show a filled icon else an empty icon
         *
         */

        holder.favBtn.setImageDrawable(
                favourite==1?
                mContext.getResources().getDrawable(R.drawable.ic_favorite_filled):
                mContext.getResources().getDrawable(R.drawable.ic_favorite_outline));

    }

    @Override
    public int getItemCount() {
        if(mCursor!=null) return mCursor.getCount();
        return 0;
    }


    public Cursor swapCursor(Cursor cursor) {

        if (mCursor == cursor) {
            return null;
        }
        Cursor oldCursor = mCursor;
        mCursor = cursor;
        if (cursor != null) {
            this.notifyDataSetChanged();
        }
        return oldCursor;
    }

    class MessageView extends RecyclerView.ViewHolder {

        ImageView imgAvatar;
        TextView tvName, tvMessage,tvTime;
        ImageButton favBtn;


        public MessageView(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvMessage = (TextView) itemView.findViewById(R.id.tv_message);
            imgAvatar = (ImageView) itemView.findViewById(R.id.img_avatar);
            tvTime=(TextView)itemView.findViewById(R.id.tv_time);
            favBtn=(ImageButton)itemView.findViewById(R.id.btn_fav);
            favBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCursor.moveToPosition(getAdapterPosition());
                    long messageId=mCursor.getLong(mCursor.getColumnIndex(DbContract.MessageEntry._ID));
                    int favourite=mCursor.getInt(mCursor.getColumnIndex(DbContract.MessageEntry.COLUMN_IS_FAVOURITE));
                    mPresenter.toggleFavourite(messageId,favourite==1);

                }
            });

        }


    }

    /**
     * This method converts time in "yyyy-MM-dd'T'HH:mm:ss" format into
     * a time which is relative to current time
     * @param time in specified String format
     * @return time in relative format
     */
    String getRelativeTime(String time){
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try{
            Date date=format.parse(time);
            long timeInMillis=date.getTime();
            CharSequence relativeTime= DateUtils.getRelativeDateTimeString(
                    mContext,timeInMillis,DateUtils.SECOND_IN_MILLIS,DateUtils.YEAR_IN_MILLIS,0);
            return String.valueOf(relativeTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}

