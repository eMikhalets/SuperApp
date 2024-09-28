package com.emikhalets.superapp.feature.convert

import com.emikhalets.superapp.core.common.AppResult
import com.emikhalets.superapp.feature.convert.domain.ExchangeModel
import com.emikhalets.superapp.feature.convert.domain.use_case.InsertCurrencyUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class InsertCurrencyUseCaseTest {

    private val repository = ConvertRepositoryTestImpl()
    private val useCase = InsertCurrencyUseCase(repository)

    @Test
    fun `validation empty code`() {
        runBlocking {
            val code = ""
            repository.setExchangesFlow(listOf())
            val actual = useCase.invoke(code)
            assertTrue(actual is InsertCurrencyUseCase.Result.Validation)
        }
    }

    @Test
    fun `validation over length code`() {
        runBlocking {
            val code = "RUBD"
            repository.setExchangesFlow(listOf())
            val actual = useCase.invoke(code)
            assertTrue(actual is InsertCurrencyUseCase.Result.Validation)
        }
    }

    @Test
    fun `validation under length code`() {
        runBlocking {
            val code = "RU"
            repository.setExchangesFlow(listOf())
            val actual = useCase.invoke(code)
            assertTrue(actual is InsertCurrencyUseCase.Result.Validation)
        }
    }

    @Test
    fun `validation wrong symbol code`() {
        runBlocking {
            val code = "RU4"
            repository.setExchangesFlow(listOf())
            val actual = useCase.invoke(code)
            assertTrue(actual is InsertCurrencyUseCase.Result.Validation)
        }
    }

    @Test
    fun `check exist code`() {
        runBlocking {
            val code = "RUB"
            repository.setExchangesFlow(
                listOf(
                    ExchangeModel(1, "USD", "RUB", 0.0, 0),
                )
            )
            val actual = useCase.invoke(code)
            assertTrue(actual is InsertCurrencyUseCase.Result.Exist)
        }
    }

    @Test
    fun `inserting first code`() {
        runBlocking {
            val code = "RUB"
            repository.setExchangesFlow(listOf())
            useCase.invoke(code)
            val expected = listOf(
                ExchangeModel(1, "RUB", "", 0.0, 0),
            )
            val actual = (repository.getExchangesSync() as AppResult.Success).data
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `inserting second code`() {
        runBlocking {
            val code = "USD"
            repository.setExchangesFlow(
                listOf(
                    ExchangeModel(1, "RUB", "", 0.0, 0),
                )
            )
            useCase.invoke(code)
            val expected = listOf(
                ExchangeModel(1, "RUB", "USD", 0.0, 0),
            )
            val actual = (repository.getExchangesSync() as AppResult.Success).data
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `inserting third code`() {
        runBlocking {
            val code = "VND"
            repository.setExchangesFlow(
                listOf(
                    ExchangeModel(1, "RUB", "USD", 0.0, 0),
                )
            )
            useCase.invoke(code)
            val expected = listOf(
                ExchangeModel(1, "RUB", "USD", 0.0, 0),
                ExchangeModel(2, "RUB", "VND", 0.0, 0),
                ExchangeModel(3, "USD", "VND", 0.0, 0),
            )
            val actual = (repository.getExchangesSync() as AppResult.Success).data
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `inserting fourth code`() {
        runBlocking {
            val code = "KZT"
            repository.setExchangesFlow(
                listOf(
                    ExchangeModel(1, "RUB", "USD", 0.0, 0),
                    ExchangeModel(2, "RUB", "VND", 0.0, 0),
                    ExchangeModel(3, "USD", "VND", 0.0, 0),
                )
            )
            useCase.invoke(code)
            val expected = listOf(
                ExchangeModel(1, "RUB", "USD", 0.0, 0),
                ExchangeModel(2, "RUB", "VND", 0.0, 0),
                ExchangeModel(3, "USD", "VND", 0.0, 0),
                ExchangeModel(4, "RUB", "KZT", 0.0, 0),
                ExchangeModel(5, "USD", "KZT", 0.0, 0),
                ExchangeModel(6, "VND", "KZT", 0.0, 0),
            )
            val actual = (repository.getExchangesSync() as AppResult.Success).data
            assertEquals(expected, actual)
        }
    }
}
