package com.example.films

import android.app.Application
import com.example.films.data.database.user.UserDataSource
import com.example.films.data.network.model.AuthorizationBody
import com.example.films.data.network.user.UserRepository
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class FilmsApp:Application() {

    @Inject
    lateinit var userRepository: UserRepository

    @Inject
    lateinit var userDataSource: UserDataSource

    override fun onCreate() {
        super.onCreate()

        updateUserToken()
    }

    private fun updateUserToken() = CoroutineScope(Dispatchers.IO).launch {
        val login = userDataSource.getLogin() ?: return@launch
        val password = userDataSource.getPassword() ?: return@launch

        val authorizationResponse = userRepository.authorization(
            body = AuthorizationBody(
                login = login,
                password = password
            )
        )

        authorizationResponse.data ?: return@launch

        userDataSource.saveToken(
            token = authorizationResponse.data.access_token
        )
    }
}