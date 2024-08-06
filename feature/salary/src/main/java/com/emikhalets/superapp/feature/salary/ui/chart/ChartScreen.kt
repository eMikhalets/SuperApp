package com.emikhalets.superapp.feature.salary.ui.chart

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.emikhalets.superapp.core.common.date.DateHelper
import com.emikhalets.superapp.core.common.helper.MoneyHelper
import com.emikhalets.superapp.core.ui.component.AppFloatingButtonBox
import com.emikhalets.superapp.core.ui.extentions.ScreenPreview
import com.emikhalets.superapp.core.ui.extentions.toast
import com.emikhalets.superapp.core.ui.theme.AppTheme
import com.emikhalets.superapp.feature.salary.domain.SalaryModel
import com.emikhalets.superapp.feature.salary.ui.chart.ChartContract.Action
import com.emikhalets.superapp.feature.salary.ui.chart.ChartContract.State
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.CartesianChartHost
import com.patrykandpatrick.vico.compose.chart.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.chart.rememberCartesianChart
import com.patrykandpatrick.vico.compose.chart.scroll.rememberVicoScrollState
import com.patrykandpatrick.vico.core.model.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.model.ExtraStore
import com.patrykandpatrick.vico.core.model.lineSeries
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

@Composable
internal fun ChartScreen(
    navigateBack: () -> Unit,
    viewModel: ChartViewModel,
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is ChartContract.Effect.Error -> toast(context, effect.message)
            }
        }
    }

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
    AppFloatingButtonBox(onClick = { onSetAction(Action.SetEditSalary(SalaryModel())) }) {
        Column(modifier = Modifier.fillMaxSize()) {
            ChartBox(
                modelProducer = state.chartModelProducer,
                list = state.salaryList,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .padding(top = 24.dp)
            )
            SalaryList(
                list = state.salaryList,
                onClick = { onSetAction(Action.SetEditSalary(it)) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

    SalaryEditDialog(
        model = state.editSalary,
        onSaveClick = { onSetAction(Action.SaveSalary(it)) },
        onDismiss = { onSetAction(Action.SetEditSalary(null)) }
    )
}

@Composable
private fun ChartBox(
    modelProducer: CartesianChartModelProducer,
    list: List<SalaryModel>,
    modifier: Modifier = Modifier,
) {
    val labelListKey = ExtraStore.Key<List<String>>()
    val scrollState = rememberVicoScrollState()
    LaunchedEffect(list) {
        modelProducer.runTransaction {
            Timber.d("runTransaction $list")
            lineSeries { series(4, 12, 8, 16) }
//            val yValues = list.map { it.value / 100 }
//            val datesMap = list.associate {
//                (DateHelper.format("M/yy", it.timestamp) ?: "") to it.timestamp
//            }
//            columnSeries { series(yValues) }
//            updateExtras { it[labelListKey] = datesMap.keys.toList() }
        }
    }
    val chart = rememberCartesianChart(
        rememberLineCartesianLayer(),
        startAxis = rememberStartAxis(),
        bottomAxis = rememberBottomAxis(),
    )
    Column(modifier = modifier) {
        CartesianChartHost(
            chart = chart,
            modelProducer = modelProducer,
            scrollState = scrollState
        )
    }
}

@Composable
private fun SalaryList(
    list: List<SalaryModel>,
    onClick: (SalaryModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier) {
        items(list) { model ->
            SalaryRow(
                model = model,
                onClick = onClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 8.dp)
            )
        }
    }
}

@Composable
private fun SalaryRow(
    model: SalaryModel,
    onClick: (SalaryModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        Text(
            text = MoneyHelper.convertMoney(model.value),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(end = 8.dp)
        )
        Text(
            text = model.type.toString(),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(end = 8.dp)
        )
        Text(
            text = DateHelper.format("dd MMM yyyy", model.timestamp) ?: "date null",
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(end = 8.dp)
        )
    }
}

@ScreenPreview
@Composable
private fun Preview() {
    AppTheme {
        ScreenContent(
            state = State(
                chartModelProducer = CartesianChartModelProducer.build(),
                salaryList = getChartSalaryList(),
            ),
            onSetAction = {},
            onBackClick = {}
        )
    }
}
