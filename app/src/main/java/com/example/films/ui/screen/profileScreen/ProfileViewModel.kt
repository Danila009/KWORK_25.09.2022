package com.example.films.ui.screen.profileScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.films.data.database.user.UserDataSource
import com.example.films.data.network.freekassa.FreekassaRepository
import com.example.films.data.network.freekassaOrder.FreekassaOrderRepository
import com.example.films.data.network.freekassaOrder.model.FreekassaOrderBody
import com.example.films.data.network.freekassaOrder.model.FreekassaOrderResponse
import com.example.films.data.network.model.*
import com.example.films.data.network.subscription.SubscriptionRepository
import com.example.films.data.network.subscription.model.Subscription
import com.example.films.data.network.user.UserRepository
import com.example.films.data.network.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userDataSource: UserDataSource,
    private val userRepository: UserRepository,
    private val freekassaRepository: FreekassaRepository,
    private val freekassaOrderRepository: FreekassaOrderRepository,
    private val subscriptionRepository: SubscriptionRepository
):ViewModel() {

    val getToken = userDataSource.getToken()

    val getUserRole = userDataSource.getRole()

    private val _responseAuthorizationResponse = MutableStateFlow<Result<AuthorizationResponse>?>(null)
    val responseAuthorizationResponse = _responseAuthorizationResponse.asStateFlow()

    private val _responseRegistrationResponse = MutableStateFlow<Result<RegistrationResponse>?>(null)
    val responseRegistrationResponse = _responseRegistrationResponse.asStateFlow()

    private val _responseUser = MutableStateFlow<Result<User>>(Result.Loading())
    val responseUser = _responseUser.asStateFlow()

    private val _responseFreekassaData = MutableStateFlow<Result<FreekassaData>?>(null)
    val responseFreekassaData = _responseFreekassaData.filterNotNull()

    private val _responseSubscriptionMain = MutableStateFlow<Result<Subscription>>(Result.Loading())
    val responseSubscriptionMain = _responseSubscriptionMain.asStateFlow()

    fun authorization(body: AuthorizationBody){
        viewModelScope.launch {
            if (body.login.isEmpty() && body.password.isEmpty()){
                _responseAuthorizationResponse.value = Result.Error(
                    message = "Заполните все поля."
                )
            }else {
                val response = userRepository.authorization(body)
                _responseAuthorizationResponse.value = response
            }
        }
    }

    fun authorizationClear(){
        viewModelScope.launch {
            _responseAuthorizationResponse.value = null
        }
    }

    fun registration(body:RegistrationBody){
        viewModelScope.launch {
            if (body.login.isEmpty() && body.password.isEmpty() && body.username.isEmpty()){
                _responseRegistrationResponse.value = Result.Error(
                    message = "Заполните все поля."
                )
            }else {
                val response = userRepository.registration(body)
                _responseRegistrationResponse.value = response
            }
        }
    }

    fun registrationClear(){
        viewModelScope.launch {
            _responseRegistrationResponse.value = null
        }
    }

    fun getUser() {
        viewModelScope.launch {
            val response = userRepository.getUser()
            _responseUser.value = response
        }
    }

    fun getFreekassaByShopId(shopId:Int){
        viewModelScope.launch {
            if (shopId == 0){
                _responseFreekassaData.value = null
                return@launch
            }

            val response = freekassaRepository.getFreekassaByShopId(shopId = shopId)
            _responseFreekassaData.value = response
        }
    }

    fun patchSubscription(subscription:Boolean){
        viewModelScope.launch {
            try {
                userRepository.patchSubscription(subscription)
            }catch (e:Exception){}
        }
    }

    suspend fun getOrders(body: FreekassaOrderBody): Result<FreekassaOrderResponse> {
        return freekassaOrderRepository.getOrders(body = body)
    }

    fun getSubscriptionMain(){
        viewModelScope.launch {
            val response = subscriptionRepository.getSubscriptionMain()
            _responseSubscriptionMain.value = response
        }
    }

    fun saveToken(token:String?) = userDataSource.saveToken(token)

    fun saveLogin(login:String) = userDataSource.saveLogin(login)

    fun savePassword(password:String) = userDataSource.savePassword(password)

    fun saveUserRole(userRole: UserRole) = userDataSource.saveRole(userRole)
}