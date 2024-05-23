package com.emikhalets.core.superapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.emikhalets.superapp.core.common.*
import com.emikhalets.superapp.core.common.StringValue

@Composable
fun StringValue?.asString(): String {
    val internal = stringResource(R.string.common_internal_error)
    return when (this) {
        StringValue.Empty -> internal
        StringValue.InternalError -> internal
        is StringValue.Message -> text ?: internal
        is StringValue.Exception -> throwable.message ?: internal
        is StringValue.Resource -> stringResource(resId, *args)
        else -> internal
    }
}