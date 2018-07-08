package com.jemcom.cowalker.Jemin.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jemcom.cowalker.R;

public class Invite3Activity extends AppCompatActivity {

    Button nextBtn,rangeBtn;
    EditText abilityEdit, careerEdit, preferenceEdit, commentEdit;

    // 첫 번째 화면 값 받아오기
    String start_date, end_date;
    String position;
    int number;

    // 두 번째 화면 값
    String task, activity, area, reward;

    // 세 번째 화면 값
    String ability, career, preference, comment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite3);

        abilityEdit = (EditText) findViewById(R.id.invite3_ability_edit);
        careerEdit = (EditText) findViewById(R.id.invite3_career_edit);
        preferenceEdit = (EditText) findViewById(R.id.invite3_preference_edit);
        commentEdit = (EditText) findViewById(R.id.invite3_comment_edit);


        nextBtn = (Button) findViewById(R.id.invite3_next_btn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ability = abilityEdit.getText().toString();
                career = careerEdit.getText().toString();
                preference = preferenceEdit.getText().toString();
                comment = commentEdit.getText().toString();

                SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("ability", ability);
                editor.putString("career", career);
                editor.putString("preference", preference);
                editor.putString("comment", comment);
                editor.commit();

                Intent intent = new Intent(Invite3Activity.this, Invite4Activity.class);
                startActivity(intent);

            }
        });
    }
}