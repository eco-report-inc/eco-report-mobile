package com.capstone.ecoreport.ui.auth

import android.content.Context
import android.content.SharedPreferences

class AuthManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean("isLoggedIn", false)
    }

    fun getAuthToken(): String? {
        return prefs.getString("token", null)
    }
    fun clearAuthToken() {
        with(prefs.edit()) {
            remove("token")
            apply()
        }
    }
}