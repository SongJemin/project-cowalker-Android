package com.jemcom.cowalker.Jemin.Activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.facebook.share.model.ShareLinkContent
import com.facebook.share.widget.ShareDialog
import com.jemcom.cowalker.Network.ApplicationController
import com.jemcom.cowalker.Network.NetworkService
import com.jemcom.cowalker.Network.Post.PostShareProject
import com.jemcom.cowalker.Network.Post.PostShareRecruit
import com.jemcom.cowalker.Network.Post.PostSharedSns
import com.jemcom.cowalker.Network.Post.Response.PostShareResponse
import com.jemcom.cowalker.Network.Post.Response.PostSharedSnsResponse
import com.jemcom.cowalker.R
import com.jemcom.cowalker.R.id.share_facebook_btn
import com.kakao.kakaolink.v2.KakaoLinkResponse
import com.kakao.kakaolink.v2.KakaoLinkService
import com.kakao.message.template.ButtonObject
import com.kakao.message.template.ContentObject
import com.kakao.message.template.FeedTemplate
import com.kakao.message.template.LinkObject
import com.kakao.network.ErrorResult
import com.kakao.network.callback.ResponseCallback
import com.kakao.util.helper.log.Logger
import kotlinx.android.synthetic.main.activity_share.*
import retrofit2.Call
import retrofit2.Response

class ShareActivity : AppCompatActivity() {
    lateinit var networkService: NetworkService
    var project_idx: String = ""
    var recruit_idx: String = ""
    var title: String = ""
    var imgUrl: String = ""

    @BindView(R.id.fb_share_button)
    internal var facebookbtn: Button? = null

    @OnClick(R.id.fb_share_button)
    internal fun fackbookShare() {
        shareFacebook()
    }

    var url = "https://cdn.xl.thumbs.canstockphoto.com/computer-generated-3d-image-cooperation-stock-illustrations_csp2074347.jpg"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share)
        networkService = ApplicationController.instance.networkSerVice
        FacebookSdk.sdkInitialize(applicationContext)
        AppEventsLogger.activateApp(this)
        ButterKnife.bind(this)



        project_idx = intent.getStringExtra("project_idx")
        recruit_idx = intent.getStringExtra("recruit_idx")
        title = intent.getStringExtra("title")
        imgUrl = intent.getStringExtra("imgUrl")

        share_kakao_btn.setOnClickListener {
            //postShareProject()
            postShareRecruit()
        }

        share_facebook_btn.setOnClickListener {
            shareFacebook()
        }
    }

    private fun sendLink(project_idx:String, recruit_idx :String) {
        Log.v("TAG","모집 상세 프로젝트 숫자 ="+project_idx + "모집 번호 숫자 = " + recruit_idx)
        val params = FeedTemplate
                .newBuilder(ContentObject.newBuilder(title,
                        imgUrl,
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


    fun postShareRecruit()
    {
        val pref = getSharedPreferences("auto", Activity.MODE_PRIVATE)
        val token = pref.getString("token","")
        var data = PostShareRecruit(recruit_idx)
        var postShareResponse = networkService.postShareRecruit(token,data)

        postShareResponse.enqueue(object : retrofit2.Callback<PostShareResponse>{

            override fun onResponse(call: Call<PostShareResponse>, response: Response<PostShareResponse>) {
                if(response.isSuccessful){
                    Log.v("TAG","모집 공유 성공")
                    sendLink(project_idx, recruit_idx)
                }
            }

            override fun onFailure(call: Call<PostShareResponse>, t: Throwable?) {
                Toast.makeText(applicationContext,"서버 연결 실패",Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun shareFacebook() {

        networkService = ApplicationController.instance.networkSerVice // 어플리케이션을 실행하자마자 어플리케이션 콘트롤러가 실행되는데 그 때 사용?3333
        //서버랑 통신
        val project_idx = "5b485b6568a9df1f8e89dfb5"
        val recruit_idx = "5b4860f286e69824a632180b"
        val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjozNCwiaWF0IjoxNTMxNDE1MzYyLCJleHAiOjE1MzQwMDczNjJ9.41CKoAi1cODVpaPJ4sFv7iBYB7vxhYV5D0jJaXZIovo"

        val postSharedSns = PostSharedSns(project_idx, recruit_idx)

        val postSharedSnsResponse = networkService.postSharedSns(token, postSharedSns)

        postSharedSnsResponse.enqueue(object : retrofit2.Callback<PostSharedSnsResponse> {
            override fun onResponse(call: Call<PostSharedSnsResponse>, response: Response<PostSharedSnsResponse>) {
                if (response.isSuccessful) {



                } else {
                    Toast.makeText(this@ShareActivity, "오류", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<PostSharedSnsResponse>, t: Throwable) {
                Toast.makeText(this@ShareActivity, "network Fail", Toast.LENGTH_SHORT).show()
            }
        })

        // recruit_idx가 없으면 project프로젝트 조회 페이지로 링크로 넘어가야 함.
//        Toast.makeText(this, "recruit_idx.length:"+recruit_idx.length(), Toast.LENGTH_SHORT).show();
        if (recruit_idx.length == 0) {
            Toast.makeText(this, "recruit_idx is null", Toast.LENGTH_SHORT).show()
            // 링크 전달
            val content = ShareLinkContent.Builder()
                    .setContentUrl(Uri.parse("http://cowalker.cf:8080/boards/$project_idx"))
                    .build()

            val shareDialog = ShareDialog(this)
            shareDialog.show(content, ShareDialog.Mode.FEED)
            //
        } else {
            //            Toast.makeText(this, "recruit_idx is null", Toast.LENGTH_SHORT).show();
            val content = ShareLinkContent.Builder()
                    .setContentUrl(Uri.parse("http://cowalker.cf:8080/boards/$project_idx/subrecruit/$recruit_idx"))
                    .build()


            val shareDialog = ShareDialog(this)
            shareDialog.show(content, ShareDialog.Mode.FEED)
        }


    }

    override fun onBackPressed() {
        val intent = Intent(applicationContext, ProjectCreateActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down)
    }
}
