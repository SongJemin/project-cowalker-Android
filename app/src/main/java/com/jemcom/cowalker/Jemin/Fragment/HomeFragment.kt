package com.jemcom.cowalker.Jemin.Fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.jemcom.cowalker.Hyunmin.Activity.ProjectIntroActivity


import com.jemcom.cowalker.Jemin.Activity.ProjectIntroCreaterActivity
import com.jemcom.cowalker.Jemin.Adapter.ProjectAdapter
import com.jemcom.cowalker.Network.ApplicationController
import com.jemcom.cowalker.Network.Get.GetProjectDetailMessage
import com.jemcom.cowalker.Network.Get.GetProjectMessage
import com.jemcom.cowalker.Jemin.Item.ProjectItem
import com.jemcom.cowalker.Network.Get.Response.GetProjectDetailResponse
import com.jemcom.cowalker.Network.Get.Response.GetProjectResponse
import com.jemcom.cowalker.Network.NetworkService
import com.jemcom.cowalker.R
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import java.util.ArrayList
import com.jemcom.cowalker.Jemin.Activity.ProjectIntroParticipActivity
import com.jemcom.cowalker.Jemin.Activity.ProjectIntroWaitActivity
import com.jemcom.cowalker.Jemin.Item.ProjectDetailItem


class HomeFragment : Fragment(), View.OnClickListener {

    var projectItems : ArrayList<ProjectItem> = ArrayList()
    lateinit var networkService : NetworkService
    lateinit var projectAdapter : ProjectAdapter
    var data : ArrayList<GetProjectMessage> = ArrayList<GetProjectMessage>()
    lateinit var detailData : ArrayList<GetProjectDetailMessage>
    var detailimgUrl : ArrayList<String> = ArrayList()
    lateinit var requestManager : RequestManager // 이미지를 불러올 때 처리하는 변수
    lateinit var project_idx : String
    var userResult : String = ""
    var projectTitle : String = ""
    var projectSummary : String = ""
    var projectArea : String = ""
    var projectDepartment : String? = null
    var projectAim : String = ""
    var projectExplain : String = ""
    var test : String = ""
    var projectCreateAt : String = ""
    lateinit var projectTestUrl : String
    lateinit var projectUserName : String
    var projectUserPofileUrl : String? = null

    var token :String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_home, container, false)
        networkService = ApplicationController.instance.networkSerVice // 어플리케이션을 실행하자마자 어플리케이션 콘트롤러가 실행되는데 그 때 사용?
        requestManager = Glide.with(this)

        val pref = view.context.getSharedPreferences("auto",Activity.MODE_PRIVATE)
        token = pref.getString("token","")
        Log.v("TAG","홈에서 토큰 값 = " + token);
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
        project_idx = data!![idx]._id!!
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
        Log.v("TAG","홈 플젝 세부사항 조회 토큰 =" + token)
        var getProjectDetailResponse = networkService.getDetailProject(token, project_idx) // 네트워크 서비스의 getContent 함수를 받아옴
        getProjectDetailResponse.enqueue(object : Callback<GetProjectDetailResponse> {
            override fun onResponse(call: Call<GetProjectDetailResponse>?, response: Response<GetProjectDetailResponse>?) {
                Log.v("TAG","세부 사항 GET 통신 성공")
                if(response!!.isSuccessful)
                {
                    userResult = response.body().user
                    Log.v("TAG", "유저 상태 = " + userResult)
                    Log.v("TAG","세부 사항 값 갖고오기 성공")
                    detailData = response.body().result

                    projectTitle = detailData[0].title

                    if(detailData[0].summary == null)
                    {
                        detailData[0].summary = "null"
                    }
                    projectSummary = detailData[0].summary
                    if(detailData[0].area == null)
                    {
                        detailData[0].area = "null"
                    }
                    projectArea = detailData[0].area

                    if(detailData[0].department==null)
                    {
                        detailData[0].department="null"
                    }
                    projectDepartment = detailData[0].department

                    if(detailData[0].aim==null){
                        detailData[0].aim = "null"
                    }
                    projectAim = detailData[0].aim

                    if(detailData[0].explain==null){
                        detailData[0].explain = "null"
                    }
                    projectExplain = detailData[0].explain

                    projectCreateAt = detailData[0].create_at
                    projectTestUrl =  detailData[0].img_url[0]

                    projectUserName = detailData[0].project_user_name
                    projectUserPofileUrl = detailData[0].title

                    Log.v("TAG", "세부 사항 제목 = "+ projectTitle + ", 요약 = " + projectSummary + ", 지역 = " + projectArea + ", 분야 = " + projectDepartment
                            + ", 목적 = " + projectAim + ", 설명 = " + projectExplain + ", 만든 날짜 = " + projectCreateAt + ", 이미지주소 = " + projectTestUrl + ", 유저명 = "
                            + projectUserName + ", 프로필 주소 = " + projectUserPofileUrl)



                    if(userResult=="개설자")
                    {
                        val intent = Intent(getActivity(), ProjectIntroActivity::class.java)
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
                    else if(userResult=="참여하기")
                    { val intent = Intent(getActivity(), ProjectIntroActivity::class.java)
                        //val intent = Intent(getActivity(), ProjectIntroActivity::class.java)
                        //val intent = Intent(getActivity(), ProjectIntroActivity::class.java)
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
                    else if(userResult=="참여대기")
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
                    for(i in 0..data.size-1) {
                      //  Log.v("TAG", "위치 = " + i)
                       // Log.v("TAG","테스트 제목 = " + data[i].title)
                       // Log.v("TAG", "장소 = " + data[i].area)
                       // Log.v("TAG", "분야 = " + data[i].department)
                      //  Log.v("TAG", "역할 = " + data[i].aim)
                        //Log.v("TAG", "imgurl = " + data[i].img_url[0])
                        //projectItems.add(ProjectItem(  "https://project-cowalker.s3.ap-northeast-2.amazonaws.com/1530802712097.jpg","asdf","asdg","ASDf","asdf"))
                        if (data[i].department == null){
                            data[i].department= " "
                            Log.v("TAG","널값 발견")
                        }

                        if (data[i].img_url!!.size == 0){
                            data[i].img_url!!.add("https://project-cowalker.s3.ap-northeast-2.amazonaws.com/1531124962614.png")
                        }

                        if(data[i].aim == null){
                            data[i].aim=="null"
                        }
                        else if(data[i].title == null){
                            data[i].title=="null"
                        }
                        else if(data[i].area == null){
                            data[i].area=="null"
                        }
                        projectItems.add(ProjectItem(data[i].img_url!![0], data[i].title, data[i].area, data[i].department, data[i].aim))
                        //projectItems.add(ProjectItem("https://project-cowalker.s3.ap-northeast-2.amazonaws.com/1531113346984.jpg", "ㅁㄴㅇㅎ", "ㅁㄴㅇㄹㄴㅁㅇㅎ", "ㅁㄴㅇㄹ", "ㅇㅎㅁㄴㅇㄹ"))
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