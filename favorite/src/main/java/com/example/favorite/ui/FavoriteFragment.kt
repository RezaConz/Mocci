package com.example.favorite.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.favorite.data.DaggerFavoriteModule
import com.example.favorite.data.ViewModelFactory
import com.example.favorite.databinding.FragmentFavoriteBinding
import com.example.core.ui.MovieAdapter
import com.example.mocci.detail.DetailActivity
import com.example.mocci.di.FavoriteModuleDependencies
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var factory: ViewModelFactory

    private val favoriteViewModel: FavoriteViewModel by viewModels {
        factory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerFavoriteModule.builder()
            .context(context)
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    context,
                    FavoriteModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieAdapter = MovieAdapter()
        movieAdapter.onItemClick = { selectedData ->
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(DetailActivity.SELECTED_DATA, selectedData)
            startActivity(intent)
        }

        favoriteViewModel.moviesFavorite.observe(viewLifecycleOwner) { movie ->
            if (movie.isEmpty()) {
                binding.tvEmpty.visibility = View.VISIBLE
            } else {
                binding.tvEmpty.visibility = View.GONE
            }
            movieAdapter.setData(movie)
        }

        binding.rvFavorite.layoutManager = GridLayoutManager(activity, 2)
        binding.rvFavorite.setHasFixedSize(true)
        binding.rvFavorite.adapter = movieAdapter

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvFavorite.adapter = null
        _binding = null
    }
}