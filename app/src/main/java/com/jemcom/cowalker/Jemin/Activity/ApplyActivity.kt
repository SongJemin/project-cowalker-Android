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
import com.jemcom.cowalker.Jemin.Adapter.QuestionListAdapter
import com.jemcom.cowalker.Network.ApplicationController
import com.jemcom.cowalker.Network.Get.GetRecruitList
import com.jemcom.cowalker.Network.Get.Response.GetQuestionListResponse
import com.jemcom.cowalker.Network.Get.Response.GetRecruitListResponse
import com.jemcom.cowalker.Network.NetworkService
import com.jemcom.cowalker.Network.Post.PostJoin
import com.jemcom.cowalker.Network.Post.Response.PostJoinResponse
import com.jemcom.cowalker.Nuri.Activity.LoginActivity
import com.jemcom.cowalker.Nuri.Adapter.RecruitListGetAdapter
import com.jemcom.cowalker.Nuri.Item.RecruitListItem
import com.jemcom.cowalker.R
import com.jemcom.cowalker.R.id.apply_apply_btn
import com.jemcom.cowalker.R.id.apply_phone_edit
import kotlinx.android.synthetic.main.activity_apply.*
import kotlinx.android.synthetic.main.activity_apply_detail.*
import kotlinx.android.synthetic.main.activity_project_intro.*
import kotlinx.android.synthetic.main.activity_project_intro_creater.*
import kotlinx.android.synthetic.main.fragment_message.*
import kotlinx.android.synthetic.main.recruit_list_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// 지원서 액티비티
class ApplyActivity : AppCompatActivity(), View.OnClickListener {


    lateinit var recruitListItems: ArrayList<RecruitListItem>
    lateinit var recruitListGetAdapter : RecruitListGetAdapter
    var recruitData : java.util.ArrayList<GetRecruitList> = java.util.ArrayList<GetRecruitList>()
    var project_idx : String = ""
    var recruit_idx : String = ""
    var questionList : ArrayList<String> = ArrayList()
    var answerList : ArrayList<String> = ArrayList()
    var count = ""
    var position = ""

    lateinit var questionListAdapter : QuestionListAdapter
    lateinit var networkService: NetworkService

    companion object {
        lateinit var activity: ApplyActivity
        //일종의 스태틱
    }
    override fun onClick(v: View) {

        var idx = apply_list_recyclerview!!.getChildAdapterPosition(v)
        3//apply_list_recyclerview!!.getChildViewHolder(v).itemView.setBackgroundColor(Color.parseColor("#000000"))
        recruit_idx = recruitData!![idx].recruit_idx!!
        position = recruitData!![idx].position!!
        Log.v("TAG", "지원서에서 클릭 모집번호 = "+ recruit_idx)
        answerList.clear()
        getQuestionList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apply)
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

        activity = this
        val getintent = intent
        project_idx = getintent.getStringExtra("project_idx")


        networkService = ApplicationController.instance.networkSerVice
        recruitListItems = ArrayList()
        getList()
        recruitListItems = ArrayList()
        apply_apply_btn.setOnClickListener(this)

        apply_apply_btn.setOnClickListener{
            val pref = applicationContext.getSharedPreferences("auto", Activity.MODE_PRIVATE)
            val token = pref.getString("token","")
              if(token.length > 0) post()
            else{
                  var intent = Intent(applicationContext,LoginActivity::class.java)
                  startActivity(intent)
              }
        }
    }

    fun post()
    {
        var introduce = apply_introduce_edit.text.toString()
        var portfolio = apply_portfolio_edit.text.toString()
        var phone = apply_phone_edit.text.toString()
        val pref = applicationContext.getSharedPreferences("auto", Activity.MODE_PRIVATE)
        val token = pref.getString("token","")


        var data = PostJoin(introduce,portfolio, recruit_idx,project_idx,position, answerList, phone)
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

    fun getList()
    {

        var getRecruitListResponse = networkService.getRecruitList(project_idx)
        //var getRecruitListResponse = networkService.getRecruitList(project_idx)

        getRecruitListResponse.enqueue(object : Callback<GetRecruitListResponse>{
            override fun onFailure(call: Call<GetRecruitListResponse>?, t: Throwable?) {
                Toast.makeText(applicationContext,"서버 연결 실패", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<GetRecruitListResponse>?, response: Response<GetRecruitListResponse>?) {
                Log.v("TAG","모집 리스트 통신")
                if(response!!.isSuccessful)
                {
                    Log.v("TAG","모집 리스트 받아오기")
                    recruitData = response.body().result
                    Log.v("TAG","모집 리스트 값 = "+recruitData.toString())

                    for(i in 0..recruitData.size-1)
                    {

                        recruitListItems.add(RecruitListItem(recruitData[i].position,recruitData[i].number,recruitData[i].task,recruitData[i].dday))
                    }
                    recruitListGetAdapter = RecruitListGetAdapter(recruitListItems)
                    recruitListGetAdapter.setOnItemClickListener(this@ApplyActivity)
                    apply_list_recyclerview.layoutManager = LinearLayoutManager(applicationContext)
                    apply_list_recyclerview.adapter = recruitListGetAdapter
                }
                //else Toast.makeText(applicationContext,"실패",Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getQuestionList()
    {
        val getQuestionListResponse = networkService.getQuestionList(recruit_idx)


        getQuestionListResponse.enqueue(object : Callback<GetQuestionListResponse> {

            override fun onResponse(call: Call<GetQuestionListResponse>, response: Response<GetQuestionListResponse>) {
                Log.v("TAG", "질문리스트 통신 성공")
                if(response.isSuccessful){
                    questionList.clear()
                    var data = response!!.body().result
                    for(i in 0..data.size-1) {
                        count = (i+1).toString()+". "+data[i]
                        questionList.add(count)
                        Log.v("TAG","질문 1 = " + count)
                    }
                    questionListAdapter = QuestionListAdapter(questionList)
                    question_recyclerview2.layoutManager = LinearLayoutManager(applicationContext)
                    question_recyclerview2.adapter = questionListAdapter

                    Log.v("TAG", "질문리스트 갖고오기 성공")
                }
            }

            override fun onFailure(call: Call<GetQuestionListResponse>, t: Throwable?) {
                Toast.makeText(applicationContext,"서버 연결 실패", Toast.LENGTH_SHORT).show()
            }

        })
    }
}