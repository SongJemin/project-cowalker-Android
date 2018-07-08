package com.jemcom.cowalker.Nuri.Fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jemcom.cowalker.Nuri.Activity.Notice_messageActivity
import com.jemcom.cowalker.Nuri.Adapter.NewsAdapter
import com.jemcom.cowalker.Nuri.Item.NewsItem
import com.jemcom.cowalker.R
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.android.synthetic.main.fragment_news.view.*

class NewsTab : Fragment(), View.OnClickListener {

    var newsItems : ArrayList<NewsItem> = ArrayList()
    lateinit var newsAdapter : NewsAdapter

    override fun onClick(v: View?) {
        var idx : Int = news_rv.getChildAdapterPosition(v)
        var intent = Intent(v!!.context, Notice_messageActivity::class.java)
        startActivity(intent)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_news,container,false)

        newsItems.add(NewsItem(R.drawable.profile, "코워커", "멤버 모집 마감 시간이 D-3 남았습니다.", "56분 전"))
        newsItems.add(NewsItem(R.drawable.profile, "블록체인 스터디", "지원이 성공적으로 완료되었습니다.", "2시간 전"))
        newsItems.add(NewsItem(R.drawable.profile, "공공서비스 어플리케이션 공모전", "이충엽 씨가 회원님을 추천하였습니다.", "3시간 전"))


        newsAdapter = NewsAdapter(newsItems)
        newsAdapter.setOnItemClickListener(this)
        v.news_rv.layoutManager = LinearLayoutManager(v.context)
        v.news_rv.adapter = newsAdapter
        return v;
    }
}