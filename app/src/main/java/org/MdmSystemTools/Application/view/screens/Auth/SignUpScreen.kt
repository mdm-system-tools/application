package org.MdmSystemTools.Application.view.screens.Auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.MdmSystemTools.Application.model.entity.Register
import org.MdmSystemTools.Application.view.utils.validator.RegisterValidator
import org.MdmSystemTools.Application.view.utils.state.SignUpErrors

@Composable
fun SignUpScreen(
    onNavigateToLogin: () -> Unit = {},
    onRegisterSuccess: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var register by remember { mutableStateOf(Register()) }
    var confirmPassword by remember { mutableStateOf("") }
    var visiblePassword by remember { mutableStateOf(false) }
    var confirmVisiblePassword by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Spacer(modifier = Modifier.height(24.dp))
        }

        item {
            Image(
                painter = painterResource(id = org.MdmSystemTools.Application.R.drawable.logo_mdm),
                contentDescription = "Logo MDM",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(160.dp)
                    .padding(bottom = 24.dp),
                alpha = 0.9f
            )
        }

        item {
            Text(
                text = "Criar Conta",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        item {
            Text(
                text = "Preencha os dados para se cadastrar",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 24.dp)
            )
        }

        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(containerColor = androidx.compose.ui.graphics.Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedTextField(
                        value = register.name,
                        onValueChange = { register = register.copy(name = it) },
                        placeholder = { Text("Digite seu nome completo") },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = "Nome"
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = register.email,
                        onValueChange = { register = register.copy(email = it) },
                        placeholder = { Text("Digite seu email") },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Email,
                                contentDescription = "Email"
                            )
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = register.phone,
                        onValueChange = { register = register.copy(phone = formatPhoneNumber(it)) },
                        placeholder = { Text("(11) 99999-9999") },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Phone,
                                contentDescription = "Telefone"
                            )
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Phone
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = register.password,
                        onValueChange = { register = register.copy(password = it) },
                        placeholder = { Text("Digite sua senha") },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Lock,
                                contentDescription = "Senha"
                            )
                        },
                        trailingIcon = {
                            IconButton(
                                onClick = { visiblePassword = !visiblePassword }
                            ) {
                                Icon(
                                    imageVector = if (visiblePassword) Icons.Default.Visibility
                                    else Icons.Default.VisibilityOff,
                                    contentDescription = if (visiblePassword) "Mostrar senha" else "Ocultar senha"
                                )
                            }
                        },
                        visualTransformation = if (visiblePassword) VisualTransformation.None
                        else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        placeholder = { Text("Confirme sua senha") },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Lock,
                                contentDescription = "Confirmar senha"
                            )
                        },
                        trailingIcon = {
                            IconButton(
                                onClick = { confirmVisiblePassword = !confirmVisiblePassword }
                            ) {
                                Icon(
                                    imageVector = if (confirmVisiblePassword) Icons.Default.Visibility
                                    else Icons.Default.VisibilityOff,
                                    contentDescription = if (confirmVisiblePassword) "Mostrar senha" else "Ocultar senha"
                                )
                            }
                        },
                        visualTransformation = if (confirmVisiblePassword) VisualTransformation.None
                        else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        singleLine = true
                    )

                    if (errorMessage.isNotEmpty()) {
                        Text(
                            text = errorMessage,
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 12.sp,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Button(
                        onClick = {
                            val result = RegisterValidator.validateRegisterForm(
                                name = register.name,
                                email = register.email,
                                phone = register.phone,
                                password = register.password,
                                confirmPassword = confirmPassword
                            )

                            errorMessage = result.getMessage()
                            when (result) {
                                is SignUpErrors.Success -> {
                                    isLoading = true
                                    // TODO: Implement real registration
                                    onRegisterSuccess()
                                }
                                else -> {} // Erro já foi atualizado em errorMessage
                            }
                        },
                        enabled = !isLoading,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = MaterialTheme.colorScheme.onPrimary,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text(
                                text = "Cadastrar",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }

        item {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Já tem uma conta? ",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 14.sp
                )
                Text(
                    text = "Faça login",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickable { onNavigateToLogin() }
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

private fun formatPhoneNumber(input: String): String {
	val digitsOnly = input.filter { it.isDigit() }
	return when {
		digitsOnly.length <= 2 -> digitsOnly
		digitsOnly.length <= 7 -> "(${digitsOnly.take(2)}) ${digitsOnly.drop(2)}"
		digitsOnly.length <= 11 -> "(${digitsOnly.take(2)}) ${
			digitsOnly.drop(2).take(5)
		}-${digitsOnly.drop(7)}"

		else -> "(${digitsOnly.take(2)}) ${digitsOnly.drop(2).take(5)}-${digitsOnly.drop(7).take(4)}"
	}
}

