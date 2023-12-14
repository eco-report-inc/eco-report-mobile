package com.capstone.ecoreport.ui.screen

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.capstone.ecoreport.R
import com.capstone.ecoreport.dl.Injection
import com.capstone.ecoreport.model.Dummy
import com.capstone.ecoreport.ui.common.UiState
import com.capstone.ecoreport.ui.item.DummyItem
import com.capstone.ecoreport.ui.item.EmptyList
import com.capstone.ecoreport.ui.theme.EcoReportTheme
import com.capstone.ecoreport.ui.viewmodel.HomeViewModel
import com.capstone.ecoreport.ui.viewmodel.ViewModelFactory

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Int) -> Unit,
) {
    val query by viewModel.query
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.search(query)
            }
            is UiState.Success -> {
                HomeContent(
                    query = query,
                    onQueryChange = viewModel::search,
                    listDummy = uiState.data,
                    navigateToDetail = navigateToDetail
                )
            }
            is UiState.Error -> {}
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    query: String,
    onQueryChange: (String) -> Unit,
    listDummy: List<Dummy>,
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        SearchBar(
            query = query,
            onQueryChange = onQueryChange,
            onSearch = {},
            active = false,
            onActiveChange = {},
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            placeholder = {
                Text(stringResource(R.string.search_user))
            },
            shape = MaterialTheme.shapes.large,
            modifier = modifier
                .padding(16.dp)
                .fillMaxWidth()
                .heightIn(min = 48.dp)
        ) {


        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListDummy(
    listDummy: List<Dummy>,
    navigateToDetail: (Int) -> Unit,
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
        items(listDummy, key = { it.id }) { item ->
            DummyItem(
                id = item.id,
                username = item.username,
                userImage = item.userImage,
                modifier = Modifier
                    .animateItemPlacement(tween(durationMillis = 200))
                    .clickable { navigateToDetail(item.id) }
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
        HomeScreen(navigateToDetail = {})
    }
}