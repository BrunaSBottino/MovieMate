package com.example.moviemate.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviemate.model.Movie
import com.example.moviemate.network.RetrofitInstance
import com.example.moviemate.view.MovieAdapter
import kotlinx.coroutines.launch
import java.io.IOException

class MainViewModel: ViewModel() {

    val movieList= MutableLiveData<List<Movie>>()

    val selectedMovie = MutableLiveData<Movie>()

    fun getMovies(page: Int, doAfter: () -> Unit) {
        viewModelScope.launch {
            val response = try {
                RetrofitInstance.api.getUpComingMovies(page)
            } catch (e: IOException){
                return@launch
            }
            if(response.isSuccessful && response.body()!=null){
                movieList.value = response.body()!!.results
            }
            Log.d("TAG", "response is successful ? ${response.isSuccessful}")
            Log.d("TAG", "response code ${response.code()}")
            Log.d("TAG", "response body ${response.body()}")
            Log.d("TAG", "response body ${response.body()!!.results.size}")
            Log.d("TAG", "response body ${response.raw()}")
            doAfter()
//            binding.RecyclerViewMovie.adapter =
//                MovieAdapter(viewModel.movieList.value, this@MoviesFragment)
        }
    }

    fun searchMovie(search:String, doAfter: () -> Unit) {
        viewModelScope.launch {
            val response = try {
                RetrofitInstance.api.getSpecificMovie(search)
            } catch (e: IOException){
                return@launch
            }
            if(response.isSuccessful && response.body()!=null){
                movieList.value = response.body()!!.results
            }
            Log.d("TAG", "response code ${response.code()}")
            Log.d("TAG", "response body ${response.body()}")
            Log.d("TAG", "response body ${response.raw()}")
            doAfter()
        }
    }


}