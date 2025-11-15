package org.MdmSystemTools.Application.view.utils.validator

import org.MdmSystemTools.Application.view.utils.state.SignInErrors
import org.MdmSystemTools.Application.view.utils.state.LoginFormState

object LoginValidator {

    /**
     * Valida todos os campos de login
     * Retorna um LoginFormState com erros individuais por campo
     */
    fun validateLoginForm(cpf: String, password: String): LoginFormState {
        val cpfError = validateCPF(cpf)
        val passwordError = validatePassword(password)

        return LoginFormState(
            cpfError = cpfError,
            passwordError = passwordError
        )
    }

    /**
     * Valida apenas o CPF
     */
    private fun validateCPF(cpf: String): SignInErrors {
        return when {
            cpf.isBlank() -> SignInErrors.CPFEmpty
            !CPFValidator.isValid(cpf) -> SignInErrors.InvalidCPF
            else -> SignInErrors.Success
        }
    }

    /**
     * Valida apenas a senha
     */
    private fun validatePassword(password: String): SignInErrors {
        return when {
            password.isBlank() -> SignInErrors.PasswordEmpty
            password.length < 6 -> SignInErrors.PasswordTooShort
            else -> SignInErrors.Success
        }
    }
}
