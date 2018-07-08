package com.jemcom.cowalker.Nuri.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.RequestManager
import com.jemcom.cowalker.Nuri.Holder.MessageViewHolder
import com.jemcom.cowalker.Nuri.Item.MessageItem
import com.jemcom.cowalker.R

class MessageAdapter(private var messageItems : ArrayList<MessageItem>, var requestManager : RequestManager) : RecyclerView.Adapter<MessageViewHolder>() {

    private lateinit var onItemClick : View.OnClickListener

    fun setOnItemClickListener(I : View.OnClickListener)
    {
        onItemClick = I
    }

    //내가 쓸 뷰홀더가 뭔지를 적어준다.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val mainView : View = LayoutInflater.from(parent.context)
                .inflate(R.layout.message_item, parent, false)
        mainView.setOnClickListener(onItemClick)
        return MessageViewHolder(mainView)
    }

    override fun getItemCount(): Int = messageItems.size

    //데이터클래스와 뷰홀더를 이어준다.
    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        requestManager.load(messageItems[position].profile).into(holder.messageProfile)
        holder.messageName.text = messageItems[position].name
        holder.messagePreview.text = messageItems[position].preView
        holder.messageDate.text = messageItems[position].date
    }
}