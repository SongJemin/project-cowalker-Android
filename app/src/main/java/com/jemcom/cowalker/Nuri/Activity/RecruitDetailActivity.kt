package com.jemcom.cowalker.Nuri.Activity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
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
import kotlinx.android.synthetic.main.activity_project_detail.*
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
    var send_recruit_idx : String = ""
    var position : String = ""
    var num : String = ""
    var task : String = ""
    var title : String = ""
    var imgUrl : String = ""

    var recruit_idx_value : String = ""
    var sharer_idx_value : String = ""
    var sharer_idx : String = ""
    var flag : Int = 0


    override fun onNewIntent(intent: Intent) {
        // super.onNewIntent(intent);
        setIntent(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recruit_detail)
        val alertDialogBuilder = AlertDialog.Builder(this)
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
        networkService = ApplicationController.instance.networkSerVice

        var test : Uri? = null
        test = intent.data
        if (test != null) {
            project_idx = test.getQueryParameter("project_idx")
            val recruit_idx = test.getQueryParameter("recruit_idx")
            val sharer_idx = test.getQueryParameter("sharer_idx")
            Log.v("TAG","까똑 = " + test)
            Log.v("TAG","테스트 값 = " + test.toString())
            Log.v("TAG", "카카오톡 프로젝트 넘버 = " + test.getQueryParameter("project_idx"))
            Log.v("TAG", "카카오톡 모집 넘버 = " + test.getQueryParameter("recruit_idx"))
            Log.v("TAG", "카카오톡 진짜 받은 프로젝트 넘버 = " + project_idx )
            Log.v("TAG", "카카오톡 진짜 받은 모집 넘버 = " + recruit_idx )
            Log.v("TAG", "카카오톡 진짜 받은 공유자 넘버 = " + sharer_idx )
            getKakao(project_idx, recruit_idx)
            setRecruitIdx(recruit_idx)
            setSharerIdx(sharer_idx)

        }
        else{
            Log.v("TAG","인텐트로 넘어온 화면")
            val getRecruitintent = intent
            num = getRecruitintent.getStringExtra("num")
            task = getRecruitintent.getStringExtra("task")
            project_idx = getRecruitintent.getStringExtra("project_idx")
            recruit_idx = getRecruitintent.getStringExtra("recruit_idx")
            title = getRecruitintent.getStringExtra("title")
            imgUrl = getRecruitintent.getStringExtra("imgUrl")
            get()
        }

        Log.v("TAG","까똑2 = " + test)

        recruit_detail_applymember_linear.setOnClickListener{
            val pref = applicationContext.getSharedPreferences("auto", Activity.MODE_PRIVATE)
            val token = pref.getString("token","")
            if(token.length > 0) {
                var intent = Intent(applicationContext, ApplyMemberActivity::class.java)
                intent.putExtra("recruit_idx", recruit_idx)
                intent.putExtra("flag", 1)
                intent.putExtra("num", num)
                Log.v("tag", "리쿠릇디테일에서 보내는 num" + num)
                intent.putExtra("task", task)
                startActivity(intent)
            }
            else
            {
                var intent = Intent(applicationContext,LoginActivity::class.java)
                startActivity(intent)
            }
        }

        recruit_detail_participmember_linear.setOnClickListener{
            var intent = Intent(applicationContext, ProjectMemberActivity::class.java)
            intent.putExtra("project_idx",project_idx)
            intent.putExtra("recruit_idx",recruit_idx)
            startActivity(intent)
        }

        recruit_detail_share_btn.setOnClickListener {
            val intent = Intent(this@RecruitDetailActivity, ShareActivity::class.java)
            intent.putExtra("project_idx", project_idx)
            intent.putExtra("recruit_idx", recruit_idx)
            intent.putExtra("title",title)
            intent.putExtra("imgUrl",imgUrl)
            Log.v("asdf","로그 = " + project_idx + ", "+ recruit_idx)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up)
            //postShareRecruit()
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
                        val intent = Intent(this@RecruitDetailActivity, ApplyModifyActivity::class.java)
                        intent.putExtra("project_idx", project_idx)
                        intent.putExtra("recruit_idx", recruit_idx)
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
                // 카카오톡에서 받은 사람이라면
                if (test != null) {
                    var intent = Intent(applicationContext, ApplyDetailActivity::class.java)
                    flag = 2
                    intent.putExtra("project_idx", project_idx)
                    intent.putExtra("recruit_idx", getRecruitIdx())
                    intent.putExtra("task", task)
                    intent.putExtra("flag", 2)
                    intent.putExtra("position", position)
                    intent.putExtra("sharer_idx", getSharerIdx())
                    Log.v("TAG", "리쿠릇에서 어플라이로 보내는 공유자번호 = " + getSharerIdx())

                    Log.v("TAG", "리쿠릇에서 어플라이로 보내는 플젝번호 = " + project_idx)

                    Log.v("TAG", "리쿠릇에서 어플라이로 보내는 포지션 = " + position)

                    startActivity(intent)
                }


                //일반적인 경우
                else{
                    var intent = Intent(applicationContext, ApplyDetailActivity::class.java)
                    flag = 1
                    intent.putExtra("flag", 1)
                    intent.putExtra("project_idx", project_idx)
                    intent.putExtra("recruit_idx", recruit_idx)
                    intent.putExtra("task", task)
                    intent.putExtra("position", position)
                    Log.v("TAG", "리쿠릇에서 어플라이로 보내는 포지션 = " + position)

                    startActivity(intent)
                }

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
                    recruit_detail_positiontop_tv.setText(data[0].position)
//                    recruit_detail_date_tv.setText(date)
                    recruit_detail_num_tv.setText(data[0].number)
                    num = data[0].number
                    recruit_detail_task_tv.setText(data[0].task)
                    recruit_detail_numtop_tv.setText(data[0].number)
                    recruit_detail_tasktop_tv.setText(data[0].task)
                    task = data[0].task
//                    recruit_detail_time_tv.setText(date)
                    recruit_detail_activity_tv.setText(data[0].activity)
                    recruit_detail_area_tv.setText(data[0].area)
                    recruit_detail_reward_tv.setText(data[0].reward)
                    recruit_detail_ability_tv.setText(data[0].ability)
                    recruit_detail_career_tv.setText(data[0].career)
                    recruit_detail_preference_tv.setText(data[0].preference)

                    //나중에 버튼 텍스트 변경 예정
                    //recruit_detail_comment_tv.setText(data[0].comment)
                    if(btnResult.equals("개설자")){
                        btnResult = "모집 관리"

                    }
                    else{
                        recruit_detail_applymember_linear.setVisibility(View.GONE)
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

    fun getKakao(project_idx : String, recruit_idx: String)
    {

        val pref = applicationContext.getSharedPreferences("auto", Activity.MODE_PRIVATE)
        val token = pref.getString("token","")

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
                    recruit_detail_positiontop_tv.setText(data[0].position)
//                    recruit_detail_date_tv.setText(date)
                    recruit_detail_num_tv.setText(data[0].number)
                    num = data[0].number
                    recruit_detail_task_tv.setText(data[0].task)
                    recruit_detail_numtop_tv.setText(data[0].number)
                    recruit_detail_tasktop_tv.setText(data[0].task)
                    task = data[0].task
//                    recruit_detail_time_tv.setText(date)
                    recruit_detail_activity_tv.setText(data[0].activity)
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
                    else{
                        recruit_detail_applymember_linear.setVisibility(View.GONE)
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



    fun setRecruitIdx(recruit_idx: String) {

        recruit_idx_value = recruit_idx
    }
    fun getRecruitIdx() : String{
        return recruit_idx_value
    }
    fun setSharerIdx(sharer_idx: String) {

        sharer_idx_value = sharer_idx
    }
    fun getSharerIdx() : String{
        return sharer_idx_value
    }
}
