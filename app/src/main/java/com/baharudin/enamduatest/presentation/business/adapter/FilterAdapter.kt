package com.baharudin.enamduatest.presentation.business.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.baharudin.enamduatest.R
import com.baharudin.enamduatest.core.util.withDelay
import com.baharudin.enamduatest.databinding.ItemFilterBinding
import com.baharudin.enamduatest.domain.model.FilterModel
import com.baharudin.enamduatest.domain.model.business.BusinessesModel

class FilterAdapter(
    private val data : List<FilterModel>,
) : RecyclerView.Adapter<FilterAdapter.FilterViewHolder>() {

    private var selectedPosition = -1

    interface OnItemClickListener{
        fun onClick(filterModel: FilterModel)
    }

    fun setItemClickListener(clickInterface : OnItemClickListener) {
        onClickListener = clickInterface
    }

    private var onClickListener : OnItemClickListener? = null

    inner class FilterViewHolder(val binding : ItemFilterBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindItem(item : FilterModel, position: Int) {
            binding.tvName.text = item.title
            if (item.isSelected) {
                binding.card.background =
                    ContextCompat.getDrawable(
                        itemView.context,
                        R.drawable.bg_border
                    )
            }
            binding.root.setOnClickListener {
                onClickListener?.onClick(item)
                selectItem(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        val inflater = ItemFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilterViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        holder.bindItem(data[position], position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    private fun selectItem(position: Int) {
        if(position != selectedPosition) {
            if(selectedPosition > -1) {
                data[selectedPosition].isSelected = false
                notifyItemChanged(selectedPosition)
            }

            selectedPosition = position
            data[position].isSelected = true

            withDelay {
                notifyItemChanged(position)
            }
        }
    }
}