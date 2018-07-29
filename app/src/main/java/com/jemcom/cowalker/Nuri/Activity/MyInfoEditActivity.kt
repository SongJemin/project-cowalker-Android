package com.jemcom.cowalker.Nuri.Activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import com.bumptech.glide.Glide
import com.jemcom.cowalker.Hyunmin.Activity.ProfileMore2Activity
import com.jemcom.cowalker.Network.ApplicationController
import com.jemcom.cowalker.Network.NetworkService
import com.jemcom.cowalker.Network.Post.Response.PutIntroEditResponse
import com.jemcom.cowalker.Network.Put.Response.PutProjectChangeResponse
import com.jemcom.cowalker.R
import kotlinx.android.synthetic.main.activity_my_info_edit.*
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
import java.util.ArrayList

class MyInfoEditActivity : AppCompatActivity() {

    lateinit var networkService: NetworkService
    private var mLayout: LinearLayout? = null
    private val REQ_CODE_SELECT_IMAGE = 100
    lateinit var data : Uri
    private var btn: ImageView? = null
    internal lateinit var context: Context
    internal var explainValue:String? = null
    private var imgList : ArrayList<MultipartBody.Part?> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_info_edit)
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
        mLayout = findViewById<View>(R.id.my_info_edit_img_layout) as LinearLayout
        context = this

        networkService = ApplicationController.instance.networkSerVice

        my_info_edit_plus_btn.setOnClickListener {

            btn = ImageView(context)

            val plusBtnparams = LinearLayout.LayoutParams(450, 450)
            plusBtnparams.setMargins(60, 0, 0, 0)
            actionBar
            btn!!.layoutParams = plusBtnparams
            btn!!.background = resources.getDrawable(R.drawable.project_default_img)

            mLayout!!.addView(btn)
            changeImage()
        }

        my_info_edit_ok_btn.setOnClickListener {
            explainValue = my_info_edit_explain_et.getText().toString()
            putEdit()
        }
    }

    fun putEdit()
    {
        val pref = applicationContext.getSharedPreferences("auto", Activity.MODE_PRIVATE)
        val token = pref.getString("token","")
        var explain = RequestBody.create(MediaType.parse("text/plain"),
                explainValue)
        var putEditResponse = networkService.putIntroEdit(token,explain,imgList)

        System.out.println("내정보 " + explain)
        putEditResponse.enqueue(object : Callback<PutIntroEditResponse>{
            override fun onFailure(call: Call<PutIntroEditResponse>?, t: Throwable?) {
                Toast.makeText(applicationContext,"서버 연결 실패",Toast.LENGTH_SHORT).show()
                System.out.println("이유가 " + t.toString())
            }

            override fun onResponse(call: Call<PutIntroEditResponse>?, response: Response<PutIntroEditResponse>?) {
                if(response!!.isSuccessful)
                {
                    var intent = Intent(applicationContext,ProfileMore2Activity::class.java)
                    startActivity(intent)
                    finish()
                }
                else Toast.makeText(applicationContext,"실패",Toast.LENGTH_SHORT).show()
            }
        })
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQ_CODE_SELECT_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    //if(ApplicationController.getInstance().is)
                    this.data = data!!.data
                    Log.v("이미지", this.data.toString())

                    val options = BitmapFactory.Options()

                    var input: InputStream? = null // here, you need to get your context.
                    try {
                        input = contentResolver.openInputStream(this.data)
                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    }

                    val bitmap = BitmapFactory.decodeStream(input, null, options) // InputStream 으로부터 Bitmap 을 만들어 준다.
                    val baos = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos)
                    val photoBody = RequestBody.create(MediaType.parse("image/jpg"), baos.toByteArray())
                    val img = File(getRealPathFromURI(context,this.data).toString()) // 가져온 파일의 이름을 알아내려고 사용합니다


                    ///RequestBody photoBody = RequestBody.create(MediaType.parse("image/jpg"), baos.toByteArray());
                    // MultipartBody.Part 실제 파일의 이름을 보내기 위해 사용!!

                    imgList.add(MultipartBody.Part.createFormData("img", img.name, photoBody))

                    //body = MultipartBody.Part.createFormData("image", photo.getName(), profile_pic);

                    Glide.with(this)
                            .load(data.data)
                            .centerCrop()
                            .into(btn)

                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }
    }
}
