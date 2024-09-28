package com.emikhalets.superapp.feature.convert

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.emikhalets.superapp.feature.convert.domain.use_case.ConvertCurrencyUseCase
import com.emikhalets.superapp.feature.convert.domain.use_case.DeleteCurrencyUseCase
import com.emikhalets.superapp.feature.convert.domain.use_case.GetExchangesUseCase
import com.emikhalets.superapp.feature.convert.domain.use_case.InsertCurrencyUseCase
import com.emikhalets.superapp.feature.convert.domain.use_case.UpdateExchangesUseCase
import com.emikhalets.superapp.feature.convert.ui.currencies.CurrenciesScreen
import com.emikhalets.superapp.feature.convert.ui.currencies.CurrenciesViewModel
import org.junit.Rule
import org.junit.Test

class CurrenciesScreenTest {

    private val repository = ConvertRepositoryTestImpl()
    private val getExchangesUseCase = GetExchangesUseCase(repository)
    private val insertCurrencyUseCase = InsertCurrencyUseCase(repository)
    private val deleteCurrencyUseCase = DeleteCurrencyUseCase(repository)
    private val convertCurrencyUseCase = ConvertCurrencyUseCase()
    private val updateExchangesUseCase = UpdateExchangesUseCase(repository)
    private val viewModel = CurrenciesViewModel(
        getExchangesUseCase,
        insertCurrencyUseCase,
        deleteCurrencyUseCase,
        convertCurrencyUseCase,
        updateExchangesUseCase,
    )

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun save_some_currencies_than_delete_one() {
        composeTestRule.setContent {
            CurrenciesScreen(
                navigateBack = {},
                viewModel = viewModel
            )
        }

        composeTestRule.onNodeWithText("Add currency").performClick()
        composeTestRule.onNodeWithText("New currency code").assertExists()
        composeTestRule.onNodeWithTag("currencyCodeTextField").performTextInput("USD")
        composeTestRule.onNodeWithTag("currencyCodeSaveIcon").performClick()
        composeTestRule.onNodeWithText("New currency code").assertDoesNotExist()
        composeTestRule.onNodeWithText("USD").assertExists()
        composeTestRule.onNodeWithText("Add currency").performClick()
        composeTestRule.onNodeWithTag("currencyCodeTextField").performTextInput("RUB")
        composeTestRule.onNodeWithTag("currencyCodeSaveIcon").performClick()
        composeTestRule.onNodeWithText("USD").assertExists()
        composeTestRule.onNodeWithText("RUB").assertExists()
    }
}
