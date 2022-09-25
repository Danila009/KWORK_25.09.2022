package com.example.films.data.network.model

data class Movie(
    val data:List<MovieItem>
)

data class MovieItem(
    val id:Int,
    val ru_title:String,
    val orig_title:String,
    val imdb_id:String,
    val kinopoisk_id:String,
    val created:String,
    val released:String,
    val updated:String,
    val blocked:Int,
    val content_type:String,
    val preview_iframe_src:String,
    val iframe_src:String,
    val year:String
)