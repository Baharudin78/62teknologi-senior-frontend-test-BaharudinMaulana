package com.baharudin.enamduatest.presentation.business

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.baharudin.enamduatest.core.util.showToast
import com.baharudin.enamduatest.databinding.ActivityMainBinding
import com.baharudin.enamduatest.domain.model.FilterModel
import com.baharudin.enamduatest.domain.model.business.BusinessesModel
import com.baharudin.enamduatest.presentation.business.adapter.BusinessAdapter
import com.baharudin.enamduatest.presentation.business.adapter.FilterAdapter
import com.baharudin.enamduatest.presentation.business.viewmodel.BusinessViewModel
import com.baharudin.enamduatest.presentation.business.viewmodel.BusinessViewState
import com.baharudin.enamduatest.presentation.business_detail.BusinessDetailActivity
import com.baharudin.enamduatest.presentation.business_detail.adapter.ReviewAdapter
import com.baharudin.enamduatest.presentation.business_search.SearchActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private val viewModel : BusinessViewModel by viewModels()
    private var dataList = ArrayList<FilterModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecycleview()
        fetchBusiness()
        observer()
        setListener()
        setupAdapterFilter()
        setupListFilter()
    }

    private fun setListener(){
        binding.etSearch.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }
        binding.ivFilter.setOnClickListener {
            binding.containerBottomSheet.visibility = View.VISIBLE
            initBottomSheet()
        }
    }

    private fun setupRecycleview(){
        val businessAdapter = BusinessAdapter(mutableListOf())
        businessAdapter.setItemClickListener(object : BusinessAdapter.OnItemClickListener{
            override fun onClick(businessesModel: BusinessesModel) {
                val intent = Intent(this@MainActivity, BusinessDetailActivity::class.java)
                    .putExtra(BusinessDetailActivity.BUSINESS_ID, businessesModel.id)
                startActivity(intent)
            }
        })
        binding.rvBusiness.apply {
            adapter = businessAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun setupAdapterFilter(){
        val filterAdapter = FilterAdapter(dataList)
        filterAdapter.setItemClickListener(object : FilterAdapter.OnItemClickListener{
            override fun onClick(filterModel: FilterModel) {
                binding.btnFilter.setOnClickListener {
                    viewModel.getBusinessSortBy(filterModel.title)
                }
            }
        })
        binding.rvFilter.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = filterAdapter
        }
    }


    private fun initBottomSheet(){
            BottomSheetBehavior.from(binding.containerBottomSheet).apply {
                peekHeight = 185
                this.state = BottomSheetBehavior.STATE_EXPANDED
            }
    }

    private fun fetchBusiness(){
        viewModel.getBusiness()
    }

    private fun observer(){
        observeState()
        observeBusiness()
    }

    private fun observeState(){
        viewModel.mState
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state ->
                handleState(state)
            }
            .launchIn(lifecycleScope)
    }

    private fun observeBusiness(){
        viewModel.mBusiness
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { business ->
                handleBusiness(business)
            }
            .launchIn(lifecycleScope)
    }

    private fun handleBusiness(business : List<BusinessesModel>){
        binding.rvBusiness.adapter?.let {
            if (it is BusinessAdapter){
                it.updateList(business)
            }
        }
    }

    private fun handleState(state : BusinessViewState){
        when(state){
            is BusinessViewState.IsLoading -> handleLoading(state.isLoading)
            is BusinessViewState.ShowToast -> this.showToast(state.message)
            is BusinessViewState.Init -> Unit
            else ->{}
        }
    }

    private fun handleLoading(isLoading : Boolean){
        if (isLoading){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun setupListFilter(){
        dataList.add(
            FilterModel(
                "best_match"
            )
        )
        dataList.add(
            FilterModel(
                "rating"
            )
        )
        dataList.add(
            FilterModel(
                "review_count"
            )
        )
        dataList.add(
            FilterModel(
                "distance"
            )
        )
    }
}