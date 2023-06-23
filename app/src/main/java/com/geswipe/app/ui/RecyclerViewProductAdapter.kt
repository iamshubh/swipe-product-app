package com.geswipe.app.ui

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.geswipe.app.R
import com.geswipe.app.data.model.ProductResponseItem
import com.geswipe.app.databinding.ItemSingleProductBinding


class RecyclerViewProductAdapter(private val items: List<ProductResponseItem>) :
    RecyclerView.Adapter<RecyclerViewProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(val binding: ItemSingleProductBinding) :
        ViewHolder(binding.root) {
        fun bind(data: ProductResponseItem) {
            binding.apply {
                tvName.text = data.productName
                tvCategory.text = data.productType
                tvPrice.text = ("Price: ₹${String.format("%.2f", data.price?: 0.0)}")
                tvTax.text = ("Tax: ₹${String.format("%.2f", data.tax ?: 0.0)}")

                Glide.with(itemView.context).load(data.image)
                    .placeholder(R.drawable.dummy)
                    .error(R.drawable.dummy)
                    .transform(
                        CenterInside(),
                        RoundedCorners(24)
                    ).into(image)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding =
            ItemSingleProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(items[position])
    }
}