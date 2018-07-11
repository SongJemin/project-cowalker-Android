package com.jemcom.cowalker.Network

import com.jemcom.cowalker.Network.Get.Response.*

import com.jemcom.cowalker.Network.Delete.DeleteProjectResponse
import com.jemcom.cowalker.Network.Delete.DeleteRecruitResponse
import com.jemcom.cowalker.Network.Get.Response.GetSearchResponse
import com.jemcom.cowalker.Network.Post.*
import com.jemcom.cowalker.Network.Post.Response.*
import com.jemcom.cowalker.Network.Put.Response.PutMyPageResponse
import com.jemcom.cowalker.Network.Put.Response.PutProjectChangeResponse
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

    @GET("/api/intro/{user_idx}") // GET할 때, 경로 앞에 api를 붙이고 시작해야한다. 그 뒤는 그대로 적기!
    fun getIntroOther( // 맞춘 형식으로 get~~~ 함수 이름 생성해주기
            @Path("user_idx") user_idx : String // @Path는 외우기, { } 안에 있는 거 value값으로 하고! 변수 이름과 타입 적기. {}있으면 보통 Path.
    ) : Call<GetIntroOtherResponse> // 반환 해주기, Response에다가 만들기!

    @GET("/api/intro")
    fun getIntroMine(
            @Header("Authorization") authorization : String
    ) : Call<GetIntroOtherResponse>

    @Multipart
    @PUT("/api/intro")
    fun putIntroEdit(
            @Header("Authorization") authorization: String,
            @Part("contents") contents : String,
            @Part img : ArrayList<MultipartBody.Part?>
    ) : Call<PutIntroEditResponse>

//    @DELETE("api/intro/{intro_idx}")
//    fun deleteIntro(
//            @Path("intro_idx") intro_idx : String
//    ) : Call<DeleteIntroResponse>




    @GET("/api/home")
    fun getProject(
            @Header("authorization") authorization : String
    ) : Call<GetProjectResponse>

    @Multipart
    @PUT("/api/project/{project_idx}")
    fun putChangeProject(
            @Path("project_idx") project_idx : String,
            @Part("title") title : RequestBody,
            @Part("summary") summary : RequestBody,
            @Part("area") area: RequestBody,
            @Part("department") department : RequestBody,
            @Part("aim") aim : RequestBody,
            @Part("explain") explain : RequestBody,
            @Part img : ArrayList<MultipartBody.Part?>
    ) : Call<PutProjectChangeResponse>

    @Multipart
    @PUT("/api/mypage")
    fun putMypage(
            @Header("authorization") authorization : String,
            @Part profile_img : MultipartBody.Part?,
            @Part background_img : MultipartBody.Part?,
//            @Part("name") name:String,
            @Part("position") position:String,
            @Part("introduce") introduce:String,
//            @Part("introduce_detail") introduce_detail:String,
            @Part("portfolio_url") portfolio_url : String,
            @Part("aim") aim : String,
            @Part("department") department : String,
            @Part("area") area : String
    ) : Call<PutMyPageResponse>

    @GET("/api/project/{project_idx}")
    fun getDetailProject(
            @Header("authorization") authorization: String,
            @Path("project_idx") project_idx : String
    ) : Call<GetProjectDetailResponse>

    @DELETE("/api/project/{project_idx}")
    fun deleteProject(
            @Path("project_idx") project_idx : String
    ) : Call<DeleteProjectResponse>

    @GET("/api/project/team/{project_idx}")
    fun getMemberProject(
            @Path("project_idx") project_idx : String
    ) : Call<GetProjectMemberResponse>


    @GET("/api/mypage")
    fun getMypage(
            @Header("authorization") authorization: String
    ) : Call<GetMypageResponse>

    @GET("/api/mypage/{user_idx}")
    fun getMypageOther(
            @Header("authorization") authorization: String,
            @Path("user_idx") user_id: String
    ) : Call<GetMypageOtherResponse>

    @GET("/api/project/{project_idx}/recruit/{recruit_idx}")
    fun getRecruitDetail(
            @Header("authorization") authorization: String,
            @Path("project_idx") project_idx: String,
            @Path("recruit_idx") recruit_idx: String
    ) : Call<GetRecruitDetailResponse>


    @DELETE("/api/project/{project_idx}/recruit/{recruit_idx}")
    fun deleteRecruit(
            @Path("project_idx") project_idx : String,
            @Path("recruit_idx") recruit_idx : String
    ) : Call<DeleteRecruitResponse>

    @GET("/api/project/{project_idx}/recruit")
    fun getRecruitList(
            @Path("project_idx") project_idx: String
    ) : Call<GetRecruitListResponse>

    @GET("/api/alarm")
    fun getAlarm(
            @Header("authorization") authorization: String
    ) : Call<GetAlarmResponse>

    @GET("/api/search")
    fun getSearch(
            @Query("aim") aim : String,
            @Query("area") area : String,
            @Query("position") position : String,
            @Query("department") department : String,
            @Query("keyword") keyword : String
    ) : Call<GetSearchResponse>

    @GET("/api/user/project")
    fun getProjectMine(
            @Header("authorization") authorization: String
    ) : Call<GetProjectMineResponse>

    @GET("/api/apply/enter_project")
    fun getProjectMineParticipate(
            @Header("authorization") authorization: String
    ) : Call<GetProjectMineParticipateResponse>

    @GET("/api/apply/apply_project")
    fun getProjectMineApply(
            @Header("authorization") authorization: String
    ) : Call<GetProjectMineApplyResponse>

    @GET("/api/user/project/{user_idx}")
    fun getProjectOther(
            @Header("authorization") authorization: String,
            @Path("user_idx") user_idx : String
    ) : Call<GetProjectMineResponse>

    @GET("/api/apply/enter_project/{user_idx}")
    fun getProjectOtherParticipate(
            @Header("authorization") authorization: String,
            @Path("user_idx") user_idx : String
    ) : Call<GetProjectMineParticipateResponse>
}