package com.geswipe.app.data.source

import com.geswipe.app.data.model.AddProductResponse
import com.geswipe.app.data.model.ApiResponse
import com.geswipe.app.data.model.ProductItem
import com.geswipe.app.data.source.networking.ApiService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import javax.inject.Inject

class ProductDataSourceImpl @Inject constructor(val service: ApiService) : ProductDataSource {
    override suspend fun fetchProducts(): ApiResponse<List<ProductItem>> {
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

    override suspend fun create(item: ProductItem): ApiResponse<AddProductResponse> {
        return try {
            val field1RequestBody = item.productName!!.toRequestBody("text/plain".toMediaTypeOrNull())
            val f1 = MultipartBody.Part.createFormData("product_name", null, field1RequestBody)

            val field2RequestBody = item.productType!!.toRequestBody("text/plain".toMediaTypeOrNull())
            val f2 = MultipartBody.Part.createFormData("product_type", null, field2RequestBody)

            val field3RequestBody = item.price!!.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            val f3 = MultipartBody.Part.createFormData("product_name", null, field3RequestBody)

            val field4RequestBody = item.tax!!.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            val f4 = MultipartBody.Part.createFormData("product_type", null, field4RequestBody)


            val response = service.create(f1, f2, f3, f4)
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