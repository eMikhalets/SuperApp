package com.emikhalets.core.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emikhalets.core.R

private val nunitoSans = FontFamily(
    Font(R.font.nunito_sans_regular, FontWeight.Normal),
    Font(R.font.nunito_sans_medium, FontWeight.Medium),
    Font(R.font.nunito_sans_semibold, FontWeight.SemiBold),
    Font(R.font.nunito_sans_bold, FontWeight.Bold),
)

val Typography = Typography(
    h1 = TextStyle(
        fontFamily = nunitoSans,
        fontWeight = FontWeight.Bold,
        fontSize = 60.sp,
        letterSpacing = (-1.5).sp
    ),
    h2 = TextStyle(
        fontFamily = nunitoSans,
        fontWeight = FontWeight.Bold,
        fontSize = 40.sp,
        letterSpacing = (-0.5).sp
    ),
    h3 = TextStyle(
        fontFamily = nunitoSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 32.sp,
        letterSpacing = 0.sp
    ),
    h4 = TextStyle(
        fontFamily = nunitoSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp,
        letterSpacing = 0.25.sp
    ),
    h5 = TextStyle(
        fontFamily = nunitoSans,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
        letterSpacing = 0.sp
    ),
    h6 = TextStyle(
        fontFamily = nunitoSans,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        letterSpacing = 0.15.sp
    ),
    subtitle1 = TextStyle(
        fontFamily = nunitoSans,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
    ),
    body1 = TextStyle(
        fontFamily = nunitoSans,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
    ),
    body2 = TextStyle(
        fontFamily = nunitoSans,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    ),
    button = TextStyle(
        fontFamily = nunitoSans,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        letterSpacing = 1.25.sp
    ),
    caption = TextStyle(
        fontFamily = nunitoSans,
        fontWeight = FontWeight.Light,
        fontSize = 12.sp,
        letterSpacing = 0.4.sp
    ),
    overline = TextStyle(
        fontFamily = nunitoSans,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
        letterSpacing = 1.5.sp
    )
)

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        Column {
            Text(
                text = "H1 text",
                style = MaterialTheme.typography.h1,
                modifier = Modifier.padding(8.dp, 4.dp)
            )
            Text(
                text = "H2 text",
                style = MaterialTheme.typography.h2,
                modifier = Modifier.padding(8.dp, 4.dp)
            )
            Text(
                text = "H3 text",
                style = MaterialTheme.typography.h3,
                modifier = Modifier.padding(8.dp, 4.dp)
            )
            Text(
                text = "H4 text",
                style = MaterialTheme.typography.h4,
                modifier = Modifier.padding(8.dp, 4.dp)
            )
            Text(
                text = "H5 text",
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(8.dp, 4.dp)
            )
            Text(
                text = "H6 text",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(8.dp, 4.dp)
            )
            Text(
                text = "Subtitle1 text",
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.padding(8.dp, 4.dp)
            )
            Text(
                text = "Subtitle2 text",
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier.padding(8.dp, 4.dp)
            )
            Text(
                text = "Body1 text",
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(8.dp, 4.dp)
            )
            Text(
                text = "Body2 text",
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(8.dp, 4.dp)
            )
            Text(
                text = "Button text",
                style = MaterialTheme.typography.button,
                modifier = Modifier.padding(8.dp, 4.dp)
            )
            Text(
                text = "Caption text",
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(8.dp, 4.dp)
            )
            Text(
                text = "Overline text",
                style = MaterialTheme.typography.overline,
                modifier = Modifier.padding(8.dp, 4.dp)
            )
        }
    }
}