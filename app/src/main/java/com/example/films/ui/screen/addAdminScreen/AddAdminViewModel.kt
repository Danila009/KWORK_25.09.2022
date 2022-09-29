package com.example.films.ui.screen.addAdminScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.films.data.network.model.User
import com.example.films.data.network.user.UserRepository
import com.example.films.data.network.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddAdminViewModel @Inject constructor(
    private val userRepository: UserRepository
):ViewModel() {

    private val _responseUsers = MutableStateFlow<Result<List<User>>>(Result.Loading())
    val responseUser = _responseUsers.asStateFlow()

    fun getUsers(){
        viewModelScope.launch {
            _responseUsers.value = userRepository.getUsers()
        }
    }

    fun postAdmin(userId:Int) {
        viewModelScope.launch {
            try {
                userRepository.postAdmin(userId)
            }catch (e:Exception){ }
        }
    }
}