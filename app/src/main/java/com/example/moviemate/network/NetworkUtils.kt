package com.example.moviemate.network

object NetworkUtils {
    const val BASE_URL = "https://api.themoviedb.org/"

    const val API_KEY = "f321a808e68611f41312aa8408531476"

    private const val BASE_POSTER_PATH = "https://image.tmdb.org/t/p/original"

    fun getCompletePosterPath(posterPath:String) : String = "$BASE_POSTER_PATH$posterPath"
}