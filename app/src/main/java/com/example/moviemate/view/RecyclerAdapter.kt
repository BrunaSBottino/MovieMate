package com.example.moviemate.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviemate.databinding.ItemMovieBinding
import com.example.moviemate.model.Movie
import com.example.moviemate.network.NetworkUtils

class MovieAdapter(var movies: List<Movie>?, val fragment: Fragment) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {


    fun addMovies(newMovieList: List<Movie>?) {
        if (newMovieList != null) {
            movies = getSumOf(movies, newMovieList)
            notifyDataSetChanged()
        }
    }

    fun makeSearch(searchResults: List<Movie>?) {
        if (searchResults != null) {
            movies = searchResults
            notifyDataSetChanged()
        }
    }

    fun clear() {
        movies = listOf()
        notifyDataSetChanged()
    }

    private fun getSumOf(movies: List<Movie>?, newMovieList: List<Movie>): List<Movie> {
        val finalMovieList = arrayListOf<Movie>()
        if (movies != null) {
            for (movie in movies) {
                finalMovieList.add(movie)
            }
        }
        for (movie in newMovieList) {
            finalMovieList.add(movie)
        }
        return finalMovieList
    }

    class MovieViewHolder(var binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(ItemMovieBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        if (movies != null) {
            val movie = movies!![position]
            holder.binding.run {
                val posterPath = NetworkUtils.getCompletePosterPath(movie.poster_path)
                Glide.with(this.root).load(posterPath).into(poster)
                textViewTitle.text = movie.original_title
                textViewPremiere.text = movie.release_date
                root.setOnClickListener {
                    if (fragment is MoviesFragment)
                        fragment.goToDetailsFragment(movie)
                }
                poster.apply {
                    poster.clipToOutline = true
                    poster.scaleType = ImageView.ScaleType.FIT_XY
                }
            }
        }
    }

    override fun getItemCount() = movies?.size ?: 0

}