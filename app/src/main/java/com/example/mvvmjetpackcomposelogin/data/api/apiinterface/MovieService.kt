package com.example.mvvmjetpackcomposelogin.data.api.apiinterface

import com.example.mvvmjetpackcomposelogin.data.api.model.MoviesModel
import com.example.mvvmjetpackcomposelogin.data.api.model.SingleMovieModel
import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {
    @GET("movie/top_rated")
    suspend fun getMoviesTopRated(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "es-ES",
        @Query("page") page: Int = 1
    ) : MovieResponseList

    @GET("movie/{movieId}")
    suspend fun getMovieById(
        @Path("movieId") movieId : Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "es-ES",
    ) : SingleMovieModel
}

data class MovieResponseList(
    @SerializedName("results") val results: List<MoviesModel>
)