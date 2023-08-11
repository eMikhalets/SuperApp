package com.emikhalets.fitness.presentation.menu

import com.emikhalets.common.UiString

data class MenuState(
    val workoutInitialized: Boolean = false,
    val error: UiString? = null,
)
