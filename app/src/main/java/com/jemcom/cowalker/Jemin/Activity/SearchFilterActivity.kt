package com.jemcom.cowalker.Jemin.Activity

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.jemcom.cowalker.R
import kotlinx.android.synthetic.main.activity_search_filter.*

class SearchFilterActivity : AppCompatActivity()  {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_filter)


        //목적
        filter_purposeall_btn.setOnClickListener {
            if (filter_purposeall_btn.isChecked()) {
                filter_purposeall_btn.setBackgroundDrawable(
                        getResources().getDrawable(R.drawable.round_button_selected)

                );filter_purposeall_btn.setTextColor(Color.WHITE);
            }
            else {
                filter_purposeall_btn.setBackgroundDrawable(
                        getResources().getDrawable(R.drawable.round_button)
                );filter_purposeall_btn.setTextColor(Color.BLACK);
            }
            }
            filter_startup_btn.setOnClickListener {
                if (filter_startup_btn.isChecked()) {
                    filter_startup_btn.setBackgroundDrawable(
                            getResources().getDrawable(R.drawable.round_button_selected)

                    );filter_startup_btn.setTextColor(Color.WHITE);
                }
                else {
                    filter_startup_btn.setBackgroundDrawable(
                            getResources().getDrawable(R.drawable.round_button)
                    );filter_startup_btn.setTextColor(Color.BLACK);
                }

            }
            filter_contest_btn.setOnClickListener {if (filter_contest_btn.isChecked()) {
                filter_contest_btn.setBackgroundDrawable(
                        getResources().getDrawable(R.drawable.round_button_selected)

                );filter_contest_btn.setTextColor(Color.WHITE);
            }
            else {
                filter_contest_btn.setBackgroundDrawable(
                        getResources().getDrawable(R.drawable.round_button)
                );filter_contest_btn.setTextColor(Color.BLACK);
            }
            }
            filter_study_btn.setOnClickListener {
                if (filter_study_btn.isChecked()) {
                    filter_study_btn.setBackgroundDrawable(
                            getResources().getDrawable(R.drawable.round_button_selected)

                    );filter_study_btn.setTextColor(Color.WHITE);
                }
                else {
                    filter_study_btn.setBackgroundDrawable(
                            getResources().getDrawable(R.drawable.round_button)
                    );filter_study_btn.setTextColor(Color.BLACK);
                }

            }
            filter_side_btn.setOnClickListener {
                if (filter_side_btn.isChecked()) {
                    filter_side_btn.setBackgroundDrawable(
                            getResources().getDrawable(R.drawable.round_button_selected)

                    );filter_side_btn.setTextColor(Color.WHITE);
                }
                else {
                    filter_side_btn.setBackgroundDrawable(
                            getResources().getDrawable(R.drawable.round_button)
                    );filter_side_btn.setTextColor(Color.BLACK);
                }
            }

        filter_startup2_btn.setOnClickListener {
            if (filter_startup2_btn.isChecked()) {
                filter_startup2_btn.setBackgroundDrawable(
                        getResources().getDrawable(R.drawable.round_button_selected)

                );filter_startup2_btn.setTextColor(Color.WHITE);
            }
            else {
                filter_startup2_btn.setBackgroundDrawable(
                        getResources().getDrawable(R.drawable.round_button)
                );filter_startup2_btn.setTextColor(Color.BLACK);
            }

        }

            filter_purposeetc_btn.setOnClickListener {
                if (filter_purposeetc_btn.isChecked()) {
                    filter_purposeetc_btn.setBackgroundDrawable(
                            getResources().getDrawable(R.drawable.round_button_selected)

                    );filter_purposeetc_btn.setTextColor(Color.WHITE);
                }
                else {
                    filter_purposeetc_btn.setBackgroundDrawable(
                            getResources().getDrawable(R.drawable.round_button)
                    );filter_purposeetc_btn.setTextColor(Color.BLACK);
                }
            }

        // 분야
            filter_fieldall_btn.setOnClickListener {
                if (filter_fieldall_btn.isChecked()) {
                    filter_fieldall_btn.setBackgroundDrawable(
                            getResources().getDrawable(R.drawable.round_button_selected)

                    );filter_fieldall_btn.setTextColor(Color.WHITE);
                }
                else {
                    filter_fieldall_btn.setBackgroundDrawable(
                            getResources().getDrawable(R.drawable.round_button)
                    );filter_fieldall_btn.setTextColor(Color.BLACK);
                }
            }
            filter_chain_btn.setOnClickListener {
                if (filter_chain_btn.isChecked()) {
                    filter_chain_btn.setBackgroundDrawable(
                            getResources().getDrawable(R.drawable.round_button_selected)

                    );filter_chain_btn.setTextColor(Color.WHITE);
                }
                else {
                    filter_chain_btn.setBackgroundDrawable(
                            getResources().getDrawable(R.drawable.round_button)
                    );filter_chain_btn.setTextColor(Color.BLACK);
                }
            }
            filter_iot_btn.setOnClickListener {
                if (filter_iot_btn.isChecked()) {
                    filter_iot_btn.setBackgroundDrawable(
                            getResources().getDrawable(R.drawable.round_button_selected)

                    );filter_iot_btn.setTextColor(Color.WHITE);
                }
                else {
                    filter_iot_btn.setBackgroundDrawable(
                            getResources().getDrawable(R.drawable.round_button)
                    );filter_iot_btn.setTextColor(Color.BLACK);
                }
            }
            filter_ai_btn.setOnClickListener {
                if (filter_ai_btn.isChecked()) {
                    filter_ai_btn.setBackgroundDrawable(
                            getResources().getDrawable(R.drawable.round_button_selected)

                    );filter_ai_btn.setTextColor(Color.WHITE);
                }
                else {
                    filter_ai_btn.setBackgroundDrawable(
                            getResources().getDrawable(R.drawable.round_button)
                    );filter_ai_btn.setTextColor(Color.BLACK);
                }
            }
            filter_design_btn.setOnClickListener {
                if (filter_design_btn.isChecked()) {
                    filter_design_btn.setBackgroundDrawable(
                            getResources().getDrawable(R.drawable.round_button_selected)

                    );filter_design_btn.setTextColor(Color.WHITE);
                }
                else {
                    filter_design_btn.setBackgroundDrawable(
                            getResources().getDrawable(R.drawable.round_button)
                    );filter_design_btn.setTextColor(Color.BLACK);
                }
            }
            filter_contents_btn.setOnClickListener {
                if (filter_contents_btn.isChecked()) {
                    filter_contents_btn.setBackgroundDrawable(
                            getResources().getDrawable(R.drawable.round_button_selected)

                    );filter_contents_btn.setTextColor(Color.WHITE);
                }
                else {
                    filter_contents_btn.setBackgroundDrawable(
                            getResources().getDrawable(R.drawable.round_button)
                    );filter_contents_btn.setTextColor(Color.BLACK);
                }
            }
            filter_fieldetc_btn.setOnClickListener {
                if (filter_fieldetc_btn.isChecked()) {
                    filter_fieldetc_btn.setBackgroundDrawable(
                            getResources().getDrawable(R.drawable.round_button_selected)

                    );filter_fieldetc_btn.setTextColor(Color.WHITE);
                }
                else {
                    filter_fieldetc_btn.setBackgroundDrawable(
                            getResources().getDrawable(R.drawable.round_button)
                    );filter_fieldetc_btn.setTextColor(Color.BLACK);
                }
            }

        //역할
            filter_roleall_btn.setOnClickListener {
                if (filter_roleall_btn.isChecked()) {
                    filter_roleall_btn.setBackgroundDrawable(
                            getResources().getDrawable(R.drawable.round_button_selected)

                    );filter_roleall_btn.setTextColor(Color.WHITE);
                }
                else {
                    filter_roleall_btn.setBackgroundDrawable(
                            getResources().getDrawable(R.drawable.round_button)
                    );filter_roleall_btn.setTextColor(Color.BLACK);
                }
            }
            filter_pm_btn.setOnClickListener {
                if (filter_pm_btn.isChecked()) {
                    filter_pm_btn.setBackgroundDrawable(
                            getResources().getDrawable(R.drawable.round_button_selected)

                    );filter_pm_btn.setTextColor(Color.WHITE);
                }
                else {
                    filter_pm_btn.setBackgroundDrawable(
                            getResources().getDrawable(R.drawable.round_button)
                    );filter_pm_btn.setTextColor(Color.BLACK);
                }
            }
            filter_planner_btn.setOnClickListener {
                if (filter_planner_btn.isChecked()) {
                    filter_planner_btn.setBackgroundDrawable(
                            getResources().getDrawable(R.drawable.round_button_selected)

                    );filter_planner_btn.setTextColor(Color.WHITE);
                }
                else {
                    filter_planner_btn.setBackgroundDrawable(
                            getResources().getDrawable(R.drawable.round_button)
                    );filter_planner_btn.setTextColor(Color.BLACK);
                }
            }
            filter_designer_btn.setOnClickListener {
                if (filter_designer_btn.isChecked()) {
                    filter_designer_btn.setBackgroundDrawable(
                            getResources().getDrawable(R.drawable.round_button_selected)

                    );filter_designer_btn.setTextColor(Color.WHITE);
                }
                else {
                    filter_designer_btn.setBackgroundDrawable(
                            getResources().getDrawable(R.drawable.round_button)
                    );filter_designer_btn.setTextColor(Color.BLACK);
                }
            }
            filter_developer_btn.setOnClickListener {
                if (filter_developer_btn.isChecked()) {
                    filter_developer_btn.setBackgroundDrawable(
                            getResources().getDrawable(R.drawable.round_button_selected)

                    );filter_developer_btn.setTextColor(Color.WHITE);
                }
                else {
                    filter_developer_btn.setBackgroundDrawable(
                            getResources().getDrawable(R.drawable.round_button)
                    );filter_developer_btn.setTextColor(Color.BLACK);
                }
            }
            filter_roleetc_btn.setOnClickListener {
                if (filter_roleetc_btn.isChecked()) {
                    filter_roleetc_btn.setBackgroundDrawable(
                            getResources().getDrawable(R.drawable.round_button_selected)

                    );filter_roleetc_btn.setTextColor(Color.WHITE);
                }
                else {
                    filter_roleetc_btn.setBackgroundDrawable(
                            getResources().getDrawable(R.drawable.round_button)
                    );filter_roleetc_btn.setTextColor(Color.BLACK);
                }
            }

        //장소
        filter_locationall_btn.setOnClickListener {
            if (filter_locationall_btn.isChecked()) {
                filter_locationall_btn.setBackgroundDrawable(
                        getResources().getDrawable(R.drawable.round_button_selected)

                );filter_locationall_btn.setTextColor(Color.WHITE);
            }
            else {
                filter_locationall_btn.setBackgroundDrawable(
                        getResources().getDrawable(R.drawable.round_button)
                );filter_locationall_btn.setTextColor(Color.BLACK);
            }
        }

        
            
            filter_seoul_btn.setOnClickListener {
                if (filter_seoul_btn.isChecked()) {
                    filter_seoul_btn.setBackgroundDrawable(
                            getResources().getDrawable(R.drawable.round_button_selected)

                    );filter_seoul_btn.setTextColor(Color.WHITE);
                }
                else {
                    filter_seoul_btn.setBackgroundDrawable(
                            getResources().getDrawable(R.drawable.round_button)
                    );filter_seoul_btn.setTextColor(Color.BLACK);
                }
            }
            filter_gyeonggi_btn.setOnClickListener {
                if (filter_gyeonggi_btn.isChecked()) {
                    filter_gyeonggi_btn.setBackgroundDrawable(
                            getResources().getDrawable(R.drawable.round_button_selected)

                    );filter_gyeonggi_btn.setTextColor(Color.WHITE);
                }
                else {
                    filter_gyeonggi_btn.setBackgroundDrawable(
                            getResources().getDrawable(R.drawable.round_button)
                    );filter_gyeonggi_btn.setTextColor(Color.BLACK);
                }
            }
            filter_incheon_btn.setOnClickListener {
                if (filter_incheon_btn.isChecked()) {
                    filter_incheon_btn.setBackgroundDrawable(
                            getResources().getDrawable(R.drawable.round_button_selected)

                    );filter_incheon_btn.setTextColor(Color.WHITE);
                }
                else {
                    filter_incheon_btn.setBackgroundDrawable(
                            getResources().getDrawable(R.drawable.round_button)
                    );filter_incheon_btn.setTextColor(Color.BLACK);
                }
            }
            filter_gangwon_btn.setOnClickListener {
                if (filter_gangwon_btn.isChecked()) {
                    filter_gangwon_btn.setBackgroundDrawable(
                            getResources().getDrawable(R.drawable.round_button_selected)

                    );filter_gangwon_btn.setTextColor(Color.WHITE);
                }
                else {
                    filter_gangwon_btn.setBackgroundDrawable(
                            getResources().getDrawable(R.drawable.round_button)
                    );filter_gangwon_btn.setTextColor(Color.BLACK);
                }
            }
            filter_chungcheong_btn.setOnClickListener {
                if (filter_chungcheong_btn.isChecked()) {
                    filter_chungcheong_btn.setBackgroundDrawable(
                            getResources().getDrawable(R.drawable.round_button_selected)

                    );filter_chungcheong_btn.setTextColor(Color.WHITE);
                }
                else {
                    filter_chungcheong_btn.setBackgroundDrawable(
                            getResources().getDrawable(R.drawable.round_button)
                    );filter_chungcheong_btn.setTextColor(Color.BLACK);
                }
            }
            filter_jeolla_btn.setOnClickListener {
                if (filter_jeolla_btn.isChecked()) {
                    filter_jeolla_btn.setBackgroundDrawable(
                            getResources().getDrawable(R.drawable.round_button_selected)

                    );filter_jeolla_btn.setTextColor(Color.WHITE);
                }
                else {
                    filter_jeolla_btn.setBackgroundDrawable(
                            getResources().getDrawable(R.drawable.round_button)
                    );filter_jeolla_btn.setTextColor(Color.BLACK);
                }
            }
            filter_gyeongsang_btn.setOnClickListener {
                if (filter_gyeongsang_btn.isChecked()) {
                    filter_gyeongsang_btn.setBackgroundDrawable(
                            getResources().getDrawable(R.drawable.round_button_selected)

                    );filter_gyeongsang_btn.setTextColor(Color.WHITE);
                }
                else {
                    filter_gyeongsang_btn.setBackgroundDrawable(
                            getResources().getDrawable(R.drawable.round_button)
                    );filter_gyeongsang_btn.setTextColor(Color.BLACK);
                }
            }
            filter_jeju_btn.setOnClickListener {
                if (filter_jeju_btn.isChecked()) {
                    filter_jeju_btn.setBackgroundDrawable(
                            getResources().getDrawable(R.drawable.round_button_selected)

                    );filter_jeju_btn.setTextColor(Color.WHITE);
                }
                else {
                    filter_jeju_btn.setBackgroundDrawable(
                            getResources().getDrawable(R.drawable.round_button)
                    );filter_jeju_btn.setTextColor(Color.BLACK);
                }
            }

        filter_confirm_btn.setOnClickListener {
            val intent = Intent(this@SearchFilterActivity, MainActivity::class.java)
            startActivity(intent)
        }
        }



}

