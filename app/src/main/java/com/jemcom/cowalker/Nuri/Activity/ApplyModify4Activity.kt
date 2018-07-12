package com.jemcom.cowalker.Nuri.Activity

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.jemcom.cowalker.Jemin.Adapter.ApplyPaperListAdapter
import com.jemcom.cowalker.Network.ApplicationController
import com.jemcom.cowalker.Network.Get.GetApplyPaperMessage
import com.jemcom.cowalker.Network.Get.Response.GetApplyPaperResponse
import com.jemcom.cowalker.Network.Get.Response.GetQuestionListResponse
import com.jemcom.cowalker.Network.NetworkService
import com.jemcom.cowalker.R
import kotlinx.android.synthetic.main.activity_apply_paper.*
import kotlinx.android.synthetic.main.activity_invite4.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class ApplyModify4Activity : AppCompatActivity() {

    lateinit var networkService: NetworkService
    internal lateinit var plusBtn: Button
    internal var count: Int = 0
    private var dynamicLayout: LinearLayout? = null
    internal lateinit var editText: Array<EditText?>
    lateinit var token : String
    lateinit var project_idx: String
    lateinit var position: String
    lateinit var task: String
    lateinit var activity: String
    lateinit var area: String
    internal var number: Int = 0
    internal var editNumber: Int = 0
    internal var question_ㅣist = ArrayList<String>()
    var question : String = ""
    var questionList : ArrayList<String> = ArrayList<String>()
    var answerList : ArrayList<String> = ArrayList<String>()
    var apply_idx : String = ""
    var applicant_idx : String = ""
    var recruit_idx : String = ""
    lateinit var applyPaperListAdapter : ApplyPaperListAdapter
    var data : ArrayList<GetApplyPaperMessage> = ArrayList<GetApplyPaperMessage>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_invite4)

        networkService = ApplicationController.instance.networkSerVice
        dynamicLayout = findViewById(R.id.invite4_question_layout) as LinearLayout
        val intent = intent
//        apply_idx = intent.getStringExtra("apply_idx")
//        applicant_idx = intent.getStringExtra("applicant_idx")
//        recruit_idx = intent.getStringExtra("recruit_idx")

        apply_idx = "5b469323f51f437c0df391e9"
        applicant_idx = "139"
        recruit_idx = "5b3ecc11ca5c3444e4f802f1"
        val pref = getSharedPreferences("auto", Activity.MODE_PRIVATE)
        token = pref.getString("token","")
        plusBtn = findViewById<View>(R.id.invite4_plus_btn) as Button

        plusBtn.setOnClickListener {
            pushButton()

        }

        invite4_confirm_btn.setOnClickListener {
            for (i in 0 until count) {
                question_ㅣist.add(editText[i]!!.text.toString())
            }
//            postBoard()
        }
       // get()
    }

    fun get()
    {
        val getApplyPaperResponse = networkService.getApplyPaper(token, apply_idx, applicant_idx)


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

    private fun pushButton() {
        count++


        val lp = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)


        val linear = LinearLayout(this)
        val text = TextView(this)

        val btn = Button(this)

        lp.setMargins(0, 0, 0, 20)
        linear.layoutParams = lp
        linear.orientation = LinearLayout.HORIZONTAL
        linear.setBackgroundColor(Color.WHITE)


        val lp2 = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
        lp2.setMargins(35, 0, 0, 0)

        text.layoutParams = lp2
        text.text = count.toString() + " "
        text.gravity = Gravity.CENTER
        text.setTextColor(Color.BLACK)
        text.textSize = 20f

        val lp3 = LinearLayout.LayoutParams(
                850,
                ViewGroup.LayoutParams.WRAP_CONTENT)


        editText[editNumber] = EditText(this)
        editText[editNumber]!!.id = editNumber
        editText[editNumber]!!.layoutParams = lp3
        editText[editNumber]!!.hint = "질문을 입력해주세요."


        val lp4 = LinearLayout.LayoutParams(
                50,
                50)
        lp4.setMargins(20, 35, 0, 0)


        linear.addView(text)
        linear.addView(editText[editNumber])
        editNumber += 1
        linear.addView(btn)

        dynamicLayout!!.addView(linear)
    }
}