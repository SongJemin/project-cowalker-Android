package com.jemcom.cowalker.Nuri.Fragment

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.jemcom.cowalker.Nuri.Adapter.MessageAdapter
import com.jemcom.cowalker.Nuri.Item.MessageItem
import com.jemcom.cowalker.Network.ApplicationController
import com.jemcom.cowalker.Network.Get.GetMessage
import com.jemcom.cowalker.Network.Get.Response.GetMessageResponse
import com.jemcom.cowalker.Network.NetworkService
import com.jemcom.cowalker.Nuri.Activity.MessageActivity
import com.jemcom.cowalker.R
import kotlinx.android.synthetic.main.fragment_message.*
import kotlinx.android.synthetic.main.fragment_message.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MessageTab: Fragment(),View.OnClickListener {

    var messageItems : ArrayList<MessageItem> = ArrayList()
    lateinit var messageAdapter : MessageAdapter
    lateinit var networkService: NetworkService
    lateinit var requestManager : RequestManager
    lateinit var data : ArrayList<GetMessage>

    override fun onClick(v: View?) {
        val index : Int = message_rv.getChildAdapterPosition(v)
        var intent = Intent(v!!.context, MessageActivity::class.java)

        message_rv.getChildViewHolder(v).itemView.setBackgroundColor(Color.parseColor("#f6f6f6"))
        intent.putExtra("partner_id",data[index].partner_idx.toString())
        startActivity(intent)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_message,container,false)

        networkService = ApplicationController.instance.networkSerVice
        requestManager = Glide.with(this)

        get(v)

        return v;
    }

    fun get(v : View)
    {

        val pref = v.context.getSharedPreferences("auto", Activity.MODE_PRIVATE)
        val token = pref.getString("token","")
        var getMessageResponse = networkService.getMessage(token)
        getMessageResponse.enqueue(object : Callback<GetMessageResponse> {
            override fun onFailure(call: Call<GetMessageResponse>?, t: Throwable?) {
                Toast.makeText(v.context,"서버 연결 실패", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<GetMessageResponse>?, response: Response<GetMessageResponse>?) {
                if(response!!.isSuccessful)
                {
                    data = response.body().result

                    if(data.size > 0) {
                        for (i in 0..data.size - 1) {
//                        data[i].partner_profile_url
                            messageItems.add(MessageItem(data[i].partner_profile_url, data[i].partner_name, data[i].contents, data[i].time))
                            messageAdapter = MessageAdapter(messageItems, requestManager)
                            messageAdapter.setOnItemClickListener(this@MessageTab)
                            v.message_rv.layoutManager = LinearLayoutManager(v.context)
                            v.message_rv.adapter = messageAdapter
                        }
                    }
                }
                else Toast.makeText(v.context,"실패", Toast.LENGTH_SHORT).show()
            }
        })
    }
}