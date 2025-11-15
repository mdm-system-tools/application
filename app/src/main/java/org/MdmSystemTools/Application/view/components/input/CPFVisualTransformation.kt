package org.MdmSystemTools.Application.view.components.input

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class CPFVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val cpf = text.text

        val formattedCpf = when {
            cpf.length <= 3 -> cpf
            cpf.length <= 6 -> "${cpf.take(3)}.${cpf.drop(3)}"
            cpf.length <= 9 -> "${cpf.take(3)}.${cpf.drop(3).take(3)}.${cpf.drop(6)}"
            else -> "${cpf.take(3)}.${cpf.drop(3).take(3)}.${cpf.drop(6).take(3)}-${cpf.drop(9).take(2)}"
        }

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return when {
                    offset in 0..3 -> offset
                    offset in 4..6 -> offset + 1
                    offset in 7..9 -> offset + 2
                    offset in 10..11 -> offset + 3
                    else -> offset
                }
            }

            override fun transformedToOriginal(offset: Int): Int {
                return when {
                    offset in 0..3 -> offset
                    offset in 4..7 -> offset - 1
                    offset in 8..11 -> offset - 2
                    offset in 12..14 -> offset - 3
                    else -> offset
                }
            }
        }

        return TransformedText(AnnotatedString(formattedCpf), offsetMapping)
    }
}
