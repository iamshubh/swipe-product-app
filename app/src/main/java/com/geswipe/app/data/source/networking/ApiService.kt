package com.geswipe.app.data.source.networking

import com.geswipe.app.data.model.AddProductResponse
import com.geswipe.app.data.model.ProductItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("/api/public/get")
    suspend fun loadAll(): Response<List<ProductItem>>

    @POST("/api/public/add")
    suspend fun create(item: ProductItem): Response<AddProductResponse>
}