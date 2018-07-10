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
import com.jemcom.cowalker.Jemin.Activity.SearchFilterActivity
import com.jemcom.cowalker.Jemin.Item.ProjectDetailItem
import com.jemcom.cowalker.Network.Get.GetSearchMessage
import com.jemcom.cowalker.Network.Get.Response.GetSearchResponse
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.view.*
import android.R.attr.data




class SearchFragment : Fragment(), View.OnClickListener  {

    var aim : String = ""
    var department : String = ""
    var position : String = ""
    var area : String = ""
    var keyword : String = ""

    override fun onClick(v: View?) {
        search_filter_btn.setOnClickListener {
            val intent = Intent(activity, SearchFilterActivity::class.java)
           // startActivity(intent)
            startActivityForResult(intent,27)

        }
        search_keyword_btn.setOnClickListener{
            keyword = search_keyword_edit.text.toString()
            searchBoard()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 27){
            aim = data!!.getStringExtra("filterAim")
            department = data!!.getStringExtra("filterDepartment")
            position = data!!.getStringExtra("filterPosition")
            area = data!!.getStringExtra("filterArea")
            Log.v("TAG","목적 반환 값 ="+ aim)
            Log.v("TAG","분야 반환 값 ="+ department)
            Log.v("TAG","역할 반환 값 ="+ position)
            Log.v("TAG","장소 값 ="+ area)

        }
    }

    var searchItems : ArrayList<ProjectItem> = ArrayList()
    lateinit var networkService : NetworkService
    lateinit var projectAdapter : ProjectAdapter
    var searchData : ArrayList<GetSearchMessage> = ArrayList<GetSearchMessage>()
    lateinit var requestManager : RequestManager // 이미지를 불러올 때 처리하는 변수
    lateinit var project_idx : String
    lateinit var user : String

    var token :String? =null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_search, container, false)
        networkService = ApplicationController.instance.networkSerVice // 어플리케이션을 실행하자마자 어플리케이션 콘트롤러가 실행되는데 그 때 사용?
        requestManager = Glide.with(this)
        val pref = this.activity!!.getSharedPreferences("pref", Activity.MODE_PRIVATE)
        token = pref.getString("token","")
        view.search_filter_btn.setOnClickListener(this)
        view.search_keyword_btn.setOnClickListener(this)
        get(view)

        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }



    fun get(v : View)
    {
        var getSearchResponse = networkService.getSearch(aim,area,position,department,keyword) // 네트워크 서비스의 getContent 함수를 받아옴
        getSearchResponse.enqueue(object : Callback<GetSearchResponse> {
            override fun onResponse(call: Call<GetSearchResponse>?, response: Response<GetSearchResponse>?) {
                Log.v("TAG","GET 통신 성공")
                if(response!!.isSuccessful)
                {
                    Log.v("TAG","값 갖고오기 성공")
                    searchData = response.body().result
                    for(i in 0..searchData.size-1) {

                        if (searchData[i].department == null){
                            searchData[i].department= " "
                            Log.v("TAG","널값 발견")
                        }

                        if (searchData[i].img_url == null){
                            searchData[i].img_url="https://project-cowalker.s3.ap-northeast-2.amazonaws.com/1531124962614.png"
                        }

                        if(searchData[i].aim == null){
                            searchData[i].aim=="null"
                        }
                        else if(searchData[i].title == null){
                            searchData[i].title=="null"
                        }
                        else if(searchData[i].area == null){
                            searchData[i].area=="null"
                        }
                        searchItems.add(ProjectItem(searchData[i].img_url, searchData[i].title, searchData[i].area, searchData[i].department, searchData[i].aim))
                        //projectItems.add(ProjectItem("https://project-cowalker.s3.ap-northeast-2.amazonaws.com/1531113346984.jpg", "ㅁㄴㅇㅎ", "ㅁㄴㅇㄹㄴㅁㅇㅎ", "ㅁㄴㅇㄹ", "ㅇㅎㅁㄴㅇㄹ"))
                        projectAdapter = ProjectAdapter(searchItems,requestManager)
                    }

                    projectAdapter.setOnItemClickListener(this@SearchFragment)
                    v.search_recyclerview.layoutManager = GridLayoutManager(v.context, 2)
                    v.search_recyclerview.adapter = projectAdapter

                }
            }

            override fun onFailure(call: Call<GetSearchResponse>?, t: Throwable?) {
                Log.v("TAG","통신 실패")
            }

        })

    }

    fun searchBoard()
    {
        var getSearchResponse = networkService.getSearch(aim,area,position,department,keyword) // 네트워크 서비스의 getContent 함수를 받아옴
        getSearchResponse.enqueue(object : Callback<GetSearchResponse> {
            override fun onResponse(call: Call<GetSearchResponse>?, response: Response<GetSearchResponse>?) {
                Log.v("TAG","검색 GET 통신 성공")
                if(response!!.isSuccessful)
                {
                    Log.v("TAG","검색값 갖고오기 성공")
                    searchData = response.body().result
                    for(i in 0..searchData.size-1) {

                        if (searchData[i].department == null){
                            searchData[i].department= " "
                            Log.v("TAG","널값 발견")
                        }

                        if (searchData[i].img_url == null){
                            searchData[i].img_url="https://project-cowalker.s3.ap-northeast-2.amazonaws.com/1531124962614.png"
                        }

                        if(searchData[i].aim == null){
                            searchData[i].aim=="null"
                        }
                        else if(searchData[i].title == null){
                            searchData[i].title=="null"
                        }
                        else if(searchData[i].area == null){
                            searchData[i].area=="null"
                        }
                        searchItems.add(ProjectItem(searchData[i].img_url, searchData[i].title, searchData[i].area, searchData[i].department, searchData[i].aim))
                        //projectItems.add(ProjectItem("https://project-cowalker.s3.ap-northeast-2.amazonaws.com/1531113346984.jpg", "ㅁㄴㅇㅎ", "ㅁㄴㅇㄹㄴㅁㅇㅎ", "ㅁㄴㅇㄹ", "ㅇㅎㅁㄴㅇㄹ"))
                        projectAdapter = ProjectAdapter(searchItems,requestManager)
                    }

                    projectAdapter.setOnItemClickListener(this@SearchFragment)
                    search_recyclerview.layoutManager = GridLayoutManager(context, 2)
                    search_recyclerview.adapter = projectAdapter

                }
            }

            override fun onFailure(call: Call<GetSearchResponse>?, t: Throwable?) {
                Log.v("TAG","검색 통신 실패")
            }

        })

    }


}// Required empty public constructor