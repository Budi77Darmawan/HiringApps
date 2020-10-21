package com.example.hiringapps.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hiringapps.R
import com.example.hiringapps.databinding.ItemGridPortofolioBinding

class GridPortofolioAdapter (private val items: List<PortofolioModel>): RecyclerView.Adapter<GridPortofolioAdapter.GridViewHolder>() {
    private val baseurl = "http://10.0.2.2:8080/images/"

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(id: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): GridViewHolder {
        return GridViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_grid_portofolio, parent, false))

    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        val item = items[position]

        holder.binding.tvName.text = item.name
        holder.binding.tvTypeportofolio.text = item.type_portofolio

        val image = baseurl + item.image
        Glide.with(holder.binding.root)
            .load(image)
            .into(holder.binding.imgPortofolio)

        holder.binding.cardview.setOnClickListener {
            onItemClickCallback.onItemClicked(position)
        }

    }

    inner class GridViewHolder(
        val binding: ItemGridPortofolioBinding
    ): RecyclerView.ViewHolder(binding.root)

}