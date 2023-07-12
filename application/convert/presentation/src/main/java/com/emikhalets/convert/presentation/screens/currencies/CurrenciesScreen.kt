package com.emikhalets.convert.presentation.screens.currencies

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
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emikhalets.convert.domain.R
import com.emikhalets.convert.presentation.screens.currencies.CurrenciesContract.Action
import com.emikhalets.core.common.ApplicationEntity.Convert.appNameRes
import com.emikhalets.core.common.date.formatFullDate
import com.emikhalets.core.common.date.localDate
import com.emikhalets.core.common.date.timestamp
import com.emikhalets.core.common.logi
import com.emikhalets.core.ui.asString
import com.emikhalets.core.ui.component.AppButton
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
        viewModel.setAction(Action.GetExchanges)
    }

    ScreenContent(
        state = state,
        onNewCurrencyVisible = { viewModel.setAction(Action.NewCurrencyShow(it)) },
        onCurrencyCodeChanged = { viewModel.setAction(Action.NewCurrencyCode(it)) },
        onCurrencySaveClick = { viewModel.setAction(Action.AddCurrency(it)) },
        onCurrencyDeleteClick = { viewModel.setAction(Action.DeleteCurrency(it)) },
        onBaseValueChanged = { viewModel.setAction(Action.Convert(it)) },
        onSetBaseCurrency = { viewModel.setAction(Action.SetBase(it)) },
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

@Composable
private fun ScreenContent(
    state: CurrenciesContract.State,
    onNewCurrencyVisible: (Boolean) -> Unit,
    onCurrencyCodeChanged: (String) -> Unit,
    onCurrencySaveClick: (String) -> Unit,
    onCurrencyDeleteClick: (String) -> Unit,
    onBaseValueChanged: (String) -> Unit,
    onSetBaseCurrency: (String) -> Unit,
    onBackClick: () -> Unit,
) {
    logi("$TAG.ScreenContent", "Invoke: state = $state")

    val date by remember { mutableStateOf(state.date.formatFullDate()) }

    AppChildScreenBox(onBackClick, stringResource(appNameRes)) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            if (state.date > 0) {
                item {
                    Text(
                        text = if (state.isOldValues) {
                            stringResource(R.string.app_convert_old_values, date)
                        } else {
                            stringResource(R.string.app_convert_valid_values, date)
                        },
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .background(
                                if (state.isOldValues) MaterialTheme.colors.error
                                else Color.Transparent
                            )
                            .padding(16.dp)
                    )
                }
            }
            state.currencies.forEach { (code, value) ->
                item {
                    CurrencyBox(
                        code = code,
                        value = if (code == state.baseCurrency) state.baseValue else value.toString(),
                        isBase = code == state.baseCurrency,
                        onBaseValueChanged = onBaseValueChanged,
                        onSetBaseCurrency = onSetBaseCurrency,
                        onDeleteCurrencyClick = onCurrencyDeleteClick,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            item {
                if (state.isNewCurrencyVisible) {
                    NewCurrencyBox(
                        code = state.newCurrencyCode,
                        onCodeChanged = onCurrencyCodeChanged,
                        onSaveClick = onCurrencySaveClick,
                        onCancelClick = { onNewCurrencyVisible(false) },
                        modifier = Modifier.fillMaxWidth()
                    )
                } else {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                    ) {
                        AppButton(
                            text = stringResource(R.string.app_convert_add),
                            onClick = { onNewCurrencyVisible(true) },
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CurrencyBox(
    code: String,
    value: String,
    isBase: Boolean,
    onBaseValueChanged: (String) -> Unit,
    onSetBaseCurrency: (String) -> Unit,
    onDeleteCurrencyClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    logi("$TAG.CurrencyBox", "Invoke: code = $code, value = $value, isBase = $isBase")
    Card(
        backgroundColor = if (isBase) {
            MaterialTheme.colors.secondary.copy(alpha = 0.1f)
        } else {
            MaterialTheme.colors.surface
        },
        modifier = modifier.padding(16.dp, 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
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
                onValueChange = { if (isBase) onBaseValueChanged(it) },
                keyboardType = KeyboardType.Number,
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f)
                    .onFocusChanged {
                        if (it.hasFocus) onSetBaseCurrency(code)
                    }
            )
            Icon(
                imageVector = Icons.Rounded.Delete,
                contentDescription = null,
                modifier = Modifier
                    .padding(8.dp)
                    .size(32.dp)
                    .clickable { onDeleteCurrencyClick(code) }
            )
        }
    }
}

@Composable
private fun NewCurrencyBox(
    code: String,
    onCodeChanged: (String) -> Unit,
    onSaveClick: (String) -> Unit,
    onCancelClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    logi("$TAG.NewCurrencyBox", "Invoke: code = $code")

    val focusRequester = remember { FocusRequester() }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
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
            imageVector = Icons.Rounded.Delete,
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
                currencies = mapOf("USD" to 123.45, "RUB" to 123.45, "VND" to 123.4),
                baseCurrency = "RUB",
                date = Date().time.localDate().minusDays(2).timestamp()
            ),
            onNewCurrencyVisible = {},
            onCurrencyCodeChanged = {},
            onCurrencySaveClick = {},
            onCurrencyDeleteClick = {},
            onBaseValueChanged = {},
            onSetBaseCurrency = {},
            onBackClick = {}
        )
    }
}
