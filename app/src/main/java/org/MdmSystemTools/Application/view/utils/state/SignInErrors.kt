package org.MdmSystemTools.Application.view.utils.state


sealed class SignInErrors {
    data object CPFEmpty : SignInErrors()
    data object InvalidCPF : SignInErrors()
    data object PasswordEmpty : SignInErrors()
    data object PasswordTooShort : SignInErrors()
    data object InvalidCredentials : SignInErrors()
    data object Success : SignInErrors()

    fun getMessage(): String = when (this) {
        CPFEmpty -> "CPF é obrigatório"
        InvalidCPF -> "CPF inválido"
        PasswordEmpty -> "Senha é obrigatória"
        PasswordTooShort -> "Senha deve ter no mínimo 6 caracteres"
        InvalidCredentials -> "CPF ou senha inválidos"
        Success -> ""
    }

    fun isSuccess(): Boolean = this is Success
}
