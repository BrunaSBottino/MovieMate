package com.example.moviemate.network

import com.example.moviemate.model.MultiMovie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import kotlin.coroutines.CoroutineContext


class Repository {

    suspend fun loadMovies(page: Int) =
        withContext(Dispatchers.IO) {
            RetrofitInstance.api.getUpComingMovies(page)
        }

    suspend fun getSpecificMovie(page: Int, search: String) =
        withContext(Dispatchers.IO) {
            RetrofitInstance.api.getSpecificMovie(page,search)
        }
}