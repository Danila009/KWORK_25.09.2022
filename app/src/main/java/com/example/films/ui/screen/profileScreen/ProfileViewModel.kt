package com.example.films.ui.screen.profileScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.films.data.database.user.UserDataSource
import com.example.films.data.network.model.*
import com.example.films.data.network.user.UserRepository
import com.example.films.data.network.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userDataSource: UserDataSource,
    private val userRepository: UserRepository,
):ViewModel() {

    val getToken = userDataSource.getToken()

    val getUserRole = userDataSource.getRole()

    private val _responseAuthorizationResponse = MutableStateFlow<Result<AuthorizationResponse>?>(null)
    val responseAuthorizationResponse = _responseAuthorizationResponse.asStateFlow()

    private val _responseRegistrationResponse = MutableStateFlow<Result<RegistrationResponse>?>(null)
    val responseRegistrationResponse = _responseRegistrationResponse.asStateFlow()

    private val _responseUser = MutableStateFlow<Result<User>>(Result.Loading())
    val responseUser = _responseUser.asStateFlow()

    fun authorization(body: AuthorizationBody){
        viewModelScope.launch {
            val response = userRepository.authorization(body)
            _responseAuthorizationResponse.value = response
        }
    }

    fun registration(body:RegistrationBody){
        viewModelScope.launch {
            val response = userRepository.registration(body)
            _responseRegistrationResponse.value = response
        }
    }

    fun getUser() {
        viewModelScope.launch {
            val response = userRepository.getUser()
            _responseUser.value = response
        }
    }

    fun saveToken(token:String?) = userDataSource.saveToken(token)

    fun saveLogin(login:String) = userDataSource.saveLogin(login)

    fun savePassword(password:String) = userDataSource.savePassword(password)

    fun saveUserRole(userRole: UserRole) = userDataSource.saveRole(userRole)
}