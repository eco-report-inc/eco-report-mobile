package com.capstone.ecoreport.ui.screen

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import com.capstone.ecoreport.R
import com.capstone.ecoreport.data.models.ReportData
import com.capstone.ecoreport.dl.Injection
import com.capstone.ecoreport.model.Dummy
import com.capstone.ecoreport.navigation.Screen
import com.capstone.ecoreport.ui.common.UiState
import com.capstone.ecoreport.ui.item.DummyItem
import com.capstone.ecoreport.ui.item.EmptyList
import com.capstone.ecoreport.ui.item.ReportItem
import com.capstone.ecoreport.ui.theme.EcoReportTheme
import com.capstone.ecoreport.ui.viewmodel.HomeViewModel
import com.capstone.ecoreport.ui.viewmodel.ViewModelFactory
import java.time.temporal.TemporalQuery
import androidx.compose.runtime.LaunchedEffect

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository(),
            Injection.provideProfilePhotoRepository()
        )
    ),
    navigateToDetail: (String) -> Unit,
    navigateToCamera: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = viewModel) {
        viewModel.getAllReports()
    }

    when (uiState) {
        is UiState.Loading -> {
            // Handle loading state
        }
        is UiState.Success -> {
            HomeContent(
                listReport = (uiState as UiState.Success<List<ReportData>>).data,
                navigateToDetail = navigateToDetail,
                navigateToCamera = navigateToCamera
            )
        }
        is UiState.Error -> {
            // Handle error state
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    listReport: List<ReportData>,
    navigateToDetail: (String) -> Unit,
    navigateToCamera: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box {
        Column {
            if (listReport.isNotEmpty()) {
                ListReport(
                    listReport = listReport,
                    navigateToDetail = navigateToDetail,
                )
            } else {
                EmptyList(
                    warning = stringResource(R.string.empty_data),
                )
            }
        }
        FloatingActionButton(
            onClick = { navigateToCamera() },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Open Camera",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListReport(
    listReport: List<ReportData>,
    navigateToDetail: (String) -> Unit,
    contentPaddingTop: Dp = 0.dp
) {
    LazyColumn(
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            bottom = 16.dp,
            top = contentPaddingTop
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(listReport, key = { it.reportId }) { report ->
            ReportItem(
                report = report,
                modifier = Modifier
                    .animateItemPlacement(tween(durationMillis = 200))
                    .clickable { navigateToDetail(report.reportId.toString())  }
            )
        }
    }
}
@Preview(
    showBackground = true,
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE
)
@Composable
fun HomeScreenPreview() {
    EcoReportTheme {
        HomeScreen(navigateToDetail = {}, navigateToCamera = {})
    }
}