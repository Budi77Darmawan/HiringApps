package com.example.hiringapps.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hiringapps.R
import com.example.hiringapps.databinding.ItemGridFreelancersBinding

class GridFreelancersAdapter (private val items: List<DetailFreelancersModel>): RecyclerView.Adapter<GridFreelancersAdapter.GridViewHolder>() {
    private val baseurl = "http://10.0.2.2:8080/images/"

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(id: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): GridViewHolder {
        return GridViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_grid_freelancers, parent, false))

    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        val item = items[position]

        holder.binding.tvName.text = item.name
        holder.binding.tvJob.text = item.job

        val image = baseurl + item.image
        Glide.with(holder.binding.root)
            .load(image)
            .into(holder.binding.imgProfil)

        holder.binding.cardview.setOnClickListener {
            onItemClickCallback.onItemClicked(position)
        }

    }

    inner class GridViewHolder(
        val binding: ItemGridFreelancersBinding
    ): RecyclerView.ViewHolder(binding.root)

}