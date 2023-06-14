package com.emikhalets.core

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource

@Composable
fun toast(message: String) {
    Toast.makeText(LocalContext.current, message, Toast.LENGTH_SHORT).show()
}

@Composable
fun toast(@StringRes stringRes: Int) {
    Toast.makeText(LocalContext.current, stringResource(stringRes), Toast.LENGTH_SHORT).show()
}