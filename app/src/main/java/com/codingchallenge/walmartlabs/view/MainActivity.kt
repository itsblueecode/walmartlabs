package com.codingchallenge.walmartlabs.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.codingchallenge.walmartlabs.R
import com.codingchallenge.walmartlabs.databinding.ActivityMainBinding
import com.codingchallenge.walmartlabs.networking.ErrorManager
import com.codingchallenge.walmartlabs.networking.RetrofitClient
import com.codingchallenge.walmartlabs.repository.CountriesInterface
import com.codingchallenge.walmartlabs.viewmodel.CountriesListViewModel
import com.codingchallenge.walmartlabs.viewmodel.CountriesListViewModelFactory


/**
 * @Author Dushane Coram
 * @Date 25, October, 2022
 * @Project WalmartLabs
 * @Copyright (c) 2022. All rights reserved.
 * @Description Show countries list
 */
class MainActivity : AppCompatActivity() {

    // VIEWS

    private lateinit var binding: ActivityMainBinding

    // OBJECTS

    private lateinit var adapter: CountriesListAdapter
    private lateinit var countriesListViewModel: CountriesListViewModel

    private val retrofit = RetrofitClient.getInstance(CountriesInterface.BASE_URL)
    private val api: CountriesInterface = retrofit.create(CountriesInterface::class.java)


    /** methods **/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        setupLayout()
        addCountriesListObservers()
    }

    override fun onStart() {
        super.onStart()
        getCountries()
    }

    private fun setupViewModel() {
        val factory = CountriesListViewModelFactory(api)
        countriesListViewModel = ViewModelProvider(
            this, factory
        )[CountriesListViewModel::class.java]
    }

    private fun setupLayout() {
        binding.srlAction.apply {
            setColorSchemeColors(
                ContextCompat.getColor(context, R.color.color_primary_normal),
                ContextCompat.getColor(context, R.color.color_primary_normal),
                ContextCompat.getColor(context, R.color.color_primary_normal)
            )
            setOnRefreshListener {
                refresh()
            }
        }
        binding.rvList.apply {
            isMotionEventSplittingEnabled = false
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }
        binding.tvRetry.setOnClickListener {
            refresh()
        }
    }

    private fun addCountriesListObservers() {
        countriesListViewModel.isLoading.observe(this) {
            binding.pbLoad.isVisible = it
        }
        countriesListViewModel.countries.observe(this) {
            if (it.exception != null) {
                showInfoView(true)
                binding.tvInfo.text = ErrorManager(it.exception).message
            } else {
                it.data?.let { list ->
                    if (list.isNotEmpty()) {
                        showInfoView(false)
                        adapter = CountriesListAdapter(this, list)
                        binding.rvList.adapter = adapter
                    } else {
                        showInfoView(true)
                        binding.tvInfo.text = resources.getString(R.string.no_countries)
                    }
                }
            }
        }
    }

    private fun getCountries() {
        countriesListViewModel.getCountries()
    }

    private fun refresh() {
        showInfoView(false)
        binding.rvList.adapter = CountriesListAdapter(this, listOf())
        binding.srlAction.isRefreshing = false
        getCountries()
    }

    private fun showInfoView(showError: Boolean) {
        binding.tvInfo.isVisible = showError
        binding.tvRetry.isVisible = showError
        binding.rvList.isVisible = !showError
    }
}