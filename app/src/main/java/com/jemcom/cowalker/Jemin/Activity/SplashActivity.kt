package com.jemcom.cowalker.Jemin.Activity

import android.os.Bundle
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Handler
import android.view.View
import com.jemcom.cowalker.Nuri.Activity.LoginActivity
import com.jemcom.cowalker.R

/**
 * Created by JAMCOM on 2018-03-25.
 */

class SplashActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)


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

        val hd = Handler()
        hd.postDelayed(splashhandler(), 1500) // 3000ms=3초후에 핸들러 실행 //딜레이 3000

    }

    private inner class splashhandler : Runnable {
        override fun run() {
           // startActivity(Intent(applicationContext, MainActivityKt::class.java))
            startActivity(Intent(applicationContext, LoginActivity::class.java))
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out) // fade in, fade out 애니메이션 효과

            this@SplashActivity.finish() // 스플래쉬 페이지 액티비티 스택에서 제거
        }
    }

    override fun onBackPressed() {
        //스플래쉬 화면에서 뒤로가기 버튼 금지
    }


}

