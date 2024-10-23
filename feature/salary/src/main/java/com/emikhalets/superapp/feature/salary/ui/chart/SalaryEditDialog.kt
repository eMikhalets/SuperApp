package com.emikhalets.superapp.feature.salary.ui.chart

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.emikhalets.superapp.core.common.constant.Const
import com.emikhalets.superapp.core.common.convertMoney
import com.emikhalets.superapp.core.ui.component.DateSelection
import com.emikhalets.superapp.core.ui.component.DropDownBox
import com.emikhalets.superapp.core.ui.component.TextFieldPrimary
import com.emikhalets.superapp.core.ui.dialog.DialogTwoAction
import com.emikhalets.superapp.core.ui.extentions.ScreenPreview
import com.emikhalets.superapp.core.ui.theme.AppTheme
import com.emikhalets.superapp.feature.salary.domain.SalaryModel
import com.emikhalets.superapp.feature.salary.domain.SalaryType
import java.util.Date

@Composable
internal fun SalaryEditDialog(
    model: SalaryModel?,
    onSaveClick: (SalaryModel) -> Unit,
    onLeftClick: () -> Unit,
) {
    model ?: return

    var value by remember { mutableLongStateOf(model.value) }
    var timestamp by remember { mutableLongStateOf(model.timestamp) }
    var type by remember { mutableStateOf(model.type) }

    val leftText = if (model.id == Const.IdNew) {
        stringResource(com.emikhalets.superapp.core.common.R.string.cancel)
    } else {
        stringResource(com.emikhalets.superapp.core.common.R.string.delete)
    }

    DialogTwoAction(
        leftText = leftText,
        rightText = stringResource(com.emikhalets.superapp.core.common.R.string.save),
        onLeftClick = onLeftClick,
        onRightClick = { onSaveClick(model.set(value, timestamp, type)) }
    ) {
        TextFieldPrimary(
            value = value.convertMoney(),
            onValueChange = { value = it.convertMoney() },
            singleLine = true,
            options = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth()
        )
        DateSelection(
            timestamp = timestamp,
            onSelect = { timestamp = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )
        DropDownBox(
            value = type.toString(),
            options = SalaryType.asStringList(),
            onSelect = { type = SalaryType.valueOf(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )
    }
}

private fun SalaryModel.set(value: Long, timestamp: Long, type: SalaryType): SalaryModel {
    return this.copy(value = value, timestamp = timestamp, type = type)
}

@ScreenPreview
@Composable
private fun Preview() {
    AppTheme {
        SalaryEditDialog(
            model = SalaryModel(
                id = 1,
                value = 12345,
                timestamp = Date().time,
                type = SalaryType.SALARY,
            ),
            onLeftClick = {},
            onSaveClick = {}
        )
    }
}
