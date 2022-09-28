package com.example.films.data.network.model

import kotlinx.serialization.Serializable

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
    val media:List<MovieMedia>,
    val translations:List<MovieTranslations>,
    val year:String
)

data class MovieTranslations(
    val id:Int,
    val title:String,
    val priority:Int,
    val iframe_src:String,
    val iframe:String,
    val short_title:String,
    val smart_title:String,
    val shorter_title:String
)

data class MovieMedia(
    val id:Int,
    val translation_id:Int,
    val path:String,
    val translation:MovieTranslations,
    val qualities:List<MovieQualities>
)

data class MovieQualities(
    val url:String,
    val resolution:Int
)

@Serializable
data class MovieMediaShort(
    val qualities:List<MovieQualitiesShort>,
    val translation:MovieTranslationsShort
)

@Serializable
data class MovieQualitiesShort(
    val url:String,
    val resolution:Int
)

@Serializable
data class MovieTranslationsShort(
    val title: String
)