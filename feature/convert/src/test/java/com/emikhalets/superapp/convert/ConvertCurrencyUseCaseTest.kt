package com.emikhalets.superapp.convert

import com.emikhalets.convert.domain.model.ExchangeModel
import com.emikhalets.convert.domain.use_case.ConvertCurrencyUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class ConvertCurrencyUseCaseTest {

    private val useCase = ConvertCurrencyUseCase()
    private val exchanges = listOf(ExchangeModel(0, "USD", "RUB", 100.0, 0))

    @Test
    fun convert_empty() {
        runBlocking {
            val value = ""
            val expected = listOf(Pair("USD", ""), Pair("RUB", ""))
            val pairs = listOf(Pair("USD", ""), Pair("RUB", ""))
            val actual = useCase.invoke(pairs, exchanges, "USD", value)
            assertEquals(expected, actual)
        }
    }

    @Test
    fun convert_1() {
        runBlocking {
            val value = "1"
            val expected = listOf(Pair("USD", "1.00"), Pair("RUB", "100.00"))
            val pairs = listOf(Pair("USD", ""), Pair("RUB", ""))
            val actual = useCase.invoke(pairs, exchanges, "USD", value)
            assertEquals(expected, actual)
        }
    }
}
