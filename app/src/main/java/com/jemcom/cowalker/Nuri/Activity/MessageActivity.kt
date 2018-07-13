package com.jemcom.cowalker.Nuri.Activity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Build
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
        val view = window.decorView
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (view != null) {
                // 23 버전 이상일 때 상태바 하얀 색상에 회색 아이콘 색상을 설정
                view.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                window.statusBarColor = Color.parseColor("#FFFFFF")
            }
        } else if (Build.VERSION.SDK_INT >= 21) {
            // 21 버전 이상일 때
            window.statusBarColor = Color.BLACK
        }
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

        val pref = applicationContext.getSharedPreferences("auto", Activity.MODE_PRIVATE)
        val token = pref.getString("token","")
        var partner_id = intent.getStringExtra("partner_id")
        var getMessageLookResponse = networkService.getMessageLook(token,partner_id)
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
