package com.emikhalets.superapp.feature.convert.ui.currencies

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.emikhalets.superapp.core.common.format
import com.emikhalets.superapp.core.ui.component.AppTopBar
import com.emikhalets.superapp.core.ui.component.FloatingButtonBox
import com.emikhalets.superapp.core.ui.component.LinearLoader
import com.emikhalets.superapp.core.ui.component.TextFieldPrimary
import com.emikhalets.superapp.core.ui.extentions.ScreenPreview
import com.emikhalets.superapp.core.ui.extentions.clickableOnce
import com.emikhalets.superapp.core.ui.theme.AppTheme
import com.emikhalets.superapp.core.ui.theme.rectangle
import com.emikhalets.superapp.feature.convert.R
import com.emikhalets.superapp.feature.convert.ui.currencies.CurrenciesContract.Action
import com.emikhalets.superapp.feature.convert.ui.currencies.CurrenciesContract.State
import java.util.Date

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
        AppTopBar(
            title = stringResource(R.string.convert_title),
            onBackClick = onBackClick
        )
        FloatingButtonBox(
            onClick = { onSetAction(Action.SetNewCurrencyVisible(true)) },
            modifier = Modifier.weight(1f)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                LinearLoader(visible = state.loading)
                ExchangesDateBox(
                    timestamp = state.date,
                    isOldExchanges = state.isOldExchanges,
                    onUpdateClick = { onSetAction(Action.UpdateExchanges) },
                    modifier = Modifier.fillMaxWidth()
                )
                PairsList(
                    pairs = state.pairList,
                    baseCode = state.baseCode,
                    onCurrencyDeleteClick = { onSetAction(Action.DeleteCurrency(it)) },
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                )
            }
        }
        BaseCurrencyBox(
            currencies = state.currencies,
            baseCode = state.baseCode,
            baseValue = state.baseValue,
            onAddCurrencyClick = { onSetAction(Action.AddCurrency) },
            onBaseCodeClick = { onSetAction(Action.SetBaseCode(it)) },
            onBaseValueChange = { onSetAction(Action.SetBaseValue(it)) },
            modifier = Modifier.fillMaxWidth()
        )
    }
    if (state.newCurrencyVisible) {
        NewCurrencyDialog(
            code = state.newCurrencyCode,
            onCodeChanged = { onSetAction(Action.SetNewCurrencyCode(it)) },
            onDismiss = { onSetAction(Action.SetNewCurrencyVisible(false)) },
            onSaveClick = { onSetAction(Action.AddCurrency) }
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
            Text(
                text = dateText,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(backColor)
                    .padding(8.dp)
            )
        }
        if (isOldExchanges) {
            Button(
                onClick = onUpdateClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = stringResource(R.string.convert_update))
            }
        }
    }
}

@Composable
private fun PairsList(
    pairs: List<Pair<String, String>>,
    baseCode: String,
    onCurrencyDeleteClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier) {
        items(pairs, key = { it.first }) { (code, value) ->
            PairRow(
                code = code,
                value = value,
                baseCode = baseCode,
                onDeleteClick = { onCurrencyDeleteClick(code) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp, 8.dp, 12.dp, 0.dp)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onBackground,
                        shape = MaterialTheme.shapes.small
                    )
                    .padding(24.dp, 8.dp, 8.dp, 8.dp)
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
        Text(
            text = "$code :",
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 24.sp,
            fontWeight = textWeight,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        Text(
            text = formatPairValue(value),
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
            tint = MaterialTheme.colorScheme.onError,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.error, MaterialTheme.shapes.small)
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
                .padding(4.dp, 2.dp)
        )
        TextFieldPrimary(
            value = baseValue,
            onValueChange = { onBaseValueChange(it) },
            trailingIcon = Icons.Rounded.Close,
            trailingIconClick = { onBaseValueChange("") },
            shape = MaterialTheme.shapes.rectangle,
            options = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
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
            Text(
                text = code,
                color = textColor,
                fontSize = 12.sp,
                fontWeight = if (code == baseCode) FontWeight.SemiBold else FontWeight.Normal,
                modifier = textModifier
            )
        }
    }
}

private fun formatPairValue(value: String): String {
    if (value.isBlank()) return ""
    val parts = value.split(".")
    val leftPart = parts.getOrNull(0) ?: ""
    var spaceCounter = 0
    val charList = leftPart.toMutableList()
    for (i in leftPart.length downTo 0) {
        if (spaceCounter == 3) {
            spaceCounter = 0
            charList.add(i, ' ')
        }
        spaceCounter++
    }
    val newLeftPart = charList.joinToString("").trim()
    return "$newLeftPart.${parts.getOrNull(1) ?: "00"}"
}

@ScreenPreview
@Composable
private fun Preview() {
    AppTheme {
        ScreenContent(
            state = State(
                pairList = listOf("USD" to "1200.00", "RUB" to "150000.00", "VND" to "7500000.00"),
                currencies = listOf("USD", "RUB", "VND"),
                baseCode = "RUB",
                baseValue = "1200.00",
                date = Date().time,
                loading = false,
            ),
            onSetAction = {},
            onBackClick = {}
        )
    }
}
