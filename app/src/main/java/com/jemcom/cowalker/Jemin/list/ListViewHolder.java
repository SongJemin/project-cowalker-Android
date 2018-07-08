package com.jemcom.cowalker.Jemin.list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jemcom.cowalker.R;

public class ListViewHolder extends RecyclerView.ViewHolder {

    public LinearLayout listLayout;
    public TextView itemTitle, itemPurpose, itemField, itemLocation;
    public ImageView itemBackground;


    public ListViewHolder(final View itemView) {
        super(itemView);
        listLayout = (LinearLayout) itemView.findViewById(R.id.layout_promise_list);
        itemTitle = (TextView) itemView.findViewById(R.id.item_title_tv);
        itemPurpose = (TextView) itemView.findViewById(R.id.item_purpose_tv);
        itemField = (TextView) itemView.findViewById(R.id.item_field_tv);
        itemLocation = (TextView) itemView.findViewById(R.id.item_location_tv);
        itemBackground = (ImageView) itemView.findViewById(R.id.item_background_image);

    }


}