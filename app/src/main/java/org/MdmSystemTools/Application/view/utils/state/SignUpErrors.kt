package org.MdmSystemTools.Application.view.utils.state


sealed class SignUpErrors {
    data object NameEmpty : SignUpErrors()
    data object NameTooShort : SignUpErrors()
    data object EmailEmpty : SignUpErrors()
    data object InvalidEmail : SignUpErrors()
    data object PhoneEmpty : SignUpErrors()
    data object InvalidPhone : SignUpErrors()
    data object PasswordEmpty : SignUpErrors()
    data object PasswordTooShort : SignUpErrors()
    data object ConfirmPasswordEmpty : SignUpErrors()
    data object PasswordsDoNotMatch : SignUpErrors()
    data object EmailAlreadyExists : SignUpErrors()
    data object Success : SignUpErrors()

    fun getMessage(): String = when (this) {
        NameEmpty -> "Nome é obrigatório"
        NameTooShort -> "Nome deve ter pelo menos 2 caracteres"
        EmailEmpty -> "Email é obrigatório"
        InvalidEmail -> "Email inválido"
        PhoneEmpty -> "Telefone é obrigatório"
        InvalidPhone -> "Telefone inválido"
        PasswordEmpty -> "Senha é obrigatória"
        PasswordTooShort -> "Senha deve ter pelo menos 6 caracteres"
        ConfirmPasswordEmpty -> "Confirmação de senha é obrigatória"
        PasswordsDoNotMatch -> "Senhas não coincidem"
        EmailAlreadyExists -> "Este email já está cadastrado"
        Success -> ""
    }


    fun isSuccess(): Boolean = this is Success
}
