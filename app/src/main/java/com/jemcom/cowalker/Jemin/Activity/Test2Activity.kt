package com.jemcom.cowalker.Jemin.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import com.jemcom.cowalker.Network.ApplicationController
import com.jemcom.cowalker.Network.Get.GetProjectMemberMessage
import com.jemcom.cowalker.Network.Get.Response.GetProjectMemberResponse
import com.jemcom.cowalker.Network.NetworkService

import com.jemcom.cowalker.R
import kotlinx.android.synthetic.main.activity_test2.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Test2Activity : AppCompatActivity() {
    var testResult : String = ""
    var project_idx: String = ""
    var projectMemberItems : ArrayList<GetProjectMemberMessage> = ArrayList()
    lateinit var memberData : ArrayList<GetProjectMemberMessage>
    lateinit var networkService : NetworkService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        networkService = ApplicationController.instance.networkSerVice // 어플리케이션을 실행하자마자 어플리케이션 콘트롤러가 실행되는데 그 때 사용?
        setContentView(R.layout.activity_test2)
        val intent = intent
        project_idx = intent.getStringExtra("project_idx")
        test_btn.setOnClickListener{

        }
    }

    fun getProjectMember()
    {
        var getProjectMemberResponse = networkService.getMemberProject(project_idx) // 네트워크 서비스의 getContent 함수를 받아옴
        getProjectMemberResponse.enqueue(object : Callback<GetProjectMemberResponse> {
            override fun onResponse(call: Call<GetProjectMemberResponse>?, response: Response<GetProjectMemberResponse>?) {
                Log.v("TAG","GET 통신 성공")
                if(response!!.isSuccessful)
                {
                    Log.v("TAG","값 갖고오기 성공")
                    memberData = response.body().member
                    for(i in 0..memberData.size-1)
                    {
                        Log.v("TAG","위치 = "+i)
                        Log.v("TAG", "데이터크기 = "  + memberData.size)
                        //projectItems.add(ProjectItem(  "https://project-cowalker.s3.ap-northeast-2.amazonaws.com/1530802712097.jpg","asdf","asdg","ASDf","asdf"))
                        projectMemberItems.add(GetProjectMemberMessage(memberData[i].member_idx, memberData[i].position, memberData[i].profile_url))

                    }

                    testResult = projectMemberItems.toString()
                    Log.v("TAG", "Test Reulst = " + testResult)

                }
            }

            override fun onFailure(call: Call<GetProjectMemberResponse>?, t: Throwable?) {
                Log.v("TAG","통신 실패")

            }

        })
    }
}
