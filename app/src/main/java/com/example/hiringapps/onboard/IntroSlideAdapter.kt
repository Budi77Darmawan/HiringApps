package com.example.hiringapps.onboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hiringapps.R

class IntroSlideAdapter(private val introSlides: List<IntroSlide>):
    RecyclerView.Adapter<IntroSlideAdapter.IntroSlideViewHolder>(){

    inner class IntroSlideViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textTittle = view.findViewById<TextView>(R.id.tv_tittle)
        private val textSubTittle = view.findViewById<TextView>(R.id.tv_subtittle)
        private val slideIcon = view.findViewById<ImageView>(R.id.slideicon)

        fun bind(introSlide: IntroSlide) {
            textTittle.text = introSlide.tittle
            textSubTittle.text = introSlide.subtittle
            slideIcon.setImageResource(introSlide.icon)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntroSlideViewHolder {
        return IntroSlideViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.onboard_container,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return introSlides.size
    }

    override fun onBindViewHolder(holder: IntroSlideViewHolder, position: Int) {
        holder.bind(introSlides[position])
    }
}