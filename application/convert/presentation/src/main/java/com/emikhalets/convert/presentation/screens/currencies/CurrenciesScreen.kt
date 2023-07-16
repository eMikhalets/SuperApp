package com.emikhalets.convert.presentation.screens.currencies

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Save
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emikhalets.convert.domain.R
import com.emikhalets.convert.presentation.CurrencyAmountInputVisualTransformation
import com.emikhalets.convert.presentation.screens.currencies.CurrenciesContract.Action
import com.emikhalets.core.common.ApplicationEntity.Convert.appNameRes
import com.emikhalets.core.common.date.formatFullDate
import com.emikhalets.core.common.date.localDate
import com.emikhalets.core.common.date.timestamp
import com.emikhalets.core.common.logi
import com.emikhalets.core.ui.asString
import com.emikhalets.core.ui.component.AppButtonOk
import com.emikhalets.core.ui.component.AppChildScreenBox
import com.emikhalets.core.ui.component.AppTextField
import com.emikhalets.core.ui.theme.AppTheme
import java.util.Date

private const val TAG = "Currencies"

@Composable
fun CurrenciesScreen(
    navigateBack: () -> Unit,
    viewModel: CurrenciesViewModel,
) {
    logi(TAG, "Invoke")
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.setAction(Action.GetCurrencies)
        viewModel.setAction(Action.GetExchanges)
    }

    ScreenContent(
        state = state,
        onNewCurrencyEvent = { code, visible ->
            viewModel.setAction(Action.NewCurrencyEvent(code, visible))
        },
        onBaseCurrencyEvent = { code, value ->
            viewModel.setAction(Action.BaseCurrencyEvent(code, value))
        },
        onCurrencySaveClick = { viewModel.setAction(Action.AddCurrency(it)) },
        onCurrencyDeleteClick = { viewModel.setAction(Action.DeleteCurrency(it)) },
        onRefreshUpdate = { viewModel.setAction(Action.GetExchanges) },
        onBackClick = navigateBack
    )

    if (state.error != null) {
        logi(TAG, "Show error message")
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier.fillMaxSize()
        ) {
            // TODO: animate transition Y bottom to top
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = state.error.asString(),
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                AppButtonOk({ viewModel.setAction(Action.DropError) })
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ScreenContent(
    state: CurrenciesContract.State,
    onNewCurrencyEvent: (String, Boolean) -> Unit,
    onBaseCurrencyEvent: (String, String) -> Unit,
    onCurrencySaveClick: (String) -> Unit,
    onCurrencyDeleteClick: (String) -> Unit,
    onRefreshUpdate: () -> Unit,
    onBackClick: () -> Unit,
) {
    logi("$TAG.ScreenContent", "Invoke: state = $state")
    AppChildScreenBox(onBackClick, stringResource(appNameRes)) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            item {
                DateLoaderBox(
                    timestamp = state.date,
                    isLoading = state.isLoading,
                    isOldExchanges = state.isOldExchanges
                )
            }
            items(state.currencies) { (code, value) ->
                CurrencyBox(
                    code = code,
                    value = if (code == state.baseCurrency) state.baseValue else value.toString(),
                    isBase = code == state.baseCurrency,
                    onBaseCurrencyEvent = onBaseCurrencyEvent,
                    onDeleteCurrencyClick = onCurrencyDeleteClick,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            item {
                NewCurrencyBox(
                    isNewCurrencyVisible = state.newCurrencyVisible,
                    newCurrencyCode = state.newCurrencyCode,
                    onNewCurrencyEvent = onNewCurrencyEvent,
                    onCurrencySaveClick = onCurrencySaveClick
                )
            }
        }
    }
}

@Composable
private fun CurrencySwipeBackBox(
    targetValue: DismissValue,
    modifier: Modifier = Modifier,
) {
    logi("$TAG.CurrencySwipeBackBox", "Invoke")
    val color by animateColorAsState(
        when (targetValue) {
            DismissValue.Default -> Color.White
            else -> Color.Red
        }
    )

    val scale by animateFloatAsState(
        if (targetValue == DismissValue.Default) 0.75f else 1f
    )

    Box(
        contentAlignment = Alignment.CenterEnd,
        modifier = modifier
            .fillMaxSize()
            .background(color)
            .padding(horizontal = Dp(20f))
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = null,
            modifier = Modifier.scale(scale)
        )
    }
}

@Composable
private fun DateLoaderBox(
    timestamp: Long,
    isLoading: Boolean,
    isOldExchanges: Boolean,
    modifier: Modifier = Modifier,
) {
    logi("$TAG.DateLoaderBox", "Invoke: timestamp = $timestamp")

    Column(modifier = Modifier.fillMaxWidth()) {
        if (timestamp > 0) {
            Text(
                text = if (isOldExchanges) {
                    stringResource(R.string.app_convert_old_values, timestamp.formatFullDate())
                } else {
                    stringResource(R.string.app_convert_valid_values, timestamp.formatFullDate())
                },
                style = MaterialTheme.typography.body2,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        if (isOldExchanges) MaterialTheme.colors.error else Color.Transparent
                    )
                    .padding(8.dp)
            )
        }
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(8.dp)
            )
        }
    }
}

@Composable
private fun NewCurrencyBox(
    isNewCurrencyVisible: Boolean,
    newCurrencyCode: String,
    onNewCurrencyEvent: (String, Boolean) -> Unit,
    onCurrencySaveClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    logi("$TAG.NewCurrencyBox", "Invoke")
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp)
            .padding(16.dp, 8.dp)
            .clip(MaterialTheme.shapes.medium)
            .clickable { onNewCurrencyEvent("", true) }
    ) {
        if (isNewCurrencyVisible) {
            NewCurrencyEditBox(
                code = newCurrencyCode,
                onCodeChanged = { onNewCurrencyEvent(it, true) },
                onSaveClick = onCurrencySaveClick,
                onCancelClick = { onNewCurrencyEvent("", false) },
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = stringResource(R.string.app_convert_add),
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun CurrencyBox(
    code: String,
    value: String,
    isBase: Boolean,
    onBaseCurrencyEvent: (String, String) -> Unit,
    onDeleteCurrencyClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    logi("$TAG.CurrencyBox", "Invoke: code = $code, value = $value, isBase = $isBase")

    val focusRequester = remember { FocusRequester() }
    val dismissState = rememberDismissState()

    if (dismissState.isDismissed(DismissDirection.EndToStart)) {
        onDeleteCurrencyClick(code)
    }

    SwipeToDismiss(
        state = dismissState,
        background = { CurrencySwipeBackBox(targetValue = dismissState.targetValue) },
        directions = setOf(DismissDirection.EndToStart),
        dismissThresholds = { direction ->
            val fraction = if (direction == DismissDirection.EndToStart) 0.2f else 0.05f
            FractionalThreshold(fraction)
        },
        modifier = modifier
            .padding(16.dp, 8.dp)
            .clip(MaterialTheme.shapes.medium)
    ) {
        Card(
            backgroundColor = if (isBase) {
                MaterialTheme.colors.secondary
            } else {
                MaterialTheme.colors.surface
            },
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    focusRequester.requestFocus()
                    onBaseCurrencyEvent(code, "")
                }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = code,
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(16.dp)
                )
                AppTextField(
                    value = value,
                    onValueChange = { if (isBase) onBaseCurrencyEvent(code, it) },
                    keyboardType = KeyboardType.Number,
                    fontSize = 25.sp,
                    visualTransformation = CurrencyAmountInputVisualTransformation(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(3f)
                        .focusRequester(focusRequester)
                        .onFocusChanged {
                            if (it.hasFocus) onBaseCurrencyEvent(code, "")
                        }
                )
            }
        }
    }
}

@Composable
private fun NewCurrencyEditBox(
    code: String,
    onCodeChanged: (String) -> Unit,
    onSaveClick: (String) -> Unit,
    onCancelClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    logi("$TAG.NewCurrencyEditBox", "Invoke: code = $code")

    val focusRequester = remember { FocusRequester() }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxSize()
    ) {
        AppTextField(
            value = code,
            onValueChange = onCodeChanged,
            placeholder = stringResource(R.string.app_convert_code_help),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .focusRequester(focusRequester)
        )
        Icon(
            imageVector = Icons.Rounded.Close,
            contentDescription = null,
            modifier = Modifier
                .padding(8.dp)
                .size(32.dp)
                .clickable { onCancelClick() }
        )
        Icon(
            imageVector = Icons.Rounded.Save,
            contentDescription = null,
            modifier = Modifier
                .padding(8.dp)
                .size(24.dp)
                .clickable { onSaveClick(code) }
        )
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        ScreenContent(
            state = CurrenciesContract.State(
                currencies = listOf("USD" to 1200, "RUB" to 100000, "VND" to 750000000),
                baseCurrency = "RUB",
                date = Date().time.localDate().timestamp(),
            ),
            onNewCurrencyEvent = { _, _ -> },
            onBaseCurrencyEvent = { _, _ -> },
            onCurrencySaveClick = {},
            onCurrencyDeleteClick = {},
            onRefreshUpdate = {},
            onBackClick = {}
        )
    }
}
