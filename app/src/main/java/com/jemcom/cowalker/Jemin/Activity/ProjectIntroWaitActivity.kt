package com.jemcom.cowalker.Jemin.Activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.RequestManager
import com.jemcom.cowalker.Network.ApplicationController
import com.jemcom.cowalker.Network.Get.GetRecruitList
import com.jemcom.cowalker.Network.Get.Response.GetRecruitListResponse
import com.jemcom.cowalker.Network.NetworkService
import com.jemcom.cowalker.Nuri.Activity.RecruitDetailActivity
import com.jemcom.cowalker.Nuri.Adapter.RecruitListGetAdapter
import com.jemcom.cowalker.Nuri.Item.RecruitListItem

import com.jemcom.cowalker.R
import kotlinx.android.synthetic.main.activity_project_intro_particip.*
import kotlinx.android.synthetic.main.activity_project_intro_wait.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProjectIntroWaitActivity : AppCompatActivity(), View.OnClickListener  {
    override fun onClick(v: View) {
        var idx = recruit_list_recyclerview4.getChildAdapterPosition(v)
        recruit_idx = data!![idx].recruit_idx!!
        Log.v("TAG", "참여하기 선택한 모집번호 = "+ recruit_idx)

        val intent = Intent(v.context, RecruitDetailActivity::class.java)
        intent.putExtra("project_idx", project_idx)
        intent.putExtra("recruit_idx", recruit_idx)
        startActivity(intent)
    }
    var data : java.util.ArrayList<GetRecruitList> = java.util.ArrayList<GetRecruitList>()
    lateinit var networkService: NetworkService

    lateinit var recruitListItems: ArrayList<RecruitListItem>
    lateinit var recruitListGetAdapter : RecruitListGetAdapter

    var title: String = ""
    var summary: String = ""
    var aim: String = ""
    var department: String = ""
    var area: String = ""
    var explain: String = ""
    var name: String = ""
    var img_url: String = ""
    var project_idx: String = ""
    lateinit var requestManager: RequestManager
    var recruit_idx : String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_intro_wait)
        recruitListItems = ArrayList()
        networkService = ApplicationController.instance.networkSerVice // 어플리케이션을 실행하자마자 어플리케이션 콘트롤러가 실행되는데 그 때 사용?

        val intent = intent
        if(intent != null) {
            if(intent.getStringExtra("title") != null) title = intent.getStringExtra("title")
            if(intent.getStringExtra("summary") != null) summary = intent.getStringExtra("summary")
            if(intent.getStringExtra("aim") != null) aim = intent.getStringExtra("aim")
            if(intent.getStringExtra("department") != null) department = intent.getStringExtra("department")
            if(intent.getStringExtra("area") != null) area = intent.getStringExtra("area")
            if(intent.getStringExtra("explain") != null) explain = intent.getStringExtra("explain")
            if(intent.getStringExtra("name") != null) name = intent.getStringExtra("name")
            if(intent.getStringExtra("img_url") != null) img_url = intent.getStringExtra("img_url")
            if(intent.getStringExtra("project_idx") != null) project_idx = intent.getStringExtra("project_idx")
            getList()
        }
    }

    fun getList()
    {
        Log.v("ParticipActivity", "참여 완료 프로젝트 번호 = " + project_idx)
        //var project_idx = "2"
        var getRecruitListResponse = networkService.getRecruitList(project_idx)
        //var getRecruitListResponse = networkService.getRecruitList(project_idx)

        getRecruitListResponse.enqueue(object : Callback<GetRecruitListResponse> {
            override fun onFailure(call: Call<GetRecruitListResponse>?, t: Throwable?) {
                Toast.makeText(applicationContext,"서버 연결 실패", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<GetRecruitListResponse>?, response: Response<GetRecruitListResponse>?) {
                Log.v("TAG","모집 리스트 통신")
                if(response!!.isSuccessful)
                {
                    Log.v("TAG","모집 리스트 받아오기")
                    data = response.body().result
                    Log.v("TAG","모집 리스트 값 = "+data.toString())

                    for(i in 0..data.size-1)
                    {

                        recruitListItems.add(RecruitListItem(data[i].position,data[i].number,data[i].task,data[i].dday))
                    }
                    recruitListGetAdapter = RecruitListGetAdapter(recruitListItems)
                    recruitListGetAdapter.setOnItemClickListener(this@ProjectIntroWaitActivity)
                    recruit_list_recyclerview3.layoutManager = LinearLayoutManager(applicationContext)
                    recruit_list_recyclerview3.adapter = recruitListGetAdapter
                }
                // else Toast.makeText(applicationContext,"실패",Toast.LENGTH_SHORT).show()
            }
        })
    }
}
