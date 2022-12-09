package com.baharudin.enamduatest.presentation.business.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.baharudin.enamduatest.databinding.ItemBusinessBinding
import com.baharudin.enamduatest.domain.model.business.BusinessesModel

class BusinessAdapter(
    private val business : MutableList<BusinessesModel>
) : RecyclerView.Adapter<BusinessAdapter.BusinessViewHolder>(){

    interface OnItemClickListener{
        fun onClick(businessesModel: BusinessesModel)
    }

    fun setItemClickListener(clickInterface : OnItemClickListener) {
        onClickListener = clickInterface
    }

    private var onClickListener : OnItemClickListener? = null

    fun updateList(mBusiness : List<BusinessesModel>){
        business.clear()
        business.addAll(mBusiness)
        notifyDataSetChanged()
    }

    inner class BusinessViewHolder(
        private val binding : ItemBusinessBinding
    ) : RecyclerView.ViewHolder(binding.root){

        fun bindItem(businessItem : BusinessesModel){
            with(binding){
                ivPhoto.load(businessItem.imageUrl)
                tvName.text = businessItem.name
                tvAlias.text = businessItem.alias
                tvPrice.text = businessItem.price

                root.setOnClickListener {
                    onClickListener?.onClick(businessItem)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusinessViewHolder {
        val inflater = ItemBusinessBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return BusinessViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: BusinessViewHolder, position: Int) =
        holder.bindItem(business[position])

    override fun getItemCount(): Int = business.size
}