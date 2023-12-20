package com.capstone.ecoreport.core.utils

import android.content.Context
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object LocationUtils {

    private const val LOCATION_REQUEST_INTERVAL = 1000L
    private const val LOCATION_REQUEST_FASTEST_INTERVAL = 500L

    fun isLocationPermissionGranted(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    suspend fun requestLocationPermission(
        context: Context,
        launcher: ActivityResultLauncher<String>
    ): Boolean {
        if (!isLocationPermissionGranted(context)) {
            launcher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }
        return true
    }

    suspend fun getLastKnownLocation(context: Context): Location? {
        if (isLocationPermissionGranted(context)) {
            val fusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(context)

            // Check for permission before accessing lastLocation
            if (isLocationPermissionGranted(context)) {
                try {
                    // Use a callback to get the last known location
                    return suspendCoroutine { continuation ->
                        fusedLocationProviderClient.lastLocation
                            .addOnSuccessListener { location: Location? ->
                                continuation.resume(location)
                            }
                            .addOnFailureListener { e: Exception ->
                                e.printStackTrace()
                                continuation.resume(null)
                            }
                    }
                } catch (e: SecurityException) {
                    e.printStackTrace()
                    return null
                }
            }
        }
        return null
    }

    @Throws(IntentSender.SendIntentException::class, ResolvableApiException::class)
    fun buildLocationRequest(): LocationRequest {
        return LocationRequest.create().apply {
            interval = LOCATION_REQUEST_INTERVAL
            fastestInterval = LOCATION_REQUEST_FASTEST_INTERVAL
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    fun buildLocationSettingsRequest(locationRequest: LocationRequest): LocationSettingsRequest {
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
        return builder.build()
    }
}

