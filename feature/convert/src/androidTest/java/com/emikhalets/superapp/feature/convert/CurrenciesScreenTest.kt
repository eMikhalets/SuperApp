package com.emikhalets.superapp.feature.convert

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.emikhalets.superapp.feature.convert.domain.use_case.ConvertCurrencyUseCase
import com.emikhalets.superapp.feature.convert.domain.use_case.DeleteCurrencyUseCase
import com.emikhalets.superapp.feature.convert.domain.use_case.GetCurrenciesUseCase
import com.emikhalets.superapp.feature.convert.domain.use_case.GetExchangesUseCase
import com.emikhalets.superapp.feature.convert.domain.use_case.InsertCurrencyUseCase
import com.emikhalets.superapp.feature.convert.domain.use_case.UpdateExchangesUseCase
import com.emikhalets.superapp.feature.convert.ui.currencies.CurrenciesScreen
import com.emikhalets.superapp.feature.convert.ui.currencies.CurrenciesViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test

class CurrenciesScreenTest {

    private val repository = ConvertRepositoryTestImpl()
    private val getExchangesUseCase = GetExchangesUseCase(repository)
    private val getCurrenciesUseCase = GetCurrenciesUseCase(repository)
    private val insertCurrencyUseCase = InsertCurrencyUseCase(repository)
    private val deleteCurrencyUseCase = DeleteCurrencyUseCase(repository)
    private val convertCurrencyUseCase = ConvertCurrencyUseCase()
    private val updateExchangesUseCase = UpdateExchangesUseCase(repository)
    private val viewModel = CurrenciesViewModel(
        getExchangesUseCase,
        getCurrenciesUseCase,
        insertCurrencyUseCase,
        deleteCurrencyUseCase,
        convertCurrencyUseCase,
        updateExchangesUseCase,
    )

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun no_saved_currencies_no_visible() {
        val currencies = viewModel.currentState.currencies

        composeTestRule.setContent {
            CurrenciesScreen(
                navigateBack = {},
                viewModel = viewModel
            )
        }

        for (currency in currencies) {
            composeTestRule.onNodeWithText("$currency :").assertExists()
            composeTestRule.onNodeWithText(currency).assertExists()
        }
    }

    @Test
    fun save_some_currencies_and_test_visible() {
        runBlocking {
            insertCurrencyUseCase.invoke("RUB")
            insertCurrencyUseCase.invoke("USD")
            val currencies = viewModel.currentState.currencies

            composeTestRule.setContent {
                CurrenciesScreen(
                    navigateBack = {},
                    viewModel = viewModel
                )
            }

            for (currency in currencies) {
                composeTestRule.onNodeWithText("$currency :").assertExists()
                composeTestRule.onNodeWithText(currency).assertExists()
            }
        }
    }
}
