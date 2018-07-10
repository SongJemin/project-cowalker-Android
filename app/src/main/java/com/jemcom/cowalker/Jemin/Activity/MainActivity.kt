package com.jemcom.cowalker.Jemin.Activity

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton

import com.jemcom.cowalker.Jemin.Fragment.HomeFragment
import com.jemcom.cowalker.Nuri.Fragment.NoticeTab
import android.widget.Toast
import com.jemcom.cowalker.Hyunmin.MypageTab
import com.jemcom.cowalker.R
import com.jemcom.cowalker.Jemin.Fragment.SearchFragment
import com.jemcom.cowalker.Nuri.Activity.RecruitDeleteActivity
import com.jemcom.cowalker.Nuri.Fragment.OtherpageTab


class MainActivity : AppCompatActivity(), View.OnClickListener {
    //test
    private val FRAGMENT1 = 1
    private val FRAGMENT2 = 2
    private val FRAGMENT4 = 4
    private val FRAGMENT5 = 5
    private val FRAGMENT6 = 6
    private var homeTabBtn: ImageButton? = null
    private var searchTabBtn: ImageButton? = null
    private var createTabBtn: ImageButton? = null
    private var alarmTabBtn: ImageButton? = null
    private var mypageTabBtn: ImageButton? = null
    val FINISH_INTERVAL_TIME = 2000
    var backPressedTime : Long = 0


    override fun onBackPressed() {
        var tempTime = System.currentTimeMillis()
        var intervalTime = tempTime-backPressedTime

        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime) {
            super.onBackPressed()
        } else {
            backPressedTime = tempTime
            Toast.makeText(applicationContext, "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

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


        // 위젯에 대한 참조
        homeTabBtn = findViewById<View>(R.id.home_tab_btn) as ImageButton
        searchTabBtn = findViewById<View>(R.id.search_tab_btn) as ImageButton
        createTabBtn = findViewById<View>(R.id.create_tab_btn) as ImageButton
        alarmTabBtn = findViewById<View>(R.id.alarm_tab_btn) as ImageButton
        mypageTabBtn = findViewById<View>(R.id.mypage_tab_btn) as ImageButton

        // 탭 버튼에 대한 리스너 연결
        homeTabBtn!!.setOnClickListener(this)
        searchTabBtn!!.setOnClickListener(this)
        createTabBtn!!.setOnClickListener(this)
        alarmTabBtn!!.setOnClickListener(this)
        mypageTabBtn!!.setOnClickListener(this)

        // 임의로 액티비티 호출 시점에 어느 프레그먼트를 프레임레이아웃에 띄울 것인지를 정함
        if(intent != null) {
            var status = intent.getStringExtra("status")
            if(status != null) {
                if (status.equals("mypage")) callFragment(FRAGMENT5)
                else callFragment(FRAGMENT6)
            }
            else callFragment(FRAGMENT1)
        }
        else callFragment(FRAGMENT1)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.home_tab_btn ->
                // '홈 버튼' 클릭 시 '홈 프래그먼트' 호출
                callFragment(FRAGMENT1)

            R.id.search_tab_btn ->{
                // '탐색 버튼' 클릭 시 '탐색 프래그먼트' 호출
                val intent = Intent(applicationContext, RecruitDeleteActivity::class.java)
                startActivity(intent)
                //callFragment(FRAGMENT2)
            }
            R.id.create_tab_btn -> {
                // '방생성 버튼' 클릭 시 '방생성 액티비티' 호출
                val intent = Intent(applicationContext, ProjectCreateActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up)
            }

            R.id.alarm_tab_btn ->
                // '알림 버튼' 클릭 시 '알림 프래그먼트' 호출
                callFragment(FRAGMENT4)

            R.id.mypage_tab_btn ->
                // '마이페이지 버튼' 클릭 시 '마이페이지 프래그먼트' 호출
                callFragment(FRAGMENT5)
        }
    }

    private fun callFragment(frament_no: Int) {

        // 프래그먼트 사용을 위해
        val transaction = supportFragmentManager.beginTransaction()

        when (frament_no) {
            1 -> {
                // '홈 프래그먼트' 호출
                val mainFragment = HomeFragment()
                transaction.replace(R.id.fragment_container, mainFragment)
                transaction.commit()
            }

            2 -> {
                // '탐색 프래그먼트' 호출
                val recomFragment = SearchFragment()
                transaction.replace(R.id.fragment_container, recomFragment)
                transaction.commit()
            }


            4 -> {
                // '알림 프래그먼트' 호출
                val alarmFragment = NoticeTab()
                transaction.replace(R.id.fragment_container, alarmFragment)
                transaction.commit()
            }

            5 -> {
                // '마이페이지 프래그먼트' 호출
                val mypageFragment = MypageTab()
                transaction.replace(R.id.fragment_container, mypageFragment)
                transaction.commit()
            }
            6 -> {
                // '타인의 마이페이지 프래그먼트' 호출
                val otherpageFragment = OtherpageTab()
                transaction.replace(R.id.fragment_container, otherpageFragment)
                transaction.commit()
            }
        }

    }
}
