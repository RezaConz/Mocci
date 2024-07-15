package com.example.mocci.detail

import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.mocci.R
import com.example.core.domain.model.Movie
import com.example.mocci.databinding.ActivityDetailBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private val detailViewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        @Suppress("DEPRECATION")
        val detailMovie = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(SELECTED_DATA, Movie::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(SELECTED_DATA)
        }

        showDetailMovie(detailMovie)

    }

    private fun showDetailMovie(detailMovie: Movie?) {
        detailMovie?.let {
            supportActionBar?.title = detailMovie.name
            binding.apply {
                tvTitle.text = detailMovie.name
                tvOverviewDetail.text = detailMovie.overview
                tvRatingMovie.text = detailMovie.vote_average.toString()
                Glide.with(this@DetailActivity)
                    .load("https://image.tmdb.org/t/p/w500${detailMovie.poster}")
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(posterMovie)
                var statusFavorite = detailMovie.isFavorite
                setStatusFavorite(statusFavorite)

                ivFavorite.setOnClickListener {
                    statusFavorite = !statusFavorite
                    detailViewModel.setFavoriteMovie(detailMovie, statusFavorite)
                    setStatusFavorite(statusFavorite)
                    showStatusFav(statusFavorite, detailMovie.name)
                }

                ivBack.setOnClickListener {
                    @Suppress("DEPRECATION")
                    onBackPressed()
                }
            }
        }
    }

    private fun showStatusFav(state: Boolean, name: String) {
        val msg = if (state) {
            "$name " + getString(R.string.added_to_favorite)
        } else {
            "$name " + getString(R.string.removed_from_favorite)
        }
        Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG)
            .show()
    }

    private fun setStatusFavorite(statusFavorite: Boolean) {
        if (statusFavorite) {
            binding.ivFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_favorite
                )
            )

        } else {
            binding.ivFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_unfavorite
                )
            )
        }
    }

    companion object {
        const val SELECTED_DATA = "selected_data"
    }
}