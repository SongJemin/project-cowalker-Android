package com.jemcom.cowalker.Nuri.Fragment

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jemcom.cowalker.R
import kotlinx.android.synthetic.main.fragment_notice.*
import kotlinx.android.synthetic.main.fragment_notice.view.*

class NoticeTab : Fragment(), View.OnClickListener {
    override fun onClick(v: View?) {
        when(v)
        {
            notice_message_tv -> {
                notice_news_tv.isSelected = false
                notice_message_tv.isSelected = true
                notice_news_tv.setTextColor(Color.parseColor("#c5c5c5"))
                notice_message_tv.setTextColor(Color.BLACK)
                replaceFragment(MessageTab())
            }
            notice_news_tv -> {
                notice_news_tv.isSelected = true
                notice_message_tv.isSelected = false
                notice_message_tv.setTextColor(Color.parseColor("#c5c5c5"))
                notice_news_tv.setTextColor(Color.BLACK)
                replaceFragment(NewsTab())
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_notice,container,false)

        v.notice_news_tv.setOnClickListener(this)
        v.notice_message_tv.setOnClickListener(this)

        v.notice_news_tv.isSelected = true
        v.notice_message_tv.setTextColor(Color.parseColor("#c5c5c5"))
        v.notice_news_tv.setTextColor(Color.BLACK)

        addFragment(NewsTab())

        return v
    }

    fun addFragment(fragment : Fragment){
        val fm = childFragmentManager
        val transaction = fm.beginTransaction()
        transaction.add(R.id.notice_fragment, fragment)
        transaction.commit()
    }

    fun replaceFragment(fragment: Fragment)
    {
        val fm = childFragmentManager
        val transaction = fm.beginTransaction()
        transaction.replace(R.id.notice_fragment, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}