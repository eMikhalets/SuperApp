package com.emikhalets.convert.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emikhalets.convert.R
import com.emikhalets.convert.presentation.screens.CurrenciesContract.Action
import com.emikhalets.core.common.date.formatFullDate
import com.emikhalets.core.common.date.localDate
import com.emikhalets.core.common.date.timestamp
import com.emikhalets.core.ui.CurrencyVisualTransformation
import com.emikhalets.core.ui.component.AppSwipeToDelete
import com.emikhalets.core.ui.component.ChildScreenColumn
import com.emikhalets.core.ui.component.DialogError
import com.emikhalets.core.ui.component.FloatingButtonBox
import com.emikhalets.core.ui.component.LinearLoader
import com.emikhalets.core.ui.extentions.ScreenPreview
import com.emikhalets.core.ui.theme.AppTheme
import java.util.Date

@Composable
internal fun CurrenciesScreen(
    navigateBack: () -> Unit,
    viewModel: CurrenciesViewModel,
) {
    val state by viewModel.state.collectAsState()

    ScreenContent(
        state = state,
        onActionSent = viewModel::setAction,
        onBackClick = navigateBack
    )
}

@Composable
private fun ScreenContent(
    state: CurrenciesContract.State,
    onActionSent: (Action) -> Unit,
    onBackClick: () -> Unit,
) {
    ChildScreenColumn(R.string.app_convert_title) {
        FloatingButtonBox(onClick = { onActionSent(Action.Input.NewCurrencyVisible(true)) }) {
            Column(modifier = Modifier.fillMaxSize()) {
                ExchangesDateBox(
                    timestamp = state.date,
                    isOldExchanges = state.isOldExchanges,
                    modifier = Modifier.fillMaxWidth()
                )
                CurrenciesList(
                    currencies = state.currencies,
                    baseCode = state.baseCode,
                    isLoading = state.isLoading,
                    onCurrencyDeleteClick = { onActionSent(Action.DeleteCurrency(it)) },
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                )
                BaseCurrencyBox(
                    currencies = state.currencies,
                    baseCode = state.baseCode,
                    baseValue = state.baseValue,
                    onAddCurrencyClick = { onActionSent(Action.AddCurrency) },
                    onBaseCodeClick = { onActionSent(Action.Input.BaseCurrencyClick(it)) },
                    onBaseCodeChange = { onActionSent(Action.Input.BaseCurrencyChange(it)) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }

    NewCurrencyBox(
        code = state.newCurrencyCode,
        isVisible = state.newCurrencyVisible,
        onCodeChange = { onActionSent(Action.Input.NewCurrencyChange(it)) },
        onVisibleChange = { onActionSent(Action.Input.NewCurrencyVisible(it)) },
        onSaveClick = { onActionSent(Action.AddCurrency) }
    )

    DialogError(
        message = state.error,
        onDismiss = { onActionSent(Action.DropError) }
    )
}

@Composable
private fun ExchangesDateBox(
    timestamp: Long,
    isOldExchanges: Boolean,
    modifier: Modifier = Modifier,
) {
    if (timestamp > 0) {
        val dateText = if (isOldExchanges) {
            stringResource(R.string.app_convert_convert_old_values, timestamp.formatFullDate())
        } else {
            stringResource(R.string.app_convert_convert_valid_values, timestamp.formatFullDate())
        }

        val backColor = if (isOldExchanges) MaterialTheme.colors.error else Color.Transparent

        Text(
            text = dateText,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(backColor)
                .padding(8.dp)
        )
    }
}

@Composable
private fun CurrenciesList(
    currencies: List<Pair<String, Long>>,
    baseCode: String,
    isLoading: Boolean,
    onCurrencyDeleteClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        LinearLoader(visible = isLoading)
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(currencies, key = { it.first }) { (code, value) ->
                AppSwipeToDelete(onLeftSwiped = { onCurrencyDeleteClick(code) }) {
                    val isBase = code == baseCode
                    Text(
                        text = "$code :",
                        fontSize = 20.sp,
                        fontWeight = if (isBase) FontWeight.SemiBold else FontWeight.Normal,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                    Text(
                        text = value.toString(),
                        fontSize = 20.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(2f)
                    )
                }
            }
        }
    }
}

@Composable
private fun BaseCurrencyBox(
    currencies: List<Pair<String, Long>>,
    baseValue: String,
    baseCode: String,
    onBaseCodeClick: (String) -> Unit,
    onBaseCodeChange: (String) -> Unit,
    onAddCurrencyClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusRequester = remember { FocusRequester() }
    Column {
        CurrenciesChooser(
            currencies = currencies,
            baseCode = baseCode,
            onBaseCodeClick = onBaseCodeClick,
            modifier = Modifier.fillMaxSize()
        )
        OutlinedTextField(
            value = baseValue,
            onValueChange = { onBaseCodeChange(it) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            visualTransformation = CurrencyVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
        )
    }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun CurrenciesChooser(
    currencies: List<Pair<String, Long>>,
    baseCode: String,
    onBaseCodeClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    FlowRow(
        modifier = Modifier.fillMaxWidth()
    ) {
        currencies.forEach { (code, _) ->
            val backColor = if (code == baseCode) {
                MaterialTheme.colors.secondary.copy(alpha = 0.3f)
            } else {
                MaterialTheme.colors.secondary.copy(alpha = 0.7f)
            }
            Text(
                text = code,
                modifier = Modifier
                    .padding(4.dp)
                    .background(backColor, CircleShape)
                    .padding(4.dp)
                    .clickable { onBaseCodeClick(code) }
            )
        }
    }
}

@ScreenPreview
@Composable
private fun Preview() {
    AppTheme {
        ScreenContent(
            state = CurrenciesContract.State(
                currencies = listOf("USD" to 1200, "RUB" to 100000, "VND" to 750000000),
                baseCode = "RUB",
                baseValue = "1200",
                date = Date().time.localDate().timestamp(),
                isLoading = true,
            ),
            onActionSent = {},
            onBackClick = {}
        )
    }
}
