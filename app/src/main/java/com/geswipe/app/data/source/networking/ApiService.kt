package com.geswipe.app.data.source.networking

import com.geswipe.app.data.model.AddProductResponse
import com.geswipe.app.data.model.ProductItem
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @GET("/api/public/get")
    suspend fun loadAll(): Response<List<ProductItem>>

    @Multipart
    @POST("/api/public/add")
    suspend fun create(
        @Part productName: MultipartBody.Part,
        @Part productType: MultipartBody.Part,
        @Part price: MultipartBody.Part,
        @Part tax: MultipartBody.Part
    ): Response<AddProductResponse>
}