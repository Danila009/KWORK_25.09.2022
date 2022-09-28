package com.example.films.data.network.model

data class MovieInfo(
    val kinopoiskId:Int = 0,
    val imdbId:String? = "",
    val nameRu:String = "",
    val nameEn:String? = "",
    val nameOriginal:String? = "",
    val posterUrl:String? = null,
    val posterUrlPreview:String = "",
    val logoUrl:String? = null,
    val coverUrl:String? = null,
    val reviewsCount:Int = 0,
    val ratingGoodReview:Float = 0f,
    val ratingGoodReviewVoteCount:Int = 0,
    val ratingKinopoisk:Float = 0f,
    val ratingKinopoiskVoteCount:String = "",
    val ratingImdb:Float = 0f,
    val ratingImdbVoteCount:String = "",
    val ratingFilmCritics:Float = 0f,
    val ratingFilmCriticsVoteCount:String = "",
    val ratingAwait:Float = 0f,
    val ratingAwaitCount:Int = 0,
    val ratingRfCritics:Float = 0f,
    val ratingRfCriticsVoteCount:Int = 0,
    val webUrl:String? = null,
    val year:Int = 0,
    val filmLength:Int = 0,
    val slogan:String? = null,
    val description:String? = null,
    val shortDescription:String = "",
    val editorAnnotation:String? = "",
    val isTicketsAvailable:Boolean = false,
    val productionStatus:String = "",
    val type:String = "",
    val ratingMpaa:String = "",
    val ratingAgeLimits:String = "",
    val hasImax:Boolean = false,
    val has3D:Boolean = false,
    val lastSync:String = "",
    val countries:List<Countrie> = listOf(),
    val genres:List<Genre> = listOf(),
    val startYear:Int = 0,
    val endYear:Int = 0,
    val serial:Boolean = false,
    val shortFilm:Boolean = false,
    val completed:Boolean = false
)

data class Countrie(
    val id:Int? = null,
    val country:String
)

data class Genre(
    val id:Int? = null,
    val genre:String
)