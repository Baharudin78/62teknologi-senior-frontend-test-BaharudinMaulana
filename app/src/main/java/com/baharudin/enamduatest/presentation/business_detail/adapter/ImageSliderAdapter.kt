package com.baharudin.enamduatest.presentation.business_detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.baharudin.enamduatest.databinding.ItemSliderBinding
import com.baharudin.enamduatest.domain.model.business_detail.BusinesssDetailModel
import com.bumptech.glide.Glide

class ImageSliderAdapter(
    private val items : MutableList<BusinesssDetailModel>
) : RecyclerView.Adapter<ImageSliderAdapter.ImageViewHolder>() {

    inner class ImageViewHolder(itemView : ItemSliderBinding) : RecyclerView.ViewHolder(itemView.root){
        private val binding = itemView
        fun bindItem(data : BusinesssDetailModel){
            with(binding){
                Glide.with(itemView)
                    .load(data.photos)
                    .into(ivSlider)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val inflater = ItemSliderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bindItem(items[position])
    }

    override fun getItemCount(): Int = items.size
}