package com.example.moviemate.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviemate.model.Movie
import com.example.moviemate.network.RetrofitInstance
import kotlinx.coroutines.launch
import java.io.IOException

class MainViewModel: ViewModel() {

    val page = MutableLiveData(0)

    val movieList= MutableLiveData<List<Movie>>()

    val selectedMovie = MutableLiveData<Movie>()

    val loading = MutableLiveData<Boolean>(false)

    val apiCalls = MutableLiveData<Int>(0)

    fun getMovies(doAfter: () -> Unit) {
        viewModelScope.launch {
            page.value = page.value?.plus(1)
            apiCalls.value = apiCalls.value?.plus(1)
            loading.value = true
            val response = try {
                RetrofitInstance.api.getUpComingMovies(page.value!!)
            } catch (e: IOException){
                return@launch
            }
            if(response.isSuccessful && response.body()!=null){
                movieList.value = response.body()!!.results
            }
            Log.d("TAG", "response is successful ? ${response.isSuccessful}")
            Log.d("TAG", "calls : ${apiCalls.value}")
            loading.value = false
            doAfter()
        }
    }

    fun searchMovie(search:String, doAfter: () -> Unit) {
        viewModelScope.launch {
            page.value = page.value?.plus(1)
            loading.value = true
            val response = try {
                RetrofitInstance.api.getSpecificMovie(page.value!!,search)
            } catch (e: IOException){
                return@launch
            }
            if(response.isSuccessful && response.body()!=null){
                movieList.value = response.body()!!.results
            }
            loading.value = false
            doAfter()
        }
    }

}