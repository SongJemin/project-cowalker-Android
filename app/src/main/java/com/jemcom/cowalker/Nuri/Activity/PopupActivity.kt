package com.jemcom.cowalker.Nuri.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

            override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popup)

                project_idx = intent.getStringExtra("project_idx")
                recruit_idx = intent.getStringExtra("recruit_idx")


        pop_kakao_btn.setOnClickListener {
            sendLink(project_idx, recruit_idx)
        }
    }

    private fun sendLink(project_idx:String, recruit_idx :String) {
        Log.v("TAG","모집 상세 프로젝트 숫자 ="+project_idx + "모집 번호 숫자 = " + recruit_idx )
        val params = FeedTemplate
                .newBuilder(ContentObject.newBuilder("추천합니다",
                        "Asdf",
                        LinkObject.newBuilder().setWebUrl("")
                                .setMobileWebUrl("").build())
                        .setDescrption("우리의 팀이 되어주세요!" +
                                "공유를 해주시면 씨앗을 드립니다")
                        .build())

                .addButton(ButtonObject("코워커 앱으로 열기", LinkObject.newBuilder()

                        //.setWebUrl("'https://developers.kakao.com")
                        //.setMobileWebUrl("http://bghgu.tk:3000/api/project/"+project_idx+"/recruit/"+recruit_idx)

                        .setAndroidExecutionParams("project_idx="+project_idx+"&recruit_idx="+recruit_idx)
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
