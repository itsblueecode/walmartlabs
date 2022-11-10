package com.codingchallenge.walmartlabs.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.codingchallenge.walmartlabs.R
import com.codingchallenge.walmartlabs.databinding.ActivityMainBinding
import com.codingchallenge.walmartlabs.networking.RetrofitClient
import com.codingchallenge.walmartlabs.repository.ProductInterface
import com.codingchallenge.walmartlabs.viewmodel.ProductListViewModelFactory
import com.codingchallenge.walmartlabs.viewmodel.ProductsListViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


/**
 * @Author Dushane Coram
 * @Date 25, October, 2022
 * @Project WalmartLabs
 * @Copyright (c) 2022. All rights reserved.
 * @Description Show school list
 */
class MainActivity : AppCompatActivity() {

    // VIEWS

    private lateinit var binding: ActivityMainBinding


    // OBJECTS

    private lateinit var adapter : ProductListAdapter
    private lateinit var loadStateAdapter: ProductsLoadStateAdapter

    private lateinit var productsListViewModel: ProductsListViewModel

    private val retrofit = RetrofitClient.getInstance(ProductInterface.BASE_URL)
    private val api: ProductInterface = retrofit.create(ProductInterface::class.java)


    /** methods **/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        supportActionBar?.title = "Walmart Products"
        setContentView(binding.root)

        initViewModel()
        initLayout()
        initProductList()
    }

    private fun initViewModel() {
        val factory = ProductListViewModelFactory(api)
        productsListViewModel = ViewModelProvider(
            this, factory
        )[ProductsListViewModel::class.java]
    }

    private fun initLayout() {
        binding.srlAction.setOnRefreshListener {
            adapter.refresh()
            binding.srlAction.isRefreshing = false
        }
        binding.rvList.apply {
            layoutManager = LinearLayoutManager(context)
            isMotionEventSplittingEnabled = false
            setHasFixedSize(true)
        }
        adapter = ProductListAdapter()
        loadStateAdapter = ProductsLoadStateAdapter { adapter.retry() }
        binding.rvList.adapter = adapter.withLoadStateFooter(
            footer = loadStateAdapter
        )
        binding.tvRetry.setOnClickListener {
            adapter.retry()
        }
    }

    private fun initProductList() {
        lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest { loadStates ->
                binding.pbLoad.isVisible = loadStates.refresh is LoadState.Loading
                binding.tvInfo.isVisible = loadStates.refresh is LoadState.Error
                binding.tvRetry.isVisible = loadStates.refresh is LoadState.Error
                if (loadStates.refresh is LoadState.NotLoading && adapter.itemCount == 0) {
                    binding.tvInfo.isVisible = true
                    binding.tvInfo.text = getString(R.string.no_products)
                    binding.rvList.isVisible = false
                } else {
                    binding.rvList.isVisible = true
                }
                if (loadStates.source.refresh is LoadState.Error) {
                    binding.tvInfo.text = (loadStates.source.refresh as LoadState.Error).error.localizedMessage ?: getString(R.string.error)
                }
            }
        }
        lifecycleScope.launch {
            productsListViewModel.productsList.collectLatest { products ->
                adapter.submitData(products)
            }
        }
    }
}