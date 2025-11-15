package org.MdmSystemTools.Application.model.repository

import org.MdmSystemTools.Application.model.entity.Login
import org.MdmSystemTools.Application.model.entity.Register

class AuthRepositoryImpl : AuthRepository {
  private val registeredUsers = mutableListOf<Register>()
  private var currentLoggedInUser: Login? = null

  init {
    // Add demo user for testing
    registeredUsers.add(
      Register(
        id = 1,
        name = "Demo User",
        email = "demo@example.com",
        phone = "(11) 98765-4321",
        password = "password123"
      )
    )
  }

  override fun login(cpf: String, password: String): Result<Login> {
    return try {
      val user = registeredUsers.find { it.email == cpf && it.password == password }
      if (user != null) {
        val login = Login(cpf = cpf, password = password)
        currentLoggedInUser = login
        Result.success(login)
      } else {
        Result.failure(Exception("CPF ou senha inválidos"))
      }
    } catch (e: Exception) {
      Result.failure(e)
    }
  }

  override fun register(register: Register): Result<Register> {
    return try {
      val existingUser = registeredUsers.find { it.email == register.email }
      if (existingUser != null) {
        Result.failure(Exception("Este email já está cadastrado"))
      } else {
        val newRegister = register.copy(id = registeredUsers.size + 1)
        registeredUsers.add(newRegister)
        Result.success(newRegister)
      }
    } catch (e: Exception) {
      Result.failure(e)
    }
  }

  override fun logout() {
    currentLoggedInUser = null
  }

  override fun isUserLoggedIn(): Boolean {
    return currentLoggedInUser != null
  }
}
