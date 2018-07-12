package com.jemcom.cowalker.Jemin.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class TestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Button btnTest; // 테스트용 버튼
    public TestAdapter(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        public void onItemClick(View view, int position, boolean isUser);
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }




}
