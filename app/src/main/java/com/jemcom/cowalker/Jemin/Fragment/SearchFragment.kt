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
import com.jemcom.cowalker.Network.Get.Response.GetProjectDetailResponse
import com.jemcom.cowalker.Network.NetworkService
import com.jemcom.cowalker.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import java.util.ArrayList
import com.jemcom.cowalker.Jemin.Activity.SearchFilterActivity
import com.jemcom.cowalker.Network.Get.GetSearchMessage
import com.jemcom.cowalker.Network.Get.Response.GetSearchResponse
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.view.*


class SearchFragment : Fragment(), View.OnClickListener  {
    var projectItems : ArrayList<ProjectItem> = ArrayList()
    lateinit var networkService : NetworkService
    var data : ArrayList<GetProjectMessage> = ArrayList<GetProjectMessage>()
    lateinit var detailData : ArrayList<GetProjectDetailMessage>
    var detailimgUrl : ArrayList<String> = ArrayList()
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



    var aim : String = ""
    var department : String = ""
    var position : String = ""
    var area : String = ""
    var keyword : String = ""

    override fun onClick(v: View?) {


            var idx = search_recyclerview!!.getChildAdapterPosition(v)
            project_idx = searchData!![idx].project_idx!!
            Log.v("TAG", "선택한 프로젝트 idx = "+project_idx)
            Log.v("TAG", "토큰 = "+ token)

        val intent = Intent(getActivity(), ProjectDetailActivity::class.java)
        intent.putExtra("project_idx", project_idx)
        startActivity(intent)

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
            searchKeyword()
        }
    }

    var searchItems : ArrayList<ProjectItem> = ArrayList()
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
       // get(view)
        defaultSearch(view)

        view.search_filter_btn.setOnClickListener {
            val intent = Intent(activity, SearchFilterActivity::class.java)
            // startActivity(intent)
            Log.v("TAG", "필터시작")
            startActivityForResult(intent,27)

        }
        view.search_keyword_btn.setOnClickListener{
            Log.v("TAG", "탐색시작")
            keyword = search_keyword_edit.text.toString()
            Log.v("TAG", "탐색시작")
            searchKeyword()
        }

        return view
    }

    fun getProjectDetail()
    {

        Log.v("TAG","플젝넘버=" + project_idx)
        Log.v("TAG","홈 플젝 세부사항 조회 토큰 =" + token)
        var getProjectDetailResponse = networkService.getDetailProject(token!!, project_idx) // 네트워크 서비스의 getContent 함수를 받아옴
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

                    if(detailData[0].img_url.size==0){
                        detailData[0].img_url.add("noImage")
                    }
                    projectTestUrl =  detailData[0].img_url[0]

                    projectUserName = detailData[0].project_user_name
                    if(detailData[0].project_user_profile_url==null){
                        detailData[0].project_user_profile_url = "default"
                    }
                    projectUserPofileUrl = detailData[0].project_user_profile_url
                    userIdx = detailData[0].user_idx

                    Log.v("TAG", "세부 사항 제목 = "+ projectTitle + ", 요약 = " + projectSummary + ", 지역 = " + projectArea + ", 분야 = " + projectDepartment
                            + ", 목적 = " + projectAim + ", 설명 = " + projectExplain + ", 만든 날짜 = " + projectCreateAt + ", 이미지주소 = " + projectTestUrl + ", 유저명 = "
                            + projectUserName + ", 프로필 주소 = " + projectUserPofileUrl)





                }
            }

            override fun onFailure(call: Call<GetProjectDetailResponse>?, t: Throwable?) {
                Log.v("TAG","세부사항 통신 실패")

            }

        })
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

/*

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

                        //searchItems.add(ProjectItem(searchData[i].img_url, "ㅁㄴㅇㄹ", "ㅁㄴㅇㄹ","ㅁㄴㅇ", "ㅊㅍ")
                        Log.v("TAG","배열 검색 [ "+ i + "] 이미지주소 = " + searchData[i].img_url)
                        Log.v("TAG","제목 [ "+ i + "]  = " + searchData[i].title)
                        Log.v("TAG","장소 [ "+ i + "]  = " + searchData[i].area)
                        Log.v("TAG","분야 [ "+ i + "]  = " + searchData[i].department)
                        Log.v("TAG","목적 [ "+ i + "]  = " + searchData[i].aim)
                        Log.v("TAG", "검색 시 배열 사이즈 = " + searchItems.size)
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
*/
    fun defaultSearch(v : View)
    {

        var getSearchResponse = networkService.getSearch(aim,area,position,department,keyword) // 네트워크 서비스의 getContent 함수를 받아옴
        getSearchResponse.enqueue(object : Callback<GetSearchResponse> {
            override fun onResponse(call: Call<GetSearchResponse>?, response: Response<GetSearchResponse>?) {
                Log.v("TAG","검색 GET 통신 성공")
                if(response!!.isSuccessful)
                {
                    Log.v("TAG","썰치 탐색 aim = "+ aim)
                    Log.v("TAG","area = "+ area)
                    Log.v("TAG","position = "+ position)
                    Log.v("TAG","department = "+ department)
                    Log.v("TAG","keyword = "+ keyword)
                    searchItems.clear()
                    Log.v("TAG","요기 검색값 갖고오기 성공")
                    searchData = response.body().result
                    Log.v("검색탭", searchData.toString())
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
                        Log.v("TAG","초기 배열 검색 [ "+ i + "] 이미지주소 = " + searchData[i].img_url)
                        Log.v("TAG","제목 [ "+ i + "]  = " + searchData[i].title)
                        Log.v("TAG","장소 [ "+ i + "]  = " + searchData[i].area)
                        Log.v("TAG","분야 [ "+ i + "]  = " + searchData[i].department)
                        Log.v("TAG","목적 [ "+ i + "]  = " + searchData[i].aim)
                        Log.v("TAG", "초기 배열 사이즈 = " + searchItems.size)

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

    fun searchKeyword()
    {

        var getSearchResponse = networkService.getSearch(aim,area,position,department,keyword) // 네트워크 서비스의 getContent 함수를 받아옴
        getSearchResponse.enqueue(object : Callback<GetSearchResponse> {
            override fun onResponse(call: Call<GetSearchResponse>?, response: Response<GetSearchResponse>?) {
                Log.v("TAG","검색 GET 통신 성공")
                if(response!!.isSuccessful)
                {
                    Log.v("TAG","썰치 탐색 aim = "+ aim)
                    Log.v("TAG","area = "+ area)
                    Log.v("TAG","position = "+ position)
                    Log.v("TAG","department = "+ department)
                    Log.v("TAG","keyword = "+ keyword)
                    searchItems.clear()
                    Log.v("TAG","요기 검색값 갖고오기 성공")
                    searchData = response.body().result
                    Log.v("검색탭", searchData.toString())
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
                        Log.v("TAG","초기 배열 검색 [ "+ i + "] 이미지주소 = " + searchData[i].img_url)
                        Log.v("TAG","제목 [ "+ i + "]  = " + searchData[i].title)
                        Log.v("TAG","장소 [ "+ i + "]  = " + searchData[i].area)
                        Log.v("TAG","분야 [ "+ i + "]  = " + searchData[i].department)
                        Log.v("TAG","목적 [ "+ i + "]  = " + searchData[i].aim)
                        Log.v("TAG", "초기 배열 사이즈 = " + searchItems.size)

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