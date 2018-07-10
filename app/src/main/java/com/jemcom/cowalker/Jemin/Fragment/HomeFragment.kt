package com.jemcom.cowalker.Jemin.Fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager


import com.jemcom.cowalker.Jemin.Activity.ProjectIntroCreaterActivity
import com.jemcom.cowalker.Jemin.Adapter.ProjectAdapter
import com.jemcom.cowalker.Network.ApplicationController
import com.jemcom.cowalker.Network.Get.GetProjectDetailMessage
import com.jemcom.cowalker.Network.Get.GetProjectMessage
import com.jemcom.cowalker.Network.Get.ProjectItem
import com.jemcom.cowalker.Network.Get.Response.GetProjectDetailResponse
import com.jemcom.cowalker.Network.Get.Response.GetProjectResponse
import com.jemcom.cowalker.Network.NetworkService
import com.jemcom.cowalker.R
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_message.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import java.util.ArrayList
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.jemcom.cowalker.Hyunmin.Activity.ProjectIntroActivity
import com.jemcom.cowalker.Jemin.Activity.ProjectIntroParticipActivity
import com.jemcom.cowalker.Jemin.Activity.ProjectIntroWaitActivity
import com.jemcom.cowalker.Network.Get.ProjectDetailItem


class HomeFragment : Fragment(), View.OnClickListener {

    var projectItems : ArrayList<ProjectItem> = ArrayList()
    var projectDetailItems : ArrayList<ProjectDetailItem> = ArrayList()
    lateinit var networkService : NetworkService
    lateinit var projectAdapter : ProjectAdapter
    var data : ArrayList<GetProjectMessage> = ArrayList<GetProjectMessage>()
    lateinit var detailData : ArrayList<GetProjectDetailMessage>
    var detailimgUrl : ArrayList<String> = ArrayList()
    lateinit var requestManager : RequestManager // 이미지를 불러올 때 처리하는 변수
    lateinit var project_idx : String
    lateinit var user : String
    lateinit var projectTitle : String
    lateinit var projectSummary : String
    lateinit var projectArea : String
    lateinit var projectDepartment : String
    lateinit var projectAim : String
    lateinit var projectExplain : String
    lateinit var test : String
    lateinit var projectCreateAt : String
    lateinit var projectTestUrl : String
    var projectImgUrl : ArrayList<String> = ArrayList()
    lateinit var projectUserName : String
    var projectUserPofileUrl : String? = null

    var token :String? =null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_home, container, false)
        networkService = ApplicationController.instance.networkSerVice // 어플리케이션을 실행하자마자 어플리케이션 콘트롤러가 실행되는데 그 때 사용?
        requestManager = Glide.with(this)
        val pref = this.activity!!.getSharedPreferences("pref", Activity.MODE_PRIVATE)
        token = pref.getString("token","")
        get(view)


        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


       // GetProjectResponseData = ArrayList()
        //initDataSet()

    }


    override fun onClick(v: View) {
        var idx = recyclerview!!.getChildAdapterPosition(v)
        project_idx = data!![idx]._id
        Log.v("TAG", "선택한 프로젝트 idx = "+project_idx)
        Log.v("TAG", "토큰 = "+ token)



        getProjectDetail();

        val intent = Intent(v.context, ProjectIntroCreaterActivity::class.java)
       // intent.putExtra("title", title)
        startActivity(intent)
    }


    fun getProjectDetail()
    {
        Log.v("TAG","플젝넘버=" + project_idx)
        var getProjectDetailResponse = networkService.getDetailProject("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoxMDYsImlhdCI6MTUzMTA3OTA5MywiZXhwIjoxNTMzNjcxMDkzfQ.ZWdFvvvkoW9wnD5wBUT8zvKlpks0krr_Z-DMgfl8qPI22", project_idx) // 네트워크 서비스의 getContent 함수를 받아옴
        getProjectDetailResponse.enqueue(object : Callback<GetProjectDetailResponse> {
            override fun onResponse(call: Call<GetProjectDetailResponse>?, response: Response<GetProjectDetailResponse>?) {
                Log.v("TAG","세부 사항 GET 통신 성공")
                if(response!!.isSuccessful)
                {
                    user = response.body().user
                    Log.v("TAG", "유저 상태 = " + user)
                    Log.v("TAG","세부 사항 값 갖고오기 성공")
                    detailData = response.body().result

                    projectTitle = detailData[0].title
                    projectSummary = detailData[0].summary
                    projectArea = detailData[0].area
                    projectDepartment = detailData[0].department
                    projectAim = detailData[0].aim
                    projectExplain = detailData[0].explain
                    projectCreateAt = detailData[0].create_at
                    projectTestUrl =  detailData[0].img_url[0]

                    projectUserName = detailData[0].project_user_name
                    projectUserPofileUrl = detailData[0].title

                    Log.v("TAG", "세부 사항 제목 = "+ projectTitle + ", 요약 = " + projectSummary + ", 지역 = " + projectArea + ", 분야 = " + projectDepartment
                            + ", 목적 = " + projectAim + ", 설명 = " + projectExplain + ", 만든 날짜 = " + projectCreateAt + ", 이미지주소 = " + projectTestUrl + ", 유저명 = "
                            + projectUserName + ", 프로필 주소 = " + projectUserPofileUrl)



                    if(user=="개발자")
                    {
                        val intent = Intent(getActivity(), ProjectIntroCreaterActivity::class.java)
                        intent.putExtra("title", projectTitle)
                        intent.putExtra("summary", projectSummary)
                        intent.putExtra("area", projectArea)
                        intent.putExtra("department", projectDepartment)
                        intent.putExtra("aim", projectAim)
                        intent.putExtra("explain", projectExplain)
                        intent.putExtra("name", projectUserName)
                        intent.putExtra("img_url", projectTestUrl)
                        intent.putExtra("project_idx", project_idx)
                        startActivity(intent)
                    }
                    else if(user=="참여하기")
                    {
                        //val intent = Intent(getActivity(), ProjectIntroActivity::class.java)
                        val intent = Intent(getActivity(), ProjectIntroCreaterActivity::class.java)
                        intent.putExtra("title", projectTitle)
                        intent.putExtra("summary", projectSummary)
                        intent.putExtra("area", projectArea)
                        intent.putExtra("department", projectDepartment)
                        intent.putExtra("aim", projectAim)
                        intent.putExtra("explain", projectExplain)
                        intent.putExtra("name", projectUserName)
                        intent.putExtra("img_url", projectTestUrl)
                        intent.putExtra("project_idx", project_idx)
                        startActivity(intent)
                    }
                    else if(user=="참여대기")
                    {
                        val intent = Intent(getActivity(), ProjectIntroWaitActivity::class.java)
                        startActivity(intent)
                    }
                    // 참여 멤버
                    else
                    {
                        val intent = Intent(getActivity(), ProjectIntroParticipActivity::class.java)
                        startActivity(intent)
                    }

                }
            }

            override fun onFailure(call: Call<GetProjectDetailResponse>?, t: Throwable?) {
                Log.v("TAG","세부사항 통신 실패")

            }

        })
    }

    fun get(v : View)
    {
        var getProjectResponse = networkService.getProject("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoxMDYsImlhdCI6MTUzMTA3OTA5MywiZXhwIjoxNTMzNjcxMDkzfQ.ZWdFvvvkoW9wnD5wBUT8zvKlpks0krr_Z-DMgfl8qPI22") // 네트워크 서비스의 getContent 함수를 받아옴
        getProjectResponse.enqueue(object : Callback<GetProjectResponse> {
            override fun onResponse(call: Call<GetProjectResponse>?, response: Response<GetProjectResponse>?) {
                Log.v("TAG","GET 통신 성공")
                if(response!!.isSuccessful)
                {
                    Log.v("TAG","값 갖고오기 성공")
                    data = response.body().result
                    test = data.toString()
                    Log.v("TAG","데이터 값"+ test)
                    for(i in 0..2)
                    {
                        Log.v("TAG","위치 = "+i)
                        Log.v("TAG", "테스트 = " + data[i].img_url[0]+data[i].title+data[i].area+data[i].department+data[i].aim)
                        Log.v("TAG", "데이터크기 = "  + data.size)
                        //projectItems.add(ProjectItem(  "https://project-cowalker.s3.ap-northeast-2.amazonaws.com/1530802712097.jpg","asdf","asdg","ASDf","asdf"))
                        projectItems.add(ProjectItem( data[i].img_url[0],data[i].title!!,data[i].area!!,data[i].department!!,data[i].aim!!))
                        projectAdapter = ProjectAdapter(projectItems,requestManager)


                    }


                    projectAdapter.setOnItemClickListener(this@HomeFragment)
                    v.recyclerview.layoutManager = GridLayoutManager(v.context, 2)
                    v.recyclerview.adapter = projectAdapter

                }
            }

            override fun onFailure(call: Call<GetProjectResponse>?, t: Throwable?) {
                Log.v("TAG","통신 실패")
            }
        })
    }


}// Required empty public constructor