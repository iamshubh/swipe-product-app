package com.geswipe.app.data.source

import com.geswipe.app.data.model.AddProductResponse
import com.geswipe.app.data.model.ApiResponse
import com.geswipe.app.data.model.ProductItem

interface ProductRepository {
    suspend fun getAllProducts(): ApiResponse<List<ProductItem>>

    suspend fun create(item: ProductItem): ApiResponse<AddProductResponse>
}