package com.baharudin.enamduatest.presentation.business_search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.baharudin.enamduatest.R
import com.baharudin.enamduatest.core.util.Constant.SEARCH_TIME_DELAY
import com.baharudin.enamduatest.core.util.showToast
import com.baharudin.enamduatest.databinding.ActivitySearchBinding
import com.baharudin.enamduatest.domain.model.business.BusinessesModel
import com.baharudin.enamduatest.presentation.business.adapter.BusinessAdapter
import com.baharudin.enamduatest.presentation.business_detail.BusinessDetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySearchBinding
    private val viewModel : BusinessSearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecycleview()
        setupObserver()

        var job : Job? = null
        binding.etSearch.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_TIME_DELAY)
                editable?.let {
                    if (editable.toString().isNotEmpty()){
                        viewModel.getSearchResult(editable.toString())
                    }
                }
            }
        }

    }

    private fun setupRecycleview(){
        val searchAdapter = BusinessAdapter(mutableListOf())
        searchAdapter.setItemClickListener(object : BusinessAdapter.OnItemClickListener{
            override fun onClick(businessesModel: BusinessesModel) {
                val intent = Intent(this@SearchActivity, BusinessDetailActivity::class.java)
                    .putExtra(BusinessDetailActivity.BUSINESS_ID, businessesModel.id)
                startActivity(intent)
            }
        })
        binding.rvBusiness.apply {
            adapter = searchAdapter
            layoutManager = LinearLayoutManager(this@SearchActivity)
        }
    }


    private fun setupObserver(){
        observeState()
        observeResult()
    }

    private fun observeState(){
        viewModel.state
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state ->
                handleState(state)
            }
            .launchIn(lifecycleScope)
    }

    private fun observeResult(){
        viewModel.searchItem
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { result ->
                handleResult(result)
            }
            .launchIn(lifecycleScope)
    }

    private fun handleResult(business : List<BusinessesModel>){
        binding.rvBusiness.adapter?.let {
            if (it is BusinessAdapter ){
                it.updateList(business)
            }
        }
    }

    private fun handleState(state : BusinessSearchViewState) {
        when(state){
            is BusinessSearchViewState.IsLoading -> handleLoading(state.isLoading)
            is BusinessSearchViewState.ShowToast -> this.showToast(state.message)
            is BusinessSearchViewState.Init -> Unit
            else -> {}
        }
    }
    private fun handleLoading(isLoading : Boolean){
        if (isLoading){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }
}