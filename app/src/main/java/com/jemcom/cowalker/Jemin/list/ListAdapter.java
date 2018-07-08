package com.jemcom.cowalker.Jemin.list;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jemcom.cowalker.Hyunmin.Activity.ProjectIntroActivity;
import com.jemcom.cowalker.R;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListViewHolder> {

    Context context;

    private List<ListData> arrayListOfListData;
    private OnItemClickListener mListener;




    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public ListAdapter(List<ListData> arrayListOfListData)
    {
        this.arrayListOfListData = arrayListOfListData;

    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View listView = layoutInflater.inflate(R.layout.list_item, parent, false);
        ListViewHolder viewHolder = new ListViewHolder(listView);

        return viewHolder;
    }


    public void onBindViewHolder(ListViewHolder holder, int position) {

        holder.itemTitle.setText(arrayListOfListData.get(position).getTitle());
        holder.itemPurpose.setText(arrayListOfListData.get(position).getPurpose());
        holder.itemField.setText(arrayListOfListData.get(position).getField());
        holder.itemLocation.setText(arrayListOfListData.get(position).getLocation());
        holder.itemBackground.setImageResource(arrayListOfListData.get(position).project_bgUrl);


        holder.listLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent3 = new Intent(context, ProjectIntroActivity.class);
                context.startActivity(intent3);
            }
        });

    }

    @Override
    public int getItemCount() {

        return arrayListOfListData.size();
    }

}


