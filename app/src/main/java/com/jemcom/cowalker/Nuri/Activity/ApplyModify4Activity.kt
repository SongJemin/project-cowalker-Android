package com.jemcom.cowalker.Nuri.Activity

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.jemcom.cowalker.Network.ApplicationController
import com.jemcom.cowalker.Network.NetworkService
import com.jemcom.cowalker.R
import kotlinx.android.synthetic.main.activity_invite4.*
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_invite2)

        networkService = ApplicationController.instance.networkSerVice

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
        get()
    }

    fun get()
    {
        var recruit_idx = ""
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