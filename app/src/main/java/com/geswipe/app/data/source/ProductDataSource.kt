package com.geswipe.app.data.source

import com.geswipe.app.data.model.AddProductResponse
import com.geswipe.app.data.model.ApiResponse
import com.geswipe.app.data.model.ProductResponseItem

interface ProductDataSource {
    suspend fun fetchProducts(): ApiResponse<List<ProductResponseItem>>

    suspend fun create(item: ProductResponseItem): ApiResponse<AddProductResponse>
}