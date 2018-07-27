package com.jemcom.cowalker.Jemin.Activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.jemcom.cowalker.Network.ApplicationController
import com.jemcom.cowalker.Network.Get.GetRecruitDetail
import com.jemcom.cowalker.Network.Get.Response.GetRecruitDetailResponse
import com.jemcom.cowalker.Network.NetworkService
import com.jemcom.cowalker.R
import kotlinx.android.synthetic.main.activity_invite.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApplyModifyActivity : AppCompatActivity(),View.OnClickListener {

    var date : String = ""
    var start_date: String = ""
    var end_date: String = ""
    var startZeroMonth: String = ""
    var startZeroDay: String = ""
    var finishZeroMonth: String = ""
    var finishZeroDay: String = ""
    lateinit var data : ArrayList<GetRecruitDetail>
    lateinit var networkService: NetworkService
    var project_idx : String = ""
    var recruit_idx : String = ""
    var number : Int = 0
    var startResult : String = ""
    var endResult : String = ""
    var position : String = ""

    var startDateIntent : String = ""
    var endDateIntent : String = ""

    override fun onClick(v: View?) {
        when(v)
        {

            
            invite1_next_btn -> {

                if(number.toString() == "" || position == null || start_date == null || end_date == null )
                {
                    Toast.makeText(getApplicationContext(), "공백 없이 입력해주세요", Toast.LENGTH_LONG).show();

                }

                try{
                    var intent = Intent(applicationContext, ApplyModify2Activity::class.java)
                    number = Integer.parseInt(invite_personnel_edit.text.toString())
                    intent.putExtra("position", position)
                    Log.v("TAG","모집 수정 position = "+ position)
                    intent.putExtra("start_date", startResult)
                    Log.v("TAG","모집 수정 start_date = "+ startResult)
                    intent.putExtra("end_date", endResult)
                    Log.v("TAG","모집 수정 end_date = "+ endResult)
                    intent.putExtra("number", number)
                    Log.v("TAG","모집 수정 number = "+ number)
                    intent.putExtra("project_idx", project_idx)
                    intent.putExtra("recruit_idx", recruit_idx)
                    startActivity(intent)
                }
                catch(e : NumberFormatException){
                    Toast.makeText(getApplicationContext(), "숫자를 입력해주세요", Toast.LENGTH_LONG).show()
                    e.printStackTrace()
                }


            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_invite)
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
        networkService = ApplicationController.instance.networkSerVice
        invite1_next_btn.setOnClickListener(this)
        get()

        invite_pm_btn.setOnClickListener {
            invite_pm_btn.isSelected = true
            invite_pm_btn.setTextColor(Color.WHITE)

            invite_planner_btn.isSelected = false
            invite_designer_btn.isSelected = false
            invite_developer_btn.isSelected = false
            invite_etc_btn.isSelected = false

            invite_planner_btn.setTextColor(Color.BLACK)
            invite_designer_btn.setTextColor(Color.BLACK)
            invite_developer_btn.setTextColor(Color.BLACK)
            invite_etc_btn.setTextColor(Color.BLACK)
            position = "PM"
        }

        invite_planner_btn.setOnClickListener {
            invite_planner_btn.isSelected = true
            invite_planner_btn.setTextColor(Color.WHITE)

            invite_pm_btn.isSelected = false
            invite_designer_btn.isSelected = false
            invite_developer_btn.isSelected = false
            invite_etc_btn.isSelected = false

            invite_pm_btn.setTextColor(Color.BLACK)
            invite_designer_btn.setTextColor(Color.BLACK)
            invite_developer_btn.setTextColor(Color.BLACK)
            invite_etc_btn.setTextColor(Color.BLACK)
            position = "기획자"
        }

        invite_designer_btn.setOnClickListener {
            invite_designer_btn.isSelected = true
            invite_designer_btn.setTextColor(Color.WHITE)

            invite_pm_btn.isSelected = false
            invite_planner_btn.isSelected = false
            invite_developer_btn.isSelected = false
            invite_etc_btn.isSelected = false

            invite_pm_btn.setTextColor(Color.BLACK)
            invite_planner_btn.setTextColor(Color.BLACK)
            invite_developer_btn.setTextColor(Color.BLACK)
            invite_etc_btn.setTextColor(Color.BLACK)

            position = "디자이너"
        }

        invite_developer_btn.setOnClickListener {
            invite_developer_btn.isSelected = true
            invite_developer_btn.setTextColor(Color.WHITE)

            invite_pm_btn.isSelected = false
            invite_planner_btn.isSelected = false
            invite_designer_btn.isSelected = false
            invite_etc_btn.isSelected = false

            invite_pm_btn.setTextColor(Color.BLACK)
            invite_planner_btn.setTextColor(Color.BLACK)
            invite_designer_btn.setTextColor(Color.BLACK)
            invite_etc_btn.setTextColor(Color.BLACK)

            position = "개발자"
        }

        invite_etc_btn.setOnClickListener {
            invite_etc_btn.isSelected = true
            invite_etc_btn.setTextColor(Color.WHITE)

            invite_pm_btn.isSelected = false
            invite_planner_btn.isSelected = false
            invite_designer_btn.isSelected = false
            invite_developer_btn.isSelected = false

            invite_pm_btn.setTextColor(Color.BLACK)
            invite_planner_btn.setTextColor(Color.BLACK)
            invite_developer_btn.setTextColor(Color.BLACK)
            invite_developer_btn.setTextColor(Color.BLACK)

            position = "기타"
        }

        invite_pm_btn.setOnClickListener {
            invite_pm_btn.isSelected = true
            invite_pm_btn.setTextColor(Color.WHITE)

            invite_planner_btn.isSelected = false
            invite_designer_btn.isSelected = false
            invite_developer_btn.isSelected = false
            invite_etc_btn.isSelected = false

            invite_planner_btn.setTextColor(Color.BLACK)
            invite_designer_btn.setTextColor(Color.BLACK)
            invite_developer_btn.setTextColor(Color.BLACK)
            invite_etc_btn.setTextColor(Color.BLACK)
            position = "PM"
        }

        invite_planner_btn.setOnClickListener {
            invite_planner_btn.isSelected = true
            invite_planner_btn.setTextColor(Color.WHITE)

            invite_pm_btn.isSelected = false
            invite_designer_btn.isSelected = false
            invite_developer_btn.isSelected = false
            invite_etc_btn.isSelected = false

            invite_pm_btn.setTextColor(Color.BLACK)
            invite_designer_btn.setTextColor(Color.BLACK)
            invite_developer_btn.setTextColor(Color.BLACK)
            invite_etc_btn.setTextColor(Color.BLACK)
            position = "기획자"
        }

        invite_designer_btn.setOnClickListener {
            invite_designer_btn.isSelected = true
            invite_designer_btn.setTextColor(Color.WHITE)

            invite_pm_btn.isSelected = false
            invite_planner_btn.isSelected = false
            invite_developer_btn.isSelected = false
            invite_etc_btn.isSelected = false

            invite_pm_btn.setTextColor(Color.BLACK)
            invite_planner_btn.setTextColor(Color.BLACK)
            invite_developer_btn.setTextColor(Color.BLACK)
            invite_etc_btn.setTextColor(Color.BLACK)

            position = "디자이너"
        }

        invite_developer_btn.setOnClickListener {
            invite_developer_btn.isSelected = true
            invite_developer_btn.setTextColor(Color.WHITE)

            invite_pm_btn.isSelected = false
            invite_planner_btn.isSelected = false
            invite_designer_btn.isSelected = false
            invite_etc_btn.isSelected = false

            invite_pm_btn.setTextColor(Color.BLACK)
            invite_planner_btn.setTextColor(Color.BLACK)
            invite_designer_btn.setTextColor(Color.BLACK)
            invite_etc_btn.setTextColor(Color.BLACK)

            position = "개발자"
        }

        invite_etc_btn.setOnClickListener {
            invite_etc_btn.isSelected = true
            invite_etc_btn.setTextColor(Color.WHITE)

            invite_pm_btn.isSelected = false
            invite_planner_btn.isSelected = false
            invite_designer_btn.isSelected = false
            invite_developer_btn.isSelected = false

            invite_pm_btn.setTextColor(Color.BLACK)
            invite_planner_btn.setTextColor(Color.BLACK)
            invite_developer_btn.setTextColor(Color.BLACK)
            invite_developer_btn.setTextColor(Color.BLACK)

            position = "기타"
        }

        invite_range_btn.setOnClickListener {
            val pd = MyYearMonthPickerDialog()
            pd.setOnConfirmDateListener { startYear, startMonth, startDay, finishYear, finishMonth, finishDay ->
                var startDay = startDay
                var finishDay = finishDay
                if (startMonth < 10) {
                    startZeroMonth = "0$startMonth"
                } else {
                    startZeroMonth = Integer.toString(startMonth)
                }
                if (startDay < 9) {
                    startDay += 1
                    startZeroDay = "0$startDay"
                } else {
                    startZeroDay = Integer.toString(startDay + 1)
                }
                if (finishMonth < 10) {
                    finishZeroMonth = "0$finishMonth"
                } else {
                    finishZeroMonth = Integer.toString(finishMonth)
                }
                if (finishDay < 9) {
                    finishDay += 1
                    finishZeroDay = "0$finishDay"
                } else {
                    finishZeroDay = Integer.toString(finishDay + 1)
                }
                this@ApplyModifyActivity.start_date = startYear.toString() + "-" + startZeroMonth + "-" + startZeroDay
                this@ApplyModifyActivity.end_date = finishYear.toString() + "-" + finishZeroMonth + "-" + finishZeroDay
                Log.v("TAG" , "ㅁㅈ수정start_date = "+this@ApplyModifyActivity.start_date)
                Log.v("TAG" , "ㅁㅈ수정start_date = "+this@ApplyModifyActivity.end_date)
                startResult = this@ApplyModifyActivity.start_date
                endResult = this@ApplyModifyActivity.end_date
                invite_range_btn.text = startResult + " ~ " + endResult

            }
            pd.show(fragmentManager, "YearMonthPickerTest")
        }



    }

    fun get()
    {

        val pref = applicationContext.getSharedPreferences("auto", Activity.MODE_PRIVATE)
        val token = pref.getString("token","")
        recruit_idx = getIntent().getStringExtra("recruit_idx").toString()
        project_idx = getIntent().getStringExtra("project_idx").toString()
        val getRecruitDetailResponse = networkService.getRecruitDetail(token,project_idx,recruit_idx)

        getRecruitDetailResponse.enqueue(object : Callback<GetRecruitDetailResponse> {
            override fun onFailure(call: Call<GetRecruitDetailResponse>?, t: Throwable?) {
                Toast.makeText(applicationContext,"서버 연결 실패", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<GetRecruitDetailResponse>?, response: Response<GetRecruitDetailResponse>?) {
                if(response!!.isSuccessful)
                {
                    data = response.body().result
                    startResult = data[0].start_date.split(",").toString().replace("T00:00:00.000Z]", "").replace("[","")
                    endResult = data[0].end_date.split(",").toString().replace("T00:00:00.000Z]", "").replace("[","")
                    date = startResult + " ~ " + endResult
                    position = data[0].position
                    if(data[0].position.equals("PM")) invite_pm_btn.isSelected = true
                    else if(data[0].position.equals("기획자")) invite_planner_btn.isSelected = true
                    else if(data[0].position.equals("디자이너")) invite_designer_btn.isSelected = true
                    else if(data[0].position.equals("개발자")) invite_developer_btn.isSelected = true
                    else invite_etc_btn.isSelected = true
                    invite_personnel_edit.setText(data[0].number)
                    invite_range_btn.text = date
                }
                else Toast.makeText(applicationContext,"실패",Toast.LENGTH_SHORT).show()
            }
        })
    }
}