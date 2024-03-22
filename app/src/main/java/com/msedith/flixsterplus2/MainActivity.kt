package com.msedith.flixsterplus2

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.msedith.flixsterplus2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var service: TrendingPeopleApiService
    private lateinit var peopleAdapter: TrendingPersonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val toolbar: Toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Trending People"

        val numberOfColumns = 2  // You can change this to 3 or more if needed
        binding.rvPeople.layoutManager = GridLayoutManager(this, numberOfColumns)

        peopleAdapter = TrendingPersonAdapter(emptyList())
        binding.rvPeople.adapter = peopleAdapter

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(TrendingPeopleApiService::class.java)

        fetchTrendingPeople()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        val newSpanCount = determineSpanCount()
        (binding.rvPeople.layoutManager as? GridLayoutManager)?.spanCount = newSpanCount
    }

    // Helper function to determine span count based on orientation.
    private fun determineSpanCount(): Int {
        val orientation = resources.configuration.orientation
        return if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            3 // or more, for landscape mode
        } else {
            2 // for portrait mode
        }
    }

    private fun fetchTrendingPeople() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val peopleResponse = service.getTrendingPeople("a07e22bc18f5cb106bfe4cc1f83ad8ed") // Replace with your API key
                withContext(Dispatchers.Main) {
                    peopleAdapter.updatePeople(peopleResponse.results)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                // Handle the error appropriately
            }
        }
    }
}
