package com.emikhalets.core.superapp.ui

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.core.text.isDigitsOnly
import java.text.NumberFormat

class CurrencyVisualTransformation : VisualTransformation {

    private val numberFormatter = NumberFormat.getInstance().apply {
        maximumFractionDigits = 2
    }

    override fun filter(text: AnnotatedString): TransformedText {
        val originalText = text.text.trim()
        if (originalText.isEmpty()) {
            return TransformedText(text, OffsetMapping.Identity)
        }
        if (!originalText.isDigitsOnly()) {
            return TransformedText(text, OffsetMapping.Identity)
        }

        val parts = originalText.split(".")
        val leftPart = parts.getOrNull(0) ?: ""
        val rightPart = parts.getOrNull(1) ?: ""
        if (rightPart.length > 2) {
            return TransformedText(text, OffsetMapping.Identity)
        }

        var spaceCounter = 0
        val newLeftPart = mutableListOf<String>()
        for (i in (leftPart.length - 1) downTo 0) {
            if (spaceCounter == 3) {
                spaceCounter = 0
                newLeftPart.add(" ")
            }
            newLeftPart.add(0, leftPart[i].toString())
            spaceCounter++
        }

        val formattedLeftPart = newLeftPart.joinToString("")
        val formattedText = buildString {
            append(formattedLeftPart)
            if (rightPart.isNotBlank()) {
                append(".")
                append(rightPart)
            }
        }
        return TransformedText(
            AnnotatedString(formattedText),
            CurrencyOffsetMapping(originalText, formattedText)
        )
    }

    class CurrencyOffsetMapping(originalText: String, formattedText: String) : OffsetMapping {

        private val originalLength: Int = originalText.length
        private val indexes = findDigitIndexes(originalText, formattedText)

        private fun findDigitIndexes(firstString: String, secondString: String): List<Int> {
            val digitIndexes = mutableListOf<Int>()
            var currentIndex = 0
            for (digit in firstString) {
                // Find the index of the digit in the second string
                val index = secondString.indexOf(digit, currentIndex)
                if (index != -1) {
                    digitIndexes.add(index)
                    currentIndex = index + 1
                } else {
                    // If the digit is not found, return an empty list
                    return emptyList()
                }
            }
            return digitIndexes
        }

        override fun originalToTransformed(offset: Int): Int {
            if (offset >= originalLength) {
                return indexes.last() + 1
            }
            return indexes[offset]
        }

        override fun transformedToOriginal(offset: Int): Int {
            return indexes
                .indexOfFirst { it >= offset }
                .takeIf { it != -1 }
                ?: originalLength
        }
    }
}