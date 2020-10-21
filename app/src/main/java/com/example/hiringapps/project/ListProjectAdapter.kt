package com.example.hiringapps.project

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hiringapps.R
import com.example.hiringapps.databinding.ItemRowProjectBinding


class ListProjectAdapter(private val items: List<ProjectModel>) :
    RecyclerView.Adapter<ListProjectAdapter.ListViewHolder>() {
    private val baseurl = "http://10.0.2.2:8080/images/"

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onIconClicked(id: Int)
        fun onItemClicked(id: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ListViewHolder {
        return ListViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_row_project,
                parent,
                false
            )
        )

    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = items[position]

        holder.binding.tvNumber.text = ((position + 1).toString())
        holder.binding.tvNameProject.text = item.name
        holder.binding.tvDescriptionProject.text = item.description

        val image = baseurl + item.image
        Glide.with(holder.binding.root)
            .load(image)
            .into(holder.binding.imgApp)

        holder.binding.cardview.setOnClickListener {
            onItemClickCallback.onItemClicked(position)
        }

        holder.binding.imgEdit.setOnClickListener {
            onItemClickCallback.onIconClicked(position)
        }
    }

    inner class ListViewHolder(val binding: ItemRowProjectBinding) :
        RecyclerView.ViewHolder(binding.root)

}