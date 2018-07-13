package com.jemcom.cowalker.Jemin.Activity

import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.jemcom.cowalker.Jemin.Adapter.MemberAdapter
import com.jemcom.cowalker.Jemin.Adapter.ProjectAdapter
import com.jemcom.cowalker.Jemin.Item.ProjectItem
import com.jemcom.cowalker.Network.ApplicationController
import com.jemcom.cowalker.Network.Get.GetProjectMemberMessage
import com.jemcom.cowalker.Network.Get.Response.GetProjectMemberResponse
import com.jemcom.cowalker.Network.NetworkService

import com.jemcom.cowalker.R
import kotlinx.android.synthetic.main.activity_project_member.*
import kotlinx.android.synthetic.main.activity_test2.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProjectMemberActivity : AppCompatActivity() {


    var testResult : String = ""
    var project_idx: String = ""
    var projectMemberItems : ArrayList<GetProjectMemberMessage> = ArrayList()
    lateinit var memberData : ArrayList<GetProjectMemberMessage>
    lateinit var networkService : NetworkService
    var memberNumber : Int = 0;
    var memberPosition : String= "";
    var profileUrl : String= "";
    lateinit var requestManager : RequestManager // 이미지를 불러올 때 처리하는 변수
    lateinit var memberAdapter : MemberAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        networkService = ApplicationController.instance.networkSerVice // 어플리케이션을 실행하자마자 어플리케이션 콘트롤러가 실행되는데 그 때 사용?
        requestManager = Glide.with(this)
        setContentView(R.layout.activity_project_member)

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
        val intent = intent
        project_idx = intent.getStringExtra("project_idx")

            getProjectMember()


    }

    fun getProjectMember()
    {
        var getProjectMemberResponse = networkService.getMemberProject(project_idx) // 네트워크 서비스의 getContent 함수를 받아옴
        getProjectMemberResponse.enqueue(object : Callback<GetProjectMemberResponse> {
            override fun onResponse(call: Call<GetProjectMemberResponse>?, response: Response<GetProjectMemberResponse>?) {
                Log.v("TAG","멤버 통신 성공")
                if(response!!.isSuccessful)
                {
                    Log.v("TAG","멤버값 갖고오기 성공")
                    memberData = response.body().member

                    testResult = projectMemberItems.toString()
                    Log.v("TAG", "Test Reulst = " + testResult)


                    memberAdapter = MemberAdapter(memberData,requestManager)
                    //memberAdapter.setOnItemClickListener()
                    member_list_recyclerview.layoutManager = LinearLayoutManager( this@ProjectMemberActivity)
                    member_list_recyclerview.adapter = memberAdapter

                }


            }

            override fun onFailure(call: Call<GetProjectMemberResponse>?, t: Throwable?) {
                Log.v("TAG","통신 실패")

            }

        })
    }
}
