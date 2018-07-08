package com.jemcom.cowalker.Hyunmin.Activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import com.jemcom.cowalker.Hyunmin.Adapter.ImageAdapter
import com.jemcom.cowalker.Jemin.Activity.MainActivity
import com.jemcom.cowalker.R
import kotlinx.android.synthetic.main.activity_project_intro.*



//import android.graphics.drawable.shapes.OvalShape
//import android.graphics.drawable.ShapeDrawable
//import kotlinx.android.synthetic.main.activity_project_intro.*


class ProjectIntroActivity : AppCompatActivity() {

    internal lateinit var tv_short: TextView
    internal lateinit var see_more: ImageButton
    internal lateinit var see_close: ImageButton
    var CHECK_NUM = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_intro)

        val viewPager = findViewById<ViewPager>(R.id.image_swipe)
        val adapter = ImageAdapter(this)
        viewPager.adapter = adapter

        btn_join.setOnClickListener {
            val nextIntent = Intent(this, MainActivity::class.java)
            startActivity(nextIntent)
        }
//        profile_img.setBackground(ShapeDrawable(OvalShape()))
//        profile_img.setClipToOutline(true) 프로필 사진 둥글게 하는 것

        tv_short = findViewById<View>(R.id.tv_short) as TextView
        see_more = findViewById<View>(R.id.see_more) as ImageButton
        see_close = findViewById<View>(R.id.see_close) as ImageButton

        see_more.setOnClickListener {
            see_more.visibility = View.GONE
            see_close.visibility = View.VISIBLE
            tv_short.maxLines = Integer.MAX_VALUE
        }
        see_close.setOnClickListener {
            see_close.visibility = View.GONE
            see_more.visibility = View.VISIBLE
            tv_short.maxLines = 2
        }

        scrap_btn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                if(CHECK_NUM == 0) {
                    scrap_btn.isSelected = true
                    CHECK_NUM = 1
                }
                else {
                    scrap_btn.isSelected = false
                    CHECK_NUM = 0
                }
            }
        });
    }
}