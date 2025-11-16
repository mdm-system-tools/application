package org.MdmSystemTools.Application.view.utils.formatter

object CPFUtils {
    const val MAX_CPF_DIGITS = 11

    fun isValidCPF(cpf: String): Boolean {
        val digitsOnly = cpf.filter { it.isDigit() }
        return digitsOnly.length == MAX_CPF_DIGITS
    }

    fun formatCPFInput(input: String): String {
        val digitsOnly = input.filter { it.isDigit() }
        return if (digitsOnly.length <= MAX_CPF_DIGITS) {
            digitsOnly
        } else {
            digitsOnly.take(MAX_CPF_DIGITS)
        }
    }
}
