package com.baharudin.enamduatest.presentation.business_detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.baharudin.enamduatest.databinding.ItemReviewBinding
import com.baharudin.enamduatest.domain.model.review.ReviewModel
import com.bumptech.glide.Glide

class ReviewAdapter(
    private val review : MutableList<ReviewModel>
) : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>(){

    fun updateList(mReview : List<ReviewModel>){
        review.clear()
        review.addAll(mReview)
        notifyDataSetChanged()
    }

    inner class ReviewViewHolder(
        private val binding : ItemReviewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindItem(reviewModel : ReviewModel){
            with(binding){
                Glide.with(binding.root.context)
                    .load(reviewModel.user.image_url)
                    .into(ivPoto)
                tvName.text = reviewModel.user.name
                tvTime.text = reviewModel.time_created
                tvText.text = reviewModel.text
                rating.rating = reviewModel.rating!!.toFloat()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val inflater = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReviewViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bindItem(review[position])
    }

    override fun getItemCount(): Int {
        return review.size
    }
}