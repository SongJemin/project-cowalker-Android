package com.jemcom.cowalker.Jemin.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import com.jemcom.cowalker.R

class InviteActivity : AppCompatActivity() {
    lateinit var startZeroMonth: String
    lateinit var startZeroDay: String
    lateinit var finishZeroMonth: String
    lateinit var finishZeroDay: String
    lateinit var nextBtn: Button
    lateinit var rangeBtn: Button
    lateinit var pmBtn: Button
    lateinit var plannerBtn: Button
    lateinit var designerBtn: Button
    lateinit var developerBtn: Button
    lateinit var etcBtn: Button

    lateinit var start_date: String
    lateinit var end_date: String
    var position: String = ""
    internal var number: Int = 0
    lateinit var project_idx: String

    lateinit var numberEdit: EditText
    var numberValue : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invite)

        val getProjectIdxintent = intent
        project_idx = getProjectIdxintent.getStringExtra("project_idx")

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

        nextBtn = findViewById<View>(R.id.invite1_next_btn) as Button
        rangeBtn = findViewById<View>(R.id.invite_range_btn) as Button
        numberEdit = findViewById<View>(R.id.invite_personnel_edit) as EditText

        pmBtn = findViewById<View>(R.id.invite_pm_btn) as Button
        plannerBtn = findViewById<View>(R.id.invite_planner_btn) as Button
        designerBtn = findViewById<View>(R.id.invite_designer_btn) as Button
        developerBtn = findViewById<View>(R.id.invite_developer_btn) as Button
        etcBtn = findViewById<View>(R.id.invite_etc_btn) as Button


        nextBtn.setOnClickListener {
            if(numberEdit.text.toString() == "" || position == null || start_date == null || end_date == null )
            {
                Toast.makeText(getApplicationContext(), "공백 없이 입력해주세요", Toast.LENGTH_LONG).show();

            }
            else {
                try{
                    numberValue = Integer.parseInt(numberEdit.text.toString())

                    val intent = Intent(this@InviteActivity, Invite2Activity::class.java)
                    Log.v("TAG", "초대1화면 플젝넘버 = $project_idx")
                    intent.putExtra("project_idx", project_idx)

                    val pref = getSharedPreferences("pref", Context.MODE_PRIVATE)
                    val editor = pref.edit()
                    editor.putString("position", position)
                    editor.putString("start_date", start_date)
                    editor.putString("end_date", end_date)
                    editor.putInt("number", numberValue)
                    editor.commit()
                    Log.v("TAG", "역할 = " + position + "시작 = " + start_date + "마지막 = " + end_date + "명 = " + number)

                    startActivity(intent)
                }
                catch (e : NumberFormatException)
                {
                    Toast.makeText(getApplicationContext(), "숫자를 입력해주세요", Toast.LENGTH_LONG).show()
                    e.printStackTrace()
                }



            }


        }

        pmBtn.setOnClickListener {
            pmBtn.isSelected = true
            pmBtn.setTextColor(Color.WHITE)

            plannerBtn.isSelected = false
            designerBtn.isSelected = false
            developerBtn.isSelected = false
            etcBtn.isSelected = false

            plannerBtn.setTextColor(Color.BLACK)
            designerBtn.setTextColor(Color.BLACK)
            developerBtn.setTextColor(Color.BLACK)
            etcBtn.setTextColor(Color.BLACK)
            position = "PM"
            Log.v("TAG", "역할 = $position")
        }

        plannerBtn.setOnClickListener {
            plannerBtn.isSelected = true
            plannerBtn.setTextColor(Color.WHITE)

            pmBtn.isSelected = false
            designerBtn.isSelected = false
            developerBtn.isSelected = false
            etcBtn.isSelected = false

            pmBtn.setTextColor(Color.BLACK)
            designerBtn.setTextColor(Color.BLACK)
            developerBtn.setTextColor(Color.BLACK)
            etcBtn.setTextColor(Color.BLACK)
            position = "기획자"
            Log.v("TAG", "역할 = $position")
        }

        designerBtn.setOnClickListener {
            designerBtn.isSelected = true
            designerBtn.setTextColor(Color.WHITE)

            pmBtn.isSelected = false
            plannerBtn.isSelected = false
            developerBtn.isSelected = false
            etcBtn.isSelected = false

            pmBtn.setTextColor(Color.BLACK)
            plannerBtn.setTextColor(Color.BLACK)
            developerBtn.setTextColor(Color.BLACK)
            etcBtn.setTextColor(Color.BLACK)

            position = "디자이너"
            Log.v("TAG", "역할 = $position")
        }

        developerBtn.setOnClickListener {
            developerBtn.isSelected = true
            developerBtn.setTextColor(Color.WHITE)

            pmBtn.isSelected = false
            plannerBtn.isSelected = false
            designerBtn.isSelected = false
            etcBtn.isSelected = false

            pmBtn.setTextColor(Color.BLACK)
            plannerBtn.setTextColor(Color.BLACK)
            designerBtn.setTextColor(Color.BLACK)
            etcBtn.setTextColor(Color.BLACK)

            position = "개발자"
            Log.v("TAG", "역할 = $position")
        }

        etcBtn.setOnClickListener {
            etcBtn.isSelected = true
            etcBtn.setTextColor(Color.WHITE)

            pmBtn.isSelected = false
            plannerBtn.isSelected = false
            designerBtn.isSelected = false
            developerBtn.isSelected = false

            pmBtn.setTextColor(Color.BLACK)
            plannerBtn.setTextColor(Color.BLACK)
            developerBtn.setTextColor(Color.BLACK)
            developerBtn.setTextColor(Color.BLACK)

            position = "기타"
            Log.v("TAG", "역할 = $position")
        }


        rangeBtn.setOnClickListener {
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
                this@InviteActivity.start_date = startYear.toString() + "-" + startZeroMonth + "-" + startZeroDay
                this@InviteActivity.end_date = finishYear.toString() + "-" + finishZeroMonth + "-" + finishZeroDay
                rangeBtn.text = this@InviteActivity.start_date + " ~ " + this@InviteActivity.end_date
            }
            pd.show(fragmentManager, "YearMonthPickerTest")
        }

    }


}
