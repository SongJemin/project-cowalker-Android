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
import android.R.attr.fragment
import android.R.attr.value
import android.R.attr.key
import android.app.Activity
import android.util.Log
import com.jemcom.cowalker.Nuri.Activity.LoginActivity

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    //test


    private val FRAGMENT1 = 1
    private val FRAGMENT2 = 2
    private val FRAGMENT4 = 4
    private val FRAGMENT5 = 5
    private val FRAGMENT6 = 6
    private var homeTabBtn: ImageButton? = null
    private var homeTabSelectedBtn: ImageButton? = null
    private var searchTabBtn: ImageButton? = null
    private var searchTabSelectedBtn: ImageButton? = null
    private var createTabBtn: ImageButton? = null
    private var createTabSelectedBtn: ImageButton? = null
    private var alarmTabBtn: ImageButton? = null
    private var alarmTabSelectedBtn: ImageButton? = null
    private var mypageTabBtn: ImageButton? = null

    private var mypageTabSelectedBtn: ImageButton? = null
    val FINISH_INTERVAL_TIME = 2000
    var backPressedTime : Long = 0

    var user_idx : String? = null
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

        val pref = applicationContext.getSharedPreferences("auto", Activity.MODE_PRIVATE)
        val token = pref.getString("token","")
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
        homeTabSelectedBtn = findViewById<View>(R.id.home_tab_selected_btn) as ImageButton
        searchTabBtn = findViewById<View>(R.id.search_tab_btn) as ImageButton
        searchTabSelectedBtn = findViewById<View>(R.id.home_tab_selected_btn) as ImageButton
        createTabBtn = findViewById<View>(R.id.create_tab_btn) as ImageButton
        createTabSelectedBtn = findViewById<View>(R.id.home_tab_selected_btn) as ImageButton
        alarmTabBtn = findViewById<View>(R.id.alarm_tab_btn) as ImageButton
        alarmTabSelectedBtn = findViewById<View>(R.id.home_tab_selected_btn) as ImageButton
        mypageTabBtn = findViewById<View>(R.id.mypage_tab_btn) as ImageButton
        mypageTabSelectedBtn = findViewById<View>(R.id.home_tab_selected_btn) as ImageButton

        // 탭 버튼에 대한 리스너 연결
        homeTabBtn!!.setOnClickListener(this)
        searchTabBtn!!.setOnClickListener(this)
        createTabBtn!!.setOnClickListener(this)
        alarmTabBtn!!.setOnClickListener(this)
        mypageTabBtn!!.setOnClickListener(this)

        // 임의로 액티비티 호출 시점에 어느 프레그먼트를 프레임레이아웃에 띄울 것인지를 정함
        if(intent != null) {
            var status = intent.getStringExtra("status")
            user_idx = intent.getStringExtra("user_idx")
            if(status != null) {
                if (status.equals("mypage")) callFragment(FRAGMENT5)
                else {
                    callFragment(FRAGMENT6)

                }

            }
            else callFragment(FRAGMENT1)
        }
        else callFragment(FRAGMENT1)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.home_tab_btn -> {
                // '홈 버튼' 클릭 시 '홈 프래그먼트' 호출
                callFragment(FRAGMENT1)
                home_tab_btn.visibility = View.GONE
                home_tab_selected_btn.visibility = View.VISIBLE

                search_tab_btn.visibility = View.VISIBLE
                search_tab_selected_btn.visibility = View.GONE
                create_tab_btn.visibility = View.VISIBLE
                create_tab_selected_btn.visibility = View.GONE
                alarm_tab_btn.visibility = View.VISIBLE
                alarm_tab_selected_btn.visibility = View.GONE
                mypage_tab_btn.visibility = View.VISIBLE
                mypage_tab_selected_btn.visibility = View.GONE
            }

            R.id.search_tab_btn ->{
                // '탐색 버튼' 클릭 시 '탐색 프래그먼트' 호출
                callFragment(FRAGMENT2)
                search_tab_btn.visibility = View.GONE
                search_tab_selected_btn.visibility = View.VISIBLE

                home_tab_btn.visibility = View.VISIBLE
                home_tab_selected_btn.visibility = View.GONE
                create_tab_btn.visibility = View.VISIBLE
                create_tab_selected_btn.visibility = View.GONE
                alarm_tab_btn.visibility = View.VISIBLE
                alarm_tab_selected_btn.visibility = View.GONE
                mypage_tab_btn.visibility = View.VISIBLE
                mypage_tab_selected_btn.visibility = View.GONE
            }
            R.id.create_tab_btn -> {
                // '방생성 버튼' 클릭 시 '방생성 액티비티' 호출
                create_tab_btn.visibility = View.GONE
                create_tab_selected_btn.visibility = View.VISIBLE

                home_tab_btn.visibility = View.VISIBLE
                home_tab_selected_btn.visibility = View.GONE
                search_tab_btn.visibility = View.VISIBLE
                search_tab_selected_btn.visibility = View.GONE
                alarm_tab_btn.visibility = View.VISIBLE
                alarm_tab_selected_btn.visibility = View.GONE
                mypage_tab_btn.visibility = View.VISIBLE
                mypage_tab_selected_btn.visibility = View.GONE

                val pref = applicationContext.getSharedPreferences("auto", Activity.MODE_PRIVATE)
                val token = pref.getString("token","")
                if(token.length > 0) {
                    val intent = Intent(applicationContext, ProjectCreateActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up)
                }
                else {
                    var intent = Intent(applicationContext,LoginActivity::class.java)
                    startActivity(intent)
                }
            }

            R.id.alarm_tab_btn -> {
                // '알림 버튼' 클릭 시 '알림 프래그먼트' 호출
                alarm_tab_btn.visibility = View.GONE
                alarm_tab_selected_btn.visibility = View.VISIBLE

                home_tab_btn.visibility = View.VISIBLE
                home_tab_selected_btn.visibility = View.GONE
                search_tab_btn.visibility = View.VISIBLE
                search_tab_selected_btn.visibility = View.GONE
                create_tab_btn.visibility = View.VISIBLE
                create_tab_selected_btn.visibility = View.GONE
                mypage_tab_btn.visibility = View.VISIBLE
                mypage_tab_selected_btn.visibility = View.GONE
                val pref = applicationContext.getSharedPreferences("auto", Activity.MODE_PRIVATE)
                val token = pref.getString("token","")
                if(token.length > 0) callFragment(FRAGMENT4)
                else {
                    Toast.makeText(this@MainActivity,"로그인 후에 이용해주세요", Toast.LENGTH_SHORT).show()
                    var intent = Intent(applicationContext,LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }

            R.id.mypage_tab_btn -> {
                // '마이페이지 버튼' 클릭 시 '마이페이지 프래그먼트' 호출
                mypage_tab_btn.visibility = View.GONE
                mypage_tab_selected_btn.visibility = View.VISIBLE

                home_tab_btn.visibility = View.VISIBLE
                home_tab_selected_btn.visibility = View.GONE
                search_tab_btn.visibility = View.VISIBLE
                search_tab_selected_btn.visibility = View.GONE
                create_tab_btn.visibility = View.VISIBLE
                create_tab_selected_btn.visibility = View.GONE
                alarm_tab_btn.visibility = View.VISIBLE
                alarm_tab_selected_btn.visibility = View.GONE
                val pref = applicationContext.getSharedPreferences("auto", Activity.MODE_PRIVATE)
                val token = pref.getString("token","")
                if(token.length > 0) callFragment(FRAGMENT5)
                else {
                    Toast.makeText(this@MainActivity,"로그인 후에 이용해주세요", Toast.LENGTH_SHORT).show()
                    var intent = Intent(applicationContext,LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
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
                val bundle = Bundle()
                bundle.putString("user_idx", user_idx)
                Log.v("TAG","메인에서 보내는 유저 번호 = "+user_idx)
                otherpageFragment.setArguments(bundle)

                transaction.replace(R.id.fragment_container, otherpageFragment)
                transaction.commit()
            }
        }
    }
}
