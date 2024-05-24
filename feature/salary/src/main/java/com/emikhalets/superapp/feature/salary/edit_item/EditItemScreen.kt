package com.emikhalets.superapp.feature.salary.edit_item

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.emikhalets.superapp.core.common.date.DateHelper
import com.emikhalets.superapp.core.common.helper.MoneyHelper
import com.emikhalets.superapp.core.ui.component.AppButton
import com.emikhalets.superapp.core.ui.component.AppDateChooser
import com.emikhalets.superapp.core.ui.component.AppSpinner
import com.emikhalets.superapp.core.ui.extentions.ScreenPreview
import com.emikhalets.superapp.core.ui.theme.AppTheme
import com.emikhalets.superapp.domain.salary.model.SalaryType
import com.emikhalets.superapp.domain.salary.model.asSalaryType
import com.emikhalets.superapp.feature.salary.R
import com.emikhalets.superapp.feature.salary.edit_item.EditItemContract.Action
import com.emikhalets.superapp.feature.salary.edit_item.EditItemContract.State

@Composable
internal fun EditItemScreen(
    itemId: Long,
    navigateBack: () -> Unit,
    viewModel: EditItemViewModel,
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.setAction(Action.GetSalary(itemId))
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
    Column(modifier = Modifier.fillMaxSize()) {
        OutlinedTextField(
            value = MoneyHelper.convertMoney(state.value),
            onValueChange = { onSetAction(Action.SetValue(it)) },
            singleLine = true,
            label = { Text(stringResource(R.string.salary_app_value_label)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 24.dp)
        )
        AppDateChooser(
            timestamp = state.date,
            onSelect = { onSetAction(Action.SetDate(it)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 24.dp)
        )
        AppSpinner(
            value = state.type.toString(),
            options = SalaryType.asStringList(),
            label = stringResource(R.string.salary_app_type_label),
            onSelect = { onSetAction(Action.SetType(it.asSalaryType())) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
        )
        AppButton(
            text = stringResource(R.string.salary_app_save),
            onClick = { onSetAction(Action.AddItem) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 32.dp)
        )
    }
}

@ScreenPreview
@Composable
private fun Preview() {
    AppTheme {
        ScreenContent(
            state = State(
                itemId = 0,
                value = 12345,
                date = DateHelper.now,
                type = SalaryType.SALARY,
            ),
            onSetAction = {},
            onBackClick = {}
        )
    }
}
