package com.emikhalets.superapp.feature.convert

import com.emikhalets.superapp.core.common.AppResult
import com.emikhalets.superapp.feature.convert.domain.ExchangeModel
import com.emikhalets.superapp.feature.convert.domain.use_case.DeleteCurrencyUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.assertEquals

class DeleteCurrencyUseCaseTest {

    private val repository = ConvertRepositoryTestImpl()
    private val useCase = DeleteCurrencyUseCase(repository)

    @Test
    fun `deleting last item`() {
        runBlocking {
            val deletingCode = "USD"
            repository.setExchangesFlow(
                listOf(
                    ExchangeModel(1, "USD", "", 0.0, 0),
                )
            )
            useCase.invoke(deletingCode)
            val expected = listOf<ExchangeModel>()
            val actual = (repository.getExchangesSync() as AppResult.Success).data
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `deleting last full exchange sub code`() {
        runBlocking {
            val deletingCode = "RUB"
            repository.setExchangesFlow(
                listOf(
                    ExchangeModel(1, "USD", "RUB", 100.0, 10000),
                )
            )
            useCase.invoke(deletingCode)
            val expected = listOf(
                ExchangeModel(1, "USD", "", 0.0, 0),
            )
            val actual = (repository.getExchangesSync() as AppResult.Success).data
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `deleting last full exchange main code`() {
        runBlocking {
            val deletingCode = "USD"
            repository.setExchangesFlow(
                listOf(
                    ExchangeModel(1, "USD", "RUB", 100.0, 10000),
                )
            )
            useCase.invoke(deletingCode)
            val expected = listOf(
                ExchangeModel(1, "RUB", "", 0.0, 0),
            )
            val actual = (repository.getExchangesSync() as AppResult.Success).data
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `deleting some code`() {
        runBlocking {
            val deletingCode = "USD"
            repository.setExchangesFlow(
                listOf(
                    ExchangeModel(1, "USD", "RUB", 100.0, 0),
                    ExchangeModel(2, "USD", "VND", 25000.0, 0),
                    ExchangeModel(3, "RUB", "VND", 250.0, 0),
                    ExchangeModel(4, "USD", "KZT", 470.0, 0),
                    ExchangeModel(5, "VND", "KZT", 0.02, 0),
                    ExchangeModel(6, "RUB", "KZT", 5.0, 0),
                )
            )
            useCase.invoke(deletingCode)
            val expected = listOf(
                ExchangeModel(3, "RUB", "VND", 250.0, 0),
                ExchangeModel(5, "VND", "KZT", 0.02, 0),
                ExchangeModel(6, "RUB", "KZT", 5.0, 0),
            )
            val actual = (repository.getExchangesSync() as AppResult.Success).data
            assertEquals(expected, actual)
        }
    }
}
