package com.codingchallenge.walmartlabs.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.codingchallenge.walmartlabs.databinding.ItemLoadingStateBinding

/**
 * @Author Dushane Coram
 * @Date 08, November, 2022
 * @Project WalmartLabs
 * @Copyright (c). All rights reserved.
 * @Description
 */
class ProductsLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<ProductsLoadStateAdapter.ProductsLoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) = ProductsLoadStateViewHolder(
        ItemLoadingStateBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        retry
    )

    override fun onBindViewHolder(holder: ProductsLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    inner class ProductsLoadStateViewHolder(
        private val binding: ItemLoadingStateBinding,
        private val retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                binding.tvInfo.text = loadState.error.localizedMessage
            }
            binding.pbLoad.visibility = if (loadState is LoadState.Loading) View.VISIBLE else View.GONE
            binding.tvRetry.visibility = if (loadState is LoadState.Error) View.VISIBLE else View.GONE
            binding.tvInfo.visibility = if (loadState is LoadState.Error) View.VISIBLE else View.GONE
            binding.tvRetry.setOnClickListener {
                retry()
            }
        }
    }
}