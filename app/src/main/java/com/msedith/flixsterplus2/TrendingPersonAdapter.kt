package com.msedith.flixsterplus2

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.msedith.flixsterplus2.databinding.PersonItemBinding

class TrendingPersonAdapter(private var people: List<Person>) : RecyclerView.Adapter<TrendingPersonAdapter.PersonViewHolder>() {

    class PersonViewHolder(val binding: PersonItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val binding = PersonItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PersonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val person = people[position]
        val posterUrls = person.known_for.map { movie ->
            "https://image.tmdb.org/t/p/w500${movie.poster_path}"
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailsActivity::class.java)
            intent.putExtra("name", person.name)
            intent.putExtra("popularity", person.popularity)
            intent.putExtra("profile_path", person.profile_path)
            intent.putStringArrayListExtra("poster_urls", ArrayList(posterUrls))
            holder.itemView.context.startActivity(intent)
        }

        with(holder) {
            with(person) {
                binding.tvName.text = name
                binding.tvPopularity.text = String.format("Popularity: %.2f", popularity)

                Glide.with(holder.itemView.context)
                    .load("https://image.tmdb.org/t/p/w500$profile_path")
                    .placeholder(R.drawable.loading_placeholder)
                    .error(R.drawable.loading_error)
                    .into(binding.ivProfile)
            }
        }
    }

    fun updatePeople(newPeople: List<Person>) {
        this.people = newPeople
        notifyDataSetChanged()
    }

    override fun getItemCount() = people.size
}
