package com.example.mvvmjetpackcomposelogin.data.api.apiinterface

import com.example.mvvmjetpackcomposelogin.data.api.model.Movie
import com.example.mvvmjetpackcomposelogin.data.api.model.MoviesModel
import com.example.mvvmjetpackcomposelogin.data.api.model.PersonModel
import com.example.mvvmjetpackcomposelogin.data.api.model.SingleTvShowModel
import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

data class PersonResponseList(
    @SerializedName("results") val results: List<PersonModel>
)

data class MovieResponseList(
    @SerializedName("results") val results: List<MoviesModel>
)

interface MovieService {

    // PERSONAS
    @GET("person/popular")
    suspend fun getPersonsPopular(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ) : PersonResponseList

    //PELICULAS
    @GET("movie/top_rated")
    suspend fun getMoviesTopRated(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ) : MovieResponseList

    @GET("movie/popular")
    suspend fun getMoviesPopular(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ) : MovieResponseList

    @GET("discover/movie")
    suspend fun getMoviesByGenre(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("with_genres") genreId: Int,
        @Query("page") page: Int = 1
    ) : MovieResponseList

    @GET("movie/{movieId}")
    suspend fun getMovieById(
        @Path("movieId") movieId : Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ) : Movie

    @GET("movie/{movieId}")
    suspend fun getMovieByIdWithVideos(
        @Path("movieId") movieId : Int,
        @Query("api_key") apiKey: String,
        @Query("append_to_response") appendToResponse: String = "videos",
        @Query("language") language: String = "en-US"
    ) : Movie

    // SERIES
    @GET("tv/top_rated")
    suspend fun getSeriesTopRated(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ) : MovieResponseList
    @GET("tv/popular")
    suspend fun getSeriesPopular(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ) : MovieResponseList

    @GET("discover/tv")
    suspend fun getSeriesByGenre(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("with_genres") genreId: Int,
        @Query("page") page: Int = 1
    ) : MovieResponseList

    @GET("tv/{tvId}")
    suspend fun getSeriesById(
        @Path("tvId") tvId : Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ) : SingleTvShowModel

}