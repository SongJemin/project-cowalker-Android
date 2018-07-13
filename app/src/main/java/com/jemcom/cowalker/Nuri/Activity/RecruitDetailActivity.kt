package com.jemcom.cowalker.Nuri.Activity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import android.widget.Toast
import com.jemcom.cowalker.Jemin.Activity.*
import com.jemcom.cowalker.Network.ApplicationController
import com.jemcom.cowalker.Network.Get.Response.GetMypageOtherResponse
import com.jemcom.cowalker.Network.Get.Response.GetRecruitDetailResponse
import com.jemcom.cowalker.Network.NetworkService
import com.jemcom.cowalker.Network.Post.PostShareProject
import com.jemcom.cowalker.Network.Post.PostShareRecruit
import com.jemcom.cowalker.Network.Post.Response.PostShareResponse
import com.jemcom.cowalker.Nuri.Adapter.RecruitListAdapter
import com.jemcom.cowalker.Nuri.Item.RecruitListItem
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
import kotlinx.android.synthetic.main.activity_recruit_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat

class RecruitDetailActivity : AppCompatActivity() {

    lateinit var networkService: NetworkService
    lateinit var recruitListItems: ArrayList<RecruitListItem>
    lateinit var recruitListAdapter : RecruitListAdapter
    var project_idx : String = ""
    var recruit_idx : String = ""
    var position : String = ""
    var url = "https://cdn.xl.thumbs.canstockphoto.com/computer-generated-3d-image-cooperation-stock-illustrations_csp2074347.jpg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recruit_detail)
        val alertDialogBuilder = AlertDialog.Builder(this)

        networkService = ApplicationController.instance.networkSerVice
        val getRecruitintent = intent
        project_idx = getRecruitintent.getStringExtra("project_idx")
        recruit_idx = getRecruitintent.getStringExtra("recruit_idx")
        get()

        recruit_detail_applymember_linear.setOnClickListener{
            var intent = Intent(applicationContext, ApplyMemberActivity::class.java)
            intent.putExtra("recruit_idx",recruit_idx)
            startActivity(intent)
        }

        recruit_detail_participmember_linear.setOnClickListener{
            var intent = Intent(applicationContext, ProjectMemberActivity::class.java)
            intent.putExtra("project_idx",project_idx)
            intent.putExtra("recruit_idx",recruit_idx)
            startActivity(intent)
        }

        recruit_detail_share_btn.setOnClickListener {
            postShareRecruit()
        }

        recruit_detail_btn.setOnClickListener {

            if(recruit_detail_btn.text.equals("모집 관리"))
            {
                val items = arrayOf<CharSequence>("모집 수정", "모집 삭제")

                // 제목셋팅
                alertDialogBuilder.setTitle("모집 관리")
                alertDialogBuilder.setItems(items
                ) { dialog, id ->
                    // 프로젝트 수정
                    if (items[id] === "모집 수정") {
                        val intent = Intent(this@RecruitDetailActivity, MainActivity::class.java)
                        startActivity(intent)

                    } else if (items[id] === "모집 삭제") {
                        val intent = Intent(this@RecruitDetailActivity, RecruitDeleteActivity::class.java)
                        intent.putExtra("project_idx", project_idx)
                        intent.putExtra("recruit_idx", recruit_idx)
                        startActivity(intent)
                    }// 프로젝트 삭제
                    dialog.dismiss()
                }

                // 다이얼로그 생성
                val alertDialog = alertDialogBuilder.create()

                // 다이얼로그 보여주기
                alertDialog.show()
            }


            else {

                var intent = Intent(applicationContext, ApplyDetailActivity::class.java)
                intent.putExtra("project_idx", project_idx)
                intent.putExtra("recruit_idx", recruit_idx)

                intent.putExtra("position", position)
                Log.v("TAG", "리쿠릇에서 어플라이로 보내는 포지션 = " + position)

                startActivity(intent)
            }
        }

    }

    fun get()
    {

        val pref = applicationContext.getSharedPreferences("auto", Activity.MODE_PRIVATE)
        val token = pref.getString("token","")
        val project_idx = "5b3dd2387172d402215033d2"
        val recruit_idx = "5b3ecc11ca5c3444e4f802f1"

        //val project_idx = "5b3dd2387172d402215033d2"
        //val recruit_idx = "5b3ecc11ca5c3444e4f802f1"
        val getRecruitDetailResponse = networkService.getRecruitDetail(token,project_idx,recruit_idx)

        getRecruitDetailResponse.enqueue(object : Callback<GetRecruitDetailResponse>{
            override fun onFailure(call: Call<GetRecruitDetailResponse>?, t: Throwable?) {
                Toast.makeText(applicationContext,"서버 연결 실패",Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<GetRecruitDetailResponse>?, response: Response<GetRecruitDetailResponse>?) {
                if(response!!.isSuccessful)
                {
                    var data = response.body().result
                    var btnResult = response.body().btnResult

//                    var sdf = SimpleDateFormat("yyyy.MM.dd")
//                    var start = sdf.format("2018-07-03T00:00:00.000Z")
//                    var end = sdf.format("2018-07-20T00:00:00.000Z")
//                    var date = start + " ~ " + end
                    position = data[0].position
                    recruit_detail_position_tv.setText(data[0].position)
//                    recruit_detail_date_tv.setText(date)
                    recruit_detail_num_tv.setText(data[0].number)
                    recruit_detail_task_tv.setText(data[0].task)
//                    recruit_detail_time_tv.setText(date)
                    recruit_detail_area_tv.setText(data[0].area)
                    recruit_detail_reward_tv.setText(data[0].reward)
                    recruit_detail_ability_tv.setText(data[0].ability)
                    recruit_detail_career_tv.setText(data[0].career)
                    recruit_detail_preference_tv.setText(data[0].preference)

                    //나중에 버튼 텍스트 변경 예정
                    //recruit_detail_comment_tv.setText(data[0].comment)
                    if(btnResult.equals("개발자")){
                        btnResult = "모집 관리"
                    }
                    recruit_detail_btn.setText(btnResult)

                    if(btnResult.equals("참여완료"))
                    {
                        recruit_detail_btn.setBackgroundColor(Color.parseColor("#eeeeee"))
                        recruit_detail_btn.setTextColor(Color.parseColor("#c5c5c5"))
                    }
                    if(btnResult.equals("모집 관리"))
                    {

                        //recruit_detail_recommend.setVisibility(View.GONE)
                    }
                }
                else Toast.makeText(applicationContext,"실패",Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun sendLink() {
        val params = FeedTemplate
                .newBuilder(ContentObject.newBuilder("공공서비스 어플리케이션 공모전",
                        url,
                        LinkObject.newBuilder().setWebUrl("")
                                .setMobileWebUrl("").build())
                        .setDescrption("이충엽님이 당신을 추천하셨습니다. 함께 해주세요!")
                        .build())

                .addButton(ButtonObject("깅스앱으로 열기", LinkObject.newBuilder()

                        .setWebUrl("'https://developers.kakao.com")
                        .setMobileWebUrl("'https://developers.kakao.com")
                        .setAndroidExecutionParams("key1=value1")
                        .setIosExecutionParams("key1=value1")
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
                    sendLink()
                }
            }

            override fun onFailure(call: Call<PostShareResponse>, t: Throwable?) {
                Toast.makeText(applicationContext,"서버 연결 실패",Toast.LENGTH_SHORT).show()
            }

        })
    }
}
