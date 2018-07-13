package com.jemcom.cowalker.Jemin.Activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.*

import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.jemcom.cowalker.Network.ApplicationController
import com.jemcom.cowalker.Network.Delete.DeleteProjectResponse
import com.jemcom.cowalker.Network.Get.GetRecruitList
import com.jemcom.cowalker.Network.Get.Response.GetRecruitListResponse
import com.jemcom.cowalker.Network.NetworkService
import com.jemcom.cowalker.Network.Post.PostShareProject
import com.jemcom.cowalker.Network.Post.PostSignup
import com.jemcom.cowalker.Network.Post.Response.PostProjectResponse
import com.jemcom.cowalker.Network.Post.Response.PostShareResponse
import com.jemcom.cowalker.Network.Post.Response.PostSignupResponse
import com.jemcom.cowalker.Nuri.Activity.LoginActivity
import com.jemcom.cowalker.Nuri.Activity.RecruitDetailActivity
import com.jemcom.cowalker.Nuri.Adapter.RecruitListAdapter
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
import kotlinx.android.synthetic.main.activity_project_intro.*
import kotlinx.android.synthetic.main.activity_project_intro_creater.*
import kotlinx.android.synthetic.main.activity_recruit_delete.*
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProjectIntroCreaterActivity : AppCompatActivity(), View.OnClickListener {

    var data : java.util.ArrayList<GetRecruitList> = java.util.ArrayList<GetRecruitList>()
    lateinit var networkService: NetworkService
    lateinit var addBtn: ImageButton
    lateinit var changeBtn: Button
    lateinit var titleTv: TextView
    lateinit var summaryTv: TextView
    lateinit var aimTv: TextView
     lateinit var departmentTv: TextView
     lateinit var areaTv: TextView
     lateinit var explainTv: TextView
     lateinit var nameTv: TextView
     lateinit var backgroundImg: ImageView

    lateinit var recruitListItems: ArrayList<RecruitListItem>
    lateinit var recruitListGetAdapter : RecruitListGetAdapter

    var url = "https://cdn.xl.thumbs.canstockphoto.com/computer-generated-3d-image-cooperation-stock-illustrations_csp2074347.jpg"

    var title: String = ""
     var summary: String = ""
      var aim: String = ""
      var department: String = ""
      var area: String = ""
      var explain: String = ""
        var name: String = ""
      var img_url: String = ""
      var project_idx: String = ""
     lateinit    var requestManager: RequestManager
    var recruit_idx : String = ""

    override fun onClick(v: View) {

        var idx = recruit_list_recyclerview.getChildAdapterPosition(v)
        recruit_idx = data!![idx].recruit_idx!!
        Log.v("TAG", "참여하기 선택한 모집번호 = "+ recruit_idx)

        val intent = Intent(v.context, RecruitDetailActivity::class.java)
        intent.putExtra("project_idx", project_idx)
        intent.putExtra("recruit_idx", recruit_idx)
        startActivity(intent)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_intro_creater)
        networkService = ApplicationController.instance.networkSerVice // 어플리케이션을 실행하자마자 어플리케이션 콘트롤러가 실행되는데 그 때 사용?
        addBtn = findViewById<View>(R.id.introcreate_add_btn) as ImageButton
        changeBtn = findViewById<View>(R.id.introcreate_change_btn) as Button
        val alertDialogBuilder = AlertDialog.Builder(this)

        titleTv = findViewById<View>(R.id.introcreater_title_tv) as TextView
        summaryTv = findViewById<View>(R.id.introcreate_summary_tv) as TextView
        aimTv = findViewById<View>(R.id.introcreate_aim_tv) as TextView
        departmentTv = findViewById<View>(R.id.introcreate_department_tv) as TextView
        areaTv = findViewById<View>(R.id.introcreate_area_tv) as TextView
        explainTv = findViewById<View>(R.id.introcreate_explain_tv) as TextView
        nameTv = findViewById<View>(R.id.introcreate_name_tv) as TextView
        backgroundImg = findViewById<View>(R.id.introcreate_background_img) as ImageView

        recruitListItems = ArrayList()

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
        }
        Log.v("TAG","테스트 개설자 화면 프로젝트넘버 = "+project_idx)
        getList()
        titleTv.text = title
        summaryTv.text = summary
        aimTv.text = aim
        departmentTv.text = department
        areaTv.text = area
        explainTv.text = explain
        nameTv.text = name

        requestManager = Glide.with(this)
        // 사진 크기 조절이 안되서 일단 주석 처리
        // requestManager.load(img_url).into(backgroundImg);


        introcreate_invite_Tv.setOnClickListener{
            val intent = Intent(this@ProjectIntroCreaterActivity, ProjectMemberActivity::class.java)
            intent.putExtra("project_idx", project_idx)
            startActivity(intent)
        }

        addBtn.setOnClickListener {
            val intent = Intent(this@ProjectIntroCreaterActivity, InviteActivity::class.java)
            intent.putExtra("project_idx", project_idx)
            startActivity(intent)
        }

        introcreate_recommend_btn.setOnClickListener {

            postShareProject()

        }

        changeBtn.setOnClickListener {
            val items = arrayOf<CharSequence>("프로젝트 수정", "프로젝트 삭제")

            // 제목셋팅
            alertDialogBuilder.setTitle("프로젝트 관리")
            alertDialogBuilder.setItems(items
            ) { dialog, id ->
                // 프로젝트 수정
                if (items[id] === "프로젝트 수정") {
                    val intent = Intent(this@ProjectIntroCreaterActivity, ProjectChangeActivity::class.java)

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
                Toast.makeText(applicationContext,"서버 연결 실패", Toast.LENGTH_SHORT).show()
            }

        })
    }


    fun getList()
    {
        var getRecruitListResponse = networkService.getRecruitList(project_idx)

        getRecruitListResponse.enqueue(object : Callback<GetRecruitListResponse>{
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
                    recruitListGetAdapter.setOnItemClickListener(this@ProjectIntroCreaterActivity)
                    recruit_list_recyclerview.layoutManager = LinearLayoutManager(applicationContext)
                    recruit_list_recyclerview.adapter = recruitListGetAdapter
                }
               // else Toast.makeText(applicationContext,"실패",Toast.LENGTH_SHORT).show()
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


    override fun onBackPressed() {
        val intent = Intent(this@ProjectIntroCreaterActivity, MainActivity::class.java)
        startActivity(intent)
    }


}
