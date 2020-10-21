package com.example.hiringapps.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.hiringapps.R
import com.example.hiringapps.databinding.ItemRowExperienceBinding

class ListExperienceAdapter(private val items: List<ExperienceModel>) :
    RecyclerView.Adapter<ListExperienceAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(id: Int)
    }

    inner class ListViewHolder(val binding: ItemRowExperienceBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_row_experience,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = items[position]

        holder.binding.tvNameCompany.text = item.company
        holder.binding.tvJob.text = item.job
        holder.binding.tvJobDate.text = item.start
        holder.binding.tvJobDecs.text = item.description

        holder.binding.layout.setOnClickListener {
            onItemClickCallback.onItemClicked(position)
        }
    }

}