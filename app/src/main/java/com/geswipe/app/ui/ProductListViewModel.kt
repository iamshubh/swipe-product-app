package com.geswipe.app.ui

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geswipe.app.data.model.ApiResponse
import com.geswipe.app.data.model.ProductItem
import com.geswipe.app.data.source.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Handles business logic for product list fragment
 */
@HiltViewModel
class ProductListViewModel @Inject constructor(private val repository: ProductRepository) :
    ViewModel() {

    sealed class ProductsUiState {
        object Loading : ProductsUiState()
        data class Success(val data: List<ProductItem>) : ProductsUiState()
        object Error : ProductsUiState()
    }

    private var _uiResponse = MutableLiveData<ProductsUiState>()
    val uiResponse: LiveData<ProductsUiState> = _uiResponse

    private var _filterResponse = MutableLiveData<List<ProductItem>>()
    val filterResponse : LiveData<List<ProductItem>> = _filterResponse

    fun loadProducts() {
        viewModelScope.launch {
            _uiResponse.postValue(ProductsUiState.Loading)
            val response = repository.getAllProducts()
            handleResponse(response)
        }
    }

    private fun handleResponse(response: ApiResponse<List<ProductItem>>) {
        when (response) {
            is ApiResponse.Success -> {
                _uiResponse.postValue(ProductsUiState.Success(response.data))
            }

            else -> {
                _uiResponse.postValue(ProductsUiState.Error)
            }
        }
    }

    fun searchProducts(query: String) {
        if (uiResponse.value is ProductsUiState.Success) {
            val items = (uiResponse.value as ProductsUiState.Success).data
            if (query.isBlank()) {
                _filterResponse.postValue(items)
            } else {
                val filteredItem = items.filter {
                    it.productName?.contains(query) == true ||
                            it.productType?.contains(query) == true ||
                            it.price?.toString()?.contains(query) == true ||
                            it.tax?.toString()?.contains(query) == true
                }
                _filterResponse.postValue(filteredItem)
            }
        }
    }
}