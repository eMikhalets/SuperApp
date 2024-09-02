package com.emikhalets.superapp.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.emikhalets.superapp.core.common.R
import com.emikhalets.superapp.core.common.StringValue

@Composable
fun StringValue?.asString(): String {
    val internal = stringResource(R.string.error_internal)
    return when (this) {
        StringValue.Empty -> internal
        StringValue.InternalError -> internal
        is StringValue.Message -> text ?: internal
        is StringValue.Exception -> throwable?.message ?: internal
        is StringValue.Resource -> stringResource(resId, *args)
        else -> internal
    }
}