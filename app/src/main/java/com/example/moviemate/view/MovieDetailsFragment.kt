package com.example.moviemate.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.moviemate.databinding.FragmentMovieDetailsBinding
import com.example.moviemate.viewModel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class MovieDetailsFragment : Fragment() {

    private var _binding : FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel : MainViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        val movie = viewModel.selectedMovie.value
        Log.d("TAG", "selected movie: ${viewModel.selectedMovie.value}")
        Log.d("TAG", "selected movie: ${viewModel.selectedMovie.value}")
        Log.d("TAG", "selected movie: ${viewModel.selectedMovie.value}")
        Log.d("TAG", "selected movie: ${viewModel.selectedMovie.value}")
        Log.d("TAG", "selected movie: ${viewModel.selectedMovie.value}")
        binding.run {
            val posterPathAdjusted = "https://image.tmdb.org/t/p/original${movie?.poster_path}"
            Glide.with(requireContext()).load(posterPathAdjusted).into(detailsPoster)
            detailsTitle.text = movie?.title
            detailsDescription.text = movie?.overview
        }

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}