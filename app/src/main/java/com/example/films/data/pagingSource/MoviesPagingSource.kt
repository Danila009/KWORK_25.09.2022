package com.example.films.data.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.films.data.network.model.MovieItem
import com.example.films.data.network.movies.repository.MoviesRepository

class MoviesPagingSource(
    private val moviesRepository: MoviesRepository,
    private val query:String?
):PagingSource<Int,MovieItem>() {
    override fun getRefreshKey(state: PagingState<Int, MovieItem>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieItem> {
        return try {
            val nextPage = params.key ?: 1

            val data = moviesRepository.getMovies(
                query = query,
                pageSize = 20,
                page = nextPage
            ).data

            LoadResult.Page(
                data = data,
                prevKey = if (nextPage == 1) null else nextPage -1,
                nextKey = nextPage.plus(1)
            )

        }catch (e:Exception){
            LoadResult.Error(e)
        }
    }
}