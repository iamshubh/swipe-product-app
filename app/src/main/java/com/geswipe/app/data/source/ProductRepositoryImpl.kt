package com.geswipe.app.data.source

import com.geswipe.app.data.model.AddProductResponse
import com.geswipe.app.data.model.ApiResponse
import com.geswipe.app.data.model.ProductResponseItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(val dataSource: ProductDataSource) :
    ProductRepository {
    override suspend fun getAllProducts(): ApiResponse<List<ProductResponseItem>> =
        withContext(Dispatchers.IO) {
            dataSource.fetchProducts()
        }

    override suspend fun create(item: ProductResponseItem): ApiResponse<AddProductResponse> =
        withContext(Dispatchers.IO) {
            dataSource.create(item)
        }


}