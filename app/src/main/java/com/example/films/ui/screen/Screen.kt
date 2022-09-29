package com.example.films.ui.screen

sealed class Screen(val route:String) {
    object HomeScreen:Screen("home_screen")
    object ProfileScreen:Screen("profile_screen")
    object CreateAdvertising:Screen("create_advertising_screen")
    object AddAdminScreen:Screen("add_admin_screen")
    object WatchMovieScreen:Screen("watch_movie_screen?url={url}"){
        fun arguments(
            url:String
        ):String = "watch_movie_screen?url=$url"
    }
    object FilmInfoScreen:Screen(
        "film_info_screen/{id}?kinopoiskId={kinopoiskId}&imdbId={imdbId}"
    ){
        fun arguments(
            id:Int,
            kinopoiskId:String,
            imdbId:String
        ):String = "film_info_screen/$id?kinopoiskId=$kinopoiskId&imdbId=$imdbId"
    }
}