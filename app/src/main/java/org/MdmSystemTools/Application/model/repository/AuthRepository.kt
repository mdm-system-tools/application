package org.MdmSystemTools.Application.model.repository

import org.MdmSystemTools.Application.model.entity.Login
import org.MdmSystemTools.Application.model.entity.Register

interface AuthRepository {
  fun login(cpf: String, password: String): Result<Login>

  fun register(register: Register): Result<Register>

  fun logout()

  fun isUserLoggedIn(): Boolean
}
