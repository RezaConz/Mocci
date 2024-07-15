package com.example.core.data.source.remote.net

import com.example.core.BuildConfig
import com.example.core.data.source.remote.response.ListMovieResponse
import com.example.core.data.source.remote.response.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("movie/now_playing")
    suspend fun getMovie(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): ListMovieResponse<MovieResponse>

}
