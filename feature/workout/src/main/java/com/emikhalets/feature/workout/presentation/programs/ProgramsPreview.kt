package com.emikhalets.feature.workout.presentation.programs

import com.emikhalets.feature.workout.domain.model.ProgramModel
import com.emikhalets.feature.workout.domain.model.ProgramType

fun getProgramsPreview(): List<ProgramModel> {
    return listOf(
        ProgramModel(1, "Program name", ProgramType.Dynamic),
        ProgramModel(2, "Program name longer longer", ProgramType.Dynamic),
        ProgramModel(3, "Program name longer longer longer longer", ProgramType.Dynamic),
        ProgramModel(
            4,
            "Program name longer longer longer longer longer longer longer longer longer longer",
            ProgramType.Dynamic
        ),
    )
}