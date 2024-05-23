package com.emikhalets.superapp.feature.salary.chart

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.emikhalets.core.superapp.ui.extentions.ScreenPreview
import com.emikhalets.core.superapp.ui.theme.AppTheme
import com.emikhalets.core.ui.component.AppTopBar
import com.emikhalets.superapp.core.common.helper.DateHelper
import com.emikhalets.superapp.domain.salary.model.SalaryModel
import com.emikhalets.superapp.domain.salary.model.SalaryType
import com.emikhalets.superapp.feature.salary.R
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
    Column(modifier = Modifier.fillMaxSize()) {
        AppTopBar(
            title = stringResource(R.string.salary_app_title),
            onBackClick = onBackClick
        )
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

@ScreenPreview
@Composable
private fun Preview() {
    AppTheme {
        ScreenContent(
            state = State(
                chartModelProducer = CartesianChartModelProducer.build(),
                salaryList = listOf(
                    SalaryModel(
                        4000000,
                        DateHelper.timestampOf(10, 3, 2023),
                        SalaryType.SALARY
                    ),
                    SalaryModel(
                        6000000,
                        DateHelper.timestampOf(10, 4, 2023),
                        SalaryType.SALARY
                    ),
                    SalaryModel(
                        10000000,
                        DateHelper.timestampOf(10, 6, 2023),
                        SalaryType.SALARY
                    ),
                    SalaryModel(
                        15000000,
                        DateHelper.timestampOf(10, 7, 2023),
                        SalaryType.SALARY
                    ),
                    SalaryModel(
                        17600000,
                        DateHelper.timestampOf(10, 9, 2023),
                        SalaryType.SALARY
                    ),
                    SalaryModel(
                        17600000,
                        DateHelper.timestampOf(10, 10, 2023),
                        SalaryType.SALARY
                    ),
                    SalaryModel(
                        17600000,
                        DateHelper.timestampOf(10, 11, 2023),
                        SalaryType.SALARY
                    ),
                ),
            ),
            onSetAction = {},
            onEditItemClick = {},
            onBackClick = {}
        )
    }
}
