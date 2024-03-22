package com.msedith.flixsterplus2

import android.graphics.drawable.Drawable
import com.bumptech.glide.request.target.Target
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener

class DetailsActivity : AppCompatActivity() {

    private lateinit var moviePosterAdapter: MoviePosterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.person_details)

        // Extract the details from the intent
        val name = intent.getStringExtra("name")
        val popularity = intent.getDoubleExtra("popularity", 0.0)
        val imageUrl = intent.getStringExtra("profile_path")
        val biography = intent.getStringExtra("biography")
        val posterUrls = intent.getStringArrayListExtra("poster_urls")

        // Assuming you have a TextView for the name and popularity and an ImageView for the profile picture
        findViewById<TextView>(R.id.tvPersonName).text = name
        findViewById<TextView>(R.id.tvPopularity).text = String.format("Popularity: %.2f", popularity)
        val ivProfilePicture = findViewById<ImageView>(R.id.ivProfilePicture)

        // Use the imageUrl to load the image with Glide and handle any potential errors
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500$imageUrl")
            .placeholder(R.drawable.loading_placeholder)
            .error(R.drawable.loading_error)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    Log.e("DetailsActivity", "Image load failed", e)
                    return false // False if you want Glide's default behavior to happen as well.
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false // False if you want Glide's default behavior to happen as well.
                }
            })
            .into(ivProfilePicture)

        val recyclerView = findViewById<RecyclerView>(R.id.rvKnownFor)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        moviePosterAdapter = MoviePosterAdapter(posterUrls ?: listOf())
        recyclerView.adapter = moviePosterAdapter
    }
}

