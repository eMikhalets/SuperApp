package com.emikhalets.superapp.core.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Typography = Typography()

val Typography.header
    get() = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold,
        fontFamily = FontFamily.SansSerif,
        lineHeight = 24.sp,
    )

val Typography.title
    get() = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium,
        fontFamily = FontFamily.SansSerif,
        lineHeight = 22.sp,
        letterSpacing = 0.4.sp
    )

val Typography.body1
    get() = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = FontFamily.SansSerif,
        lineHeight = 20.sp,
    )

val Typography.body2
    get() = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = FontFamily.SansSerif,
        lineHeight = 18.sp,
    )

val Typography.button
    get() = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        fontFamily = FontFamily.SansSerif,
        lineHeight = 16.sp,
        letterSpacing = 0.2.sp
    )

val Typography.caption
    get() = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = FontFamily.SansSerif,
        lineHeight = 14.sp,
        letterSpacing = 0.4.sp
    )