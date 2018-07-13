package com.jemcom.cowalker.Hyunmin

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.jemcom.cowalker.Hyunmin.Activity.MypageProfileEditActivity
import com.jemcom.cowalker.Hyunmin.Activity.MypageProjectlistActivity
import com.jemcom.cowalker.Hyunmin.Activity.ProfileEditActivity
import com.jemcom.cowalker.Hyunmin.Activity.ProfileMore2Activity
import com.jemcom.cowalker.Jemin.Activity.MainActivity
import com.jemcom.cowalker.Network.ApplicationController
import com.jemcom.cowalker.Network.Get.Response.GetMypageResponse
import com.jemcom.cowalker.Network.NetworkService
import com.jemcom.cowalker.Network.Put.Response.PutMypagePhotoResponse
import com.jemcom.cowalker.Nuri.Activity.LoginActivity
import com.jemcom.cowalker.Nuri.Activity.OtherpageProjectlistActivity
import com.jemcom.cowalker.R
import kotlinx.android.synthetic.main.fragment_mypage.*
import kotlinx.android.synthetic.main.fragment_mypage.view.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStream

class MypageTab : Fragment(),View.OnClickListener {

    private val REQ_CODE_SELECT_IMAGE = 100
    lateinit var data : Uri
    var status : String? = null
    var img = ""
    private var image : MultipartBody.Part? = null

    override fun onClick(v: View?) {
        when(v)
        {
            mypage_background_img->{
                status = "background"
                changeImage()
                put(v!!)
            }
            mypage_profile_change_btn->{
                status = "profile"
                changeImage()
                put(v!!)
            }
            logout_btn -> {
                val pref = v!!.context.getSharedPreferences("auto", Activity.MODE_PRIVATE)
                val editor = pref.edit()
                editor.clear()
                editor.commit()
                val intent = Intent(activity, LoginActivity::class.java)
                startActivity(intent)
            }
            mypage_project_btn ->{
                val intent = Intent(activity, MypageProjectlistActivity::class.java)
                startActivity(intent)
            }

            mypage_edit_btn -> {
                val intent = Intent(activity, MypageProfileEditActivity::class.java)
                startActivity(intent)
            }

            mypage_intro_btn -> {
                val intent = Intent(activity, ProfileMore2Activity::class.java)
                startActivity(intent)
            }
        }
    }

    lateinit var networkService : NetworkService
    lateinit var requestManager : RequestManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_mypage, container, false)

        networkService = ApplicationController.instance.networkSerVice
        requestManager = Glide.with(this)
        view.logout_btn.setOnClickListener(this)
        view.mypage_project_btn.setOnClickListener(this)
        view.mypage_edit_btn.setOnClickListener(this)
        view.mypage_intro_btn.setOnClickListener(this)
        view.mypage_background_img.setOnClickListener(this)
        view.mypage_profile_change_btn.setOnClickListener(this)
        get(view)

        return view
    }

    fun get(v : View)
    {
        val pref = v.context.getSharedPreferences("auto", Activity.MODE_PRIVATE)
        val token = pref.getString("token","")
        var getMypageResponse = networkService.getMypage(token)

        getMypageResponse.enqueue(object : Callback<GetMypageResponse>{
            override fun onFailure(call: Call<GetMypageResponse>?, t: Throwable?) {
                Toast.makeText(v.context,"서버 연결 실패",Toast.LENGTH_SHORT).show()
                System.out.println("이유가 뭘까?" + t.toString())
            }

            override fun onResponse(call: Call<GetMypageResponse>?, response: Response<GetMypageResponse>?) {
                if(response!!.isSuccessful)
                {
                    var data = response.body().data
                    v.mypage_name_tv.setText(data[0].name)
                    v.mypage_role_tv.setText(data[0].position)
                    v.mypage_summary_tv.setText(data[0].introduce)
                    v.mypage_aim_tv.setText(data[0].aim)
                    v.mypage_department_tv.setText(data[0].department)
                    v.mypage_area_tv.setText(data[0].area)
                    v.mypage_link_tv.setText(data[0].portfolio_url)
                    requestManager.load(data[0].profile_url).into(v.mypage_profile_img)
                    requestManager.load(data[0].background_url).into(v.mypage_background_img)
                }
                else Toast.makeText(v.context,"실패",Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQ_CODE_SELECT_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    //if(ApplicationController.getInstance().is)
                    this.data = data!!.data

                    val options = BitmapFactory.Options()

                    var input: InputStream? = null // here, you need to get your context.
                    try {
                        input = context!!.contentResolver.openInputStream(this.data)
                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    }

                    val bitmap = BitmapFactory.decodeStream(input, null, options) // InputStream 으로부터 Bitmap 을 만들어 준다.
                    val baos = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos)
                    val photoBody = RequestBody.create(MediaType.parse("image/jpg"), baos.toByteArray())
                    val photo = File(this.data.toString()) // 가져온 파일의 이름을 알아내려고 사용합니다

                    ///RequestBody photoBody = RequestBody.create(MediaType.parse("image/jpg"), baos.toByteArray());
                    // MultipartBody.Part 실제 파일의 이름을 보내기 위해 사용!!

                    if(status.equals("background")) {
                        image = MultipartBody.Part.createFormData("background_img", photo.name, photoBody)

                        Glide.with(this)
                                .load(data.data)
                                .centerCrop()
                                .into(mypage_background_img)
                    }
                    else
                    {
                        image = MultipartBody.Part.createFormData("profile_img", photo.name, photoBody)

                        Glide.with(this)
                                .load(data.data)
                                .centerCrop()
                                .into(mypage_profile_img)
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }

    }

    fun changeImage(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = android.provider.MediaStore.Images.Media.CONTENT_TYPE
        intent.data = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        startActivityForResult(intent, REQ_CODE_SELECT_IMAGE)
    }

    fun getRealPathFromURI(context: Context, contentUri: Uri): String {
        var cursor: Cursor? = null
        try {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            cursor = context.contentResolver.query(contentUri, proj, null, null, null)
            val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            return cursor.getString(column_index)
        } finally {
            cursor?.close()
        }
    }

    fun put(v : View)
    {
        System.out.println("왜?!")
        val pref = v.context.getSharedPreferences("auto", Activity.MODE_PRIVATE)
        val token = pref.getString("token","")
        if(status.equals("background")) {
            var putPhotoResponse = networkService.putPhoto(token,null,image)
            putPhotoResponse.enqueue(object : Callback<PutMypagePhotoResponse>{
                override fun onFailure(call: Call<PutMypagePhotoResponse>?, t: Throwable?) {
                    Toast.makeText(v.context,"서버 연결 실패",Toast.LENGTH_SHORT).show()
                    System.out.println("왜?"+ t.toString())
                }

                override fun onResponse(call: Call<PutMypagePhotoResponse>?, response: Response<PutMypagePhotoResponse>?) {
                    if(response!!.isSuccessful)
                    {
                        System.out.println("왜?")
                        val intent = Intent(v.context, MainActivity::class.java)
                        intent.putExtra("status","mypage")
                        startActivity(intent)
                    }
                    else Toast.makeText(v.context,"실패",Toast.LENGTH_SHORT).show()
                }
            })
        }
        else{
//        {
//            var putPhotoResponse = networkService.putBackgroundPhoto(token,null)
//            putPhotoResponse.enqueue(object : Callback<PutMypagePhotoResponse>{
//                override fun onFailure(call: Call<PutMypagePhotoResponse>?, t: Throwable?) {
//                    Toast.makeText(v.context,"서버 연결 실패",Toast.LENGTH_SHORT).show()
//                }
//
//                override fun onResponse(call: Call<PutMypagePhotoResponse>?, response: Response<PutMypagePhotoResponse>?) {
//                    if(response!!.isSuccessful)
//                    {
//                        val intent = Intent(v.context,MainActivity::class.java)
//                        intent.putExtra("status","mypage")
//                        startActivity(intent)
//                    }
//                    else Toast.makeText(v.context,"실패",Toast.LENGTH_SHORT).show()
//                }
//            })
        }

    }
}