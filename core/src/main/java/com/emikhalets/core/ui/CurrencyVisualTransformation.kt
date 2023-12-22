package com.emikhalets.core.ui;

import android.icu.text.DecimalFormat
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class CurrencyVisualTransformation(
    private val numberOfDecimals: Int = 2,
) : VisualTransformation {

    private val symbols = DecimalFormat().decimalFormatSymbols
    private val dotChar = '.'
    private val commaChar = ','

    override fun filter(text: AnnotatedString): TransformedText {
        val content = text.text.replace(commaChar, dotChar)
        val dotsCount = content.count { it == dotChar }
        if (dotsCount >= 2) {
            return getPreviousText(content)
        }

        val contentParts = content.split(dotChar)
        val fractionContent = contentParts.getOrNull(1)
            ?: return getPreviousText(content)
        if (fractionContent.length > 2) {
            return getPreviousText(content)
        }

        val offsetMapping = FixedCursorOffsetMapping(text.length, text.length)
        return TransformedText(text, offsetMapping)
    }

    private fun getPreviousText(content: String): TransformedText {
        val notDotContent = content.dropLast(1)
        val newText = AnnotatedString(notDotContent)
        val offsetMapping = FixedCursorOffsetMapping(content.length, notDotContent.length)
        return TransformedText(newText, offsetMapping)
    }

    private class FixedCursorOffsetMapping(
        private val contentLength: Int,
        private val formattedContentLength: Int,
    ) : OffsetMapping {

        override fun originalToTransformed(offset: Int): Int = formattedContentLength
        override fun transformedToOriginal(offset: Int): Int = contentLength
    }
}