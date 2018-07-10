package com.jemcom.cowalker.Jemin.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jemcom.cowalker.R;

public class InviteActivity extends AppCompatActivity {
    String startZeroMonth;
    String startZeroDay;
    String finishZeroMonth;
    String finishZeroDay;
    Button nextBtn,rangeBtn;
    Button pmBtn, plannerBtn, designerBtn, developerBtn, etcBtn;

    String start_date, end_date;
    String position;
    int number;

    EditText numberEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);

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

        nextBtn = (Button) findViewById(R.id.invite1_next_btn);
        rangeBtn = (Button) findViewById(R.id.invite_range_btn);
        numberEdit = (EditText) findViewById(R.id.invite_personnel_edit);
        
        pmBtn = (Button) findViewById(R.id.invite_pm_btn);
        plannerBtn = (Button) findViewById(R.id.invite_planner_btn);
        designerBtn = (Button) findViewById(R.id.invite_designer_btn);
        developerBtn = (Button) findViewById(R.id.invite_developer_btn);
        etcBtn = (Button) findViewById(R.id.invite_etc_btn);


        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                number = Integer.parseInt(numberEdit.getText().toString());

                Intent intent = new Intent(InviteActivity.this, Invite2Activity.class);

                SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("position", position);
                editor.putString("start_date", start_date);
                editor.putString("end_date", end_date);
                editor.putInt("number", number);
                editor.commit();


                Log.v("TAG","역할 = " + position + "시작 = " + start_date + "마지막 = " + end_date + "명 = " + number);

                startActivity(intent);
            }
        });

        pmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pmBtn.setSelected(true);
                pmBtn.setTextColor(Color.WHITE);

                plannerBtn.setSelected(false);
                designerBtn.setSelected(false);
                developerBtn.setSelected(false);
                etcBtn.setSelected(false);

                plannerBtn.setTextColor(Color.BLACK);
                designerBtn.setTextColor(Color.BLACK);
                developerBtn.setTextColor(Color.BLACK);
                etcBtn.setTextColor(Color.BLACK);
                position = "PM";
                Log.v("TAG","역할 = " + position);
            }
        });

        plannerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plannerBtn.setSelected(true);
                plannerBtn.setTextColor(Color.WHITE);

                pmBtn.setSelected(false);
                designerBtn.setSelected(false);
                developerBtn.setSelected(false);
                etcBtn.setSelected(false);

                pmBtn.setTextColor(Color.BLACK);
                designerBtn.setTextColor(Color.BLACK);
                developerBtn.setTextColor(Color.BLACK);
                etcBtn.setTextColor(Color.BLACK);
                position = "기획자";
                Log.v("TAG","역할 = " + position);
            }
        });

        designerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                designerBtn.setSelected(true);
                designerBtn.setTextColor(Color.WHITE);

                pmBtn.setSelected(false);
                plannerBtn.setSelected(false);
                developerBtn.setSelected(false);
                etcBtn.setSelected(false);

                pmBtn.setTextColor(Color.BLACK);
                plannerBtn.setTextColor(Color.BLACK);
                developerBtn.setTextColor(Color.BLACK);
                etcBtn.setTextColor(Color.BLACK);

                position = "디자이너";
                Log.v("TAG","역할 = " + position);
            }
        });

        developerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                developerBtn.setSelected(true);
                developerBtn.setTextColor(Color.WHITE);

                pmBtn.setSelected(false);
                plannerBtn.setSelected(false);
                designerBtn.setSelected(false);
                etcBtn.setSelected(false);

                pmBtn.setTextColor(Color.BLACK);
                plannerBtn.setTextColor(Color.BLACK);
                designerBtn.setTextColor(Color.BLACK);
                etcBtn.setTextColor(Color.BLACK);

                position = "개발자";
                Log.v("TAG","역할 = " + position);
            }
        });

        etcBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etcBtn.setSelected(true);
                etcBtn.setTextColor(Color.WHITE);

                pmBtn.setSelected(false);
                plannerBtn.setSelected(false);
                designerBtn.setSelected(false);
                developerBtn.setSelected(false);

                pmBtn.setTextColor(Color.BLACK);
                plannerBtn.setTextColor(Color.BLACK);
                developerBtn.setTextColor(Color.BLACK);
                developerBtn.setTextColor(Color.BLACK);

                position = "기타";
                Log.v("TAG","역할 = " + position);
            }
        });


        rangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyYearMonthPickerDialog pd = new MyYearMonthPickerDialog();
                pd.setOnConfirmDateListener(new MyYearMonthPickerDialog.OnConfirmDateListener() {
                    @Override
                    public void onConfirmDateListener(int startYear, int startMonth, int startDay, int finishYear, int finishMonth, int finishDay) {
                        if(startMonth <10)
                        {
                            startZeroMonth = "0"+startMonth;
                        }
                        else
                        {
                            startZeroMonth=Integer.toString(startMonth);
                        }
                        if(startDay <9)
                        {
                            startDay+=1;
                            startZeroDay = "0"+startDay;
                        }
                        else
                        {
                            startZeroDay=Integer.toString(startDay+1);
                        }
                        if(finishMonth <10)
                        {
                            finishZeroMonth = "0"+finishMonth;
                        }
                        else
                        {
                            finishZeroMonth=Integer.toString(finishMonth);
                        }
                        if(finishDay <9)
                        {
                            finishDay+=1;
                            finishZeroDay = "0"+finishDay;
                        }
                        else
                        {
                            finishZeroDay=Integer.toString(finishDay+1);
                        }
                        InviteActivity.this.start_date = startYear + "-" + startZeroMonth + "-" + startZeroDay;
                        InviteActivity.this.end_date = finishYear + "-" + finishZeroMonth + "-" + finishZeroDay;
                        rangeBtn.setText(InviteActivity.this.start_date + " ~ " + InviteActivity.this.end_date);
                    }
                });
                pd.show(getFragmentManager(), "YearMonthPickerTest");

            }

        });

    }


}
