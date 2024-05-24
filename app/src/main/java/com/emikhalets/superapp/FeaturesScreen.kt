package com.emikhalets.superapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.emikhalets.superapp.core.ui.AppFeature
import com.emikhalets.superapp.core.ui.component.AppButtonTopIcon
import com.emikhalets.superapp.core.ui.extentions.ScreenPreview
import com.emikhalets.superapp.core.ui.getApplicationFeatures
import com.emikhalets.superapp.core.ui.theme.AppTheme

@Composable
fun FeaturesScreen(
    navigateToFeature: (feature: AppFeature) -> Unit,
) {
    val features = remember { getApplicationFeatures() }
    ScreenContent(
        features = features,
        onFeatureClick = navigateToFeature,
    )
}

@Composable
private fun ScreenContent(
    features: List<AppFeature>,
    onFeatureClick: (AppFeature) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp)
    ) {
        items(features) { feature ->
            FeatureButton(
                feature = feature,
                onClick = { onFeatureClick(feature) },
            )
        }
    }
}

@Composable
private fun FeatureButton(
    feature: AppFeature,
    onClick: (AppFeature) -> Unit,
) {
    AppButtonTopIcon(
        text = stringResource(feature.nameRes),
        icon = feature.icon,
        onClick = { onClick(feature) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 16.dp, 16.dp, 0.dp)
    )
}

@ScreenPreview
@Composable
private fun Preview() {
    AppTheme {
        ScreenContent(
            features = getApplicationFeatures(),
            onFeatureClick = {},
        )
    }
}
