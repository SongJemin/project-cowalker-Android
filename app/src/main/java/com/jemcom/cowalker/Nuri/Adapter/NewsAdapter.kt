package com.jemcom.cowalker.Nuri.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jemcom.cowalker.Nuri.Holder.NewsViewHolder
import com.jemcom.cowalker.Nuri.Item.NewsItem
import com.jemcom.cowalker.R

class NewsAdapter(private var newsItems : ArrayList<NewsItem>) : RecyclerView.Adapter<NewsViewHolder>() {

    private lateinit var onItemClick : View.OnClickListener

    fun setOnItemClickListener(I : View.OnClickListener)
    {
        onItemClick = I
    }

    //내가 쓸 뷰홀더가 뭔지를 적어준다.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val mainView : View = LayoutInflater.from(parent.context)
                .inflate(R.layout.news_item, parent, false)
        mainView.setOnClickListener(onItemClick)
        return NewsViewHolder(mainView)
    }

    override fun getItemCount(): Int = newsItems.size

    //데이터클래스와 뷰홀더를 이어준다.
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.newsProfile.setImageResource(newsItems[position].profile)
        holder.newsName.text = newsItems[position].name
        holder.newsPreview.text = newsItems[position].preView
        holder.newsDate.text = newsItems[position].date
    }
}