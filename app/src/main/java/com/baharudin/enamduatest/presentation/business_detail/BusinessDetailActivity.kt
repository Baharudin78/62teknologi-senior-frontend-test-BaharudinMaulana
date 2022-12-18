package com.baharudin.enamduatest.presentation.business_detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.baharudin.enamduatest.R
import com.baharudin.enamduatest.core.util.showToast
import com.baharudin.enamduatest.databinding.ActivityBusinessDetailBinding
import com.baharudin.enamduatest.domain.model.business_detail.BusinesssDetailModel
import com.baharudin.enamduatest.domain.model.review.ReviewModel
import com.baharudin.enamduatest.presentation.business_detail.adapter.ImageSliderAdapter
import com.baharudin.enamduatest.presentation.business_detail.adapter.ReviewAdapter
import com.baharudin.enamduatest.presentation.business_detail.viewmodel.BusinessDetailViewModel
import com.baharudin.enamduatest.presentation.business_detail.viewmodel.DetailBusinessState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import me.relex.circleindicator.CircleIndicator
import java.util.*


@AndroidEntryPoint
class BusinessDetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityBusinessDetailBinding
    private val viewModel : BusinessDetailViewModel by viewModels()
    private lateinit var adapterImage : ImageSliderAdapter
    private var imagesModel:BusinesssDetailModel? = null
    lateinit var viewPagerAdapter: ImageSliderAdapter
    lateinit var circleIndicator: CircleIndicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBusinessDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAdapterReview()
        //setupAdapterImage()
        observer()
        getDataBusiness()

    }

    private fun observer(){
        observeState()
        observeDetail()
        observeReview()
    }


    private fun setupAdapterReview(){
        val reviewAdapter = ReviewAdapter(mutableListOf())
        binding.rvReview.apply {
            adapter = reviewAdapter
            layoutManager = LinearLayoutManager(this@BusinessDetailActivity)
        }
    }

    private fun getDataBusiness() {
        val id = intent.getStringExtra(BUSINESS_ID) ?: ""
        viewModel.getBusinessById(id)
        viewModel.getReview(id)
    }

//    private fun setupAdapterImage(){
//        Log.d("PHOTOSSS", "1")
//        imagesModel?.photos?.let {
//            Log.d("PHOTOSSS", "$it")
//            viewPagerAdapter = ImageSliderAdapter(this, it)
//            binding.viewPager.adapter = viewPagerAdapter
//            indicator = this.findViewById(R.id.indicator) as CircleIndicator
//            indicator.setViewPager(binding.viewPager)
//        }
//    }


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

    private fun observeReview(){
        viewModel.review
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { review ->
                handleReview(review)
            }
            .launchIn(lifecycleScope)
    }

    private fun handleReview(review : List<ReviewModel>){
        binding.rvReview.adapter?.let {
            if (it is ReviewAdapter) {
                it.updateList(review)
            }
        }
    }

    private fun handleState(state: DetailBusinessState){
        when(state){
            is DetailBusinessState.Init -> Unit
            is DetailBusinessState.ShowToast -> this.showToast(state.message)
           // is DetailBusinessState.IsLoading -> handleLoading(state.isLoading)
            else -> {}
        }
    }

    private fun handleProduct(businessDetail : BusinesssDetailModel?){
        with(binding){
            tvAlias.text = businessDetail?.alias ?: "Empty"
            tvName.text = businessDetail?.name ?: "Empty"
            tvPhone.text = businessDetail?.phone ?: "021+++"
            tvRating.text = businessDetail?.rating.toString() ?: "0"
            businessDetail?.photos.let {
                viewPagerAdapter = ImageSliderAdapter(this@BusinessDetailActivity, it ?: emptyList())
                binding.viewPager.adapter = viewPagerAdapter
                circleIndicator = this@BusinessDetailActivity.findViewById(R.id.indicator) as CircleIndicator
                indicator.setViewPager(binding.viewPager)
            }
            btMaps.setOnClickListener {
                val uri: String =
                    java.lang.String.format(Locale.ENGLISH, "geo:%f,%f", businessDetail?.coordinates?.latitude, businessDetail?.coordinates?.longitude)
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                startActivity(intent)
            }
        }
    }

    companion object{
        const val BUSINESS_ID = "BUSINESS_ID"
    }
}