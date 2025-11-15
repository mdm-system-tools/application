package org.MdmSystemTools.Application.view.utils.validator

import android.util.Patterns
import org.MdmSystemTools.Application.view.utils.state.SignUpErrors

object RegisterValidator {

    /**
     * Valida todos os campos de registro
     * Retorna um SignUpErrors com o tipo de erro ou Success
     */
    fun validateRegisterForm(
        name: String,
        email: String,
        phone: String,
        password: String,
        confirmPassword: String
    ): SignUpErrors {
        return when {
            name.isBlank() -> SignUpErrors.NameEmpty
            name.length < 2 -> SignUpErrors.NameTooShort
            email.isBlank() -> SignUpErrors.EmailEmpty
            !isValidEmail(email) -> SignUpErrors.InvalidEmail
            phone.isBlank() -> SignUpErrors.PhoneEmpty
            !isValidPhone(phone) -> SignUpErrors.InvalidPhone
            password.isBlank() -> SignUpErrors.PasswordEmpty
            password.length < 6 -> SignUpErrors.PasswordTooShort
            confirmPassword.isBlank() -> SignUpErrors.ConfirmPasswordEmpty
            password != confirmPassword -> SignUpErrors.PasswordsDoNotMatch
            else -> SignUpErrors.Success
        }
    }

    /**
     * Valida o formato do email
     */
    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    /**
     * Valida o formato do telefone (deve ter no mínimo 10 dígitos)
     */
    private fun isValidPhone(phone: String): Boolean {
        val digitsOnly = phone.filter { it.isDigit() }
        return digitsOnly.length >= 10
    }
}
