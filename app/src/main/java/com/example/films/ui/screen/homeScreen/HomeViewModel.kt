package com.example.films.ui.screen.homeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.films.data.network.model.MovieItem
import com.example.films.data.network.movies.repository.MoviesRepository
import com.example.films.data.pagingSource.MoviesPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
):ViewModel() {

    fun getMovies(
        query:String?
    ): Flow<PagingData<MovieItem>> {
        return Pager(PagingConfig(pageSize = 20)){
            MoviesPagingSource(
                query = query,
                moviesRepository = moviesRepository
            )
        }.flow.cachedIn(viewModelScope)
    }
}