package org.MdmSystemTools.Application.view.utils.validator

object CPFValidator {
    private const val CPF_LENGTH = 11
    private const val INVALID_DIGIT = -1

    fun isValid(cpf: String?): Boolean {
        if (cpf.isNullOrBlank()) {
            return false
        }

        val digitsOnly = cpf.filter { it.isDigit() }

        if (digitsOnly.length != CPF_LENGTH) {
            return false
        }


        val digits = digitsOnly.map { it.toString().toInt() }


        if (isSequenceRepeated(digits)) {
            return false
        }


        return isFirstDigitValid(digits) && isSecondDigitValid(digits)
    }

    /**
     * Verifica se o CPF é uma sequência repetida
     * Exemplos: 111.111.111-11, 222.222.222-22, etc.
     */
    private fun isSequenceRepeated(digits: List<Int>): Boolean {
        val firstDigit = digits[0]
        return digits.all { it == firstDigit }
    }
    private fun isFirstDigitValid(digits: List<Int>): Boolean {
        return try {
            val soma = (0..8).sumOf { i ->
                digits[i] * (10 - i)
            }

            var resto = (soma * 10) % 11

            if (resto == 10) {
                resto = 0
            }

            resto == digits[9]
        } catch (e: Exception) {
            false
        }
    }

    private fun isSecondDigitValid(digits: List<Int>): Boolean {
        return try {
            val soma = (0..9).sumOf { i ->
                digits[i] * (11 - i)
            }

            var resto = (soma * 10) % 11

            if (resto == 10) {
                resto = 0
            }

            resto == digits[10]
        } catch (e: Exception) {
            false
        }
    }
}
