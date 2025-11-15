package org.MdmSystemTools.Application.view.utils.state


data class LoginFormState(
    val cpfError: SignInErrors = SignInErrors.Success,
    val passwordError: SignInErrors = SignInErrors.Success
) {

    fun hasError(): Boolean = !cpfError.isSuccess() || !passwordError.isSuccess()

    fun getCpfErrorMessage(): String = if (!cpfError.isSuccess()) cpfError.getMessage() else ""

    fun getPasswordErrorMessage(): String = if (!passwordError.isSuccess()) passwordError.getMessage() else ""
}
