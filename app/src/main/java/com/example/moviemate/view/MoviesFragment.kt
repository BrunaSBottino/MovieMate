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
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesFragment : Fragment() {

    private val viewModel: MainViewModel by sharedViewModel()

    private var _binding: FragmentMoviesBinding? = null

    private val binding get() = _binding!!

    private val recyclerView: RecyclerView by lazy {
        binding.RecyclerViewMovie
    }

    private var page = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        binding.RecyclerViewMovie.apply {
            layoutManager =
                object : LinearLayoutManager(context, RecyclerView.VERTICAL, false) {
                    override fun scrollVerticallyBy(
                        dy: Int,
                        recycler: RecyclerView.Recycler?,
                        state: RecyclerView.State?
                    ): Int {
                        if ((findLastCompletelyVisibleItemPosition() + 1) % 20 == 0) {
                            updateRecyclerView()
                        }
                        return super.scrollVerticallyBy(dy, recycler, state)
                    }
                }
            viewModel.getMovies(
                page = page,
                doAfter = {
                    binding.RecyclerViewMovie.adapter =
                        MovieAdapter(viewModel.movieList.value, this@MoviesFragment)
                })
        }
        binding.editText.doAfterTextChanged {
            updateRecyclerViewWithSearch(binding.editText.text.toString())
        }
        return binding.root
    }

    private fun updateRecyclerViewWithSearch(search: String) {
        page = 1
        viewModel.searchMovie(
            search = search,
            doAfter = {
                if (recyclerView.adapter is MovieAdapter) {
                    (recyclerView.adapter as MovieAdapter).makeSearch(viewModel.movieList.value)
                }
            }
        )
    }


    private fun updateRecyclerView() {
        page++
        viewModel.getMovies(
            page = page,
            doAfter = {
                if (recyclerView.adapter is MovieAdapter) {
                    (recyclerView.adapter as MovieAdapter).updateMovieList(viewModel.movieList.value)
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun goToDetailsFragment(movie: Movie) {
        viewModel.selectedMovie.value = movie
        Navigation.findNavController(binding.root).navigate(R.id.goToDetailsFragment)
    }

}