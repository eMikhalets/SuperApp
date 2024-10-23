package com.emikhalets.superapp.feature.convert.ui.currencies

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emikhalets.superapp.core.common.convertMoney
import com.emikhalets.superapp.core.common.format
import com.emikhalets.superapp.core.common.timestamp
import com.emikhalets.superapp.core.ui.component.ButtonPrimary
import com.emikhalets.superapp.core.ui.component.LinearLoader
import com.emikhalets.superapp.core.ui.component.TextFieldPrimary
import com.emikhalets.superapp.core.ui.component.TextPrimary
import com.emikhalets.superapp.core.ui.extentions.ScreenPreview
import com.emikhalets.superapp.core.ui.extentions.clickableOnce
import com.emikhalets.superapp.core.ui.theme.AppTheme
import com.emikhalets.superapp.core.ui.theme.listItemBox
import com.emikhalets.superapp.feature.convert.R
import com.emikhalets.superapp.feature.convert.ui.currencies.CurrenciesContract.Action
import com.emikhalets.superapp.feature.convert.ui.currencies.CurrenciesContract.State

@Composable
internal fun CurrenciesScreen(
    navigateBack: () -> Unit,
    viewModel: CurrenciesViewModel,
) {
    val state by viewModel.state.collectAsState()

    ScreenContent(
        state = state,
        onSetAction = viewModel::setAction,
        onBackClick = navigateBack
    )
}

@Composable
private fun ScreenContent(
    state: State,
    onSetAction: (Action) -> Unit,
    onBackClick: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            LinearLoader(visible = state.loading)
            ExchangesDateBox(
                timestamp = state.updateDate,
                isOldExchanges = state.isOldExchanges,
                onUpdateClick = { onSetAction(Action.UpdateExchanges) },
                modifier = Modifier.fillMaxWidth()
            )
            PairsList(
                pairs = state.pairs,
                baseCode = state.baseCode,
                onCurrencyDeleteClick = { onSetAction(Action.DeleteCurrency(it)) },
                onNewCurrencyClick = { onSetAction(Action.SetNewCurrencyVisible(true)) },
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            )
        }
        BaseCurrencyBox(
            currencies = state.codes,
            baseCode = state.baseCode,
            baseValue = state.baseValue.convertMoney(),
            onAddCurrencyClick = { onSetAction(Action.AddCurrency) },
            onBaseCodeClick = { onSetAction(Action.SetBaseCode(it)) },
            onBaseValueChange = { onSetAction(Action.SetBaseValue(it.convertMoney())) },
            modifier = Modifier.fillMaxWidth()
        )
    }
    if (state.newCodeVisible) {
        NewCurrencyDialog(
            code = state.newCode,
            onCodeChanged = { onSetAction(Action.SetNewCurrencyCode(it)) },
            onSaveClick = { onSetAction(Action.AddCurrency) },
            onCancelClick = { onSetAction(Action.SetNewCurrencyVisible(false)) },
        )
    }
}

@Composable
private fun ExchangesDateBox(
    timestamp: Long,
    isOldExchanges: Boolean,
    onUpdateClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        if (timestamp > 0) {
            val dateText = stringResource(
                R.string.convert_exchanges_date,
                timestamp.format("dd MMMM yyyy") ?: ""
            )
            val backColor = if (isOldExchanges) {
                MaterialTheme.colorScheme.error
            } else {
                Color.Transparent
            }
            TextPrimary(
                text = dateText,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(backColor)
                    .padding(8.dp)
            )
        }
        if (isOldExchanges) {
            ButtonPrimary(
                text = stringResource(R.string.convert_update),
                onClick = onUpdateClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
        HorizontalDivider()
    }
}

@Composable
private fun PairsList(
    pairs: List<Pair<String, Long>>,
    baseCode: String,
    onCurrencyDeleteClick: (String) -> Unit,
    onNewCurrencyClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier) {
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
        items(pairs, key = { it.first }) { (code, value) ->
            PairRow(
                code = code,
                value = value.convertMoney(),
                baseCode = baseCode,
                onDeleteClick = { onCurrencyDeleteClick(code) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp, 8.dp, 12.dp, 0.dp)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onBackground,
                        shape = MaterialTheme.shapes.listItemBox
                    )
                    .padding(24.dp, 8.dp, 8.dp, 8.dp)
            )
        }
        item {
            TextPrimary(
                text = stringResource(R.string.convert_add),
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp, 8.dp, 12.dp, 0.dp)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onBackground,
                        shape = MaterialTheme.shapes.listItemBox
                    )
                    .clickableOnce { onNewCurrencyClick() }
                    .padding(16.dp)
            )
        }
    }
}

@Composable
private fun PairRow(
    code: String,
    value: String,
    baseCode: String,
    onDeleteClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        val textWeight = if (code == baseCode) {
            FontWeight.Bold
        } else {
            FontWeight.Normal
        }
        TextPrimary(
            text = "$code :",
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 24.sp,
            fontWeight = textWeight,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        TextPrimary(
            text = value,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 24.sp,
            fontWeight = textWeight,
            textAlign = TextAlign.End,
            modifier = Modifier
                .fillMaxWidth()
                .weight(3f)
                .padding(end = 40.dp)
        )
        Icon(
            imageVector = Icons.Rounded.Delete,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onBackground,
                    shape = MaterialTheme.shapes.listItemBox
                )
                .clip(MaterialTheme.shapes.small)
                .clickableOnce { onDeleteClick(code) }
                .padding(8.dp)
        )
    }
}

@Composable
private fun BaseCurrencyBox(
    currencies: List<String>,
    baseValue: String,
    baseCode: String,
    onBaseCodeClick: (String) -> Unit,
    onBaseValueChange: (String) -> Unit,
    onAddCurrencyClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column {
        CurrenciesChooser(
            currencies = currencies,
            baseCode = baseCode,
            onBaseCodeClick = {
                onBaseCodeClick(it)
                onBaseValueChange("")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 8.dp)
        )
        TextFieldPrimary(
            value = baseValue,
            onValueChange = { onBaseValueChange(it) },
            trailingIcon = Icons.Rounded.Close,
            trailingIconClick = { onBaseValueChange("") },
            options = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 24.dp)
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun CurrenciesChooser(
    currencies: List<String>,
    baseCode: String,
    onBaseCodeClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    FlowRow(modifier = modifier) {
        currencies.forEach { code ->
            val textColor = if (code == baseCode) {
                MaterialTheme.colorScheme.onPrimary
            } else {
                MaterialTheme.colorScheme.onBackground
            }
            val textModifier = if (code == baseCode) {
                Modifier
                    .padding(4.dp, 2.dp)
                    .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(30))
                    .clip(RoundedCornerShape(30))
                    .clickableOnce { onBaseCodeClick(code) }
                    .padding(8.dp, 2.dp)
            } else {
                Modifier
                    .padding(4.dp, 2.dp)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onBackground,
                        shape = RoundedCornerShape(30)
                    )
                    .clip(RoundedCornerShape(30))
                    .clickableOnce { onBaseCodeClick(code) }
                    .padding(8.dp, 2.dp)
            }
            TextPrimary(
                text = code,
                color = textColor,
                fontSize = 12.sp,
                fontWeight = if (code == baseCode) FontWeight.SemiBold else FontWeight.Normal,
                modifier = textModifier
            )
        }
    }
}

@ScreenPreview
@Composable
private fun Preview() {
    AppTheme {
        ScreenContent(
            state = State(
                loading = false,
                pairs = listOf("USD" to 120000, "RUB" to 15000000, "VND" to 750000000),
                codes = listOf("USD", "RUB", "VND"),
                baseCode = "RUB",
                baseValue = 15000000,
                isOldExchanges = true,
                updateDate = timestamp(),
            ),
            onSetAction = {},
            onBackClick = {}
        )
    }
}

@ScreenPreview
@Composable
private fun EmptyPreview() {
    AppTheme {
        ScreenContent(
            state = State(
                loading = false,
                pairs = emptyList(),
                codes = emptyList(),
                baseCode = "",
                baseValue = 0,
                isOldExchanges = false,
                updateDate = 0,
            ),
            onSetAction = {},
            onBackClick = {}
        )
    }
}
