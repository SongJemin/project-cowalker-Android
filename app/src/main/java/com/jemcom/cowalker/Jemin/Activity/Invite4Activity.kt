package com.jemcom.cowalker.Jemin.Activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.jemcom.cowalker.Nuri.Activity.LoginActivity
import com.jemcom.cowalker.Network.ApplicationController
import com.jemcom.cowalker.Network.NetworkService
import com.jemcom.cowalker.Network.Post.PostInvite
import com.jemcom.cowalker.Network.Post.Response.PostInviteResponse
import com.jemcom.cowalker.R
import retrofit2.Call

import java.util.ArrayList

import retrofit2.Response

class Invite4Activity : AppCompatActivity() {

    lateinit var networkService: NetworkService
    internal lateinit var confirmBtn: Button
    internal lateinit var plusBtn: Button
    internal var count: Int = 0
    private var dynamicLayout: LinearLayout? = null
    internal lateinit var editText: Array<EditText?>

    // 통신을 위한 변수 값
    lateinit var token : String
    lateinit var project_idx: String
    lateinit var position: String
    lateinit var start_date: String
    lateinit var end_date: String
    lateinit var task: String
    lateinit var activity: String
    lateinit var reward: String
    lateinit var area: String
    lateinit var ability: String
    lateinit var career: String
    lateinit var preference: String
    lateinit var comment: String
    internal var number: Int = 0
    internal var editNumber: Int = 0
    internal var question_ㅣist = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invite4)
        val linearLayout: LinearLayout
        editText = arrayOfNulls(10)

        networkService = ApplicationController.instance.networkSerVice


        dynamicLayout = findViewById<View>(R.id.invite4_question_layout) as LinearLayout
        val pref = getSharedPreferences("pref", Context.MODE_PRIVATE)
        val pref2 = applicationContext.getSharedPreferences("auto",Activity.MODE_PRIVATE)
        token = pref2.getString("token","")
        // project_idx 값
        project_idx = pref.getString("project_idx", "")

        // 첫 번째 화면 값
        position = pref.getString("position", "")
        start_date = pref.getString("start_date", "")
        end_date = pref.getString("end_date", "")
        number = pref.getInt("number", 0)

        // 두 번째 화면 값
        task = pref.getString("task", "")
        activity = pref.getString("activity", "")
        area = pref.getString("area", "")
        reward = pref.getString("reward", "")

        // 세 번째 화면 값
        ability = pref.getString("ability", "")
        career = pref.getString("career", "")
        preference = pref.getString("preference", "")
        comment = pref.getString("comment", "")
        count = 0
        editNumber = 0

        Log.v("TAG", "중간 프로젝트넘버 = " + project_idx + ", 역할 = " + position + ", 시작날짜 = " + start_date + ", 끝날짜 = " + end_date
                + ", 사람수 = " + number + "명, 활동 = " + activity + ", 활동기간 = " + activity + ", 위치 = " + area
                + ", 혜택 = " + reward + ", 역량 = " + task + ", 경력 = " + career + ", 우대사항 = " + preference + ", 코멘트 = " + comment)

        confirmBtn = findViewById<View>(R.id.invite4_confirm_btn) as Button
        plusBtn = findViewById<View>(R.id.invite4_plus_btn) as Button

        plusBtn.setOnClickListener {
            pushButton()

        }

        confirmBtn.setOnClickListener {
                for (i in 0 until count) {
                question_ㅣist.add(editText[i]!!.text.toString())
                Log.d("Value ", "Val " + editText[i]!!.text + ", Size = " + question_ㅣist.size)

            }

            postBoard()
            // Intent intent= new Intent(Invite4Activity.this, MainActivity.class);
            //startActivity(intent);
        }

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


    fun postBoard() {
        var data = PostInvite(project_idx, position, start_date, end_date, number, task, activity, reward, area, ability, career, preference, comment, question_ㅣist)
        var postInviteResponse = networkService.postInvite(token,data)

     //   Log.v("TAG", "서버 전송 : 토큰 = " + token + ", 제목 = " + titleValue + ", 요약 소개 = " + summaryValue
       //         + ", 지역 = " + areaValue + ", 분야 = " + departmentValue + ", 목적 = " + aimValue + ", 설명 = " + explainValue + ", img = " + imgList)
        Log.v("TAG", "최종 토큰 = " + token + ", 프로젝트넘버 = " + project_idx + ", 역할 = " + position + ", 시작날짜 = " + start_date + ", 끝날짜 = " + end_date
                + ", 사람수 = " + number + "명, 활동 = " + activity + ", 활동기간 = " + activity + ", 위치 = " + area
                + ", 혜택 = " + reward + ", 역량 = " + task + ", 경력 = " + career + ", 우대사항 = " + preference + ", 코멘트 = " + comment + ", 질문리스트 = " + question_ㅣist)

        postInviteResponse.enqueue(object : retrofit2.Callback<PostInviteResponse>{

            override fun onResponse(call: Call<PostInviteResponse>, response: Response<PostInviteResponse>) {
                Log.v("TAG", "통신 성공")
                if(response.isSuccessful){
                    Log.v("TAG", "값 전달 성공")
                    var intent = Intent(applicationContext, LoginActivity::class.java)
                    startActivity(intent)
                }
            }

            override fun onFailure(call: Call<PostInviteResponse>, t: Throwable?) {
                Toast.makeText(applicationContext,"서버 연결 실패",Toast.LENGTH_SHORT).show()
            }

        })

    }
}
