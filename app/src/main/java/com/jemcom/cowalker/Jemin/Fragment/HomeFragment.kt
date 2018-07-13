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
import com.jemcom.cowalker.Jemin.Activity.ProjectDetailActivity


import com.jemcom.cowalker.Jemin.Adapter.ProjectAdapter
import com.jemcom.cowalker.Network.ApplicationController
import com.jemcom.cowalker.Network.Get.GetProjectDetailMessage
import com.jemcom.cowalker.Network.Get.GetProjectMessage
import com.jemcom.cowalker.Jemin.Item.ProjectItem
import com.jemcom.cowalker.Network.Get.Response.GetProjectResponse
import com.jemcom.cowalker.Network.NetworkService
import com.jemcom.cowalker.R
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import java.util.ArrayList


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
    var projectUserPofileUrl : String? = ""
    var userIdx : String? = ""

    var token :String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_home, container, false)
        networkService = ApplicationController.instance.networkSerVice // 어플리케이션을 실행하자마자 어플리케이션 콘트롤러가 실행되는데 그 때 사용?
        requestManager = Glide.with(this)

        val pref = view.context.getSharedPreferences("auto",Activity.MODE_PRIVATE)
        token = pref.getString("token","")
        Log.v("Home Fragment","홈에서 토큰 값 = " + token);
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

        val intent = Intent(getActivity(), ProjectDetailActivity::class.java)
        intent.putExtra("project_idx", project_idx)
        startActivity(intent)

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