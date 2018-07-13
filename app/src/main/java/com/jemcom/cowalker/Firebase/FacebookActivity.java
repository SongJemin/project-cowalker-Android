package com.jemcom.cowalker.Firebase;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.jemcom.cowalker.Network.ApplicationController;
import com.jemcom.cowalker.Network.NetworkService;
import com.jemcom.cowalker.Network.Post.PostSharedSns;
import com.jemcom.cowalker.Network.Post.Response.PostSharedSnsResponse;
import com.jemcom.cowalker.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;

public class FacebookActivity extends AppCompatActivity {

    NetworkService networkService;

    @BindView(R.id.fb_share_button)
    Button facebookbtn;

    @OnClick(R.id.fb_share_button)
    void fackbookShare(){
        shareFacebook();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook);


        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        ButterKnife.bind(this);
        networkService = ApplicationController.instance.networkSerVice;

    }

    public void shareFacebook() {


        //서버랑 통신
        String project_idx="5b485b6568a9df1f8e89dfb5";
        String recruit_idx= "5b4860f286e69824a632180b";
        String token="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjozNCwiaWF0IjoxNTMxNDE1MzYyLCJleHAiOjE1MzQwMDczNjJ9.41CKoAi1cODVpaPJ4sFv7iBYB7vxhYV5D0jJaXZIovo";

        PostSharedSns postSharedSns=new PostSharedSns(project_idx,recruit_idx);

        Call<PostSharedSnsResponse> postSharedSnsResponse=networkService.postSharedSns(token,postSharedSns);

        postSharedSnsResponse.enqueue(new retrofit2.Callback<PostSharedSnsResponse>() {
            @Override
            public void onResponse(@NonNull Call<PostSharedSnsResponse> call, @NonNull Response<PostSharedSnsResponse> response) {
                if(response.isSuccessful()) {

                    Toast.makeText(FacebookActivity.this, "hahaa", Toast.LENGTH_SHORT).show();



                }else {
                    Toast.makeText(FacebookActivity.this, "오류", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PostSharedSnsResponse> call, Throwable t) {
                Toast.makeText(FacebookActivity.this, "network Fail", Toast.LENGTH_SHORT).show();
            }
        });

        // recruit_idx가 없으면 project프로젝트 조회 페이지로 링크로 넘어가야 함.
//        Toast.makeText(this, "recruit_idx.length:"+recruit_idx.length(), Toast.LENGTH_SHORT).show();
        if(recruit_idx.length()==0){
            Toast.makeText(this, "recruit_idx is null", Toast.LENGTH_SHORT).show();
            // 링크 전달
            ShareLinkContent content = new ShareLinkContent.Builder()
                    .setContentUrl(Uri.parse("http://cowalker.cf:8080/boards/"+project_idx))
                    .build();

            ShareDialog shareDialog = new ShareDialog(this);
            shareDialog.show(content, ShareDialog.Mode.FEED);
            //
        }else{
//            Toast.makeText(this, "recruit_idx is null", Toast.LENGTH_SHORT).show();
            ShareLinkContent content = new ShareLinkContent.Builder()
                    .setContentUrl(Uri.parse("http://cowalker.cf:8080/boards/"+project_idx+"/subrecruit/"+recruit_idx))
                    .build();



            ShareDialog shareDialog = new ShareDialog(this);
            shareDialog.show(content, ShareDialog.Mode.FEED);
        }






    }



}
