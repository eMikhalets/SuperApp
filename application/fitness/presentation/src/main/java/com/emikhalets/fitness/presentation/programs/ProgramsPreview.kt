package com.emikhalets.fitness.presentation.programs

import com.emikhalets.fitness.domain.entity.ProgramEntity
import com.emikhalets.fitness.domain.entity.enums.ProgramType

fun getProgramsPreview(): List<ProgramEntity> {
    return listOf(
        ProgramEntity(
            1, "Program name", emptyList(), ProgramType.Dynamic
        ),
        ProgramEntity(
            2, "Program name longer longer", emptyList(), ProgramType.Dynamic
        ),
        ProgramEntity(
            3, "Program name longer longer longer longer",
            emptyList(), ProgramType.Dynamic
        ),
        ProgramEntity(
            4,
            "Program name longer longer longer longer longer longer longer longer longer longer",
            emptyList(), ProgramType.Dynamic
        ),
    )
}