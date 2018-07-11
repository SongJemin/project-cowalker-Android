package com.jemcom.cowalker.Hyunmin.Adapter

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.RequestManager
import com.jemcom.cowalker.R
import com.bumptech.glide.Glide



class ImageAdapter internal constructor(internal var context: Context, var requestManager: RequestManager, var data : ArrayList<String>) : PagerAdapter() {


    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = inflater.inflate(R.layout.profile_more_item, null)
        val image_container = v.findViewById(R.id.image_container) as ImageView

        Glide.with(context).load(data[position]).into(image_container)
        container.addView(v)

        return v
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container!!.removeView(`object` as View?)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return data.size
    }
}