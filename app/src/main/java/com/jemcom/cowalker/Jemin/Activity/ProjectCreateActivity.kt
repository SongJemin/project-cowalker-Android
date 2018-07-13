package com.jemcom.cowalker.Jemin.Activity

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast

import com.jemcom.cowalker.R

class ProjectCreateActivity : AppCompatActivity() {

    internal lateinit var nextBtn: Button
    internal lateinit var titleEdit: EditText
    internal lateinit var summaryEdit: EditText
    internal var projectTitle = ""
    internal var projectSummary = ""
    internal var projectAim = ""
    internal var projectDepartment = ""
    internal var projectArea = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_create)

        nextBtn = findViewById<View>(R.id.create1_next_btn) as Button
        titleEdit = findViewById<View>(R.id.create1_title_edit) as EditText
        summaryEdit = findViewById<View>(R.id.create1_summary_edit) as EditText



        nextBtn.setOnClickListener(View.OnClickListener {
            if (titleEdit.text.toString().isEmpty()) {
                Toast.makeText(this@ProjectCreateActivity, "제목을 입력해주세요", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            if (summaryEdit.text.toString().isEmpty()) {
                Toast.makeText(this@ProjectCreateActivity, "한줄 소개말을 입력해주세요", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            projectTitle = titleEdit.text.toString()
            projectSummary = summaryEdit.text.toString()

            Log.v("TAG", "title = " + projectTitle + ", summary = " + projectSummary + ", aim = "
                    + projectAim + ", department = " + projectDepartment + ", Area = " + projectArea)
            val intent = Intent(this@ProjectCreateActivity, ProjectCreate2Activity::class.java)

            //Intent intent = new Intent(ProjectCreateActivity.this, ProjectCreate2Activity.class);
            intent.putExtra("title", projectTitle)
            intent.putExtra("summary", projectSummary)
            intent.putExtra("aim", projectAim)
            intent.putExtra("department", projectDepartment)
            intent.putExtra("area", projectArea)
            startActivity(intent)
        })

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

        val purpose_list = arrayOf("창업", "공모전 참여", "스터디", "사이드 프로젝트", "창업", "기타")
        val field_list = arrayOf("블록체인", "IoT", "인공지능", "디자인", "콘텐츠", "기타")
        val location_list = arrayOf("서울", "경기도", "인천", "강원도", "충청도", "전라도", "경상도", "제주도")


        val purposeSpinner = findViewById<View>(R.id.create1_purpose_spinner) as Spinner
        val fieldSpinner = findViewById<View>(R.id.create1_field_spinner) as Spinner
        val locationSpinner = findViewById<View>(R.id.create1_location_spinner) as Spinner

        // 목적 에 대한 Spinner
        val adapter = ArrayAdapter(
                applicationContext, // 현재화면의 제어권자
                R.layout.spin,
                purpose_list)
        adapter.setDropDownViewResource(
                R.layout.spin_dropdown)
        purposeSpinner.adapter = adapter

        // 분야 에 대한 Spinner
        val adapter2 = ArrayAdapter(
                applicationContext, // 현재화면의 제어권자
                R.layout.spin,
                field_list)
        adapter2.setDropDownViewResource(
                R.layout.spin_dropdown)
        fieldSpinner.adapter = adapter2

        // 지역 에 대한 Spinner
        val adapter3 = ArrayAdapter(
                applicationContext, // 현재화면의 제어권자
                R.layout.spin,
                location_list)
        adapter3.setDropDownViewResource(
                R.layout.spin_dropdown)
        locationSpinner.adapter = adapter3

        purposeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                if (position == 0) {
                    projectAim = "창업"
                } else if (position == 1) {
                    projectAim = "공모전 참여"
                } else if (position == 2) {
                    projectAim = "스터디"
                } else if (position == 3) {
                    projectAim = "사이드 프로젝트"
                } else if (position == 4) {
                    projectAim = "창업"
                } else if (position == 5) {
                    projectAim = "기타"
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        fieldSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                if (position == 0) {
                    projectDepartment = "블록체인"
                } else if (position == 1) {
                    projectDepartment = "IoT"
                } else if (position == 2) {
                    projectDepartment = "인공지능"
                } else if (position == 3) {
                    projectDepartment = "디자인"
                } else if (position == 4) {
                    projectDepartment = "콘텐츠"
                } else if (position == 5) {
                    projectDepartment = "기타"
                }
            }


            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }


        locationSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                if (position == 0) {
                    projectArea = "서울"
                } else if (position == 1) {
                    projectArea = "경기도"
                } else if (position == 2) {
                    projectArea = "인천"
                } else if (position == 3) {
                    projectArea = "강원도"
                } else if (position == 4) {
                    projectArea = "충청도"
                } else if (position == 5) {
                    projectArea = "전라도"
                } else if (position == 6) {
                    projectArea = "경상도"
                } else if (position == 7) {
                    projectArea = "제주도"
                }
            }


            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

    }

    override fun onBackPressed() {
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down)
    }
}
