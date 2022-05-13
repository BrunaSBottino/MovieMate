package com.example.moviemate.network

import com.example.moviemate.model.MultiMovie
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("/3/movie/upcoming?api_key=${Credentials.API_KEY}")
    suspend fun getUpComingMovies(@Query("page")page: Int): Response<MultiMovie>

    @GET("/3/search/movie?api_key=${Credentials.API_KEY}")
    suspend fun getSpecificMovie(@Query("query")search: String): Response<MultiMovie>
//https://api.themoviedb.org/3/movie/upcoming?api_key=f321a808e68611f41312aa8408531476&page=1
//https://api.themoviedb.org/movie/upcoming?api_key=f321a808e68611f41312aa8408531476
}
