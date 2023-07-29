package com.emikhalets.feature.workout.domain.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.emikhalets.feature.workout.R

enum class ProgramType {

    Dynamic, Static;

    @Composable
    fun getName(): String {
        return when (this) {
            Dynamic -> stringResource(R.string.feature_workout_program_dynamic)
            Static -> stringResource(R.string.feature_workout_program_dynamic)
        }
    }

    companion object {

        fun getList(): List<ProgramType> {
            return values().toList()
        }
    }
}