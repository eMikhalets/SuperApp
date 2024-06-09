package com.emikhalets.superapp.feature.salary.chart

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.emikhalets.superapp.core.ui.component.AppFloatingButtonBox
import com.emikhalets.superapp.core.ui.extentions.ScreenLandPreview
import com.emikhalets.superapp.core.ui.theme.AppTheme
import com.emikhalets.superapp.core.common.date.DateHelper
import com.emikhalets.superapp.domain.salary.SalaryModel
import com.emikhalets.superapp.feature.salary.chart.ChartContract.Action
import com.emikhalets.superapp.feature.salary.chart.ChartContract.State
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.CartesianChartHost
import com.patrykandpatrick.vico.compose.chart.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.chart.rememberCartesianChart
import com.patrykandpatrick.vico.core.model.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.model.ExtraStore
import com.patrykandpatrick.vico.core.model.columnSeries

@Composable
internal fun ChartScreen(
    navigateBack: () -> Unit,
    navigateEditItem: (id: Long) -> Unit,
    viewModel: ChartViewModel,
) {
    val state by viewModel.state.collectAsState()

    ScreenContent(
        state = state,
        onSetAction = viewModel::setAction,
        onEditItemClick = navigateEditItem,
        onBackClick = navigateBack
    )
}

@Composable
private fun ScreenContent(
    state: State,
    onSetAction: (Action) -> Unit,
    onEditItemClick: (Long) -> Unit,
    onBackClick: () -> Unit,
) {
    AppFloatingButtonBox(onClick = { onEditItemClick(0) }) {
        ChartBox(
            modelProducer = state.chartModelProducer,
            list = state.salaryList,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .padding(top = 24.dp)
        )
    }
}

@Composable
private fun ChartBox(
    modelProducer: CartesianChartModelProducer,
    list: List<SalaryModel>,
    modifier: Modifier = Modifier,
) {
    val labelListKey = ExtraStore.Key<List<String>>()
    LaunchedEffect(list) {
        modelProducer.tryRunTransaction {
            val yValues = list.map { it.value / 100 }
            val datesMap = list.associate {
                DateHelper.formatDate(it.timestamp, "M/yy") to it.timestamp
            }
            columnSeries { series(yValues) }
            updateExtras { it[labelListKey] = datesMap.keys.toList() }
        }
    }
    val chartState = rememberCartesianChart(
        rememberColumnCartesianLayer(),
        startAxis = rememberStartAxis(),
        bottomAxis = rememberBottomAxis(
            valueFormatter = { x, chartValues, _ ->
                chartValues.model.extraStore[labelListKey][x.toInt()]
            }
        ),
    )
    Column(modifier = modifier) {
        CartesianChartHost(chartState, modelProducer)
    }
}

@ScreenLandPreview
@Composable
private fun Preview() {
    AppTheme {
        ScreenContent(
            state = State(
                chartModelProducer = CartesianChartModelProducer.build(),
                salaryList = getChartSalaryList(),
            ),
            onSetAction = {},
            onEditItemClick = {},
            onBackClick = {}
        )
    }
}
