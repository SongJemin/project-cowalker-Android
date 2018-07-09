package com.jemcom.cowalker.Hyunmin.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.jemcom.cowalker.R

class ProfileEditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage_profile_edit)

        val spinner1 = findViewById<View>(R.id.role_spin) as Spinner
        val spinner2 = findViewById<View>(R.id.object_spin) as Spinner
        val spinner3 = findViewById<View>(R.id.field_spin) as Spinner
        val spinner4 = findViewById<View>(R.id.location_spin) as Spinner

        val adapter1 = ArrayAdapter.createFromResource(this,
                R.array.profile_edit_role_spin, android.R.layout.simple_spinner_item)
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val adapter2 = ArrayAdapter.createFromResource(this,
                R.array.profile_edit_object_spin, android.R.layout.simple_spinner_item)
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val adapter3 = ArrayAdapter.createFromResource(this,
                R.array.profile_edit_field_spin, android.R.layout.simple_spinner_item)
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val adapter4 = ArrayAdapter.createFromResource(this,
                R.array.profile_edit_location_spin, android.R.layout.simple_spinner_item)
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner1.setAdapter(adapter1)
        spinner2.setAdapter(adapter2)
        spinner3.setAdapter(adapter3)
        spinner4.setAdapter(adapter4)
    }
}