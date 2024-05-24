package com.emikhalets.salary.presentation.add_salary

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Update
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.emikhalets.core.common.helper.DateHelper
import com.emikhalets.core.common.helper.MoneyHelper
import com.emikhalets.core.ui.component.AppTopBar
import com.emikhalets.core.ui.extentions.ScreenPreview
import com.emikhalets.core.ui.theme.AppTheme
import com.emikhalets.salary.R
import com.emikhalets.superapp.feature.salary.edit_item.EditItemContract.Action
import com.emikhalets.superapp.feature.salary.edit_item.EditItemContract.State
import com.emikhalets.superapp.feature.salary.edit_item.EditItemViewModel
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
    viewModel: EditItemViewModel,
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
            title = stringResource(R.string.salary_app_title),
            onBackClick = onBackClick
        )
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )
        ValueTextFieldBox(
            value = state.currentSalaryValue,
            onValueChange = { onSetAction(Action.SetCurrentSalaryValue(it)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 8.dp)
        )
        DateChooserBox(
            timestamp = state.currentSalaryDate,
            showDialog = state.showDateDialog,
            onDatePicked = { onSetAction(Action.SetCurrentSalaryDate(it)) },
            onDateCanceled = { onSetAction(Action.ShowDateDialog(false)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 8.dp)
        )
        TypeChooserBox(
            type = state.currentSalaryType,
            onTypeSelected = { onSetAction(Action.SetCurrentSalaryType(it)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 8.dp)
        )
        AddSalaryButton(
            onClick = { onSetAction(Action.AddSalary) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 24.dp)
        )
    }
}

@Composable
private fun ChartBox(
    modelProducer: CartesianChartModelProducer,
    list: List<com.emikhalets.superapp.domain.salary.model.SalaryModel>,
    onDeleteClick: (com.emikhalets.superapp.domain.salary.model.SalaryModel) -> Unit,
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

@Composable
private fun ValueTextFieldBox(
    value: Long?,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = MoneyHelper.convertMoney(value),
        onValueChange = onValueChange,
        singleLine = true,
        label = { Text(stringResource(R.string.salary_app_value_label)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DateChooserBox(
    timestamp: Long,
    showDialog: Boolean,
    onDatePicked: (Long?) -> Unit,
    onDateCanceled: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val datePickerState = rememberDatePickerState()
    val confirmEnabled by remember { derivedStateOf { datePickerState.selectedDateMillis != null } }
    if (showDialog) {
        DatePickerDialog(
            onDismissRequest = {},
            confirmButton = {
                TextButton(
                    onClick = { onDatePicked(datePickerState.selectedDateMillis) },
                    enabled = confirmEnabled
                ) {
                    Text(text = stringResource(R.string.salary_app_date_select))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = onDateCanceled
                ) {
                    Text(text = stringResource(R.string.salary_app_date_cancel))
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
    OutlinedTextField(
        value = DateHelper.formatDate(datePickerState.selectedDateMillis, "dd.MM.yyyy"),
        onValueChange = {},
        readOnly = true,
        singleLine = true,
        leadingIcon = { Icon(Icons.Rounded.Update, contentDescription = null) },
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TypeChooserBox(
    type: com.emikhalets.superapp.domain.salary.model.SalaryType,
    onTypeSelected: (com.emikhalets.superapp.domain.salary.model.SalaryType) -> Unit,
    modifier: Modifier = Modifier,
) {
    val options = com.emikhalets.superapp.domain.salary.model.SalaryType.entries
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(type) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
    ) {
        OutlinedTextField(
            value = selectedOptionText.toString(),
            onValueChange = {},
            readOnly = true,
            label = { Text(stringResource(R.string.salary_app_date_label)) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = modifier.menuAnchor(),
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption.toString()) },
                    onClick = {
                        selectedOptionText = selectionOption
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}

@Composable
private fun AddSalaryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(text = stringResource(R.string.salary_app_add_new))
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
                    com.emikhalets.superapp.domain.salary.model.SalaryModel(
                        4000000,
                        DateHelper.timestampOf(10, 3, 2023),
                        com.emikhalets.superapp.domain.salary.model.SalaryType.SALARY
                    ),
                    com.emikhalets.superapp.domain.salary.model.SalaryModel(
                        6000000,
                        DateHelper.timestampOf(10, 4, 2023),
                        com.emikhalets.superapp.domain.salary.model.SalaryType.SALARY
                    ),
                    com.emikhalets.superapp.domain.salary.model.SalaryModel(
                        10000000,
                        DateHelper.timestampOf(10, 6, 2023),
                        com.emikhalets.superapp.domain.salary.model.SalaryType.SALARY
                    ),
                    com.emikhalets.superapp.domain.salary.model.SalaryModel(
                        15000000,
                        DateHelper.timestampOf(10, 7, 2023),
                        com.emikhalets.superapp.domain.salary.model.SalaryType.SALARY
                    ),
                    com.emikhalets.superapp.domain.salary.model.SalaryModel(
                        17600000,
                        DateHelper.timestampOf(10, 9, 2023),
                        com.emikhalets.superapp.domain.salary.model.SalaryType.SALARY
                    ),
                    com.emikhalets.superapp.domain.salary.model.SalaryModel(
                        17600000,
                        DateHelper.timestampOf(10, 10, 2023),
                        com.emikhalets.superapp.domain.salary.model.SalaryType.SALARY
                    ),
                    com.emikhalets.superapp.domain.salary.model.SalaryModel(
                        17600000,
                        DateHelper.timestampOf(10, 11, 2023),
                        com.emikhalets.superapp.domain.salary.model.SalaryType.SALARY
                    ),
                ),
                currentSalaryValue = 12345,
                currentSalaryDate = DateHelper.nowTimestamp,
                currentSalaryType = com.emikhalets.superapp.domain.salary.model.SalaryType.SALARY,
                showDateDialog = false,
            ),
            onSetAction = {},
            onBackClick = {}
        )
    }
}
