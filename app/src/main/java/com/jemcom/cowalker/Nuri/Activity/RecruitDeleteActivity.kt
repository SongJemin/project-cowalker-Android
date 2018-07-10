package com.jemcom.cowalker.Nuri.Activity

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.jemcom.cowalker.Network.ApplicationController
import com.jemcom.cowalker.Network.Get.GetRecruitList
import com.jemcom.cowalker.Network.Get.Response.GetRecruitListResponse
import com.jemcom.cowalker.Network.NetworkService
import com.jemcom.cowalker.Nuri.Adapter.RecruitListAdapter
import com.jemcom.cowalker.Nuri.Item.RecruitListItem
import com.jemcom.cowalker.R
import kotlinx.android.synthetic.main.activity_recruit_delete.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecruitDeleteActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var networkService: NetworkService
    var recruitListItems : ArrayList<RecruitListItem> = ArrayList()
    lateinit var recruitListAdapter : RecruitListAdapter

    override fun onClick(v: View?) {
        val index : Int = recruitList_rv.getChildAdapterPosition(v)

        recruitList_rv.getChildViewHolder(v)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recruit_delete)

        networkService = ApplicationController.instance.networkSerVice

        get()
    }
    fun get()
    {
        var project_idx = "2"
        var getRecruitListResponse = networkService.getRecruitList(project_idx)

        getRecruitListResponse.enqueue(object : Callback<GetRecruitListResponse>{
            override fun onFailure(call: Call<GetRecruitListResponse>?, t: Throwable?) {
                Toast.makeText(applicationContext,"서버 연결 실패", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<GetRecruitListResponse>?, response: Response<GetRecruitListResponse>?) {
                if(response!!.isSuccessful)
                {
                    var data = response.body().result
                    for(i in 0..data.size-1)
                    {
                        recruitListItems.add(RecruitListItem(data[i].position,data[i].number,data[i].task,data[i].dday))
                    }
                    recruitListAdapter = RecruitListAdapter(recruitListItems)
                    recruitListAdapter.setOnItemClickListener(this@RecruitDeleteActivity)
                    recruitList_rv.layoutManager = LinearLayoutManager(applicationContext)
                    recruitList_rv.adapter = recruitListAdapter
                }
                else Toast.makeText(applicationContext,"실패",Toast.LENGTH_SHORT).show()
            }
        })
    }
}
