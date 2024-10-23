package com.emikhalets.superapp.feature.convert

import com.emikhalets.superapp.core.common.AppResult
import com.emikhalets.superapp.core.common.localDate
import com.emikhalets.superapp.core.common.timestamp
import com.emikhalets.superapp.feature.convert.domain.ExchangeModel
import com.emikhalets.superapp.feature.convert.domain.use_case.UpdateExchangesUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.assertTrue

class UpdateCurrencyUseCaseTest {

    private val repository = ConvertRepositoryTestImpl()
    private val useCase = UpdateExchangesUseCase(repository)

    @Test
    fun `no need update`() {
        runBlocking {
            val now = timestamp()
            val exchanges = listOf(
                ExchangeModel(1, "USD", "RUB", 0.0, now),
                ExchangeModel(2, "USD", "VND", 0.0, now),
                ExchangeModel(3, "RUB", "VND", 0.0, now),
            )
            useCase.invoke(exchanges)
            assertTrue(useCase.invoke(exchanges) is UpdateExchangesUseCase.Result.NotUpdated)
        }
    }

    @Test
    fun `updating case`() {
        runBlocking {
            val date = timestamp().localDate().minusDays(1).timestamp()
            val exchanges = listOf(
                ExchangeModel(1, "USD", "RUB", 0.0, date),
                ExchangeModel(2, "USD", "VND", 0.0, date),
                ExchangeModel(3, "RUB", "VND", 0.0, date),
            )
            repository.setExchangesFlow(exchanges)
            repository.setExchangesRemote(
                listOf(
                    Pair("USDRUB", 12.0),
                    Pair("USDVND", 57.0),
                    Pair("RUBVND", 38.0),
                )
            )
            useCase.invoke(exchanges)
            val expected = listOf(
                ExchangeModel(1, "USD", "RUB", 12.0, date),
                ExchangeModel(2, "USD", "VND", 57.0, date),
                ExchangeModel(3, "RUB", "VND", 38.0, date),
            )
            val actual = (repository.getExchangesSync() as AppResult.Success).data
            var result = true
            repeat(3) { i ->
                if (actual[i].value != expected[i].value) result = false
            }
            assertTrue(result)
        }
    }
}
