package com.capstone.ecoreport.ui.screen

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.capstone.ecoreport.R
import com.capstone.ecoreport.dl.Injection
import com.capstone.ecoreport.ui.common.UiState
import com.capstone.ecoreport.ui.components.GoogleMapsView
import com.capstone.ecoreport.ui.viewmodel.DetailViewModel
import com.capstone.ecoreport.ui.viewmodel.ViewModelFactory

@Composable
fun DetailScreen(
    reportId: String,
    navigateBack: () -> Unit,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository(), Injection.provideProfilePhotoRepository())
    )
) {
    viewModel.getReportById(reportId)
    val uiState = viewModel.uiState.collectAsState().value
    when (uiState) {
        is UiState.Success -> {
            val data = uiState.data
            DetailInfo(
                reportId = data.reportId.toString(),
                username = data.userId,
                userImageUrl = data.images.firstOrNull()?.imageUrl ?: "",
                photoUrl = data.images.getOrNull(1)?.imageUrl ?: "",
                name = data.placeName,
                description = data.createdAt,
                releaseDate = data.updatedAt,
                lat = data.latitude.toDouble(),
                lon = data.longitude.toDouble(),
                navigateBack = navigateBack,
            )
        }
        is UiState.Error -> {
        }
        is UiState.Loading -> {
        }
    }
}

@Composable
fun DetailInfo(
    reportId: String,
    username: String,
    userImageUrl: String,
    photoUrl: String,
    name: String,
    description: String,
    releaseDate: String,
    lat: Double,
    lon: Double,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        IconButton(
            onClick = { navigateBack() },
            modifier = Modifier
                .align(Alignment.Start)
                .padding(top = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back"
            )
        }

        // Header Text
        Text(
            text = name,
            style = MaterialTheme.typography.displaySmall,
            modifier = Modifier
                .padding(bottom = 64.dp, top = 16.dp, start = 8.dp)
                .align(Alignment.Start)
        )

        // Photo
        Image(
            painter = rememberImagePainter(userImageUrl),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(shape = RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

        // Description Text
        Text(
            text = description,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        // Google Maps
        GoogleMapsView(lat = lat, lon = lon, modifier = Modifier.fillMaxWidth().height(200.dp))

        // Reported By Text
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            // User Image
            Image(
                painter = rememberImagePainter(photoUrl),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
                contentScale = ContentScale.Crop
            )

            // User Name
            Text(
                text = "Dilaporkan oleh: $username",
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        // Report ID Text
        Text(
            text = "Report ID: $reportId",
            modifier = Modifier.padding(vertical = 8.dp)
        )
        // Release Date Text
        Text(
            text = "Release Date: $releaseDate",
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}