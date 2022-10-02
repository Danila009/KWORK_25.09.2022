package com.example.films.ui.screen.createAdvertisingScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.films.data.network.advertising.AdvertisingRepository
import com.example.films.data.network.model.Advertising
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

    private val _responseCreateAdvertisingResponse = MutableStateFlow<Result<Advertising>>(Result.Loading())
    val responseCreateAdvertisingResponse = _responseCreateAdvertisingResponse.asStateFlow()

    private val _responseUploadAdvertisingImage = MutableStateFlow<Boolean?>(null)
    val responseUploadAdvertisingImage = _responseUploadAdvertisingImage.asStateFlow()

    fun createAdvertising(body: AdvertisingCreate){
        viewModelScope.launch {
            try {
                val response = advertisingRepository.postAdvertising(body)
                _responseCreateAdvertisingResponse.value = response
            }catch (e:Exception){ }
        }
    }

    fun uploadAdvertisingImage(
        id:Int,
        image:ByteArray
    ){
        viewModelScope.launch {
            val response = advertisingRepository.uploadAdvertisingImage(
                id = id,
                image = image
            )
            _responseUploadAdvertisingImage.value = response
        }
    }
}