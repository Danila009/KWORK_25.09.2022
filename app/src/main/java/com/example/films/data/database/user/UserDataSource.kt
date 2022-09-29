package com.example.films.data.database.user

import android.content.Context
import com.example.films.data.network.model.UserRole
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class UserDataSource @Inject constructor(
    @ApplicationContext context: Context
) {
    val userShared = context.getSharedPreferences(
        USER_SHARED_KEY,Context.MODE_PRIVATE
    )

    fun saveToken(token:String?){
        userShared.edit()
            .putString(USER_TOKEN_KEY,token)
            .apply()
    }

    fun getToken():String? {
        return userShared.getString(USER_TOKEN_KEY,null)
    }

    fun saveLogin(login:String){
        userShared.edit()
            .putString(USER_LOGIN_KEY,login)
            .apply()
    }

    fun getLogin():String? {
        return userShared.getString(USER_LOGIN_KEY,null)
    }

    fun savePassword(password:String){
        userShared.edit()
            .putString(USER_PASSWORD_KEY,password)
            .apply()
    }

    fun getPassword():String?{
        return userShared.getString(USER_PASSWORD_KEY,null)
    }

    fun saveRole(role: UserRole){
        userShared.edit()
            .putString(USER_ROLE_KEY,role.name)
            .apply()
    }

    fun getRole():UserRole {
        return enumValueOf(
            userShared.getString(
                USER_ROLE_KEY,
                UserRole.BASE_USER.name
            ) ?: UserRole.BASE_USER.name
        )
    }

    companion object {
        const val USER_SHARED_KEY = "user_shared_key"
        const val USER_TOKEN_KEY = "user_token_key"
        const val USER_LOGIN_KEY = "user_login_key"
        const val USER_PASSWORD_KEY = "user_password_key"
        const val USER_ROLE_KEY = "user_role_key"
    }
}