package com.codingchallenge.walmartlabs.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.codingchallenge.walmartlabs.R
import com.codingchallenge.walmartlabs.databinding.ItemProductBinding
import com.codingchallenge.walmartlabs.databinding.ItemSeparatorBinding
import com.codingchallenge.walmartlabs.model.ProductModel
import com.codingchallenge.walmartlabs.repository.ProductInterface
import com.squareup.picasso.Picasso

/**
 * @Author Dushane Coram
 * @Date 08, November, 2022
 * @Project WalmartLabs
 * @Copyright (c). All rights reserved.
 * @Description
 */
class ProductListAdapter : PagingDataAdapter<ProductModel,
        RecyclerView.ViewHolder>(ProductDiffCallBack) {

    companion object {
        private const val ITEM_PRODUCT = 0
        private const val ITEM_SEPARATOR = 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position) is ProductModel.ProductData)
            ITEM_PRODUCT else ITEM_SEPARATOR
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : RecyclerView.ViewHolder {
        return if (viewType == ITEM_PRODUCT) ProductViewHolder(
            ItemProductBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
        else ProductSeparatorViewHolder(
            ItemSeparatorBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        if (item is ProductModel.ProductData)
            (holder as ProductViewHolder).bind(item)
    }

    object ProductDiffCallBack : DiffUtil.ItemCallback<ProductModel>() {
        override fun areItemsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
            val isSameProductData = oldItem is ProductModel.ProductData
                    && newItem is ProductModel.ProductData
                    && oldItem.productId == newItem.productId
            val isSameSeparator = oldItem is ProductModel.ProductSeparator
                    && newItem is ProductModel.ProductSeparator
                    && oldItem.tag == newItem.tag

            return isSameProductData || isSameSeparator
        }
        override fun areContentsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
            return oldItem == newItem
        }
    }

    inner class ProductViewHolder(
        private val binding: ItemProductBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProductModel.ProductData) = with(binding) {
            tvProductName.text = item.productName
            tvPrice.text = item.price
            tvReviewCount.text = item.reviewCount.toString()
            rbReviewRating.rating = item.reviewRating.toFloat()
            if (item.inStock) {
                tvAddToCart.visibility = View.VISIBLE
                tvNotInStock.visibility = View.GONE
            } else {
                tvAddToCart.visibility = View.GONE
                tvNotInStock.visibility = View.VISIBLE
            }
            Picasso.with(ivProductImage.context)
                .load("${ProductInterface.BASE_URL}${item.productImage.substring(1, item.productImage.length)}")
                .error(R.drawable.ic_nodpi_walmart_black)
                .centerCrop()
                .fit()
                .into(ivProductImage)
        }
    }

    inner class ProductSeparatorViewHolder(binding: ItemSeparatorBinding) :
        RecyclerView.ViewHolder(binding.root)

}
