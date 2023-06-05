package com.emikhalets.medialib.presentation.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

class MediaLibBottomBarItem(
    val screen: MediaLibScreen,
    @StringRes val titleRes: Int,
    @DrawableRes val iconRes: Int,
)