package com.javahelps.todobasic;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Sneha on 09-09-2017.
 */

public class CustomAdapter extends ArrayAdapter<Todo> {

    Context mContext;
    ArrayList<Todo> mList ;
    DeleteButtonClickListener mDeleteButtonClickListener;

    public CustomAdapter(@NonNull Context context, ArrayList<Todo> list ,DeleteButtonClickListener deleteButtonClickListener) {
        super(context, 0);
        mContext = context ;
        mList = list;
        mDeleteButtonClickListener = deleteButtonClickListener ;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_row_layout , null);
            ViewHolder viewHolder = new ViewHolder();
            CheckBox task = (CheckBox) convertView.findViewById(R.id.task_title);
            TextView date = (TextView)convertView.findViewById(R.id.date);
            TextView time = (TextView)convertView.findViewById(R.id.todotime);
            viewHolder.task = task ;
            viewHolder.date = date ;
            viewHolder.time = time ;
            convertView.setTag(viewHolder);
        }
        ViewHolder holder = (ViewHolder)convertView.getTag();
        Todo item = mList.get(position);
        holder.task.setText(item.getTask().toString());
        holder.date.setText(item.getDate().toString());
        holder.time.setText(item.getTime().toString());

        holder.task.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mDeleteButtonClickListener.onLongClicked(position,v);
                return true ;
            }
        });
        return convertView;

    }
    interface DeleteButtonClickListener {

        void onLongClicked(int position,View v);

    }
    static class ViewHolder{
        CheckBox task ;
        TextView date ;
        TextView time ;
    }
}
