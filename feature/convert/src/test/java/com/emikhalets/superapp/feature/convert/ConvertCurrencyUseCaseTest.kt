package com.emikhalets.superapp.feature.convert

import com.emikhalets.superapp.feature.convert.domain.ExchangeModel
import com.emikhalets.superapp.feature.convert.domain.use_case.ConvertCurrencyUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.assertEquals

class ConvertCurrencyUseCaseTest {

    private val useCase = ConvertCurrencyUseCase()
    private val exchanges = listOf(
        ExchangeModel(0, "USD", "RUB", 100.0, 0),
        ExchangeModel(0, "USD", "VND", 25000.0, 0),
        ExchangeModel(0, "RUB", "VND", 250.0, 0),
        ExchangeModel(0, "USD", "KZT", 470.0, 0),
        ExchangeModel(0, "VND", "KZT", 0.02, 0),
        ExchangeModel(0, "RUB", "KZT", 5.0, 0),
    )

    @Test
    fun `convert case 0`() {
        runBlocking {
            val baseValue = 0L
            val baseCode = "USD"
            val pairs = listOf(
                Pair("USD", 0L),
                Pair("RUB", 0L),
                Pair("VND", 0L),
                Pair("KZT", 0L)
            )
            val expected = listOf(
                Pair("USD", 0L),
                Pair("RUB", 0L),
                Pair("VND", 0L),
                Pair("KZT", 0L)
            )
            val actual = useCase.invoke(pairs, exchanges, baseCode, baseValue)
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `convert case 1`() {
        runBlocking {
            val baseValue = 100L
            val baseCode = "USD"
            val pairs = listOf(
                Pair("USD", 0L),
                Pair("RUB", 0L),
                Pair("VND", 0L),
                Pair("KZT", 0L),
            )
            val expected = listOf(
                Pair("USD", 100L),
                Pair("RUB", 10000L),
                Pair("VND", 2500000L),
                Pair("KZT", 47000L),
            )
            val actual = useCase.invoke(pairs, exchanges, baseCode, baseValue)
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `convert case 2`() {
        runBlocking {
            val baseValue = 20000L
            val baseCode = "USD"
            val pairs = listOf(
                Pair("USD", 0L),
                Pair("RUB", 0L),
                Pair("VND", 0L),
                Pair("KZT", 0L),
            )
            val expected = listOf(
                Pair("USD", 20000L),
                Pair("RUB", 2000000L),
                Pair("VND", 500000000L),
                Pair("KZT", 9400000L),
            )
            val actual = useCase.invoke(pairs, exchanges, baseCode, baseValue)
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `convert case 3`() {
        runBlocking {
            val baseValue = 50L
            val baseCode = "USD"
            val pairs = listOf(
                Pair("USD", 0L),
                Pair("RUB", 0L),
                Pair("VND", 0L),
                Pair("KZT", 0L),
            )
            val expected = listOf(
                Pair("USD", 50L),
                Pair("RUB", 5000L),
                Pair("VND", 1250000L),
                Pair("KZT", 23500L),
            )
            val actual = useCase.invoke(pairs, exchanges, baseCode, baseValue)
            assertEquals(expected, actual)
        }
    }
}
