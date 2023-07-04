package com.emikhalets.fitness.presentation.programs

import com.emikhalets.fitness.domain.entity.ProgramEntity

fun getProgramsPreview(): List<ProgramEntity> {
    return listOf(
        ProgramEntity("Program name", emptyList()),
        ProgramEntity("Program name longer longer", emptyList()),
        ProgramEntity("Program name longer longer longer longer", emptyList()),
        ProgramEntity(
            "Program name longer longer longer longer longer longer longer longer longer longer",
            emptyList()
        ),
    )
}