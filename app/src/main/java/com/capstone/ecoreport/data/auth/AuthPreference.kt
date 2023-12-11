package com.capstone.ecoreport.data.auth

import android.content.Context
import android.content.SharedPreferences

class AuthPreference(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        "AuthPreference",
        Context.MODE_PRIVATE
    )
    fun saveAuthToken(token: String) {
        with(sharedPreferences.edit()) {
            putString("token", token)
            apply() // Pastikan menggunakan apply()
        }
    }
    fun getAuthToken(): String? {
        return sharedPreferences.getString("token", null)
    }
    fun clearAuthTokenPref() {
        with(sharedPreferences.edit()) {
            remove("token")
            apply()
        }
    }
}
