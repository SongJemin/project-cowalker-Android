package com.jemcom.cowalker.Jemin.Fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button


import com.jemcom.cowalker.Jemin.Activity.SearchFilterActivity
import com.jemcom.cowalker.R

import java.util.ArrayList

// default이므로 서버이용시 지울꺼임

class SearchFragment : Fragment() {
    private val mRecyclerView: RecyclerView? = null
    private val mLayoutManager: RecyclerView.LayoutManager? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_search, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //initDataSet();

    }


}// Required empty public constructor