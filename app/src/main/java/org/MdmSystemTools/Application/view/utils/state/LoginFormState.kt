package org.MdmSystemTools.Application.view.utils.state

/**
 * Estado do formulário de login com erros individuais por campo
 */
data class LoginFormState(
    val cpfError: SignInErrors = SignInErrors.Success,
    val passwordError: SignInErrors = SignInErrors.Success
) {
    /**
     * Verifica se há algum erro no formulário
     */
    fun hasError(): Boolean = !cpfError.isSuccess() || !passwordError.isSuccess()

    /**
     * Retorna mensagem do erro do CPF ou string vazia
     */
    fun getCpfErrorMessage(): String = if (!cpfError.isSuccess()) cpfError.getMessage() else ""

    /**
     * Retorna mensagem do erro da senha ou string vazia
     */
    fun getPasswordErrorMessage(): String = if (!passwordError.isSuccess()) passwordError.getMessage() else ""
}
