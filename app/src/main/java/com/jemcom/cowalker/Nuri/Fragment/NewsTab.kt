package com.jemcom.cowalker.Nuri.Fragment

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.jemcom.cowalker.Network.ApplicationController
import com.jemcom.cowalker.Network.Get.Response.GetAlarmResponse
import com.jemcom.cowalker.Network.NetworkService
import com.jemcom.cowalker.Nuri.Adapter.NewsAdapter
import com.jemcom.cowalker.Nuri.Item.NewsItem
import com.jemcom.cowalker.R
import kotlinx.android.synthetic.main.fragment_news.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsTab : Fragment() {

    var newsItems : ArrayList<NewsItem> = ArrayList()
    lateinit var newsAdapter : NewsAdapter
    lateinit var networkService: NetworkService


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_news,container,false)

        networkService = ApplicationController.instance.networkSerVice

        get(v)

        return v;
    }

    fun get(v : View)
    {
        val pref = v.context.getSharedPreferences("auto", Activity.MODE_PRIVATE)
        val token = pref.getString("token","")
        var getAlarmResponse = networkService.getAlarm(token)

        getAlarmResponse.enqueue(object : Callback<GetAlarmResponse>
        {
            override fun onFailure(call: Call<GetAlarmResponse>?, t: Throwable?) {
                Toast.makeText(v.context,"서버 연결 실패", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<GetAlarmResponse>?, response: Response<GetAlarmResponse>?) {
                if(response!!.isSuccessful)
                {
                    var data = response.body().result
                    for(i in 0..data.size-1)
                    {
                        newsItems.add(NewsItem(data[i].project_name, data[i].contents, "56분 전"))
                    }
                    newsAdapter = NewsAdapter(newsItems)
                    v.news_rv.layoutManager = LinearLayoutManager(v.context)
                    v.news_rv.adapter = newsAdapter
                }
                else Toast.makeText(v.context,"실패",Toast.LENGTH_SHORT).show()
            }
        })
    }
}