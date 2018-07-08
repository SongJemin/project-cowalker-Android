package com.jemcom.cowalker.Hyunmin.Adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.jemcom.cowalker.R

class ImageAdapter internal constructor(internal var context: Context) : PagerAdapter() {
    internal var galImage: Bitmap? = null
    internal var options: BitmapFactory.Options
    private val galImages = intArrayOf(R.drawable.photo1, R.drawable.photo2, R.drawable.photo3, R.drawable.photo4, R.drawable.photo5)

    init {
        options = BitmapFactory.Options()
    }

    override fun getCount(): Int {
        return galImages.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as ImageView
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageView = ImageView(context)
        val padding = context.resources.getDimensionPixelSize(R.dimen.padding_medium)
        imageView.setPadding(padding, padding, padding, padding)
        imageView.scaleType = ImageView.ScaleType.CENTER_INSIDE

        options.inSampleSize = 4
        galImage = BitmapFactory.decodeResource(context.resources, galImages[position], options)

        imageView.setImageBitmap(galImage)
        (container as ViewPager).addView(imageView, 0)
        return imageView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        (container as ViewPager).removeView(`object` as ImageView)
    }
}