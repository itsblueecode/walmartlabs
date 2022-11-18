package com.codingchallenge.walmartlabs.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.codingchallenge.walmartlabs.R
import com.codingchallenge.walmartlabs.model.Country

/**
 * @Author Dushane Coram
 * @Date 17, November, 2022
 * @Project WalmartLabs
 * @Copyright (c). All rights reserved.
 * @Description
 */
open class CountriesListAdapter(
    private val context: Context,
    private val data: List<Country>
    ) : RecyclerView.Adapter<CountriesListAdapter.ViewHolderItem>() {

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderItem {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_country, parent, false)

        return ViewHolderItem(view)
    }

    override fun onBindViewHolder(holder: ViewHolderItem, position: Int) {
        val country = data[position]
        holder.tvNameAndRegion.text = context.resources.getString(
            R.string.name_region,
            country.name,
            country.region
        )
        holder.tvCode.text = country.code
        holder.tvCapital.text = country.capital
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return this.data.size
    }


    // VIEW HOLDERS


    inner class ViewHolderItem(view: View) : RecyclerView.ViewHolder(view) {
        val tvNameAndRegion: TextView
        val tvCode: TextView
        val tvCapital: TextView
        init {
            tvNameAndRegion = view.findViewById(R.id.tv_name_and_region)
            tvCode = view.findViewById(R.id.tv_code)
            tvCapital = view.findViewById(R.id.tv_capital)
        }
    }

}