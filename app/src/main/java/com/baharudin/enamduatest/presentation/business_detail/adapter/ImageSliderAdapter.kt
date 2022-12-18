package com.baharudin.enamduatest.presentation.business_detail.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.baharudin.enamduatest.R
import com.baharudin.enamduatest.databinding.ItemSliderBinding
import com.baharudin.enamduatest.domain.model.business_detail.BusinesssDetailModel
import com.bumptech.glide.Glide

class ImageSliderAdapter(
    private val context : Context,
    private val imageList : List<String?>
) : PagerAdapter() {

    override fun getCount(): Int {
        return imageList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view: View =  (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
            R.layout.item_slider, null)
        val ivImages = view.findViewById<ImageView>(R.id.iv_slider)

        imageList[position].let {
            Glide.with(context)
                .load(it)
                .into(ivImages);
        }

        val viewPager = container as ViewPager
        viewPager.addView(view, 0)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val viewPager = container as ViewPager
        val view = `object` as View
        viewPager.removeView(view)
    }

}