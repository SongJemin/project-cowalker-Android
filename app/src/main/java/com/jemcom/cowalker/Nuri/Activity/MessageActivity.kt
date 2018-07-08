package com.jemcom.cowalker.Nuri.Activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.jemcom.cowalker.Nuri.Adapter.ChatAdapter
import com.jemcom.cowalker.Nuri.Item.ChatItem
import com.jemcom.cowalker.Network.ApplicationController
import com.jemcom.cowalker.Network.Get.Response.GetMessageLookResponse
import com.jemcom.cowalker.Network.NetworkService
import com.jemcom.cowalker.R
import kotlinx.android.synthetic.main.activity_message.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MessageActivity : AppCompatActivity(),View.OnClickListener {

    var chatItems : ArrayList<ChatItem> = ArrayList()
    lateinit var chatAdapter : ChatAdapter
    lateinit var networkService: NetworkService

    override fun onClick(v: View?) {
        when(v)
        {
            message_send_iv -> {
                val intent = Intent(applicationContext, Notice_messageActivity::class.java)
                intent.putExtra("partner_id",getIntent().getStringExtra("partner_id"))
                System.out.println("안뇽" + intent.getStringExtra("partner_id"))
                startActivity(intent)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)

        networkService = ApplicationController.instance.networkSerVice

        get()

        message_name_tv.setText(intent.getStringExtra("partner_id"))
        message_send_iv.setOnClickListener(this)

        chatAdapter = ChatAdapter(chatItems)
        chatAdapter.setOnItemClickListener(this)
        chat_rv.layoutManager = LinearLayoutManager(this)
        chat_rv.adapter = chatAdapter
    }

    fun get()
    {

        var partner_id = intent.getStringExtra("partner_id")
        var getMessageLookResponse = networkService.getMessageLook("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoyLCJpYXQiOjE1MzA5NTE1ODMsImV4cCI6MTUzMzU0MzU4M30.90d2qcRcikydx8R-lMMyLgcYGcAxY0Poi61a-NGpujY",partner_id)
        getMessageLookResponse.enqueue(object : Callback<GetMessageLookResponse> {
            override fun onFailure(call: Call<GetMessageLookResponse>?, t: Throwable?) {
                Toast.makeText(applicationContext,"서버 연결 실패", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<GetMessageLookResponse>?, response: Response<GetMessageLookResponse>?) {
                if(response!!.isSuccessful)
                {
                    var data = response.body().result
                    for(i in 0..data.size-1)
                    {
                        chatItems.add(ChatItem(data[i].from_user_name, data[i].contents, "56분 전"))
                        chatAdapter = ChatAdapter(chatItems)
                        chatAdapter.setOnItemClickListener(this@MessageActivity)
                        chat_rv.layoutManager = LinearLayoutManager(applicationContext)
                        chat_rv.adapter = chatAdapter
                    }
                    message_name_tv.setText(data[0].to_user_name)
                }
                else Toast.makeText(applicationContext,"실패", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
