package com.capstone.ecoreport.ui.components

import android.os.Bundle
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.launch

@Composable
fun GoogleMapsView(lat: Double, lon: Double, modifier: Modifier = Modifier) {
    var mapView: MapView? by remember { mutableStateOf(null) }

    val context = LocalContext.current

    DisposableEffect(Unit) {
        mapView = MapView(context)
        mapView?.onCreate(Bundle())

        onDispose {
            mapView?.onDestroy()
        }
    }

    val coroutineScope = rememberCoroutineScope()

    AndroidView(
        factory = { mapView ?: MapView(context) }, // Gunakan operator Elvis
        modifier = modifier.fillMaxSize()
    ) { mapView ->
        coroutineScope.launch {
            mapView?.getMapAsync { googleMap ->
                val location = LatLng(lat, lon)
                googleMap.addMarker(MarkerOptions().position(location).title("Marker"))
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(location))
            }
        }
    }
}