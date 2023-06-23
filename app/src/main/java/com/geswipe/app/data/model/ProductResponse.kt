package com.geswipe.app.data.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.geswipe.app.Common.SOMETHING_WENT_WRONG
import com.google.gson.annotations.SerializedName

sealed class ApiResponse<out T : Any> {
    class Failure(val errorCode: Int, val msg: String = SOMETHING_WENT_WRONG) :
        ApiResponse<Nothing>()

    class Exception(val throwable: Throwable?) : ApiResponse<Nothing>()
    class Success<out T : Any>(val data: T) : ApiResponse<T>()
}

@Parcelize
data class ProductResponse(

    @field:SerializedName("ProductResponse")
    val items: List<ProductItem>
) : Parcelable

@Parcelize
data class ProductItem(

    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("product_type")
    val productType: String? = null,

    @field:SerializedName("price")
    val price: Float? = null,

    @field:SerializedName("tax")
    val tax: Float? = null,

    @field:SerializedName("product_name")
    val productName: String? = null
) : Parcelable
