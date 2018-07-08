package com.jemcom.cowalker.Nuri.Adapter

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jemcom.cowalker.Nuri.Holder.ChatViewHolder
import com.jemcom.cowalker.Nuri.Item.ChatItem
import com.jemcom.cowalker.R

class ChatAdapter(private var chatItems : ArrayList<ChatItem>) : RecyclerView.Adapter<ChatViewHolder>() {

    private lateinit var onItemClick : View.OnClickListener

    fun setOnItemClickListener(I : View.OnClickListener)
    {
        onItemClick = I
    }

    //내가 쓸 뷰홀더가 뭔지를 적어준다.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val mainView : View = LayoutInflater.from(parent.context)
                .inflate(R.layout.chat_item, parent, false)
        mainView.setOnClickListener(onItemClick)
        return ChatViewHolder(mainView)
    }

    override fun getItemCount(): Int = chatItems.size

    //데이터클래스와 뷰홀더를 이어준다.
    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        if(chatItems[position].name.equals("나")) holder.chatName.setTextColor(Color.parseColor("#f8c930"))
        else holder.chatName.setTextColor(Color.parseColor("#64dfff"))
        holder.chatName.text = chatItems[position].name
        holder.chatPreview.text = chatItems[position].preView
        holder.chatDate.text = chatItems[position].date
    }
}