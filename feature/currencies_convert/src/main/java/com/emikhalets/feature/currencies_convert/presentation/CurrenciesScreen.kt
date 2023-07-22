package com.emikhalets.feature.currencies_convert.presentation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emikhalets.core.common.date.formatFullDate
import com.emikhalets.core.common.date.localDate
import com.emikhalets.core.common.date.timestamp
import com.emikhalets.core.ui.ApplicationEntity
import com.emikhalets.core.ui.CurrencyAmountInputVisualTransformation
import com.emikhalets.core.ui.ScreenPreview
import com.emikhalets.core.ui.component.AppCard
import com.emikhalets.core.ui.component.AppContent
import com.emikhalets.core.ui.component.AppFloatButtonBox
import com.emikhalets.core.ui.component.AppLinearLoader
import com.emikhalets.core.ui.component.AppTextField
import com.emikhalets.core.ui.dialog.AppErrorDialog
import com.emikhalets.core.ui.getName
import com.emikhalets.core.ui.theme.AppTheme
import com.emikhalets.core.ui.theme.text
import com.emikhalets.core.ui.theme.textTitle
import com.emikhalets.feature.currencies_convert.R
import com.emikhalets.feature.currencies_convert.presentation.CurrenciesContract.Action
import java.util.Date

@Composable
fun CurrenciesScreen(
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
    AppContent(ApplicationEntity.Convert.getName(), onBackClick) {
        AppFloatButtonBox(
            icon = Icons.Rounded.Add,
            onClick = { onActionSent(Action.NewCurrencyEvent("", true)) }
        ) {
            CurrenciesConvertBox(
                date = state.date,
                isOldExchanges = state.isOldExchanges,
                isLoading = state.isLoading,
                currencies = state.currencies,
                baseValue = state.baseValue,
                baseCurrency = state.baseCurrency,
                onBaseCurrencyEvent = { code, value ->
                    onActionSent(Action.BaseCurrencyEvent(code, value))
                },
                onCurrencyDeleteClick = { onActionSent(Action.DeleteCurrency(it)) }
            )
        }
    }

    NewCurrencyBox(
        newCurrencyCode = state.newCurrencyCode,
        isVisible = state.newCurrencyVisible,
        onNewCurrencyEvent = { code, visible ->
            onActionSent(Action.NewCurrencyEvent(code, visible))
        },
        onSaveClick = { onActionSent(Action.AddCurrency(it)) }
    )

    AppErrorDialog(
        message = state.error,
        onDismiss = { onActionSent(Action.DropError) }
    )
}

@Composable
private fun CurrenciesConvertBox(
    date: Long,
    isOldExchanges: Boolean,
    isLoading: Boolean,
    currencies: List<Pair<String, Long>>,
    baseValue: String,
    baseCurrency: String,
    onBaseCurrencyEvent: (String, String) -> Unit,
    onCurrencyDeleteClick: (String) -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        DateLoaderBox(
            timestamp = date,
            isOldExchanges = isOldExchanges
        )
        AppLinearLoader(
            visible = isLoading
        )
        CurrenciesList(
            currencies = currencies,
            baseValue = baseValue,
            baseCurrency = baseCurrency,
            onBaseCurrencyEvent = onBaseCurrencyEvent,
            onCurrencyDeleteClick = onCurrencyDeleteClick
        )
    }
}

@Composable
private fun DateLoaderBox(
    timestamp: Long,
    isOldExchanges: Boolean,
    modifier: Modifier = Modifier,
) {
    if (timestamp > 0) {
        val dateText = if (isOldExchanges) {
            stringResource(
                R.string.feature_currencies_convert_old_values,
                timestamp.formatFullDate()
            )
        } else {
            stringResource(
                R.string.feature_currencies_convert_valid_values,
                timestamp.formatFullDate()
            )
        }

        val backColor = if (isOldExchanges) MaterialTheme.colors.error else Color.Transparent

        Text(
            text = dateText,
            style = MaterialTheme.typography.text,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(backColor)
                .padding(8.dp)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CurrenciesList(
    currencies: List<Pair<String, Long>>,
    baseValue: String,
    baseCurrency: String,
    onBaseCurrencyEvent: (String, String) -> Unit,
    onCurrencyDeleteClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(currencies, key = { it.first }) { (code, value) ->
            CurrencyBox(
                code = code,
                value = if (code == baseCurrency) baseValue else value.toString(),
                isBase = code == baseCurrency,
                onBaseCurrencyEvent = onBaseCurrencyEvent,
                onDeleteCurrencyClick = onCurrencyDeleteClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .animateItemPlacement()
            )
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
    val focusRequester = remember { FocusRequester() }

    val dismissState = rememberDismissState(
        confirmStateChange = {
            if (it == DismissValue.DismissedToStart) {
                onDeleteCurrencyClick(code)
                return@rememberDismissState true
            }
            false
        }
    )

    val backColor = if (isBase) MaterialTheme.colors.secondary else MaterialTheme.colors.surface

    SwipeToDismiss(
        state = dismissState,
        background = { CurrencySwipeBackBox(targetValue = dismissState.targetValue) },
        directions = setOf(DismissDirection.EndToStart),
        dismissThresholds = { FractionalThreshold(0.2f) },
        modifier = modifier
            .padding(16.dp, 4.dp)
            .clip(MaterialTheme.shapes.medium)
    ) {
        AppCard(
            backgroundColor = backColor,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    focusRequester.requestFocus()
                    onBaseCurrencyEvent(code, "")
                }
        ) {
            CurrencyTextValueBox(
                code = code,
                value = value,
                isBase = isBase,
                focusRequester = focusRequester,
                onBaseCurrencyEvent = onBaseCurrencyEvent,
                onDeleteCurrencyClick = onDeleteCurrencyClick
            )
        }
    }
}

@Composable
private fun CurrencyTextValueBox(
    code: String,
    value: String,
    isBase: Boolean,
    focusRequester: FocusRequester,
    onBaseCurrencyEvent: (String, String) -> Unit,
    onDeleteCurrencyClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = code,
            style = MaterialTheme.typography.textTitle,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(16.dp)
                .width(70.dp)
        )
        AppTextField(
            value = value,
            onValueChange = { if (isBase) onBaseCurrencyEvent(code, it) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            fontSize = 24.sp,
            visualTransformation = CurrencyAmountInputVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterVertically)
                .focusRequester(focusRequester)
                .onFocusChanged { if (it.hasFocus) onBaseCurrencyEvent(code, "") }
        )
    }
}

@Composable
private fun CurrencySwipeBackBox(
    targetValue: DismissValue,
    modifier: Modifier = Modifier,
) {
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

@ScreenPreview
@Composable
private fun Preview() {
    AppTheme {
        ScreenContent(
            state = CurrenciesContract.State(
                currencies = listOf("USD" to 1200, "RUB" to 100000, "VND" to 750000000),
                baseCurrency = "RUB",
                baseValue = "1200",
                date = Date().time.localDate().timestamp(),
                isLoading = true,
            ),
            onActionSent = {},
            onBackClick = {}
        )
    }
}
