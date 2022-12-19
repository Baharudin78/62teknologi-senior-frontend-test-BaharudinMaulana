package com.baharudin.enamduatest.presentation.business_search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baharudin.enamduatest.core.util.Resource
import com.baharudin.enamduatest.domain.model.business.BusinessesModel
import com.baharudin.enamduatest.domain.use_case.BusinessUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BusinessSearchViewModel @Inject constructor(
    private val businessUseCase: BusinessUseCase
) : ViewModel(){

    private val _state = MutableStateFlow<BusinessSearchViewState>(BusinessSearchViewState.Init)
    val state : StateFlow<BusinessSearchViewState> get() = _state

    private val _searchItem = MutableStateFlow<List<BusinessesModel>>(mutableListOf())
    val searchItem : StateFlow<List<BusinessesModel>> get() = _searchItem

    private fun setLoading(){
        _state.value = BusinessSearchViewState.IsLoading(true)
    }

    private fun hideLoading(){
        _state.value = BusinessSearchViewState.IsLoading(false)
    }

    private fun showToast(message : String) {
        _state.value = BusinessSearchViewState.ShowToast(message)
    }

    fun getSearchResult(query : String) {
        viewModelScope.launch {
            businessUseCase.getBusinessBySearchString.invoke(query)
                .onStart {
                    setLoading()
                }
                .catch { exception ->
                    hideLoading()
                    showToast(exception.message.orEmpty())
                }
                .collect{result ->
                    when(result){
                        is Resource.Success -> {
                            hideLoading()
                            _searchItem.value = result.data ?: emptyList()
                        }
                        is Resource.Error -> {
                            hideLoading()
                            showToast(result.message.orEmpty())
                        }
                        else -> {

                        }
                    }
                }
        }
    }
}

sealed class BusinessSearchViewState{
    object Init : BusinessSearchViewState()
    data class IsLoading(val isLoading : Boolean) : BusinessSearchViewState()
    data class ShowToast(val message : String) : BusinessSearchViewState()
}