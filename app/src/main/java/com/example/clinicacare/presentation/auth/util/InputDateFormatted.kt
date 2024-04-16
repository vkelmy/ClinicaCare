package com.example.clinicacare.presentation.auth.util

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class InputDateFormatted() : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        val cpfMask = text.text.mapIndexed { index, c ->
            when (index) {
                1 -> "$c/"
                3 -> "$c/"
                else -> c
            }
        }.joinToString(separator = "")

        return TransformedText(
            AnnotatedString(cpfMask),
            DateOffsetMapping
        )
    }

    object DateOffsetMapping: OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            return when {
                offset > 3 -> offset + 2
                offset > 1 -> offset + 1
                else -> offset
            }
        }

        override fun transformedToOriginal(offset: Int): Int {
            return when {
                offset > 3 -> offset - 2
                offset > 1 -> offset - 1
                else -> offset
            }
        }
    }
}