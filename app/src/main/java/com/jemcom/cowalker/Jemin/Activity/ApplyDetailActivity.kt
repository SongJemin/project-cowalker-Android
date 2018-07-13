package com.jemcom.cowalker.Jemin.Activity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.jemcom.cowalker.Jemin.Adapter.ProjectAdapter
import com.jemcom.cowalker.Jemin.Adapter.QuestionDetailAdapter
import com.jemcom.cowalker.Jemin.Adapter.QuestionListAdapter
import com.jemcom.cowalker.Network.ApplicationController
import com.jemcom.cowalker.Network.Delete.DeleteProjectResponse
import com.jemcom.cowalker.Network.Get.Response.GetQuestionListResponse
import com.jemcom.cowalker.Network.NetworkService
import com.jemcom.cowalker.Network.Post.PostJoin
import com.jemcom.cowalker.Network.Post.Response.PostJoinResponse
import com.jemcom.cowalker.Nuri.Activity.LoginActivity
import com.jemcom.cowalker.Nuri.Adapter.RecruitListGetAdapter

import com.jemcom.cowalker.R
import com.jemcom.cowalker.R.id.*
import kotlinx.android.synthetic.main.activity_apply.*
import kotlinx.android.synthetic.main.activity_apply_detail.*
import kotlinx.android.synthetic.main.activity_project_intro_creater.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApplyDetailActivity : AppCompatActivity() {


    lateinit var questionDetailAdapter : QuestionDetailAdapter
    lateinit var networkService: NetworkService
    var project_idx : String = ""
    var recruit_idx : String = ""
    var questionList : ArrayList<String> = ArrayList()
    var detailAnswerList : ArrayList<String> = ArrayList()
    var count = ""
    var position : String = ""
    var task : String = ""
    var sharer_idx : String = ""
    var flag : Int = 0

    companion object {
        lateinit var detailActivity: ApplyDetailActivity
        //일종의 스태틱
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apply_detail)
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
        detailActivity = this
        networkService = ApplicationController.instance.networkSerVice
        val getRecruitintent = intent
        recruit_idx = getRecruitintent.getStringExtra("recruit_idx")
        Log.v("TAG", "모집 상세에서 받은 모집 번호 = "+recruit_idx)
        project_idx = getRecruitintent.getStringExtra("project_idx")
        flag = getRecruitintent.getIntExtra("flag",0)

       // 공유로 들어온 경우
        if(flag == 2)
        {
            Log.v("TAG", "공유로 들어온 경우")
            sharer_idx = getRecruitintent.getStringExtra("sharer_idx")
        }
        else
        {
            Log.v("TAG", "일반적으로 들어온 경우")

        }

        Log.v("TAG", "모집 상세에서 받은 공유자 번호 = "+sharer_idx)
        position = getRecruitintent.getStringExtra("position")
        task = getRecruitintent.getStringExtra("task")
        get()
        Log.v("TAG","리쿠릇에서 받은 포지션 = " + position)
        apply_detail_position1_tv.setText(position)
        apply_detail_summary_tv.setText(task)

        apply_detail_apply_btn.setOnClickListener {

            if(sharer_idx!="")
            {
                Log.v("sdaf","일반지원")
                postShare()
            }
            else{
                Log.v("sdaf","공유지원")
                post()
            }


        }

    }

    fun get()
    {
        val getQuestionListResponse = networkService.getQuestionList(recruit_idx)


        getQuestionListResponse.enqueue(object : Callback<GetQuestionListResponse> {

            override fun onResponse(call: Call<GetQuestionListResponse>, response: Response<GetQuestionListResponse>) {
                Log.v("TAG", "질문리스트 통신 성공")
                if(response.isSuccessful){
                    var data = response!!.body().result
                    for(i in 0..data.size-1) {
                        count = (i+1).toString()+". "+data[i]
                        questionList.add(count)
                        Log.v("TAG","질문 1 = " + count)
                    }
                    questionDetailAdapter = QuestionDetailAdapter(questionList)
                    question_recyclerview.layoutManager = LinearLayoutManager(applicationContext)
                    question_recyclerview.adapter = questionDetailAdapter

                    Log.v("TAG", "질문리스트 갖고오기 성공")
                }
            }

            override fun onFailure(call: Call<GetQuestionListResponse>, t: Throwable?) {
                Toast.makeText(applicationContext,"서버 연결 실패", Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun post()
    {
        var introduce = apply_detail_introduce_edit.text.toString()
        var portfolio = apply_detail_portfolio_edit.text.toString()
        var phone = apply_detail_phone_edit.text.toString()
        val pref = applicationContext.getSharedPreferences("auto", Activity.MODE_PRIVATE)
        val token = pref.getString("token","")

        Log.v("TAG", "detail 모집번호 = " + recruit_idx)
        Log.v("TAG", "detail 포지션 = " + position)
        var data = PostJoin(introduce,portfolio, recruit_idx,project_idx,position, detailAnswerList, phone)

        var postJoinResponse = networkService.postJoin(token,data)

        postJoinResponse.enqueue(object : Callback<PostJoinResponse>{
            override fun onFailure(call: Call<PostJoinResponse>?, t: Throwable?) {
                Toast.makeText(applicationContext,"서버 연결 실패",Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<PostJoinResponse>?, response: Response<PostJoinResponse>?) {
                if(response!!.isSuccessful)
                {
                    var message = response.body()
                    Toast.makeText(applicationContext,"성공",Toast.LENGTH_SHORT).show()

                    var intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)

                }
                else Toast.makeText(applicationContext,"실패",Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun postShare()
    {
        var introduce = apply_detail_introduce_edit.text.toString()
        var portfolio = apply_detail_portfolio_edit.text.toString()
        var phone = apply_detail_phone_edit.text.toString()
        val pref = applicationContext.getSharedPreferences("auto", Activity.MODE_PRIVATE)
        val token = pref.getString("token","")

        Log.v("TAG", "detail 모집번호 = " + recruit_idx)
        Log.v("TAG", "detail 포지션 = " + position)
        Log.v("TAG", "공유할때 프로젝트 번호 = " + project_idx)
        Log.v("TAG", "공유할때 공유자 번호 = " + sharer_idx)
        var data = PostJoin(introduce,portfolio, recruit_idx,project_idx,position, detailAnswerList, phone)

        var postJoinResponse = networkService.postShare(token,data,project_idx, recruit_idx, sharer_idx)

        postJoinResponse.enqueue(object : Callback<PostJoinResponse>{

            override fun onFailure(call: Call<PostJoinResponse>?, t: Throwable?) {

                Toast.makeText(applicationContext,"서버 연결 실패",Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<PostJoinResponse>?, response: Response<PostJoinResponse>?) {
                Log.v("TAG","공유 통신 성공")
                if(response!!.isSuccessful)
                {Log.v("TAG","공유 전달 성공")
                    var message = response.body()
                    Toast.makeText(applicationContext,"성공",Toast.LENGTH_SHORT).show()

                    var intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)

                }
                else Toast.makeText(applicationContext,"실패",Toast.LENGTH_SHORT).show()
            }
        })
    }
}
