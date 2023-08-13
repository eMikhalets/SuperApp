package com.emikhalets.convert.presentation.screens

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
import com.emikhalets.convert.R
import com.emikhalets.convert.presentation.screens.CurrenciesContract.Action
import com.emikhalets.core.common.StringEmpty
import com.emikhalets.core.common.date.formatFullDate
import com.emikhalets.core.common.date.localDate
import com.emikhalets.core.common.date.timestamp
import com.emikhalets.core.ui.CurrencyAmountInputVisualTransformation
import com.emikhalets.core.ui.component.AppCard
import com.emikhalets.core.ui.component.AppTextField
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
                    modifier = Modifier.fillMaxWidth()
                )
                BaseCurrencyBox(
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
        LinearLoader(
            visible = isLoading
        )
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(currencies, key = { it.first }) { (code, value) ->
                CurrencyBox(
                    code = code,
                    value = value.toString(),
                    isBase = code == baseCode,
                    onDeleteCurrencyClick = onCurrencyDeleteClick,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun BaseCurrencyBox(
    baseValue: String,
    baseCode: String,
    onBaseCodeClick: (String) -> Unit,
    onBaseCodeChange: (String) -> Unit,
    onAddCurrencyClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusRequester = remember { FocusRequester() }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = code,
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun CurrencyBox(
    code: String,
    value: String,
    isBase: Boolean,
    onDeleteCurrencyClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val dismissState = rememberDismissState(
        confirmStateChange = {
            if (it == DismissValue.DismissedToStart) {
                onDeleteCurrencyClick(code)
                return@rememberDismissState true
            }
            false
        }
    )
    SwipeToDismiss(
        state = dismissState,
        background = { CurrencySwipeBackBox(targetValue = dismissState.targetValue) },
        directions = setOf(DismissDirection.EndToStart),
        dismissThresholds = { FractionalThreshold(0.2f) },
        modifier = modifier
            .padding(16.dp, 4.dp)
            .clip(MaterialTheme.shapes.medium)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text =,
                fontSi,
                modifier = Modifier.fillMaxWidth()
            )
        }
        AppCard(modifier = Modifier.fillMaxWidth()) {
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
private fun CurrencySwipeBackBox(
    targetValue: DismissValue,
    modifier: Modifier = Modifier,
) {
    val color by animateColorAsState(
        when (targetValue) {
            DismissValue.Default -> Color.White
            else -> Color.Red
        }, label = StringEmpty
    )

    val scale by animateFloatAsState(
        if (targetValue == DismissValue.Default) 0.75f else 1f, label = StringEmpty
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
