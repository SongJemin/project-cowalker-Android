package com.jemcom.cowalker.Jemin.Activity

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.jemcom.cowalker.Jemin.Adapter.ApplyPaperListAdapter
import com.jemcom.cowalker.Network.ApplicationController
import com.jemcom.cowalker.Network.Get.GetApplyPaperMessage
import com.jemcom.cowalker.Network.Get.Response.GetApplyPaperResponse
import com.jemcom.cowalker.Network.Get.Response.GetQuestionListResponse
import com.jemcom.cowalker.Network.NetworkService
import com.jemcom.cowalker.Network.Put.Response.PutCreaterDecideResponse
import com.jemcom.cowalker.Nuri.Activity.LoginActivity

import com.jemcom.cowalker.R
import kotlinx.android.synthetic.main.activity_apply_paper.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApplyPaperActivity : AppCompatActivity() {
    lateinit var applyPaperListAdapter : ApplyPaperListAdapter
    lateinit var networkService: NetworkService
    var apply_idx : String = ""
    var applicant_idx : String = ""
    var recruit_idx : String = ""
    var answerList : ArrayList<String> = ArrayList<String>()
    var data : ArrayList<GetApplyPaperMessage> = ArrayList<GetApplyPaperMessage>()
    var question : String = ""
    var questionList : ArrayList<String> = ArrayList<String>()

    var join : Int = 0
    var token : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apply_paper)
        networkService = ApplicationController.instance.networkSerVice
        val intent = intent
        apply_idx = intent.getStringExtra("apply_idx")
        applicant_idx = intent.getStringExtra("applicant_idx")
        recruit_idx = intent.getStringExtra("recruit_idx")
        Log.v("TAG", "지원서액티비티 지원서 번호 = " + apply_idx)
        Log.v("TAG", "지원서액티비티 지원자 번호 = " + applicant_idx)
        Log.v("TAG", "지원서액티비티 모집 번호 = " + recruit_idx)
        get()

        apply_paper_approve_btn.setOnClickListener{
            join = 1
            changeJoin()
            var intent = Intent(applicationContext, ApplyMemberActivity::class.java)
            intent.putExtra("recruit_idx", recruit_idx)
            startActivity(intent)
        }

        apply_paper_reject_btn.setOnClickListener {
            join = 2
            changeJoin()
            var intent = Intent(applicationContext, ApplyMemberActivity::class.java)
            intent.putExtra("recruit_idx", recruit_idx)
            startActivity(intent)
        }

        //applyPaperListAdapter = ApplyPaperListAdapter(questionList)
        //apply_paper_recyclerview.layoutManager = LinearLayoutManager(applicationContext)
        //apply_paper_recyclerview.adapter = applyPaperListAdapter

    }

    fun get()
    {
        val pref = getSharedPreferences("auto", Activity.MODE_PRIVATE)
        val token = pref.getString("token","")
        val getApplyPaperResponse = networkService.getApplyPaper(token, apply_idx, applicant_idx)
        Log.v("TAG", "토큰 = "+token)
        Log.v("TAG", "지원서번호 = "+apply_idx)
        Log.v("TAG", "지원자번호 = "+applicant_idx)


        getApplyPaperResponse.enqueue(object : Callback<GetApplyPaperResponse> {

            override fun onResponse(call: Call<GetApplyPaperResponse>, response: Response<GetApplyPaperResponse>) {
                if(response.isSuccessful){
                    data = response!!.body().result

                    for(i in 0..data[0].answers!!.size-1) {
                        answerList.add(data[0].answers!![i])
                        Log.v("TAG", "대답[" + i +  "] ="  + answerList[i])
                    }

                    apply_paper_introduce.setText(data[0].introduce)
                    apply_paper_portfolio_url.setText(data[0].portfolio_url)
                    apply_paper_phone.setText(data[0].phone)

                    getQuestionList()

                    Log.v("TAG", "질문리스트 갖고오기 성공")
                }
            }

            override fun onFailure(call: Call<GetApplyPaperResponse>, t: Throwable?) {
                Toast.makeText(applicationContext,"서버 연결 실패", Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun getQuestionList()
    {

        val getQuestionListResponse = networkService.getQuestionList(recruit_idx)


        getQuestionListResponse.enqueue(object : Callback<GetQuestionListResponse> {

            override fun onResponse(call: Call<GetQuestionListResponse>, response: Response<GetQuestionListResponse>) {
                Log.v("TAG", "질문리스트 통신 성공")
                Log.v("TAG", "지원서액티비티 모집 번호 = "+ recruit_idx)
                if(response.isSuccessful){
                    var data = response!!.body().result
                    for(i in 0..data.size-1) {
                        question = (i+1).toString()+". "+data[i]
                        questionList.add(question)
                        Log.v("ApplyPaperActivity","질문 [" + i +"] = " + questionList[i])
                    }

                    applyPaperListAdapter = ApplyPaperListAdapter(answerList,questionList)
                    apply_paper_recyclerview.layoutManager = LinearLayoutManager(applicationContext)
                    apply_paper_recyclerview.adapter = applyPaperListAdapter

                }
            }

            override fun onFailure(call: Call<GetQuestionListResponse>, t: Throwable?) {
                Toast.makeText(applicationContext,"서버 연결 실패", Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun changeJoin() {

        val pref = getSharedPreferences("auto", Activity.MODE_PRIVATE)
        val token = pref.getString("token","")
        val putCreaterDecideResponse = networkService.putCreaterDecide(token, apply_idx, applicant_idx, join)

        putCreaterDecideResponse.enqueue(object : retrofit2.Callback<PutCreaterDecideResponse>{

            override fun onResponse(call: Call<PutCreaterDecideResponse>, response: Response<PutCreaterDecideResponse>) {
                Log.v("TAG", "조인 통신 성공")
                if(response.isSuccessful){

                    Log.v("TAG", "조인 수정 성공")

                }
            }

            override fun onFailure(call: Call<PutCreaterDecideResponse>, t: Throwable?) {
                Toast.makeText(applicationContext,"서버 연결 실패", Toast.LENGTH_SHORT).show()
            }

        })
    }

}
