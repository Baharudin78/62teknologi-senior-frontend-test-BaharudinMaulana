package com.baharudin.enamduatest.presentation.business.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baharudin.enamduatest.core.util.Resource
import com.baharudin.enamduatest.domain.model.business.BusinessesModel
import com.baharudin.enamduatest.domain.use_case.BusinessUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BusinessViewModel @Inject constructor(
    private val businessUseCase: BusinessUseCase
) : ViewModel(){

    private val _state = MutableStateFlow<BusinessViewState>(BusinessViewState.Init)
    val mState: StateFlow<BusinessViewState> get() = _state

    private val _business = MutableStateFlow<List<BusinessesModel>>(mutableListOf())
    val mBusiness: StateFlow<List<BusinessesModel>> get() = _business

    private val _isRefresh = MutableStateFlow(false)
    val isRefresh: StateFlow<Boolean> = _isRefresh


    private fun setLoading(){
        _state.value = BusinessViewState.IsLoading(true)
    }

    private fun hideLoading(){
        _state.value = BusinessViewState.IsLoading(false)
    }

    private fun showToast(message: String){
        _state.value = BusinessViewState.ShowToast(message)
    }

    init {
        getBusiness()
    }
    fun getBusiness(){
        viewModelScope.launch {
            businessUseCase.getBusiness.invoke()
                .onStart {
                    setLoading()
                }
                .catch { exception ->
                    hideLoading()
                    showToast(exception.message.orEmpty())
                }
                .collect{result ->
                    hideLoading()
                    when(result){
                        is Resource.Success ->{
                            _business.value = result.data ?: emptyList()
                        }
                        is Resource.Error -> {
                            showToast(result.message.orEmpty())
                        }
                        else -> {

                        }
                    }
                }
        }
    }

    fun refresh(){
        viewModelScope.launch {
            _isRefresh.emit(true)
            getBusiness()
            _isRefresh.emit(false)
        }
    }

    sealed class BusinessViewState {
        object Init : BusinessViewState()
        data class IsLoading(val isLoading: Boolean) : BusinessViewState()
        data class ShowToast(val message : String) : BusinessViewState()
    }
}