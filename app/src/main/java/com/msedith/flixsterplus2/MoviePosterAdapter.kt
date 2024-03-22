package com.msedith.flixsterplus2

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.msedith.flixsterplus2.databinding.ItemMoviePosterBinding

class MoviePosterAdapter(private val posterUrls: List<String>) : RecyclerView.Adapter<MoviePosterAdapter.PosterViewHolder>() {

    class PosterViewHolder(private val binding: ItemMoviePosterBinding) : RecyclerView.ViewHolder(binding.root) {
        val imageView: ImageView = binding.ivMoviePoster
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PosterViewHolder {
        val binding = ItemMoviePosterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PosterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PosterViewHolder, position: Int) {
        val posterUrl = posterUrls[position]
        Glide.with(holder.itemView.context)
            .load(posterUrl)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int = posterUrls.size
}
