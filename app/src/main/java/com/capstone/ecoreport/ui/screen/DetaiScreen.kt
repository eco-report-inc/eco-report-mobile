package com.capstone.ecoreport.ui.screen

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.capstone.ecoreport.dl.Injection
import com.capstone.ecoreport.model.DummyData
import com.capstone.ecoreport.ui.common.UiState
import com.capstone.ecoreport.ui.viewmodel.DetailViewModel
import com.capstone.ecoreport.ui.viewmodel.ViewModelFactory

@Composable
fun DetailScreen(
    dummyId: Int,
    navigateBack: () -> Unit,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    )
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getDummyById(dummyId)
            }

            is UiState.Success -> {
                val data = uiState.data
                DetailInfo(
                    id = data.id,
                    username = data.username,
                    userImage = data.userImage,
                    photo = data.photo,
                    name = data.name,
                    description = data.description,
                    releaseDate = data.release,
                    lat = data.lat,
                    lon = data.lon,
                    navigateBack = navigateBack,
                )
            }

            is UiState.Error -> {}
        }
    }
}

@Composable
fun DetailInfo(
    id: Int,
    username: String,
    @DrawableRes userImage: Int,
    @DrawableRes photo: Int,
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
        // Header Text
        Text(
            text = name,
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Photo
        Image(
            painter = painterResource(id = photo),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(shape = RoundedCornerShape(8.dp))
        )

        // Description Text
        Text(
            text = description,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        // Google Maps
        GoogleMaps(lat = lat, lon = lon, modifier = Modifier.fillMaxWidth().height(200.dp))

        // Reported By Text
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            // User Image
            Image(
                painter = painterResource(id = userImage),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
            )

            // User Name
            Text(
                text = "Dilaporkan oleh: $username",
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

@Composable
fun GoogleMaps(lat: Double, lon: Double, modifier: Modifier = Modifier) {
    // Implementasi tampilan Google Maps berdasarkan lat dan lon
    // (Anda dapat menggunakan pustaka seperti Google Maps Android API atau komponen lainnya)
}