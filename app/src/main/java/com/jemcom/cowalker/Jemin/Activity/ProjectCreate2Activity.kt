package com.jemcom.cowalker.Jemin.Activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import com.bumptech.glide.Glide
import com.jemcom.cowalker.Network.ApplicationController
import com.jemcom.cowalker.Network.NetworkService
import com.jemcom.cowalker.Network.Post.Response.PostProjectResponse
import com.jemcom.cowalker.R
import kotlinx.android.synthetic.main.activity_project_create2.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStream
import java.util.ArrayList


class ProjectCreate2Activity : AppCompatActivity() {

    lateinit var networkService: NetworkService
    internal lateinit var CropIntent: Intent
    private var mLayout: LinearLayout? = null
    private var imgButton: ImageView? = null
    private val REQ_CODE_SELECT_IMAGE = 100
    lateinit var data : Uri
    private var btn: ImageView? = null
    internal lateinit var context: Context
    internal var count = 0
    internal var uri: Uri? = null

    internal var titleValue: String? = null
    internal var summaryValue:String? = null
    internal var aimValue:String? = null
    internal var departmentValue:String? = null
    internal var areaValue:String? = null
    internal var explainValue:String? = null


    private var imgList : ArrayList<MultipartBody.Part?> = ArrayList()
    //private var img : ArrayList<MultipartBody.Part>
    //internal var btn: Array<ImageView>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_create2)

        val intent = intent
        titleValue = intent.getStringExtra("title")
        summaryValue = intent.getStringExtra("summary")
        aimValue = intent.getStringExtra("aim")
        departmentValue = intent.getStringExtra("department")
        areaValue = intent.getStringExtra("area")

        networkService = ApplicationController.instance.networkSerVice



        mLayout = findViewById<View>(R.id.create_project2_layout) as LinearLayout
        context = this

        create2_plus_btn.setOnClickListener {

            btn = ImageView(context)

            val plusBtnparams = LinearLayout.LayoutParams(450, 450)
            plusBtnparams.setMargins(60, 0, 0, 0)
            actionBar
            btn!!.layoutParams = plusBtnparams
            btn!!.background = resources.getDrawable(R.drawable.project_default_img)
            //btn.setText("버튼"+String.valueOf(count));

            mLayout!!.addView(btn)
            changeImage()
        }



        create2_confirm_btn.setOnClickListener {
            explainValue = create2_explain_edit.getText().toString()
            postBoard()
            //val intent = Intent(this@ProjectCreate2Activity, InviteActivity::class.java)
            //startActivity(intent)
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

    fun changeImage(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = android.provider.MediaStore.Images.Media.CONTENT_TYPE
        intent.data = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        startActivityForResult(intent, REQ_CODE_SELECT_IMAGE)

    }

    fun postBoard() {
        val pref = getSharedPreferences("auto", Activity.MODE_PRIVATE)
        val token = pref.getString("token","")
        Log.v("TAG","토큰 확인 = " + token);
        val title = RequestBody.create(MediaType.parse("text.plain"), titleValue)
        val summary = RequestBody.create(MediaType.parse("text.plain"), summaryValue)
        val area = RequestBody.create(MediaType.parse("text.plain"), areaValue)
        val department = RequestBody.create(MediaType.parse("text.plain"), departmentValue)
        val aim = RequestBody.create(MediaType.parse("text.plain"), aimValue)
        val explain = RequestBody.create(MediaType.parse("text.plain"), explainValue)
        val postProjectResponse = networkService.uploadProject(token, title, summary, area, department, aim, explain, imgList)

        Log.v("TAG", "프로젝트 생성 전송 : 토큰 = " + token + ", 제목 = " + titleValue + ", 요약 소개 = " + summaryValue
                + ", 지역 = " + areaValue + ", 분야 = " + departmentValue + ", 목적 = " + aimValue + ", 설명 = " + explainValue + ", img = " + imgList)

        postProjectResponse.enqueue(object : retrofit2.Callback<PostProjectResponse>{

            override fun onResponse(call: Call<PostProjectResponse>, response: Response<PostProjectResponse>) {
                Log.v("TAG", "통신 성공")
                if(response.isSuccessful){
                    var message = response!!.body()
                    Log.v("TAG","idx = " + message.project_idx)
                    val pref = getSharedPreferences("pref", Context.MODE_PRIVATE)
                    val editor = pref.edit()
                    editor.putString("project_idx", message.project_idx)

                    editor.commit()

                    Log.v("TAG", "값 전달 성공")
                    var intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                }
            }

            override fun onFailure(call: Call<PostProjectResponse>, t: Throwable?) {
                Toast.makeText(applicationContext,"서버 연결 실패",Toast.LENGTH_SHORT).show()
            }

        })
    }



}