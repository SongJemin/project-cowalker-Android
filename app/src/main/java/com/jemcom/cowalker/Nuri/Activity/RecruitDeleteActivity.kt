package com.jemcom.cowalker.Nuri.Activity

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.jemcom.cowalker.Network.ApplicationController
import com.jemcom.cowalker.Network.Get.Response.GetRecruitListResponse
import com.jemcom.cowalker.Network.NetworkService
import com.jemcom.cowalker.Nuri.Adapter.RecruitListAdapter
import com.jemcom.cowalker.Nuri.Item.RecruitListItem
import com.jemcom.cowalker.R
import kotlinx.android.synthetic.main.activity_recruit_delete.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.content.DialogInterface
import android.graphics.Color
import android.os.Build
import android.support.v7.app.AlertDialog
import com.jemcom.cowalker.Jemin.Activity.ProjectDetailActivity
import com.jemcom.cowalker.Network.Delete.DeleteRecruitResponse
import com.jemcom.cowalker.Network.Get.GetRecruitList


class RecruitDeleteActivity : AppCompatActivity(),View.OnClickListener {

    lateinit var networkService: NetworkService
    lateinit var recruitListItems: ArrayList<RecruitListItem>
    lateinit var recruitListAdapter : RecruitListAdapter
    var check = 0
    var project_idx : String = ""
    var recruit_idx : String = ""
    var position = 0
    lateinit var data : ArrayList<GetRecruitList>

    companion object {
        lateinit var activity: RecruitDeleteActivity
        //일종의 스태틱
    }

    override fun onClick(v: View?) {
        when(v)
        {
            delete_cancel_tv ->{
                var intent = Intent(applicationContext,LoginActivity::class.java)
                startActivity(intent)
            }
            delete_ok_tv -> {
                AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setMessage("모집을 삭제하시겠습니까?")
                        .setPositiveButton("삭제", DialogInterface.OnClickListener { dialog, which ->
                            delete()
                        })
                        .setNegativeButton("취소", null)
                        .show()
//                var intent = Intent(applicationContext,ProjectDetailActivity::class.java)
//                startActivity(intent)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recruit_delete)
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
        val getRecruitintent = intent
        project_idx = getRecruitintent.getStringExtra("project_idx")
        recruitListItems = ArrayList()

        activity = this

        delete_cancel_tv.setOnClickListener(this)
        delete_ok_tv.setOnClickListener(this)


        get()
    }

    fun get()
    {

        var getRecruitListResponse = networkService.getRecruitList(project_idx)


        getRecruitListResponse.enqueue(object : Callback<GetRecruitListResponse>{
            override fun onFailure(call: Call<GetRecruitListResponse>?, t: Throwable?) {
                Toast.makeText(applicationContext,"서버 연결 실패", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<GetRecruitListResponse>?, response: Response<GetRecruitListResponse>?) {
                Log.v("TAG","모집 리스트 통신")
                if(response!!.isSuccessful)
                {
                    Log.v("TAG","모집 리스트 받아오기")
                    data = response.body().result
                    Log.v("TAG","모집 리스트 값 = "+data.toString())

                    for(i in 0..data.size-1)
                    {

                        recruitListItems.add(RecruitListItem(data[i].position,data[i].number,data[i].task,data[i].dday))
                    }
                    recruitListAdapter = RecruitListAdapter(recruitListItems)
                    recruitList_rv.layoutManager = LinearLayoutManager(applicationContext)
                    recruitList_rv.adapter = recruitListAdapter
                }
                else Toast.makeText(applicationContext,"실패",Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun delete()
    {
        val pref = applicationContext.getSharedPreferences("auto", Activity.MODE_PRIVATE)
        val token = pref.getString("token","")
        System.out.println("값은" + project_idx + "힝" + data[position].recruit_idx)
        var deleteRecruitResponse = networkService.deleteRecruit(token,project_idx,data[position].recruit_idx)

        deleteRecruitResponse.enqueue(object : Callback<DeleteRecruitResponse>{
            override fun onFailure(call: Call<DeleteRecruitResponse>?, t: Throwable?) {
                Toast.makeText(applicationContext,"서버 연결 실패", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<DeleteRecruitResponse>?, response: Response<DeleteRecruitResponse>?) {
                if(response!!.isSuccessful)
                {
                    var intent = Intent(applicationContext,ProjectDetailActivity::class.java)
                    intent.putExtra("project_idx",project_idx)
                    startActivity(intent)
                }
                else Toast.makeText(applicationContext,"실패",Toast.LENGTH_SHORT).show()
            }
        })
    }
}
