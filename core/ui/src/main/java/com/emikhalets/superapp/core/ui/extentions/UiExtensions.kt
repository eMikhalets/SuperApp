package com.emikhalets.superapp.core.ui.extentions

import android.content.Context
import android.widget.Toast
import com.emikhalets.superapp.core.common.StringValue
import com.emikhalets.superapp.core.common.StringValue.Companion.asString

fun toast(context: Context?, massage: StringValue) {
    context ?: return
    Toast.makeText(context, massage.asString(context), Toast.LENGTH_SHORT).show()
}