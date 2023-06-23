package com.geswipe.app.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geswipe.app.data.model.ApiResponse
import com.geswipe.app.data.model.ProductItem
import com.geswipe.app.data.source.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AddProdViewModel @Inject constructor(private val repo: ProductRepository) : ViewModel() {

    private var _addProductResponse = MutableLiveData<Boolean>()
    val addProductResponse: LiveData<Boolean> = _addProductResponse

    private var _validation = MutableLiveData<String>()
    val validation: LiveData<String> = _validation

    fun addProduct(
        name: String?,
        type: String?,
        price: String?,
        tax: String?,
        image: String? = ""
    ) {
        viewModelScope.launch {
            var validation = true
            withContext(Dispatchers.Default) {
                validation = validate(name, type, price, tax)
            }
            if (!validation) {
                return@launch
            }
            val item = ProductItem(image, type, price?.toFloat(), tax?.toFloat(), name)
            val response = repo.create(item)
            if (response is ApiResponse.Success) {
                _addProductResponse.postValue(true)
            } else {
                _addProductResponse.postValue(false)
            }
        }
    }

    private fun validate(name: String?, type: String?, price: String?, tax: String?): Boolean {
        return if (name.isNullOrBlank()) {
            _validation.postValue("product name can't be empty")
            false
        } else if (type.isNullOrBlank()) {
            _validation.postValue("product type can't be empty")
            false
        } else if (price.isNullOrBlank()) {
            _validation.postValue("Please enter price")
            false
        } else if (tax.isNullOrBlank()) {
            _validation.postValue("Please enter price")
            false
        } else {
            val check1 = price.toFloatOrNull()
            val check2 = tax.toFloatOrNull()
            if (check1 == null || check2 == null) {
                _validation.postValue("Please enter valid price/tax")
                false
            } else {
                true
            }
        }
    }
}