package com.example.core.utils

import com.example.core.data.source.local.entity.MovieEntity
import com.example.core.data.source.remote.response.MovieResponse
import com.example.core.domain.model.Movie

object DataMapper {
    fun mapResponsesToEntities(input: List<MovieResponse>): List<MovieEntity> {
        val movieList = ArrayList<MovieEntity>()
        input.map { movieResponse ->

            val movie = MovieEntity(
                movie_id = movieResponse.id,
                name = movieResponse.originalTitle,
                overview = movieResponse.overview,
                poster_path = movieResponse.posterPath,
                release_date = movieResponse.releaseDate,
                vote_average = movieResponse.voteAverage,
                vote_count = movieResponse.voteCount,
                isFavorite = false,
                original_language = movieResponse.originalLanguage,
                isTvShows = false
            )
            movieList.add(movie)
        }
        return movieList
    }

    fun mapEntitiesToDomain(input: List<MovieEntity>): List<Movie> =
        input.map {
            Movie(
                id = it.movie_id,
                name = it.name.toString(),
                overview = it.overview,
                poster = it.poster_path,
                isFavorite = it.isFavorite,
                vote_average = it.vote_average,
                release_date = it.release_date,
                vote_count = it.vote_count,
                original_language = it.original_language,
                isTvShows = it.isTvShows
            )
        }

    fun mapDomainToEntity(input: Movie) =
        MovieEntity(
            movie_id = input.id,
            overview = input.overview,
            name = input.name,
            poster_path = input.poster,
            vote_average = input.vote_average,
            isFavorite = input.isFavorite,
            release_date = input.release_date,
            vote_count = input.vote_count,
            original_language = input.original_language,
            isTvShows = input.isTvShows
        )

}