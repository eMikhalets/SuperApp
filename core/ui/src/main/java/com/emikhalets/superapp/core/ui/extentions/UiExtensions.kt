package com.emikhalets.superapp.core.ui.extentions

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.superapp.core.common.StringValue
import com.emikhalets.superapp.core.common.StringValue.Companion.asString
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.Locale

fun toast(context: Context?, massage: StringValue) {
    context ?: return
    Toast.makeText(context, massage.asString(context), Toast.LENGTH_SHORT).show()
}

fun String.capitalize(): String {
    return buildString {
        this@capitalize.forEachIndexed { index, item ->
            if (index == 0) append(item.titlecase(Locale.getDefault()))
            else append(item.lowercase(Locale.getDefault()))
        }
    }
}

fun ViewModel.launch(block: suspend CoroutineScope.() -> Unit): Job {
    return viewModelScope.launch { block() }
}

fun ViewModel.launchMain(block: suspend CoroutineScope.() -> Unit): Job {
    return viewModelScope.launch(Dispatchers.Main) { block() }
}

fun ViewModel.launchDefault(block: suspend CoroutineScope.() -> Unit): Job {
    return viewModelScope.launch(Dispatchers.Default) { block() }
}

fun ViewModel.launchIO(block: suspend CoroutineScope.() -> Unit): Job {
    return viewModelScope.launch(Dispatchers.IO) { block() }
}