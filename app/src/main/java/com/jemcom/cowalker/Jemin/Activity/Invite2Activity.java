package com.jemcom.cowalker.Jemin.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.jemcom.cowalker.R;

public class Invite2Activity extends AppCompatActivity {
    String url = "https://cdn.xl.thumbs.canstockphoto.com/computer-generated-3d-image-cooperation-stock-illustrations_csp2074347.jpg";
    private LinearLayout mLayout;
    private EditText mEditText;
    private Button mButton;
    private EditText edit;
    private Button button;
    private Button nextBtn;
    int count;

    // 두 번째 화면 값
    String task, activity, area, reward;
    EditText taskEdit, activityEdit, areaEdit, rewardEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite2);


        taskEdit = (EditText) findViewById(R.id.invite2_task_edit);
        activityEdit = (EditText) findViewById(R.id.invite2_activity_edit);
        areaEdit = (EditText) findViewById(R.id.invite2_area_edit);
        rewardEdit = (EditText) findViewById(R.id.invite2_reward_edit);




        View view = getWindow().getDecorView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (view != null) {
                // 23 버전 이상일 때 상태바 하얀 색상에 회색 아이콘 색상을 설정
                view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
            }
        } else if (Build.VERSION.SDK_INT >= 21) {
            // 21 버전 이상일 때
            getWindow().setStatusBarColor(Color.BLACK);
        }

        nextBtn = (Button) findViewById(R.id.invite2_next_btn);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                task = taskEdit.getText().toString();
                activity = activityEdit.getText().toString();
                area = areaEdit.getText().toString();
                reward = rewardEdit.getText().toString();

                SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("task", task);
                editor.putString("activity", activity);
                editor.putString("area", area);
                editor.putString("reward", reward);
                editor.commit();

                Intent intent = new Intent(Invite2Activity.this, Invite3Activity.class);

                startActivity(intent);
            }
        });


    }
}
