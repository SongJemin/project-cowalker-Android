package com.jemcom.cowalker.Nuri.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.jemcom.cowalker.R
import com.kakao.kakaolink.v2.KakaoLinkResponse
import com.kakao.kakaolink.v2.KakaoLinkService
import com.kakao.message.template.ButtonObject
import com.kakao.message.template.ContentObject
import com.kakao.message.template.FeedTemplate
import com.kakao.message.template.LinkObject
import com.kakao.network.ErrorResult
import com.kakao.network.callback.ResponseCallback
import com.kakao.util.helper.log.Logger
import kotlinx.android.synthetic.main.activity_popup.*

class PopupActivity : AppCompatActivity() {

    var project_idx : String = ""
    var recruit_idx : String = ""
    var recommend_idx : String = ""
    var check_flag : String = ""
    var imgUrl: String = ""
    var title: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popup)

                project_idx = intent.getStringExtra("project_idx")
                recruit_idx = intent.getStringExtra("recruit_idx")
                recommend_idx = intent.getStringExtra("recommend_idx")
                check_flag = "1"
        imgUrl = intent.getStringExtra("imgUrl")
        title = intent.getStringExtra("title")

        pop_kakao_btn.setOnClickListener {
            sendLink(project_idx, recruit_idx, recommend_idx, check_flag)
        }

        pop_link_btn.setOnClickListener {
            Toast.makeText(applicationContext,"준비 중입니다",Toast.LENGTH_SHORT).show()
        }

        pop_message_btn.setOnClickListener {
            Toast.makeText(applicationContext,"준비 중입니다",Toast.LENGTH_SHORT).show()
        }

        pop_mail_btn.setOnClickListener {
            Toast.makeText(applicationContext,"준비 중입니다",Toast.LENGTH_SHORT).show()
        }

        pop_more_btn.setOnClickListener {
            Toast.makeText(applicationContext,"준비 중입니다", Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendLink(project_idx : String, recruit_idx : String, recommend_idx : String, check_flag : String) {
        Log.v("TAG","모집 상세 프로젝트 숫자 ="+project_idx + "모집 번호 숫자 = " + recruit_idx + "추천인 숫자 = " + recommend_idx)
        val params = FeedTemplate
                .newBuilder(ContentObject.newBuilder(title,
                        imgUrl,
                        LinkObject.newBuilder().setWebUrl("")
                                .setMobileWebUrl("").build())
                        .setDescrption("당신은 해당 모집에 추천되셨습니다. " +
                                "함께 해주세요!")
                        .build())

                .addButton(ButtonObject("코워커 앱으로 열기", LinkObject.newBuilder()

                        //.setWebUrl("'https://developers.kakao.com")
                        //.setMobileWebUrl("http://bghgu.tk:3000/api/project/"+project_idx+"/recruit/"+recruit_idx)

                        .setAndroidExecutionParams("project_idx="+project_idx+"&recruit_idx="+recruit_idx +"&recommend_idx="+recommend_idx +"&check_flag="+check_flag)
                        .build()))
                .build()

        KakaoLinkService.getInstance().sendDefault(this, params, object : ResponseCallback<KakaoLinkResponse>() {

            override fun onFailure(errorResult: ErrorResult) {

                Logger.e(errorResult.toString())
            }

            override fun onSuccess(result: KakaoLinkResponse) {}
        })
    }

}
