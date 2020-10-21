package com.example.hiringapps.offers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hiringapps.R
import com.example.hiringapps.databinding.ItemRowOffersBinding

class ListOffersAdapter(private val items: List<OffersModel>) :
    RecyclerView.Adapter<ListOffersAdapter.ListViewHolder>() {
    private val baseurl = "http://10.0.2.2:8080/images/"

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(id: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ListViewHolder {
        return ListViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_row_offers,
                parent,
                false
            )
        )

    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = items[position]

        holder.binding.tvName.text = item.name
        holder.binding.tvProject.text = item.project_name

        val image = baseurl + item.image
        Glide.with(holder.binding.root)
            .load(image)
            .into(holder.binding.imgProfil)

        when (item.status_confirm) {
            "-1" -> {
                holder.binding.tvStatus.text = "Rejected"
                holder.binding.tvStatus.setBackgroundResource(R.color.red)
            }
            "0" -> {
                holder.binding.tvStatus.text = "Pending"
                holder.binding.tvStatus.setBackgroundResource(R.color.orange)
            }
            else -> {
                holder.binding.tvStatus.text = "Accepted"
                holder.binding.tvStatus.setBackgroundResource(R.color.green)
            }
        }

        holder.binding.cardview.setOnClickListener {
            onItemClickCallback.onItemClicked(position)
        }

    }

    inner class ListViewHolder(val binding: ItemRowOffersBinding) :
        RecyclerView.ViewHolder(binding.root)
}