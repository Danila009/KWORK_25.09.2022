package com.example.films.ui.screen.editingAdvertisingScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.films.data.network.advertising.AdvertisingRepository
import com.example.films.data.network.model.Advertising
import com.example.films.data.network.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditingAdvertisingViewModel @Inject constructor(
    private val advertisingRepository: AdvertisingRepository
):ViewModel() {

    private val _responseAdvertising = MutableStateFlow<Result<List<Advertising>>>(Result.Loading())
    val responseAdvertising = _responseAdvertising.asStateFlow()

    private val _responseDeleteAdvertisingResult = MutableStateFlow<Result<Unit?>>(Result.Loading())
    val responseDeleteAdvertisingResult = _responseDeleteAdvertisingResult.asStateFlow()

    fun getAdvertising(){
        viewModelScope.launch {
            val response = advertisingRepository.getAdvertising()
            _responseAdvertising.value = response
        }
    }

    fun deleteAdvertising(id:Int){
        viewModelScope.launch {
            val response = advertisingRepository.deleteAdvertising(id)
            _responseDeleteAdvertisingResult.value = response
        }
    }
}