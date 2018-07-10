package com.jemcom.cowalker.Jemin.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.jemcom.cowalker.R;

public class ProjectChangeActivity extends AppCompatActivity {

    Button nextBtn;
    EditText titleEdit, summaryEdit;
    String projectTitle="";
    String projectSummary="";
    String projectAim="";
    String projectDepartment="";
    String projectArea="";
    String project_idx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_change);

        Intent intent = getIntent();
        project_idx = intent.getStringExtra("project_idx");

        nextBtn = (Button) findViewById(R.id.projectchange_next_btn);
        titleEdit = (EditText) findViewById(R.id.projectchange_title_edit);
        summaryEdit = (EditText) findViewById(R.id.projectchange_summary_edit);



        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                projectTitle = titleEdit.getText().toString();
                projectSummary = summaryEdit.getText().toString();

                Log.v("TAG", "title = " + projectTitle + ", summary = " + projectSummary + ", aim = "
                        + projectAim + ", department = " + projectDepartment + ", Area = " + projectArea);
                Intent intent = new Intent(ProjectChangeActivity.this, ProjectChange2Activity.class);
                intent.putExtra("title",projectTitle);
                intent.putExtra("summary",projectSummary);
                intent.putExtra("aim",projectAim);
                intent.putExtra("department",projectDepartment);
                intent.putExtra("area",projectArea);
                intent.putExtra("project_idx",project_idx);
                startActivity(intent);
            }
        });

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

        final String [] purpose_list = {"창업","공모전 참여","스터디", "사이드 프로젝트","창업","기타"};
        final String [] field_list = {"블록체인","IoT","인공지능","디자인","콘텐츠","기타"};
        final String [] location_list = {"서울","경기도","인천", "강원도","충청도","전라도","경상도","제주도"};


        final Spinner purposeSpinner = (Spinner) findViewById(R.id.projectchange_purpose_spinner);
        final Spinner fieldSpinner = (Spinner) findViewById(R.id.projectchange_field_spinner);
        final Spinner locationSpinner = (Spinner) findViewById(R.id.projectchange_location_spinner);

        // 목적 에 대한 Spinner
        ArrayAdapter adapter = new ArrayAdapter(
                getApplicationContext(), // 현재화면의 제어권자
                R.layout.spin,
                purpose_list);
        adapter.setDropDownViewResource(
                R.layout.spin_dropdown);
        purposeSpinner.setAdapter(adapter);

        // 분야 에 대한 Spinner
        ArrayAdapter adapter2 = new ArrayAdapter(
                getApplicationContext(), // 현재화면의 제어권자
                R.layout.spin,
                field_list);
        adapter2.setDropDownViewResource(
                R.layout.spin_dropdown);
        fieldSpinner.setAdapter(adapter2);

        // 지역 에 대한 Spinner
        ArrayAdapter adapter3 = new ArrayAdapter(
                getApplicationContext(), // 현재화면의 제어권자
                R.layout.spin,
                location_list);
        adapter3.setDropDownViewResource(
                R.layout.spin_dropdown);
        locationSpinner.setAdapter(adapter3);

        purposeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    projectAim = "창업";
                }
                else if(position==1){
                    projectAim = "공모전 참여";
                }
                else if(position==2){
                    projectAim = "스터디";
                }
                else if(position==3){
                    projectAim = "사이드 프로젝트";
                }
                else if(position==4){
                    projectAim = "창업";
                }
                else if(position==5){
                    projectAim = "기타";
                }
            }



            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fieldSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    projectDepartment = "블록체인";
                }
                else if(position==1){
                    projectDepartment = "IoT";
                }
                else if(position==2){
                    projectDepartment = "인공지능";
                }
                else if(position==3){
                    projectDepartment = "디자인";
                }
                else if(position==4){
                    projectDepartment = "콘텐츠";
                }
                else if(position==5){
                    projectDepartment = "기타";
                }
            }



            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        locationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    projectArea = "서울";
                }
                else if(position==1){
                    projectArea = "경기도";
                }
                else if(position==2){
                    projectArea = "인천";
                }
                else if(position==3){
                    projectArea = "강원도";
                }
                else if(position==4){
                    projectArea = "충청도";
                }
                else if(position==5){
                    projectArea = "전라도";
                }
                else if(position==6){
                    projectArea = "경상도";
                }
                else if(position==7){
                    projectArea = "제주도";
                }
            }



            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}
