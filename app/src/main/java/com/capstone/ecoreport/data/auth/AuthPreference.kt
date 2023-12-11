package com.capstone.ecoreport.data.auth

import android.content.Context
import android.content.SharedPreferences

class AuthPreference(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        "AuthPreference",
        Context.MODE_PRIVATE
    )

    companion object {
        private const val AUTH_TOKEN_KEY = "auth_token"
    }

    fun saveAuthToken(token: String) {
        with(sharedPreferences.edit()) {
            putString(AUTH_TOKEN_KEY, token)
            apply()
        }
    }

    fun getAuthToken(): String? {
        return sharedPreferences.getString(AUTH_TOKEN_KEY, null)
    }

    fun clearAuthTokenPref() {
        with(sharedPreferences.edit()) {
            remove(AUTH_TOKEN_KEY)
            apply()
        }
    }
}
