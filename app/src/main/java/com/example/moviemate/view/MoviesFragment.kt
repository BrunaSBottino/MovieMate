package com.example.moviemate.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviemate.R
import com.example.moviemate.databinding.FragmentMoviesBinding
import com.example.moviemate.model.Movie
import com.example.moviemate.viewModel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MoviesFragment : Fragment() {

    private val viewModel: MainViewModel by sharedViewModel()

    private var _binding: FragmentMoviesBinding? = null

    private val binding get() = _binding!!

    private var movieAdapter = MovieAdapter(null, this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.i("TAG", "onCreateView: created view")
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        binding.RecyclerViewMovie.apply {

            val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

            layoutManager = linearLayoutManager
            adapter = movieAdapter

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    if (dy > 0) { //check for scroll down
                        if (viewModel.loading.value == false) {
                            val visibleItemCount = linearLayoutManager.childCount; // 3
                            val totalItemCount = linearLayoutManager.itemCount; // 20
                            val pastVisiblesItems =
                                linearLayoutManager.findFirstVisibleItemPosition(); // 17
                            if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                                val search = binding.editText.text.toString()
                                if(search!=""){
                                    loadMoreSearch(search)
                                    Log.i("TAG", "chamou mais da pesquisa ${viewModel.page.value}")
                                }else{
                                    loadMoreMovies()
                                }
                            }
                        }
                    }
                }
            })
        }
        binding.editText.apply{
            doAfterTextChanged {
                val search = binding.editText.text.toString()
                if(search != "") {
                    startSearch(search)
                }else{
                    loadAfterClear()
                }
            }
        }
        if(viewModel.movieList.value.isNullOrEmpty()) {
            loadMoreMovies()
        }
        return binding.root
    }

    private fun loadAfterClear() {
        movieAdapter.clear()
        viewModel.page.value = 0
        loadMoreMovies()
    }

    private fun startSearch(search: String) {
        viewModel.page.value = 0
        viewModel.searchMovie(
            search = search,
            doAfter = { updateRecyclerView(Action.NEW_SEARCH) }
        )
    }

    private fun loadMoreSearch(search: String){
        viewModel.searchMovie(
            search = search,
            doAfter = { updateRecyclerView(Action.LOAD_MORE) }
        )
    }

    private fun loadMoreMovies() {
        viewModel.getMovies(
            doAfter = { updateRecyclerView(Action.LOAD_MORE) }
        )
    }

    fun updateRecyclerView(action: Action) {
        movieAdapter.run {
            when(action){
                Action.LOAD_MORE -> addMovies(viewModel.movieList.value)
                Action.NEW_SEARCH -> makeSearch(viewModel.movieList.value)
            }
        }
    }

    enum class Action {
        NEW_SEARCH,
        LOAD_MORE
    }

    fun goToDetailsFragment(movie: Movie) {
        viewModel.selectedMovie.value = movie
        Navigation.findNavController(binding.root).navigate(R.id.goToDetailsFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}