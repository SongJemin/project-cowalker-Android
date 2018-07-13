package com.jemcom.cowalker.Jemin.Activity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.jemcom.cowalker.Network.ApplicationController
import com.jemcom.cowalker.Network.Delete.DeleteProjectResponse
import com.jemcom.cowalker.Network.Get.GetProjectDetailMessage
import com.jemcom.cowalker.Network.Get.GetRecruitList
import com.jemcom.cowalker.Network.Get.Response.GetMypageOtherResponse
import com.jemcom.cowalker.Network.Get.Response.GetProjectDetailResponse
import com.jemcom.cowalker.Network.Get.Response.GetRecruitListResponse
import com.jemcom.cowalker.Network.NetworkService
import com.jemcom.cowalker.Network.Post.PostShareProject
import com.jemcom.cowalker.Network.Post.Response.PostShareResponse
import com.jemcom.cowalker.Nuri.Activity.RecruitDetailActivity
import com.jemcom.cowalker.Nuri.Adapter.RecruitListGetAdapter
import com.jemcom.cowalker.Nuri.Item.RecruitListItem

import com.jemcom.cowalker.R
import com.kakao.kakaolink.v2.KakaoLinkResponse
import com.kakao.kakaolink.v2.KakaoLinkService
import com.kakao.message.template.ButtonObject
import com.kakao.message.template.ContentObject
import com.kakao.message.template.FeedTemplate
import com.kakao.message.template.LinkObject
import com.kakao.network.ErrorResult
import com.kakao.network.callback.ResponseCallback
import com.kakao.util.helper.log.Logger
import kotlinx.android.synthetic.main.activity_project_detail.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProjectDetailActivity : AppCompatActivity(), View.OnClickListener {


    override fun onClick(v: View) {
        var idx = project_detail_recyclerview.getChildAdapterPosition(v)
        recruit_idx = data!![idx].recruit_idx!!
        num = data!![idx].number!!.toString()
        task = data!![idx].task!!
        Log.v("TAG", "참여하기 선택한 모집번호 = "+ recruit_idx)

        val intent = Intent(v.context, RecruitDetailActivity::class.java)
        intent.putExtra("project_idx", project_idx)
        intent.putExtra("recruit_idx", recruit_idx)
        intent.putExtra("num", num)
        intent.putExtra("task", task)
        var userResult : String = ""
        startActivity(intent)
    }
    var url = "https://cdn.xl.thumbs.canstockphoto.com/computer-generated-3d-image-cooperation-stock-illustrations_csp2074347.jpg"
    var data : java.util.ArrayList<GetRecruitList> = java.util.ArrayList<GetRecruitList>()
    lateinit var networkService: NetworkService

    lateinit var recruitListItems: ArrayList<RecruitListItem>
    lateinit var recruitListGetAdapter : RecruitListGetAdapter
    lateinit var detailData : java.util.ArrayList<GetProjectDetailMessage>


    var btnResult : String = ""
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
    var num : String = ""
    var task : String = ""
    var project_user_profile_url: String = ""
    var user_idx : String = ""
    var createAt : String = ""
    var projectUserName : String = ""
    lateinit var imgUrl : String
    var userResult : String = ""
    var projectUserProfileUrl : String? = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_detail)
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
        recruitListItems = ArrayList()
        val alertDialogBuilder = AlertDialog.Builder(this)
        networkService = ApplicationController.instance.networkSerVice // 어플리케이션을 실행하자마자 어플리케이션 콘트롤러가 실행되는데 그 때 사용?
        Log.v("asdf","여기 실행")
        requestManager = Glide.with(this)   // 사진 크기 조절이 안되서 일단 주석 처리
        //project_detail_profile_iv.setBackground(ContextCompat.getDrawable(this, R.drawable.profile_default));
        var getintent = intent
        project_idx = getintent.getStringExtra("project_idx")


        getList()
        getProjectDetail()



        project_detail_see_more.setOnClickListener(this)
        project_detail_see_close.setOnClickListener(this)
        project_detail_profile_iv.setOnClickListener(this)

        project_detail_recommend_btn.setOnClickListener {
            postShareProject()
        }
        project_detail_profile_iv.setOnClickListener {
            get()
        }

        project_detail_name_tv.setOnClickListener {
            get()
        }

        project_detail_see_more.setOnClickListener  {
            project_detail_see_more.visibility = View.GONE
            project_detail_see_close.visibility = View.VISIBLE
            project_detail_explain_tv.maxLines = Integer.MAX_VALUE
        }

        project_detail_see_close.setOnClickListener {
            project_detail_see_close.visibility = View.GONE
            project_detail_see_more.visibility = View.VISIBLE
            project_detail_explain_tv.maxLines = 2
        }

        project_detail_add_btn.setOnClickListener {
            val intent = Intent(this@ProjectDetailActivity, InviteActivity::class.java)
            intent.putExtra("project_idx", project_idx)
            startActivity(intent)
        }

        project_detail_change_btn.setOnClickListener {
            if (project_detail_change_btn.text.equals("프로젝트 관리")) {
                val items = arrayOf<CharSequence>("프로젝트 수정", "프로젝트 삭제")

                // 제목셋팅
                alertDialogBuilder.setTitle("프로젝트 관리")
                alertDialogBuilder.setItems(items
                ) { dialog, id ->
                    // 프로젝트 수정
                    if (items[id] === "프로젝트 수정") {
                        val intent = Intent(this@ProjectDetailActivity, ProjectChangeActivity::class.java)

                        intent.putExtra("project_idx", project_idx)
                        startActivity(intent)

                    } else if (items[id] === "프로젝트 삭제") {
                        deleteBoard()
                    }// 프로젝트 삭제
                    dialog.dismiss()
                }

                // 다이얼로그 생성
                val alertDialog = alertDialogBuilder.create()

                // 다이얼로그 보여주기
                alertDialog.show()
            }

            else if (project_detail_change_btn.text.equals("참여하기")) {
                val nextIntent = Intent(this, ApplyActivity::class.java)
                nextIntent.putExtra("project_idx", project_idx)
                startActivity(nextIntent)
            }


        }

    }

    fun getList()
    {
        Log.v("ParticipActivity", "플젝세부사항 프로젝트 번호 = " + project_idx)
        var getRecruitListResponse = networkService.getRecruitList(project_idx)

        getRecruitListResponse.enqueue(object : Callback<GetRecruitListResponse> {
            override fun onFailure(call: Call<GetRecruitListResponse>?, t: Throwable?) {
                Toast.makeText(applicationContext,"2서버 연결 실패", Toast.LENGTH_SHORT).show()
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
                    recruitListGetAdapter.setOnItemClickListener(this@ProjectDetailActivity)
                    project_detail_recyclerview.layoutManager = LinearLayoutManager(applicationContext)
                    project_detail_recyclerview.adapter = recruitListGetAdapter
                }
                // else Toast.makeText(applicationContext,"실패",Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun get()
    {
        val pref = getSharedPreferences("auto", Activity.MODE_PRIVATE)
        val token = pref.getString("token","")
        var getMypageOtherResponse = networkService.getMypageOther(token,user_idx)
        Log.v("TAG", "세부사항 아덜페이저 유저번호=" + user_idx)
        getMypageOtherResponse.enqueue(object : Callback<GetMypageOtherResponse>{
            override fun onFailure(call: Call<GetMypageOtherResponse>?, t: Throwable?) {
            }

            override fun onResponse(call: Call<GetMypageOtherResponse>?, response: Response<GetMypageOtherResponse>?) {
                if(response!!.isSuccessful)
                {
                    var data = response.body()
                    if(data.user_status.equals("타인의 페이지"))
                    {
                        var intent = Intent(this@ProjectDetailActivity,MainActivity::class.java)
                        intent.putExtra("status","otherpage")
                        intent.putExtra("user_idx", user_idx)
                        startActivity(intent)
                    }
                    else
                    {
                        var intent = Intent(this@ProjectDetailActivity,MainActivity::class.java)
                        intent.putExtra("status","mypage")
                        startActivity(intent)
                    }
                }
                else Toast.makeText(this@ProjectDetailActivity
                        ,"실패",Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun postShareProject()
    {
        val pref = getSharedPreferences("auto", Activity.MODE_PRIVATE)
        val token = pref.getString("token","")
        var data = PostShareProject(project_idx)
        var postShareResponse = networkService.postShareProject(token,data)

        postShareResponse.enqueue(object : retrofit2.Callback<PostShareResponse>{

            override fun onResponse(call: Call<PostShareResponse>, response: Response<PostShareResponse>) {
                if(response.isSuccessful){
                    Log.v("TAG","프로젝트 공유 성공")
                    sendLink()
                }
            }

            override fun onFailure(call: Call<PostShareResponse>, t: Throwable?) {
                Toast.makeText(applicationContext,"1서버 연결 실패",Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun sendLink() {

        Log.v("TAG","프로젝트 숫자 ="+project_idx)
        val params = FeedTemplate
                .newBuilder(ContentObject.newBuilder("공공서비스 어플리케이션 공모전",
                        url,
                        LinkObject.newBuilder().setWebUrl("")
                                .setMobileWebUrl("").build())
                        .setDescrption("이충엽님이 당신을 추천하셨습니다. 함께 해주세요!")
                        .build())

                .addButton(ButtonObject("깅스앱으로 열기", LinkObject.newBuilder()

                        .setWebUrl("'https://developers.kakao.com")
                        .setMobileWebUrl("'http://bghgu.tk:3000/api/project?project_idx="+project_idx)
                        /*.setAndroidExecutionParams("key1=value1")
                        .setIosExecutionParams("key1=value1")*/
                        .build()))
                .build()

        KakaoLinkService.getInstance().sendDefault(this, params, object : ResponseCallback<KakaoLinkResponse>() {

            override fun onFailure(errorResult: ErrorResult) {

                Logger.e(errorResult.toString())
            }
            override fun onSuccess(result: KakaoLinkResponse) {}
        })
    }

    fun getProjectDetail()
    {
        val pref = getSharedPreferences("auto", Activity.MODE_PRIVATE)
        val token = pref.getString("token","")
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

                    title = detailData[0].title

                    if(detailData[0].summary == null)
                    {
                        detailData[0].summary = "null"
                    }
                    summary= detailData[0].summary
                    if(detailData[0].area == null)
                    {
                        detailData[0].area = "null"
                    }
                    area = detailData[0].area

                    if(detailData[0].department==null)
                    {
                        detailData[0].department="null"
                    }
                    department = detailData[0].department

                    if(detailData[0].aim==null){
                        detailData[0].aim = "null"
                    }
                    aim = detailData[0].aim

                    if(detailData[0].explain==null){
                        detailData[0].explain = "null"
                    }
                    explain = detailData[0].explain

                    createAt = detailData[0].create_at

                    if(detailData[0].img_url.size==0){
                        detailData[0].img_url.add("noImage")
                    }
                    imgUrl = detailData[0].img_url[0]

                    projectUserName = detailData[0].project_user_name
                    if(detailData[0].project_user_profile_url==null){
                        detailData[0].project_user_profile_url = "default"
                    }
                    projectUserProfileUrl = detailData[0].project_user_profile_url
                    user_idx = detailData[0].user_idx

                    Log.v("TAG", "세부 사항 제목 = "+ title + ", 요약 = " + summary + ", 지역 = " + area + ", 분야 = " + department
                            + ", 목적 = " + aim + ", 설명 = " + explain + ", 만든 날짜 = " + createAt + ", 이미지주소 = " + imgUrl + ", 유저명 = "
                            + projectUserName + ", 프로필 주소 = " + projectUserProfileUrl)


                    project_detail_title_tv.text = title
                    project_detail_summary_tv.text = summary
                    project_detail_aim_tv.text = aim
                    project_detail_department_tv.text = department
                    project_detail_area_tv.text = area
                    project_detail_explain_tv.text = explain
                    project_detail_name_tv.text = projectUserName

                    if(projectUserName
                            != "default"){
                        Log.v("TAG","사진 디폴트 ㄴㄴ")
                        requestManager.load(projectUserProfileUrl).into(project_detail_profile_iv);
                    }
                    else{
                        Log.v("TAG", "프로필 사진 디폴트")

                    }

                    Log.v("TAG","우리 유저 상태 = "+userResult)
                    if(userResult.equals("개설자")){
                        btnResult = "프로젝트 관리"
                        project_detail_change_btn.setText(btnResult)

                    }

                    else if(userResult.equals("참여하기"))
                    {
                        btnResult = "참여하기"
                        project_detail_change_btn.setText(btnResult)
                        project_detail_add_btn.setVisibility(View.GONE)
                        project_detail_delete_btn.setVisibility(View.GONE)

                    }
                    else if(userResult.equals("참여대기"))
                    {
                        btnResult = "참여 대기"
                        project_detail_change_btn.setText(btnResult)
                        project_detail_change_btn.setBackgroundColor(Color.parseColor("#eeeeee"))
                        project_detail_change_btn.setTextColor(Color.parseColor("#c5c5c5"))
                        project_detail_add_btn.setVisibility(View.GONE)
                        project_detail_delete_btn.setVisibility(View.GONE)
                    }
                    else
                    {
                        btnResult = "참여 완료"
                        project_detail_change_btn.setText(btnResult)
                        project_detail_change_btn.setBackgroundColor(Color.parseColor("#eeeeee"))
                        project_detail_change_btn.setTextColor(Color.parseColor("#c5c5c5"))
                        project_detail_add_btn.setVisibility(View.GONE)
                        project_detail_delete_btn.setVisibility(View.GONE)
                    }


                }
            }

            override fun onFailure(call: Call<GetProjectDetailResponse>?, t: Throwable?) {
                Log.v("TAG","세부사항 통신 실패")

            }

        })
    }


    fun deleteBoard() {

        val deleteProjectResponse = networkService.deleteProject(project_idx)


        deleteProjectResponse.enqueue(object : Callback<DeleteProjectResponse> {

            override fun onResponse(call: Call<DeleteProjectResponse>, response: Response<DeleteProjectResponse>) {
                Log.v("TAG", "통신 성공")
                if(response.isSuccessful){
                    var message = response!!.body()

                    Log.v("TAG", "삭제 성공")
                    var intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                }
            }

            override fun onFailure(call: Call<DeleteProjectResponse>, t: Throwable?) {
                Toast.makeText(applicationContext,"3서버 연결 실패", Toast.LENGTH_SHORT).show()
            }

        })
    }


    override fun onBackPressed() {
        val intent = Intent(this@ProjectDetailActivity, MainActivity::class.java)
        startActivity(intent)
    }


}
