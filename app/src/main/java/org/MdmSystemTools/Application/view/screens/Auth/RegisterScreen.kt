package org.MdmSystemTools.Application.view.screens.Auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RegisterScreen(
    onNavigateToLogin: () -> Unit = {},
    onRegisterSuccess: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var confirmarSenha by remember { mutableStateOf("") }
    var senhaVisivel by remember { mutableStateOf(false) }
    var confirmarSenhaVisivel by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                        MaterialTheme.colorScheme.background
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            // Logo da empresa como plano de fundo
            Image(
                painter = painterResource(id = org.MdmSystemTools.Application.R.drawable.logo_mdm),
                contentDescription = "Logo MDM",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(160.dp)
                    .padding(bottom = 20.dp),
                alpha = 0.9f
            )

            // Título
            Text(
                text = "Criar conta",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Preencha os dados para se cadastrar",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Card com formulário
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Campo de nome
                    OutlinedTextField(
                        value = nome,
                        onValueChange = { nome = it },
                        label = { Text("Nome completo") },
                        placeholder = { Text("Digite seu nome completo") },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = "Nome"
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    // Campo de email
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
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
                        singleLine = true
                    )

                    // Campo de telefone
                    OutlinedTextField(
                        value = telefone,
                        onValueChange = { telefone = formatPhoneNumber(it) },
                        label = { Text("Telefone") },
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
                        singleLine = true
                    )

                    // Campo de senha
                    OutlinedTextField(
                        value = senha,
                        onValueChange = { senha = it },
                        label = { Text("Senha") },
                        placeholder = { Text("Digite sua senha") },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Lock,
                                contentDescription = "Senha"
                            )
                        },
                        trailingIcon = {
                            IconButton(
                                onClick = { senhaVisivel = !senhaVisivel }
                            ) {
                                Icon(
                                    imageVector = if (senhaVisivel) Icons.Default.Visibility
                                    else Icons.Default.VisibilityOff,
                                    contentDescription = if (senhaVisivel) "Ocultar senha" else "Mostrar senha"
                                )
                            }
                        },
                        visualTransformation = if (senhaVisivel) VisualTransformation.None
                        else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    // Campo de confirmar senha
                    OutlinedTextField(
                        value = confirmarSenha,
                        onValueChange = { confirmarSenha = it },
                        label = { Text("Confirmar senha") },
                        placeholder = { Text("Digite novamente sua senha") },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Lock,
                                contentDescription = "Confirmar senha"
                            )
                        },
                        trailingIcon = {
                            IconButton(
                                onClick = { confirmarSenhaVisivel = !confirmarSenhaVisivel }
                            ) {
                                Icon(
                                    imageVector = if (confirmarSenhaVisivel) Icons.Default.Visibility
                                    else Icons.Default.VisibilityOff,
                                    contentDescription = if (confirmarSenhaVisivel) "Ocultar senha" else "Mostrar senha"
                                )
                            }
                        },
                        visualTransformation = if (confirmarSenhaVisivel) VisualTransformation.None
                        else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    // Mensagem de erro
                    if (errorMessage.isNotEmpty()) {
                        Text(
                            text = errorMessage,
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 14.sp,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    // Botão de cadastro
                    Button(
                        onClick = {
                            val validationResult = validateRegistrationForm(
                                nome = nome,
                                email = email,
                                telefone = telefone,
                                senha = senha,
                                confirmarSenha = confirmarSenha
                            )

                            if (validationResult.isNotEmpty()) {
                                errorMessage = validationResult
                            } else {
                                errorMessage = ""
                                isLoading = true
                                // Simular cadastro (substituir por lógica real)
                                // TODO: Implementar registro real
                                onRegisterSuccess()
                            }
                        },
                        enabled = !isLoading,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
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

            // Link para login
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
                    text = "Fazer login",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickable { onNavigateToLogin() }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

private fun formatPhoneNumber(input: String): String {
    val digitsOnly = input.filter { it.isDigit() }
    return when {
        digitsOnly.length <= 2 -> digitsOnly
        digitsOnly.length <= 7 -> "(${digitsOnly.take(2)}) ${digitsOnly.drop(2)}"
        digitsOnly.length <= 11 -> "(${digitsOnly.take(2)}) ${digitsOnly.drop(2).take(5)}-${digitsOnly.drop(7)}"
        else -> "(${digitsOnly.take(2)}) ${digitsOnly.drop(2).take(5)}-${digitsOnly.drop(7).take(4)}"
    }
}

private fun validateRegistrationForm(
    nome: String,
    email: String,
    telefone: String,
    senha: String,
    confirmarSenha: String
): String {
    return when {
        nome.isBlank() -> "Nome é obrigatório"
        nome.length < 2 -> "Nome deve ter pelo menos 2 caracteres"
        email.isBlank() -> "Email é obrigatório"
        !isValidEmail(email) -> "Email inválido"
        telefone.isBlank() -> "Telefone é obrigatório"
        telefone.filter { it.isDigit() }.length < 10 -> "Telefone inválido"
        senha.isBlank() -> "Senha é obrigatória"
        senha.length < 6 -> "Senha deve ter pelo menos 6 caracteres"
        confirmarSenha.isBlank() -> "Confirmação de senha é obrigatória"
        senha != confirmarSenha -> "Senhas não coincidem"
        else -> ""
    }
}

private fun isValidEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}