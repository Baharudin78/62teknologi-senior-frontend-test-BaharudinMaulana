package com.baharudin.enamduatest.presentation.business_detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baharudin.enamduatest.core.util.Resource
import com.baharudin.enamduatest.domain.model.business_detail.BusinesssDetailModel
import com.baharudin.enamduatest.domain.model.review.ReviewModel
import com.baharudin.enamduatest.domain.use_case.BusinessUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BusinessDetailViewModel @Inject constructor(
    private val businessUseCase: BusinessUseCase
) : ViewModel(){

    private val _state = MutableStateFlow<DetailBusinessState>(DetailBusinessState.Init)
    val state : StateFlow<DetailBusinessState> get() = _state

    private val _business = MutableStateFlow<BusinesssDetailModel?>(null)
    val business : StateFlow<BusinesssDetailModel?> get() = _business

    private val _review = MutableStateFlow<List<ReviewModel>>(mutableListOf())
    val review : StateFlow<List<ReviewModel>> get() = _review

    private fun setLoading(){
        _state.value = DetailBusinessState.IsLoading(true)
    }

    private fun hideLoading(){
        _state.value = DetailBusinessState.IsLoading(false)
    }

    private fun showToast(message: String){
        _state.value = DetailBusinessState.ShowToast(message)
    }

    fun getBusinessById(id : String) {
        viewModelScope.launch {
            businessUseCase.getBusinessById.invoke(id)
                .onStart {
                    setLoading()
                }
                .catch { exception ->
                    hideLoading()
                    showToast(exception.message.orEmpty())
                }
                .collect{ result ->
                    hideLoading()
                    when(result){
                        is Resource.Success -> {
                            _business.value = result.data
                        }
                        is Resource.Error -> {
                            showToast(result.message.orEmpty())
                        }
                        else -> {}
                    }
                }
        }
    }

    fun getReview(id : String) {
        viewModelScope.launch {
            businessUseCase.getBusinessReview.invoke(id)
                .onStart {
                    setLoading()
                }
                .catch { exception->
                    hideLoading()
                    showToast(exception.message.orEmpty())
                }
                .collect{ result ->
                    hideLoading()
                    when(result) {
                        is Resource.Success -> {
                            _review.value = result.data!!
                        }
                        is Resource.Error -> {
                            showToast(result.message.orEmpty())
                        }
                        else -> {}
                    }
                }
        }
    }

}
sealed class DetailBusinessState {
    object Init : DetailBusinessState()
    data class IsLoading(val isLoading: Boolean) : DetailBusinessState()
    data class ShowToast(val message : String) : DetailBusinessState()
}