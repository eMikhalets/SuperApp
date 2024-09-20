package com.emikhalets.superapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.emikhalets.superapp.core.ui.AppFeature
import com.emikhalets.superapp.core.ui.component.ButtonPrimaryTopIcon
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
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier.fillMaxSize()
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
    ButtonPrimaryTopIcon(
        text = stringResource(feature.nameRes),
        icon = feature.icon,
        onClick = { onClick(feature) },
        modifier = Modifier
            .fillMaxWidth()
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
