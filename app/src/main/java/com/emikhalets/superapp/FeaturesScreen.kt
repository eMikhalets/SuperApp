package com.emikhalets.superapp

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emikhalets.core.superapp.ui.AppFeature
import com.emikhalets.core.superapp.ui.extentions.ScreenPreview
import com.emikhalets.core.superapp.ui.getApplicationFeatures
import com.emikhalets.core.superapp.ui.theme.AppTheme

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
    LazyColumn(
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
    Button(
        onClick = { onClick(feature) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 16.dp, 16.dp, 0.dp)
    ) {
        Icon(
            imageVector = feature.icon,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = stringResource(feature.nameRes),
            fontSize = 18.sp
        )
    }
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
