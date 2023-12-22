package com.capstone.ecoreport.ui.item

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import com.capstone.ecoreport.data.models.ReportData

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
                    .clickable { navigateToDetail(report.reportId) }
            )
        }
    }
}