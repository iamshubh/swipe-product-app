package com.geswipe.app.data.source

import com.geswipe.app.data.model.AddProductResponse
import com.geswipe.app.data.model.ApiResponse
import com.geswipe.app.data.model.ProductResponseItem
import com.geswipe.app.data.source.networking.ApiService
import retrofit2.HttpException
import javax.inject.Inject

class ProductDataSourceImpl @Inject constructor(val service: ApiService) : ProductDataSource {
    override suspend fun fetchProducts(): ApiResponse<List<ProductResponseItem>> {
        return try {
            val response = service.loadAll()
            if (response.isSuccessful && response.body() != null) {
                ApiResponse.Success(response.body()!!)
            } else {
                ApiResponse.Failure(response.code(), response.message())
            }
        } catch (e: HttpException) {
            e.printStackTrace()
            ApiResponse.Failure(e.code(), e.message())
        } catch (e: Exception) {
            e.printStackTrace()
            ApiResponse.Exception(e)

        }
    }

    override suspend fun create(item: ProductResponseItem): ApiResponse<AddProductResponse> {
        return try {
            val response = service.create(item)
            if (response.isSuccessful && response.body() != null) {
                ApiResponse.Success(response.body()!!)
            } else {
                ApiResponse.Failure(response.code(), response.message())
            }
        } catch (e: HttpException) {
            ApiResponse.Failure(e.code(), e.message())
        } catch (e: Exception) {
            ApiResponse.Exception(e)
        }
    }
}