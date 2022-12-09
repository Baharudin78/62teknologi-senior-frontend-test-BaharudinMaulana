package com.baharudin.enamduatest.presentation.business_detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.baharudin.enamduatest.core.util.showToast
import com.baharudin.enamduatest.databinding.ActivityBusinessDetailBinding
import com.baharudin.enamduatest.domain.model.business_detail.BusinesssDetailModel
import com.baharudin.enamduatest.presentation.business_detail.adapter.ImageSliderAdapter
import com.baharudin.enamduatest.presentation.business_detail.viewmodel.BusinessDetailViewModel
import com.baharudin.enamduatest.presentation.business_detail.viewmodel.DetailBusinessState
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@AndroidEntryPoint
class BusinessDetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityBusinessDetailBinding
    private val viewModel : BusinessDetailViewModel by viewModels()
    private lateinit var adapterImage : ImageSliderAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBusinessDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAdapterImage()
        observer()
        getDataBusiness()
    }

    private fun observer(){
        observeState()
        observeDetail()
    }

    private fun getDataBusiness() {
        val id = intent.getStringExtra(BUSINESS_ID) ?: ""
        viewModel.getBusinessById(id)
    }

    private fun setupAdapterImage(){
        adapterImage = ImageSliderAdapter(mutableListOf())
        binding.viewPager.adapter = adapterImage

    }


    private fun observeState(){
        viewModel.state
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state ->
                handleState(state)
            }
            .launchIn(lifecycleScope)
    }

    private fun observeDetail(){
        viewModel.business
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { business ->
                handleProduct(business)
            }
            .launchIn(lifecycleScope)
    }

    private fun handleState(state: DetailBusinessState){
        when(state){
            is DetailBusinessState.Init -> Unit
            is DetailBusinessState.ShowToast -> this.showToast(state.message)
           // is DetailBusinessState.IsLoading -> handleLoading(state.isLoading)
        }
    }

    private fun handleProduct(businessDetail : BusinesssDetailModel?){
        with(binding){
            tvAlias.text = businessDetail?.alias
            tvName.text = businessDetail?.name
            tvPhone.text = businessDetail?.phone
            tvRating.text = businessDetail?.rating.toString()

        }
    }

    companion object{
        const val BUSINESS_ID = "BUSINESS_ID"
    }
}