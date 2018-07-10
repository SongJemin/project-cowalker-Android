package com.jemcom.cowalker.Network

import com.jemcom.cowalker.Network.Get.Response.*
import com.jemcom.cowalker.Network.Post.*
import com.jemcom.cowalker.Network.Post.Response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface NetworkService {

    @POST("/api/signup")
    fun postSignup(
            @Body signup: PostSignup
    ): Call<PostSignupResponse>

    @GET("/api/signup/check")
    fun getSignupCheck(
            @Query("email") email : String
    ) : Call<GetSignupResponse>

    @POST("/api/signin")
    fun postLogin(
            @Body login : PostLogin
    ) : Call<PostLoginResponse>

    @Multipart
    @POST("/api/project")
    fun uploadProject(
            @Header("authorization") authorization : String,
            @Part("title") title : RequestBody,
            @Part("summary") summary : RequestBody,
            @Part("area") area: RequestBody,
            @Part("department") department : RequestBody,
            @Part("aim") aim : RequestBody,
            @Part("explain") explain : RequestBody,
            @Part img : ArrayList<MultipartBody.Part?>
    ) : Call<PostProjectResponse>


    @POST("/api/project/recruit")
    fun postInvite(
            @Header("authorization") authorization: String,
            @Body inviteUp : PostInvite
    ) : Call<PostInviteResponse>

    @POST("/api/apply")
    fun postJoin(
            @Header("authorization") authorization : String,
            @Body join : PostJoin
    ) : Call<PostJoinResponse>

    @GET("/api/apply/{apply_idx}")
    fun getApplyMine(
            @Header("authorization") authorization : String,
            @Path("apply_idx") apply_idx : String
    ) : Call<GetApplyResponse>

    @POST("/api/message/{user_id}")
    fun postMessage(
            @Header("authorization") authorization : String,
            @Body contents : PostMessageSend,
            @Path("user_id") user_id : String
    ) : Call<PostMessageResponse>

    @GET("/api/message")
    fun getMessage(
            @Header("authorization") authorization: String
    ) : Call<GetMessageResponse>

    @GET("/api/message/{partner_id}")
    fun getMessageLook(
            @Header("authorization") authorization: String,
            @Path("partner_id") partner_id : String
    ) : Call<GetMessageLookResponse>

    @GET("api/intro/{user_idx}") // GET할 때, 경로 앞에 api를 붙이고 시작해야한다. 그 뒤는 그대로 적기!
    fun getIntroOther( // 맞춘 형식으로 get~~~ 함수 이름 생성해주기
            @Path("user_idx") user_idx : String // @Path는 외우기, { } 안에 있는 거 value값으로 하고! 변수 이름과 타입 적기. {}있으면 보통 Path.
    ) : Call<GetIntroOtherResponse> // 반환 해주기, Response에다가 만들기!

    @GET("api/intro")
    fun getIntroMine(
            @Header("Authorization") authorization : String
    ) : Call<GetIntroMineResponse>

    @PUT("api/intro")
    fun putIntroEdit(
            @Header("Authorization") authorization: String,
            @Body introEdit : PutEdit
    ) : Call<PutIntroEditResponse>

//    @DELETE("api/intro/{intro_idx}")
//    fun deleteIntro(
//            @Path("intro_idx") intro_idx : String
//    ) : Call<DeleteIntroResponse>

}