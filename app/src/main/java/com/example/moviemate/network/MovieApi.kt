package com.example.moviemate.network

import com.example.moviemate.model.MultiMovie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("/3/movie/upcoming?api_key=${NetworkUtils.API_KEY}")
    suspend fun getUpComingMovies(@Query("page")page: Int): Response<MultiMovie>

    @GET("/3/search/movie?api_key=${NetworkUtils.API_KEY}")
    suspend fun getSpecificMovie(@Query("page")page: Int, @Query("query") search: String): Response<MultiMovie>

}
