package com.capstone.ecoreport.data.heatmap

import android.graphics.Color
import com.capstone.ecoreport.data.api.ApiService
import com.capstone.ecoreport.data.models.ReportData
import com.capstone.ecoreport.data.models.ReportResponse
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import com.google.maps.android.heatmaps.Gradient
import com.google.maps.android.heatmaps.HeatmapTileProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class HeatmapManager(private val apiService: ApiService) {
    suspend fun getAllReportsWithQuery(longitude: String, latitude: String): Response<ReportResponse> {
        return apiService.getAllReportsWithQuery(longitude, latitude)
    }
    suspend fun createHeatmap(googleMap: GoogleMap, reportDataList: List<ReportData>) {
        withContext(Dispatchers.Main) {
            if (reportDataList.isNotEmpty()) {
                // Convert reportDataList menjadi List<LatLng> sesuai kebutuhan
                val latLngList: List<LatLng> = reportDataList.map { reportData ->
                    LatLng(reportData.latitude.toDouble(), reportData.longitude.toDouble())
                }
                // Menghitung jumlah laporan di sekitar atau radius tertentu
                val reportCount = reportDataList.size

                // Menghitung radius dalam piksel berdasarkan jarak dalam kilometer
                val radiusInKm = 3
                val earthRadius = 6371 // Radius bumi dalam kilometer
                val visibleRegion = googleMap.projection.visibleRegion
                val radiusInPixels = (radiusInKm * (visibleRegion.farRight.longitude - visibleRegion.farLeft.longitude) /
                        (2 * Math.PI * earthRadius)).toFloat()

                // Menentukan warna berdasarkan jumlah laporan
                val color = when {
                    reportCount >= 10 -> Color.rgb(255, 0, 0)     // Merah jika lebih dari 10 laporan
                    reportCount >= 8 -> Color.rgb(255, 165, 0)    // Orange jika 8-10 laporan
                    reportCount >= 2 -> Color.rgb(255, 255, 0)    // Kuning jika 2-7 laporan
                    reportCount >= 1 -> Color.rgb(0, 128, 0)      // Hijau jika 1-2 laporan
                    else -> Color.TRANSPARENT// Tidak ada heatmap jika kurang dari 1 laporan
                }

                // Buat HeatmapTileProvider dan tambahkan overlay ke peta
                val provider = HeatmapTileProvider.Builder()
                    .data(latLngList)
                    .gradient(Gradient(intArrayOf(color), floatArrayOf(0f, 1f)))
                    .radius(radiusInPixels.toInt())
                    .opacity(0.7)
                    .build()

                // Hapus overlay peta panas jika sudah di tangani
                googleMap.clear()

                // Tambahkan overlay baru
                googleMap.addTileOverlay(TileOverlayOptions().tileProvider(provider))
            }
        }
    }
}
