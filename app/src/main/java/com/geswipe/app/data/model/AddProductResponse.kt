package com.geswipe.app.data.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class AddProductResponse(

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("product_id")
	val productId: Int? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("product_details")
	val productDetails: ProductDetails? = null
) : Parcelable

@Parcelize
data class ProductDetails(

	@field:SerializedName("name")
	val name: String? = null
) : Parcelable
