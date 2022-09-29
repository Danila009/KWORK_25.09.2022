package com.example.films.ui.screen.homeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.films.data.network.advertising.AdvertisingRepository
import com.example.films.data.network.model.Advertising
import com.example.films.data.network.model.MovieItem
import com.example.films.data.network.model.User
import com.example.films.data.network.movies.repository.MoviesRepository
import com.example.films.data.network.user.UserRepository
import com.example.films.data.pagingSource.MoviesPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val advertisingRepository: AdvertisingRepository,
    private val userRepository: UserRepository
):ViewModel() {

    private val _responseAdvertising = MutableStateFlow<Advertising?>(null)
    val responseAdvertising = _responseAdvertising.filterNotNull()

    private val _responseUser = MutableStateFlow<User?>(null)
    val responseUser = _responseUser.filterNotNull()

    fun getUser(){
        viewModelScope.launch {
            try {
                val response = userRepository.getUser()
                response.data?.let {
                    _responseUser.value = it
                }
            }catch (e:Exception){}
        }
    }

    fun getAdvertising(){
        viewModelScope.launch {
            try {
                val response = advertisingRepository.getAdvertisingRandom()
                _responseAdvertising.value = response
            }catch (e:Exception){}
        }
    }

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