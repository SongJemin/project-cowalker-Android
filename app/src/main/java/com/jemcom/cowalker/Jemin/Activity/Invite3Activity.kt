package com.jemcom.cowalker.Jemin.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import com.jemcom.cowalker.R
import kotlinx.android.synthetic.main.activity_invite3.*

class Invite3Activity : AppCompatActivity() {

    internal lateinit var nextBtn: Button
    internal var rangeBtn: Button? = null
    internal lateinit var abilityEdit: EditText
    internal lateinit var careerEdit: EditText
    internal lateinit var preferenceEdit: EditText
    internal lateinit var commentEdit: EditText

    // 첫 번째 화면 값 받아오기
    internal var start_date: String? = null
    internal var end_date: String? = null
    internal var position: String? = null
    internal var number: Int = 0

    internal var task: String? = null
    internal var activity: String? = null
    internal var area: String? = null
    internal var reward: String? = null

    internal lateinit var ability: String
    internal lateinit var career: String
    internal lateinit var preference: String
    internal lateinit var comment: String
    internal lateinit var project_idx: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invite3)

        val getProjectIdxintent = intent
        project_idx = getProjectIdxintent.getStringExtra("project_idx")

        abilityEdit = findViewById<View>(R.id.invite3_ability_edit) as EditText
        careerEdit = findViewById<View>(R.id.invite3_career_edit) as EditText
        preferenceEdit = findViewById<View>(R.id.invite3_preference_edit) as EditText
        commentEdit = findViewById<View>(R.id.invite3_comment_edit) as EditText


        nextBtn = findViewById<View>(R.id.invite3_next_btn) as Button
        nextBtn.setOnClickListener {
            ability = abilityEdit.text.toString()
            career = careerEdit.text.toString()
            preference = preferenceEdit.text.toString()
            comment = commentEdit.text.toString()

            if(ability == "" || career == "" || preference == "" )
            {
                Toast.makeText(getApplicationContext(), "공백 없이 입력해주세요", Toast.LENGTH_LONG).show();

            }

            else{
                val pref = getSharedPreferences("pref", Context.MODE_PRIVATE)
                val editor = pref.edit()
                editor.putString("ability", ability)
                editor.putString("career", career)
                editor.putString("preference", preference)
                editor.putString("comment", comment)
                editor.commit()
                val intent = Intent(this@Invite3Activity, Invite4Activity::class.java)
                Log.v("TAG", "초대3화면 플젝넘버 = $project_idx")
                intent.putExtra("project_idx", project_idx)
                startActivity(intent)
            }


        }
    }
}
