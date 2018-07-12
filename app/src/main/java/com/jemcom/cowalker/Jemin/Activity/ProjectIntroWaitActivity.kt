package com.jemcom.cowalker.Jemin.Activity

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.jemcom.cowalker.Network.ApplicationController
import com.jemcom.cowalker.Network.Get.GetRecruitList
import com.jemcom.cowalker.Network.Get.Response.GetMypageOtherResponse
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
    var url = "https://cdn.xl.thumbs.canstockphoto.com/computer-generated-3d-image-cooperation-stock-illustrations_csp2074347.jpg"
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
    var project_user_profile_url: String = ""
    var user_idx: String = ""

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
            if(intent.getStringExtra("project_user_profile_url") != null) project_user_profile_url = intent.getStringExtra("project_user_profile_url")
            if(intent.getStringExtra("user_idx") != null) user_idx = intent.getStringExtra("user_idx")

        }
        getList()
        intro_wait_title_tv.text = title
        intro_wait_summary_tv.text = summary
        intro_wait_aim_tv.text = aim
        intro_wait_department_tv.text = department
        intro_wait_area_tv.text = area
        intro_wait_explain_tv.text = explain
        intro_wait_name_tv.text = name

        requestManager = Glide.with(this)
        // 사진 크기 조절이 안되서 일단 주석 처리
        requestManager.load(project_user_profile_url).into(intro_wait_profile_iv);

        intro_wait_see_more.setOnClickListener(this)
        intro_wait_see_close.setOnClickListener(this)
        intro_wait_profile_iv.setOnClickListener(this)



        intro_wait_share_btn.setOnClickListener {
            postShareProject()
        }

        intro_wait_profile_iv.setOnClickListener {
            get()
        }

        intro_wait_name_tv.setOnClickListener {
            get()
        }

        intro_wait_see_more.setOnClickListener  {
            intro_wait_see_more.visibility = View.GONE
            intro_wait_see_close.visibility = View.VISIBLE
            intro_wait_explain_tv.maxLines = Integer.MAX_VALUE
        }

        intro_wait_see_close.setOnClickListener {
            intro_wait_see_close.visibility = View.GONE
            intro_wait_see_more.visibility = View.VISIBLE
           intro_wait_explain_tv.maxLines = 2
        }
    }

    fun getList()
    {
        Log.v("WaitActivity", "참여 완료 프로젝트 번호 = " + project_idx)
        //var project_idx = "2"
        var getRecruitListResponse = networkService.getRecruitList(project_idx)
        //var getRecruitListResponse = networkService.getRecruitList(project_idx)

        getRecruitListResponse.enqueue(object : Callback<GetRecruitListResponse> {
            override fun onFailure(call: Call<GetRecruitListResponse>?, t: Throwable?) {
                Toast.makeText(applicationContext,"서버 연결 실패", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<GetRecruitListResponse>?, response: Response<GetRecruitListResponse>?) {
                Log.v("TAG","wait 모집 리스트 통신")
                if(response!!.isSuccessful)
                {
                    Log.v("TAG","wait 모집 리스트 받아오기")
                    data = response.body().result
                    Log.v("TAG","모집 리스트 값 = "+data.toString())

                    for(i in 0..data.size-1)
                    {

                        recruitListItems.add(RecruitListItem(data[i].position,data[i].number,data[i].task,data[i].dday))
                    }
                    recruitListGetAdapter = RecruitListGetAdapter(recruitListItems)
                    recruitListGetAdapter.setOnItemClickListener(this@ProjectIntroWaitActivity)
                    recruit_list_recyclerview4.layoutManager = LinearLayoutManager(applicationContext)
                    recruit_list_recyclerview4.adapter = recruitListGetAdapter
                }
                // else Toast.makeText(applicationContext,"실패",Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun get()
    {
        val pref = getSharedPreferences("auto", Activity.MODE_PRIVATE)
        val token = pref.getString("token","")
        Log.v("TAG", "wait 유저번호=" + user_idx)
        var getMypageOtherResponse = networkService.getMypageOther(token,user_idx)
        getMypageOtherResponse.enqueue(object : Callback<GetMypageOtherResponse>{
            override fun onFailure(call: Call<GetMypageOtherResponse>?, t: Throwable?) {
            }

            override fun onResponse(call: Call<GetMypageOtherResponse>?, response: Response<GetMypageOtherResponse>?) {
                if(response!!.isSuccessful)
                {
                    var data = response.body()
                    if(data.user_status.equals("타인의 페이지"))
                    {
                        var intent = Intent(this@ProjectIntroWaitActivity,MainActivity::class.java)
                        intent.putExtra("user_idx", user_idx)
                        intent.putExtra("status","otherpage")
                        startActivity(intent)
                    }
                    else
                    {
                        var intent = Intent(this@ProjectIntroWaitActivity,MainActivity::class.java)
                        intent.putExtra("status","mypage")
                        startActivity(intent)
                    }
                }
                else Toast.makeText(this@ProjectIntroWaitActivity
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
                Toast.makeText(applicationContext,"서버 연결 실패",Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun sendLink() {
        val params = FeedTemplate
                .newBuilder(ContentObject.newBuilder("공공서비스 어플리케이션 공모전",
                        url,
                        LinkObject.newBuilder().setWebUrl("")
                                .setMobileWebUrl("").build())
                        .setDescrption("이충엽님이 당신을 추천하셨습니다. 함께 해주세요!")
                        .build())

                .addButton(ButtonObject("깅스앱으로 열기", LinkObject.newBuilder()

                        .setWebUrl("'https://developers.kakao.com")
                        .setMobileWebUrl("'https://developers.kakao.com")
                        .setAndroidExecutionParams("key1=value1")
                        .setIosExecutionParams("key1=value1")
                        .build()))
                .build()

        KakaoLinkService.getInstance().sendDefault(this, params, object : ResponseCallback<KakaoLinkResponse>() {

            override fun onFailure(errorResult: ErrorResult) {

                Logger.e(errorResult.toString())
            }
            override fun onSuccess(result: KakaoLinkResponse) {}
        })
    }
}
