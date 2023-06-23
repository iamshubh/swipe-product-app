package com.geswipe.app.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geswipe.app.data.model.ApiResponse
import com.geswipe.app.data.model.ProductResponse
import com.geswipe.app.data.model.ProductResponseItem
import com.geswipe.app.data.source.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(private val repository: ProductRepository) :
    ViewModel() {

    sealed class ProductsUiState {
        object Loading : ProductsUiState()
        data class Success(val data: List<ProductResponseItem>) : ProductsUiState()
        object Error : ProductsUiState()
    }

    private var _uiResponse = MutableLiveData<ProductsUiState>()
    val uiResponse: LiveData<ProductsUiState> = _uiResponse

    fun loadProducts() {
        viewModelScope.launch {
            _uiResponse.postValue(ProductsUiState.Loading)
            val response = repository.getAllProducts()
            handleResponse(response)
        }
    }

    private fun handleResponse(response: ApiResponse<List<ProductResponseItem>>) {
        when (response) {
            is ApiResponse.Success -> {
                _uiResponse.postValue(ProductsUiState.Success(response.data))
            }
            else -> {
                _uiResponse.postValue(ProductsUiState.Error)
            }
        }
    }
}