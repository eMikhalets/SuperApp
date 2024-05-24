package com.emikhalets.core.superapp.ui.extentions

import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "Main Theme Screen",
    showBackground = true,
    device = Devices.PIXEL_4
)
annotation class ScreenPreview

@Preview(
    name = "Main Theme Screen",
    showBackground = true,
    device = "spec:width=411dp,height=891dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"


)
annotation class ScreenLandPreview

@Preview(name = "Main Theme Box", showBackground = true)
annotation class BoxPreview