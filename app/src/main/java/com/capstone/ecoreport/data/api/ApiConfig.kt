package com.capstone.ecoreport.data.api

import android.content.Context
import com.capstone.ecoreport.BuildConfig
import com.capstone.ecoreport.ui.auth.AuthManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    fun createApiService(context: Context): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getOkHttpClient(context))  // Tambahkan ini jika Anda menggunakan interceptor atau konfigurasi lain yang memerlukan context
            .build()

        return retrofit.create(ApiService::class.java)
    }

    private fun getOkHttpClient(context: Context): OkHttpClient {
        // Konfigurasi OkHttpClient jika diperlukan (seperti interceptor, dll.)
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                // Implementasi interceptor jika diperlukan
                val request = chain.request().newBuilder()
                    // ... konfigurasi lainnya ...
                    .build()
                chain.proceed(request)
            }
            .build()
    }
}
