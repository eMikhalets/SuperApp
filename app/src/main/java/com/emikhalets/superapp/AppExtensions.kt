package com.emikhalets.superapp

import com.emikhalets.core.ui.BottomBarModel

internal fun MutableList<BottomBarModel>.update(items: List<BottomBarModel>) {
    when {
        items.isEmpty() -> this.clear()
        this != items -> {
            this.clear()
            this.addAll(items)
        }
    }
}
