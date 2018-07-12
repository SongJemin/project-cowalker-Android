package com.jemcom.cowalker.Nuri.Activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.jemcom.cowalker.Jemin.Activity.MyYearMonthPickerDialog
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

    lateinit var date : String
    lateinit var start_date: String
    lateinit var end_date: String
    lateinit var startZeroMonth: String
    lateinit var startZeroDay: String
    lateinit var finishZeroMonth: String
    lateinit var finishZeroDay: String
    lateinit var data : ArrayList<GetRecruitDetail>
    lateinit var networkService: NetworkService

    override fun onClick(v: View?) {
        when(v)
        {
            invite1_next_btn -> {

                var intent = Intent(applicationContext,ApplyModify2Activity::class.java)
                intent.putExtra("position",data[0].position)
                intent.putExtra("number",invite_personnel_edit.text)
                intent.putExtra("start_date",invite_range_btn.text.split(" ~ ")[0])
                intent.putExtra("end_date,",invite_range_btn.text.split(" ~ ")[1])
                startActivity(intent)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_invite)

        networkService = ApplicationController.instance.networkSerVice
        invite1_next_btn.setOnClickListener(this)

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
                invite_range_btn.text = this@ApplyModifyActivity.start_date + " ~ " + this@ApplyModifyActivity.end_date
            }
            pd.show(fragmentManager, "YearMonthPickerTest")
        }


        get()
    }

    fun get()
    {

        val recruit_idx = "5b3ecc11ca5c3444e4f802f1"
        val project_idx = "5b3dd2387172d402215033d2"
        val getRecruitDetailResponse = networkService.getRecruitDetail("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoyLCJpYXQiOjE1MzA2NzAxNTMsImV4cCI6MTUzMzI2MjE1M30.BdRb0yary7AY8_yi8MDRDXuXrW19QSqRJI-9Xin3SXs",project_idx,recruit_idx)

        getRecruitDetailResponse.enqueue(object : Callback<GetRecruitDetailResponse> {
            override fun onFailure(call: Call<GetRecruitDetailResponse>?, t: Throwable?) {
                Toast.makeText(applicationContext,"서버 연결 실패", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<GetRecruitDetailResponse>?, response: Response<GetRecruitDetailResponse>?) {
                if(response!!.isSuccessful)
                {
                    data = response.body().result
                    var start = data[0].start_date.split("T").toString()
                    var end = data[0].end_date.split("T").toString()
                    date = start + " ~ " + end
                    if(data[0].position.equals("PM")) invite_pm_btn.isSelected = true
                    else if(data[0].position.equals("기획자")) invite_planner_btn.isSelected = true
                    else if(data[0].position.equals("디자이너")) invite_designer_btn.isSelected = true
                    else if(data[0].position.equals("개발자")) invite_developer_btn.isSelected = true
                    else invite_role_tv.isSelected = true
                    invite_personnel_edit.setText(data[0].number)
                    invite_range_btn.text = date
                }
                else Toast.makeText(applicationContext,"실패",Toast.LENGTH_SHORT).show()
            }
        })
    }
}