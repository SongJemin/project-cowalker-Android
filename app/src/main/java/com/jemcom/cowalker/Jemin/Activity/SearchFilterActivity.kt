package com.jemcom.cowalker.Jemin.Activity

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.jemcom.cowalker.R
import kotlinx.android.synthetic.main.activity_search_filter.*

class SearchFilterActivity : AppCompatActivity(), View.OnClickListener {
    var filterAim : String = ""
    var filterDepartment : String = ""
    var filterPosition : String = ""
    var filterArea : String = ""

    override fun onClick(v: View?) {
        when (v) {

            filter_aimall_btn -> {
                filter_aimall_btn.setSelected(true)
                filter_aimall_btn.setTextColor(Color.WHITE)

                filter_startup_btn.setSelected(false)
                filter_startup_btn.setTextColor(Color.BLACK)
                filter_contest_btn.setSelected(false)
                filter_contest_btn.setTextColor(Color.BLACK)
                filter_study_btn.setSelected(false)
                filter_study_btn.setTextColor(Color.BLACK)
                filter_side_btn.setSelected(false)
                filter_side_btn.setTextColor(Color.BLACK)
                filter_aimetc_btn.setSelected(false)
                filter_aimetc_btn.setTextColor(Color.BLACK)
                filter_creative_btn.setSelected(false)
                filter_creative_btn.setTextColor(Color.BLACK)
                filterAim = "전체"
            }

            filter_startup_btn -> {
                filter_startup_btn.setSelected(true)
                filter_startup_btn.setTextColor(Color.WHITE)

                filter_aimall_btn.setSelected(false)
                filter_aimall_btn.setTextColor(Color.BLACK)
                filter_contest_btn.setSelected(false)
                filter_contest_btn.setTextColor(Color.BLACK)
                filter_study_btn.setSelected(false)
                filter_study_btn.setTextColor(Color.BLACK)
                filter_side_btn.setSelected(false)
                filter_side_btn.setTextColor(Color.BLACK)
                filter_aimetc_btn.setSelected(false)
                filter_aimetc_btn.setTextColor(Color.BLACK)
                filter_creative_btn.setSelected(false)
                filter_creative_btn.setTextColor(Color.BLACK)
                filterAim = "창업"
            }

            filter_contest_btn -> {
                filter_contest_btn.setSelected(true)
                filter_contest_btn.setTextColor(Color.WHITE)

                filter_aimall_btn.setSelected(false)
                filter_aimall_btn.setTextColor(Color.BLACK)
                filter_startup_btn.setSelected(false)
                filter_startup_btn.setTextColor(Color.BLACK)
                filter_study_btn.setSelected(false)
                filter_study_btn.setTextColor(Color.BLACK)
                filter_side_btn.setSelected(false)
                filter_side_btn.setTextColor(Color.BLACK)
                filter_aimetc_btn.setSelected(false)
                filter_aimetc_btn.setTextColor(Color.BLACK)
                filter_creative_btn.setSelected(false)
                filter_creative_btn.setTextColor(Color.BLACK)
                filterAim = "공모전 참여"
            }

            filter_study_btn -> {
                filter_study_btn.setSelected(true)
                filter_study_btn.setTextColor(Color.WHITE)

                filter_aimall_btn.setSelected(false)
                filter_aimall_btn.setTextColor(Color.BLACK)
                filter_startup_btn.setSelected(false)
                filter_startup_btn.setTextColor(Color.BLACK)
                filter_contest_btn.setSelected(false)
                filter_contest_btn.setTextColor(Color.BLACK)
                filter_side_btn.setSelected(false)
                filter_side_btn.setTextColor(Color.BLACK)
                filter_aimetc_btn.setSelected(false)
                filter_aimetc_btn.setTextColor(Color.BLACK)
                filter_creative_btn.setSelected(false)
                filter_creative_btn.setTextColor(Color.BLACK)
                filterAim = "스터디"
            }

            filter_side_btn -> {
                filter_side_btn.setSelected(true)
                filter_side_btn.setTextColor(Color.WHITE)

                filter_aimall_btn.setSelected(false)
                filter_aimall_btn.setTextColor(Color.BLACK)
                filter_startup_btn.setSelected(false)
                filter_startup_btn.setTextColor(Color.BLACK)
                filter_contest_btn.setSelected(false)
                filter_contest_btn.setTextColor(Color.BLACK)
                filter_study_btn.setSelected(false)
                filter_study_btn.setTextColor(Color.BLACK)
                filter_aimetc_btn.setSelected(false)
                filter_aimetc_btn.setTextColor(Color.BLACK)
                filter_creative_btn.setSelected(false)
                filter_creative_btn.setTextColor(Color.BLACK)
                filterAim = "사이드 프로젝트"
            }

            filter_creative_btn -> {
                filter_creative_btn.setSelected(true)
                filter_creative_btn.setTextColor(Color.WHITE)

                filter_aimall_btn.setSelected(false)
                filter_aimall_btn.setTextColor(Color.BLACK)
                filter_startup_btn.setSelected(false)
                filter_startup_btn.setTextColor(Color.BLACK)
                filter_contest_btn.setSelected(false)
                filter_contest_btn.setTextColor(Color.BLACK)
                filter_study_btn.setSelected(false)
                filter_study_btn.setTextColor(Color.BLACK)
                filter_aimetc_btn.setSelected(false)
                filter_aimetc_btn.setTextColor(Color.BLACK)
                filter_side_btn.setSelected(false)
                filter_side_btn.setTextColor(Color.BLACK)
                filterAim = "창작"
            }

            filter_aimetc_btn -> {
                filter_aimetc_btn.setSelected(true)
                filter_aimetc_btn.setTextColor(Color.WHITE)

                filter_aimall_btn.setSelected(false)
                filter_aimall_btn.setTextColor(Color.BLACK)
                filter_startup_btn.setSelected(false)
                filter_startup_btn.setTextColor(Color.BLACK)
                filter_contest_btn.setSelected(false)
                filter_contest_btn.setTextColor(Color.BLACK)
                filter_study_btn.setSelected(false)
                filter_study_btn.setTextColor(Color.BLACK)
                filter_side_btn.setSelected(false)
                filter_side_btn.setTextColor(Color.BLACK)
                filter_creative_btn.setSelected(false)
                filter_creative_btn.setTextColor(Color.BLACK)
                filterAim = "기타"
            }
            
            //////////////////////////////////////////////
            filter_departmentall_btn -> {
                filter_departmentall_btn.setSelected(true)
                filter_departmentall_btn.setTextColor(Color.WHITE)

                filter_chain_btn.setSelected(false)
                filter_chain_btn.setTextColor(Color.BLACK)
                filter_iot_btn.setSelected(false)
                filter_iot_btn.setTextColor(Color.BLACK)
                filter_ai_btn.setSelected(false)
                filter_ai_btn.setTextColor(Color.BLACK)
                filter_design_btn.setSelected(false)
                filter_design_btn.setTextColor(Color.BLACK)
                filter_departmentetc_btn.setSelected(false)
                filter_departmentetc_btn.setTextColor(Color.BLACK)
                filter_departstartup_btn.setSelected(false)
                filter_departstartup_btn.setTextColor(Color.BLACK)
                filterDepartment = "전체"
            }

            filter_chain_btn -> {
                filter_chain_btn.setSelected(true)
                filter_chain_btn.setTextColor(Color.WHITE)

                filter_departmentall_btn.setSelected(false)
                filter_departmentall_btn.setTextColor(Color.BLACK)
                filter_iot_btn.setSelected(false)
                filter_iot_btn.setTextColor(Color.BLACK)
                filter_ai_btn.setSelected(false)
                filter_ai_btn.setTextColor(Color.BLACK)
                filter_design_btn.setSelected(false)
                filter_design_btn.setTextColor(Color.BLACK)
                filter_departmentetc_btn.setSelected(false)
                filter_departmentetc_btn.setTextColor(Color.BLACK)
                filter_departstartup_btn.setSelected(false)
                filter_departstartup_btn.setTextColor(Color.BLACK)
                filterDepartment = "블록체인"
            }

            filter_iot_btn -> {
                filter_iot_btn.setSelected(true)
                filter_iot_btn.setTextColor(Color.WHITE)

                filter_departmentall_btn.setSelected(false)
                filter_departmentall_btn.setTextColor(Color.BLACK)
                filter_chain_btn.setSelected(false)
                filter_chain_btn.setTextColor(Color.BLACK)
                filter_ai_btn.setSelected(false)
                filter_ai_btn.setTextColor(Color.BLACK)
                filter_design_btn.setSelected(false)
                filter_design_btn.setTextColor(Color.BLACK)
                filter_departmentetc_btn.setSelected(false)
                filter_departmentetc_btn.setTextColor(Color.BLACK)
                filter_departstartup_btn.setSelected(false)
                filter_departstartup_btn.setTextColor(Color.BLACK)
                filterDepartment = "iot"
            }

            filter_ai_btn -> {
                filter_ai_btn.setSelected(true)
                filter_ai_btn.setTextColor(Color.WHITE)

                filter_departmentall_btn.setSelected(false)
                filter_departmentall_btn.setTextColor(Color.BLACK)
                filter_chain_btn.setSelected(false)
                filter_chain_btn.setTextColor(Color.BLACK)
                filter_iot_btn.setSelected(false)
                filter_iot_btn.setTextColor(Color.BLACK)
                filter_design_btn.setSelected(false)
                filter_design_btn.setTextColor(Color.BLACK)
                filter_departmentetc_btn.setSelected(false)
                filter_departmentetc_btn.setTextColor(Color.BLACK)
                filter_departstartup_btn.setSelected(false)
                filter_departstartup_btn.setTextColor(Color.BLACK)
                filterDepartment = "인공지능"
            }

            filter_design_btn -> {
                filter_design_btn.setSelected(true)
                filter_design_btn.setTextColor(Color.WHITE)

                filter_departmentall_btn.setSelected(false)
                filter_departmentall_btn.setTextColor(Color.BLACK)
                filter_chain_btn.setSelected(false)
                filter_chain_btn.setTextColor(Color.BLACK)
                filter_iot_btn.setSelected(false)
                filter_iot_btn.setTextColor(Color.BLACK)
                filter_ai_btn.setSelected(false)
                filter_ai_btn.setTextColor(Color.BLACK)
                filter_departmentetc_btn.setSelected(false)
                filter_departmentetc_btn.setTextColor(Color.BLACK)
                filter_departstartup_btn.setSelected(false)
                filter_departstartup_btn.setTextColor(Color.BLACK)
                filterDepartment = "디자인"
            }

            filter_departstartup_btn -> {
                filter_departstartup_btn.setSelected(true)
                filter_departstartup_btn.setTextColor(Color.WHITE)

                filter_departmentall_btn.setSelected(false)
                filter_departmentall_btn.setTextColor(Color.BLACK)
                filter_chain_btn.setSelected(false)
                filter_chain_btn.setTextColor(Color.BLACK)
                filter_iot_btn.setSelected(false)
                filter_iot_btn.setTextColor(Color.BLACK)
                filter_ai_btn.setSelected(false)
                filter_ai_btn.setTextColor(Color.BLACK)
                filter_departmentetc_btn.setSelected(false)
                filter_departmentetc_btn.setTextColor(Color.BLACK)
                filter_design_btn.setSelected(false)
                filter_design_btn.setTextColor(Color.BLACK)
                filterDepartment = "창업"
            }

            filter_departmentetc_btn -> {
                filter_departmentetc_btn.setSelected(true)
                filter_departmentetc_btn.setTextColor(Color.WHITE)

                filter_departmentall_btn.setSelected(false)
                filter_departmentall_btn.setTextColor(Color.BLACK)
                filter_chain_btn.setSelected(false)
                filter_chain_btn.setTextColor(Color.BLACK)
                filter_iot_btn.setSelected(false)
                filter_iot_btn.setTextColor(Color.BLACK)
                filter_ai_btn.setSelected(false)
                filter_ai_btn.setTextColor(Color.BLACK)
                filter_design_btn.setSelected(false)
                filter_design_btn.setTextColor(Color.BLACK)
                filter_departstartup_btn.setSelected(false)
                filter_departstartup_btn.setTextColor(Color.BLACK)
                filterDepartment = "기타"
            }
            
            
            
            ////////////////////////////////
            filter_positionall_btn -> {
                filter_positionall_btn.setSelected(true)
                filter_positionall_btn.setTextColor(Color.WHITE)

                filter_pm_btn.setSelected(false)
                filter_pm_btn.setTextColor(Color.BLACK)
                filter_planner_btn.setSelected(false)
                filter_planner_btn.setTextColor(Color.BLACK)
                filter_designer_btn.setSelected(false)
                filter_designer_btn.setTextColor(Color.BLACK)
                filter_developer_btn.setSelected(false)
                filter_developer_btn.setTextColor(Color.BLACK)
                filter_positionetc_btn.setSelected(false)
                filter_positionetc_btn.setTextColor(Color.BLACK)
                filterPosition = "전체"
            }

            filter_pm_btn -> {
                filter_pm_btn.setSelected(true)
                filter_pm_btn.setTextColor(Color.WHITE)

                filter_positionall_btn.setSelected(false)
                filter_positionall_btn.setTextColor(Color.BLACK)
                filter_planner_btn.setSelected(false)
                filter_planner_btn.setTextColor(Color.BLACK)
                filter_designer_btn.setSelected(false)
                filter_designer_btn.setTextColor(Color.BLACK)
                filter_developer_btn.setSelected(false)
                filter_developer_btn.setTextColor(Color.BLACK)
                filter_positionetc_btn.setSelected(false)
                filter_positionetc_btn.setTextColor(Color.BLACK)
                filterPosition = "PM"
            }

            filter_planner_btn -> {
                filter_planner_btn.setSelected(true)
                filter_planner_btn.setTextColor(Color.WHITE)

                filter_positionall_btn.setSelected(false)
                filter_positionall_btn.setTextColor(Color.BLACK)
                filter_pm_btn.setSelected(false)
                filter_pm_btn.setTextColor(Color.BLACK)
                filter_designer_btn.setSelected(false)
                filter_designer_btn.setTextColor(Color.BLACK)
                filter_developer_btn.setSelected(false)
                filter_developer_btn.setTextColor(Color.BLACK)
                filter_positionetc_btn.setSelected(false)
                filter_positionetc_btn.setTextColor(Color.BLACK)
                filterPosition = "기획자"
            }

            filter_designer_btn -> {
                filter_designer_btn.setSelected(true)
                filter_designer_btn.setTextColor(Color.WHITE)

                filter_positionall_btn.setSelected(false)
                filter_positionall_btn.setTextColor(Color.BLACK)
                filter_pm_btn.setSelected(false)
                filter_pm_btn.setTextColor(Color.BLACK)
                filter_planner_btn.setSelected(false)
                filter_planner_btn.setTextColor(Color.BLACK)
                filter_developer_btn.setSelected(false)
                filter_developer_btn.setTextColor(Color.BLACK)
                filter_positionetc_btn.setSelected(false)
                filter_positionetc_btn.setTextColor(Color.BLACK)
                filterPosition = "디자이너"
            }

            filter_developer_btn -> {
                filter_developer_btn.setSelected(true)
                filter_developer_btn.setTextColor(Color.WHITE)

                filter_positionall_btn.setSelected(false)
                filter_positionall_btn.setTextColor(Color.BLACK)
                filter_pm_btn.setSelected(false)
                filter_pm_btn.setTextColor(Color.BLACK)
                filter_planner_btn.setSelected(false)
                filter_planner_btn.setTextColor(Color.BLACK)
                filter_designer_btn.setSelected(false)
                filter_designer_btn.setTextColor(Color.BLACK)
                filter_positionetc_btn.setSelected(false)
                filter_positionetc_btn.setTextColor(Color.BLACK)
                filterPosition = "개발자"
            }

            filter_positionetc_btn -> {
                filter_positionetc_btn.setSelected(true)
                filter_positionetc_btn.setTextColor(Color.WHITE)

                filter_positionall_btn.setSelected(false)
                filter_positionall_btn.setTextColor(Color.BLACK)
                filter_pm_btn.setSelected(false)
                filter_pm_btn.setTextColor(Color.BLACK)
                filter_planner_btn.setSelected(false)
                filter_planner_btn.setTextColor(Color.BLACK)
                filter_designer_btn.setSelected(false)
                filter_designer_btn.setTextColor(Color.BLACK)
                filter_developer_btn.setSelected(false)
                filter_developer_btn.setTextColor(Color.BLACK)
                filterPosition = "기타"

            }
                
                ////////////////////////////////////////////////

            filter_areaall_btn -> {
                filter_areaall_btn.setSelected(true)
                filter_areaall_btn.setTextColor(Color.WHITE)

                filter_seoul_btn.setSelected(false)
                filter_seoul_btn.setTextColor(Color.BLACK)
                filter_gyeonggi_btn.setSelected(false)
                filter_gyeonggi_btn.setTextColor(Color.BLACK)
                filter_incheon_btn.setSelected(false)
                filter_incheon_btn.setTextColor(Color.BLACK)
                filter_gangwon_btn.setSelected(false)
                filter_gangwon_btn.setTextColor(Color.BLACK)
                filter_chungcheong_btn.setSelected(false)
                filter_chungcheong_btn.setTextColor(Color.BLACK)
                filter_jeolla_btn.setSelected(false)
                filter_jeolla_btn.setTextColor(Color.BLACK)
                filter_gyeongsang_btn.setSelected(false)
                filter_gyeongsang_btn.setTextColor(Color.BLACK)
                filter_jeju_btn.setSelected(false)
                filter_jeju_btn.setTextColor(Color.BLACK)
                filterArea = "전체"
            }

            filter_seoul_btn -> {
                filter_seoul_btn.setSelected(true)
                filter_seoul_btn.setTextColor(Color.WHITE)

                filter_areaall_btn.setSelected(false)
                filter_areaall_btn.setTextColor(Color.BLACK)
                filter_gyeonggi_btn.setSelected(false)
                filter_gyeonggi_btn.setTextColor(Color.BLACK)
                filter_incheon_btn.setSelected(false)
                filter_incheon_btn.setTextColor(Color.BLACK)
                filter_gangwon_btn.setSelected(false)
                filter_gangwon_btn.setTextColor(Color.BLACK)
                filter_chungcheong_btn.setSelected(false)
                filter_chungcheong_btn.setTextColor(Color.BLACK)
                filter_jeolla_btn.setSelected(false)
                filter_jeolla_btn.setTextColor(Color.BLACK)
                filter_gyeongsang_btn.setSelected(false)
                filter_gyeongsang_btn.setTextColor(Color.BLACK)
                filter_jeju_btn.setSelected(false)
                filter_jeju_btn.setTextColor(Color.BLACK)
                filterArea = "서울"
            }

            filter_gyeonggi_btn -> {
                filter_gyeonggi_btn.setSelected(true)
                filter_gyeonggi_btn.setTextColor(Color.WHITE)

                filter_areaall_btn.setSelected(false)
                filter_areaall_btn.setTextColor(Color.BLACK)
                filter_seoul_btn.setSelected(false)
                filter_seoul_btn.setTextColor(Color.BLACK)
                filter_incheon_btn.setSelected(false)
                filter_incheon_btn.setTextColor(Color.BLACK)
                filter_gangwon_btn.setSelected(false)
                filter_gangwon_btn.setTextColor(Color.BLACK)
                filter_chungcheong_btn.setSelected(false)
                filter_chungcheong_btn.setTextColor(Color.BLACK)
                filter_jeolla_btn.setSelected(false)
                filter_jeolla_btn.setTextColor(Color.BLACK)
                filter_gyeongsang_btn.setSelected(false)
                filter_gyeongsang_btn.setTextColor(Color.BLACK)
                filter_jeju_btn.setSelected(false)
                filter_jeju_btn.setTextColor(Color.BLACK)
                filterArea = "경기도"
            }

            filter_incheon_btn -> {
                filter_incheon_btn.setSelected(true)
                filter_incheon_btn.setTextColor(Color.WHITE)

                filter_areaall_btn.setSelected(false)
                filter_areaall_btn.setTextColor(Color.BLACK)
                filter_seoul_btn.setSelected(false)
                filter_seoul_btn.setTextColor(Color.BLACK)
                filter_gyeonggi_btn.setSelected(false)
                filter_gyeonggi_btn.setTextColor(Color.BLACK)
                filter_gangwon_btn.setSelected(false)
                filter_gangwon_btn.setTextColor(Color.BLACK)
                filter_chungcheong_btn.setSelected(false)
                filter_chungcheong_btn.setTextColor(Color.BLACK)
                filter_jeolla_btn.setSelected(false)
                filter_jeolla_btn.setTextColor(Color.BLACK)
                filter_gyeongsang_btn.setSelected(false)
                filter_gyeongsang_btn.setTextColor(Color.BLACK)
                filter_jeju_btn.setSelected(false)
                filter_jeju_btn.setTextColor(Color.BLACK)
                filterArea = "인천"
            }

            filter_gangwon_btn -> {
                filter_gangwon_btn.setSelected(true)
                filter_gangwon_btn.setTextColor(Color.WHITE)

                filter_areaall_btn.setSelected(false)
                filter_areaall_btn.setTextColor(Color.BLACK)
                filter_seoul_btn.setSelected(false)
                filter_seoul_btn.setTextColor(Color.BLACK)
                filter_gyeonggi_btn.setSelected(false)
                filter_gyeonggi_btn.setTextColor(Color.BLACK)
                filter_incheon_btn.setSelected(false)
                filter_incheon_btn.setTextColor(Color.BLACK)
                filter_chungcheong_btn.setSelected(false)
                filter_chungcheong_btn.setTextColor(Color.BLACK)
                filter_jeolla_btn.setSelected(false)
                filter_jeolla_btn.setTextColor(Color.BLACK)
                filter_gyeongsang_btn.setSelected(false)
                filter_gyeongsang_btn.setTextColor(Color.BLACK)
                filter_jeju_btn.setSelected(false)
                filter_jeju_btn.setTextColor(Color.BLACK)
                filterArea = "강원도"
            }

            filter_chungcheong_btn -> {
                filter_chungcheong_btn.setSelected(true)
                filter_chungcheong_btn.setTextColor(Color.WHITE)

                filter_areaall_btn.setSelected(false)
                filter_areaall_btn.setTextColor(Color.BLACK)
                filter_seoul_btn.setSelected(false)
                filter_seoul_btn.setTextColor(Color.BLACK)
                filter_gyeonggi_btn.setSelected(false)
                filter_gyeonggi_btn.setTextColor(Color.BLACK)
                filter_incheon_btn.setSelected(false)
                filter_incheon_btn.setTextColor(Color.BLACK)
                filter_gangwon_btn.setSelected(false)
                filter_gangwon_btn.setTextColor(Color.BLACK)
                filter_jeolla_btn.setSelected(false)
                filter_jeolla_btn.setTextColor(Color.BLACK)
                filter_gyeongsang_btn.setSelected(false)
                filter_gyeongsang_btn.setTextColor(Color.BLACK)
                filter_jeju_btn.setSelected(false)
                filter_jeju_btn.setTextColor(Color.BLACK)
                filterArea = "충청도"
            }


            filter_jeolla_btn -> {
                filter_jeolla_btn.setSelected(true)
                filter_jeolla_btn.setTextColor(Color.WHITE)

                filter_chungcheong_btn.setSelected(false)
                filter_chungcheong_btn.setTextColor(Color.BLACK)
                filter_areaall_btn.setSelected(false)
                filter_areaall_btn.setTextColor(Color.BLACK)
                filter_seoul_btn.setSelected(false)
                filter_seoul_btn.setTextColor(Color.BLACK)
                filter_gyeonggi_btn.setSelected(false)
                filter_gyeonggi_btn.setTextColor(Color.BLACK)
                filter_incheon_btn.setSelected(false)
                filter_incheon_btn.setTextColor(Color.BLACK)
                filter_gangwon_btn.setSelected(false)
                filter_gangwon_btn.setTextColor(Color.BLACK)
                filter_gyeongsang_btn.setSelected(false)
                filter_gyeongsang_btn.setTextColor(Color.BLACK)
                filter_jeju_btn.setSelected(false)
                filter_jeju_btn.setTextColor(Color.BLACK)
                filterArea = "전라도"

            }

            filter_gyeongsang_btn -> {
                filter_gyeongsang_btn.setSelected(true)
                filter_gyeongsang_btn.setTextColor(Color.WHITE)

                filter_jeolla_btn.setSelected(false)
                filter_jeolla_btn.setTextColor(Color.BLACK)
                filter_areaall_btn.setSelected(false)
                filter_areaall_btn.setTextColor(Color.BLACK)
                filter_seoul_btn.setSelected(false)
                filter_seoul_btn.setTextColor(Color.BLACK)
                filter_gyeonggi_btn.setSelected(false)
                filter_gyeonggi_btn.setTextColor(Color.BLACK)
                filter_incheon_btn.setSelected(false)
                filter_incheon_btn.setTextColor(Color.BLACK)
                filter_gangwon_btn.setSelected(false)
                filter_gangwon_btn.setTextColor(Color.BLACK)
                filter_chungcheong_btn.setSelected(false)
                filter_chungcheong_btn.setTextColor(Color.BLACK)
                filter_jeju_btn.setSelected(false)
                filter_jeju_btn.setTextColor(Color.BLACK)
                filterArea = "경상도"

            }

            filter_jeju_btn -> {
                filter_jeju_btn.setSelected(true)
                filter_jeju_btn.setTextColor(Color.WHITE)

                filter_chungcheong_btn.setSelected(false)
                filter_chungcheong_btn.setTextColor(Color.BLACK)
                filter_areaall_btn.setSelected(false)
                filter_areaall_btn.setTextColor(Color.BLACK)
                filter_seoul_btn.setSelected(false)
                filter_seoul_btn.setTextColor(Color.BLACK)
                filter_gyeonggi_btn.setSelected(false)
                filter_gyeonggi_btn.setTextColor(Color.BLACK)
                filter_incheon_btn.setSelected(false)
                filter_incheon_btn.setTextColor(Color.BLACK)
                filter_gangwon_btn.setSelected(false)
                filter_gangwon_btn.setTextColor(Color.BLACK)
                filter_chungcheong_btn.setSelected(false)
                filter_chungcheong_btn.setTextColor(Color.BLACK)
                filter_gyeongsang_btn.setSelected(false)
                filter_gyeongsang_btn.setTextColor(Color.BLACK)
                filterArea = "제주도"
            }
            
            

            filter_confirm_btn -> {
                finish()
            }

        }
        lateinit var aim: String
        lateinit var area: String
        lateinit var position: String
        lateinit var department: String
    }
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_search_filter)

            filter_aimall_btn.setOnClickListener(this)
            filter_startup_btn.setOnClickListener(this)
            filter_contest_btn.setOnClickListener(this)
            filter_study_btn.setOnClickListener(this)
            filter_side_btn.setOnClickListener(this)
            filter_creative_btn.setOnClickListener(this)
            filter_aimetc_btn.setOnClickListener(this)

            filter_departmentall_btn.setOnClickListener(this)
            filter_chain_btn.setOnClickListener(this)
            filter_iot_btn.setOnClickListener(this)
            filter_ai_btn.setOnClickListener(this)
            filter_design_btn.setOnClickListener(this)
            filter_departstartup_btn.setOnClickListener(this)
            filter_departmentetc_btn.setOnClickListener(this)


            filter_positionall_btn.setOnClickListener(this)
            filter_pm_btn.setOnClickListener(this)
            filter_planner_btn.setOnClickListener(this)
            filter_designer_btn.setOnClickListener(this)
            filter_developer_btn.setOnClickListener(this)
            filter_positionetc_btn.setOnClickListener(this)

            filter_areaall_btn.setOnClickListener(this)
            filter_seoul_btn.setOnClickListener(this)
            filter_gyeonggi_btn.setOnClickListener(this)
            filter_incheon_btn.setOnClickListener(this)
            filter_gangwon_btn.setOnClickListener(this)
            filter_chungcheong_btn.setOnClickListener(this)
            filter_jeolla_btn.setOnClickListener(this)
            filter_gyeongsang_btn.setOnClickListener(this)
            filter_jeju_btn.setOnClickListener(this)

            filter_confirm_btn.setOnClickListener {
                val intent = Intent()
                intent.putExtra("filterAim", filterAim)
                intent.putExtra("filterDepartment", filterDepartment)
                intent.putExtra("filterPosition", filterPosition)
                intent.putExtra("filterArea", filterArea)

                setResult(27, intent)
                finish()



            }
        }



}

