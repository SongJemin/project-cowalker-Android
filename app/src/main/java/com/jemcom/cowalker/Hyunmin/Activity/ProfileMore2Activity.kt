package com.jemcom.cowalker.Hyunmin.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.jemcom.cowalker.Network.ApplicationController
import com.jemcom.cowalker.Network.Get.Response.GetIntroMineResponse
import com.jemcom.cowalker.Network.NetworkService
import com.jemcom.cowalker.R
import kotlinx.android.synthetic.main.activity_profile_more2.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileMore2Activity : AppCompatActivity() {

    lateinit var networkService: NetworkService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_more2)
        networkService = ApplicationController.instance.networkSerVice
        get()
    }
    fun get()
    {
        var getIntroMineResponse = networkService.getIntroMine("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoyLCJpYXQiOjE1MzEwOTk2NDcsImV4cCI6MTUzMzY5MTY0N30.oElkCl0nyKBIUBKP9ZsZjCETymmHiVCp7h90cX2syM0")

        getIntroMineResponse.enqueue(object : Callback<GetIntroMineResponse> {
            override fun onFailure(call: Call<GetIntroMineResponse>?, t: Throwable?) {
                Toast.makeText(applicationContext, "서버 연결 실패", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<GetIntroMineResponse>?, response: Response<GetIntroMineResponse>?) {
                if(response!!.isSuccessful){
                    var data = response.body().result
//                    profile_more_contents2_tv.setText(data[0], intro_contents)
                }
            }

//            override fun equals(other: Any?): Boolean {
//                return super.equals(other)
//            }
//
//            override fun hashCode(): Int {
//                return super.hashCode()
//            }
//
//            override fun toString(): String {
//                return super.toString()
//            }
        })
    }
}

