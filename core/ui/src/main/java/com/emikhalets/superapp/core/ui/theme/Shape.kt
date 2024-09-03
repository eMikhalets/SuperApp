package com.emikhalets.superapp.core.ui.theme

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(24.dp),
    extraLarge = RoundedCornerShape(32.dp)
)

val Shapes.rectangle get() = RectangleShape
val Shapes.circle get() = CircleShape
val Shapes.button get() = RoundedCornerShape(8.dp)
val Shapes.textField get() = RoundedCornerShape(4.dp)
val Shapes.dialog get() = RoundedCornerShape(24.dp)
val Shapes.listItemBox get() = RoundedCornerShape(8.dp)