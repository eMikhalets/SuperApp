package com.emikhalets.core.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emikhalets.core.ui.BoxPreview
import com.emikhalets.core.ui.R

private val nunitoSans = FontFamily(
    Font(R.font.nunito_sans_regular, FontWeight.Normal),
    Font(R.font.nunito_sans_medium, FontWeight.Medium),
    Font(R.font.nunito_sans_semibold, FontWeight.SemiBold),
    Font(R.font.nunito_sans_bold, FontWeight.Bold),
)

val Typography = Typography(
    defaultFontFamily = nunitoSans
)

val Typography.text
    get() = TextStyle(
        fontFamily = nunitoSans,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = 0.5.sp
    )

val Typography.textSub
    get() = TextStyle(
        fontFamily = nunitoSans,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        letterSpacing = 0.3.sp
    )

val Typography.textTitle
    get() = TextStyle(
        fontFamily = nunitoSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        letterSpacing = 0.3.sp
    )

val Typography.textHeader
    get() = TextStyle(
        fontFamily = nunitoSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        letterSpacing = 0.3.sp
    )

val Typography.textLabel
    get() = TextStyle(
        fontFamily = nunitoSans,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        letterSpacing = 0.3.sp
    )

@BoxPreview
@Composable
private fun Preview() {
    AppTheme {
        Column(
            Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            Text(
                text = "Custom textTitle",
                style = MaterialTheme.typography.textTitle,
                modifier = Modifier.padding(8.dp, 4.dp)
            )
            Text(
                text = "Custom textHeader",
                style = MaterialTheme.typography.textHeader,
                modifier = Modifier.padding(8.dp, 4.dp)
            )
            Text(
                text = "Custom textLabel",
                style = MaterialTheme.typography.textLabel,
                modifier = Modifier.padding(8.dp, 4.dp)
            )
            Text(
                text = "Custom text",
                style = MaterialTheme.typography.text,
                modifier = Modifier.padding(8.dp, 4.dp)
            )
            Text(
                text = "Custom textSub",
                style = MaterialTheme.typography.textSub,
                modifier = Modifier.padding(8.dp, 4.dp)
            )
        }
    }
}