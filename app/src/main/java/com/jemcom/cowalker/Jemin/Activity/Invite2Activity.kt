package com.jemcom.cowalker.Jemin.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout

import com.jemcom.cowalker.R

class Invite2Activity : AppCompatActivity() {
    internal var url = "https://cdn.xl.thumbs.canstockphoto.com/computer-generated-3d-image-cooperation-stock-illustrations_csp2074347.jpg"
    private val mLayout: LinearLayout? = null
    private val mEditText: EditText? = null
    private val mButton: Button? = null
    private val edit: EditText? = null
    private val button: Button? = null
    private var nextBtn: Button? = null
    internal var count: Int = 0

    internal lateinit var task: String
    internal lateinit var activity: String
    internal lateinit var area: String
    internal lateinit var reward: String
    internal lateinit var taskEdit: EditText
    internal lateinit var activityEdit: EditText
    internal lateinit var areaEdit: EditText
    internal lateinit var rewardEdit: EditText
    internal lateinit var project_idx: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invite2)


        taskEdit = findViewById<View>(R.id.invite2_task_edit) as EditText
        activityEdit = findViewById<View>(R.id.invite2_activity_edit) as EditText
        areaEdit = findViewById<View>(R.id.invite2_area_edit) as EditText
        rewardEdit = findViewById<View>(R.id.invite2_reward_edit) as EditText

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

        nextBtn = findViewById<View>(R.id.invite2_next_btn) as Button

        nextBtn!!.setOnClickListener {
            task = taskEdit.text.toString()
            activity = activityEdit.text.toString()
            area = areaEdit.text.toString()
            reward = rewardEdit.text.toString()

            val pref = getSharedPreferences("pref", Context.MODE_PRIVATE)
            val editor = pref.edit()
            editor.putString("task", task)
            editor.putString("activity", activity)
            editor.putString("area", area)
            editor.putString("reward", reward)
            editor.commit()

            val intent = Intent(this@Invite2Activity, Invite3Activity::class.java)
            Log.v("TAG", "초대2화면 플젝넘버 = $project_idx")
            intent.putExtra("project_idx", project_idx)
            startActivity(intent)
        }


    }
}
