package com.emikhalets.superapp

import androidx.activity.compose.setContent
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.emikhalets.superapp.core.ui.AppFeature
import org.junit.Rule
import org.junit.Test

class FeaturesScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun visibility_feature_convert() {
        composeTestRule.activity.setContent {
            FeaturesScreen(
                navigateToFeature = {},
            )
        }

        val feature = AppFeature.Convert
        if (feature.visible) {
            val name = composeTestRule.activity.resources.getString(feature.nameRes)
            composeTestRule.onNodeWithText(name).assertExists()
        }
    }

    @Test
    fun visibility_feature_notes() {
        composeTestRule.activity.setContent {
            FeaturesScreen(
                navigateToFeature = {},
            )
        }

        val feature = AppFeature.Notes
        if (feature.visible) {
            val name = composeTestRule.activity.resources.getString(feature.nameRes)
            composeTestRule.onNodeWithText(name).assertExists()
        }
    }

    @Test
    fun visibility_feature_salary() {
        composeTestRule.activity.setContent {
            FeaturesScreen(
                navigateToFeature = {},
            )
        }

        val feature = AppFeature.Salary
        if (feature.visible) {
            val name = composeTestRule.activity.resources.getString(feature.nameRes)
            composeTestRule.onNodeWithText(name).assertExists()
        }
    }
}
