package com.example.films.ui.screen.createAdvertisingScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.films.data.network.advertising.AdvertisingRepository
import com.example.films.data.network.model.AdvertisingCreate
import com.example.films.data.network.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateAdvertisingViewModel @Inject constructor(
    private val advertisingRepository: AdvertisingRepository
):ViewModel() {

    private val _responseCreateAdvertisingResponse = MutableStateFlow<Result<Unit?>>(Result.Loading())
    val responseCreateAdvertisingResponse = _responseCreateAdvertisingResponse.asStateFlow()

    fun createAdvertising(body: AdvertisingCreate){
        viewModelScope.launch {
            try {
                val response = advertisingRepository.postAdvertising(body)
                _responseCreateAdvertisingResponse.value = response
            }catch (e:Exception){ }
        }
    }
}