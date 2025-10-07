package org.MdmSystemTools.Application.view.screens.Auth

import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
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
import androidx.compose.ui.graphics.Brush
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
fun SignInScreen(
	onNavigateToRegister: () -> Unit,
	onNavigateToDashboard: () -> Unit,
	modifier: Modifier = Modifier,
) {
	var email by remember { mutableStateOf("") }
	var password by remember { mutableStateOf("") }
	var visiblePassword by remember { mutableStateOf(false) }
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
				.padding(24.dp),
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.Center
		) {
			Image(
				painter = painterResource(id = org.MdmSystemTools.Application.R.drawable.logo_mdm),
				contentDescription = "Logo MDM",
				contentScale = ContentScale.Fit,
				modifier = Modifier
					.size(200.dp)
					.padding(bottom = 24.dp),
				alpha = 0.9f
			)


			Text(
				text = "Bem-vindo de volta!",
				fontSize = 28.sp,
				fontWeight = FontWeight.Bold,
				color = MaterialTheme.colorScheme.onBackground,
				textAlign = TextAlign.Center,
				modifier = Modifier.padding(bottom = 8.dp)
			)

			Text(
				text = "Faça login para continuar",
				fontSize = 16.sp,
				color = MaterialTheme.colorScheme.onSurfaceVariant,
				textAlign = TextAlign.Center,
				modifier = Modifier.padding(bottom = 32.dp)
			)

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


					OutlinedTextField(
						value = password,
						onValueChange = { password = it },
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
								onClick = { visiblePassword = !visiblePassword }
							) {
								Icon(
									imageVector = if (visiblePassword) Icons.Default.Visibility
									else Icons.Default.VisibilityOff,
									contentDescription = if (visiblePassword) "Ocultar senha" else "Mostrar senha"
								)
							}
						},
						visualTransformation = if (visiblePassword) VisualTransformation.None
						else PasswordVisualTransformation(),
						keyboardOptions = KeyboardOptions(
							keyboardType = KeyboardType.Password
						),
						modifier = Modifier.fillMaxWidth(),
						singleLine = true
					)


					if (errorMessage.isNotEmpty()) {
						Text(
							text = errorMessage,
							color = MaterialTheme.colorScheme.error,
							fontSize = 14.sp,
							modifier = Modifier.fillMaxWidth()
						)
					}


					Button(
						onClick = {
							//TODO isso aqui é uma regra de negocio que vai fica um
							// pouco mais complexa no futuro. é melhor mover isso para uma
							// viewmodel e usar uma interface
							if (email.isBlank() || password.isBlank()) {
								errorMessage = "Por favor, preencha todos os campos"
							} else if (!isValidEmail(email)) {
								errorMessage = "Email inválido"
							} else {
								errorMessage = ""
								isLoading = true
								// TODO ERRO HORRIVEL, deve usar navcontroller e não
								// sobrepor a tela anterior, e se eu quiser deslogar ?
								// que tela o app vai me mandar?
								onNavigateToDashboard()
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
								text = "Entrar",
								fontSize = 16.sp,
								fontWeight = FontWeight.Medium
							)
						}
					}
				}
			}

			Row(
				horizontalArrangement = Arrangement.Center,
				verticalAlignment = Alignment.CenterVertically
			) {
				Text(
					text = "Não tem uma conta? ",
					color = MaterialTheme.colorScheme.onSurfaceVariant,
					fontSize = 14.sp
				)
				Text(
					text = "Cadastre-se",
					color = MaterialTheme.colorScheme.primary,
					fontSize = 14.sp,
					fontWeight = FontWeight.Medium,
					modifier = Modifier.clickable { onNavigateToRegister() }
				)
			}

			Spacer(modifier = Modifier.height(24.dp))

			Text(
				text = "Esqueceu sua senha?",
				color = MaterialTheme.colorScheme.primary,
				fontSize = 14.sp,
				fontWeight = FontWeight.Medium,
				modifier = Modifier.clickable {
				}
			)
		}
	}
}

// TODO isso é uma regra de negocio e não deveria está aqui
private fun isValidEmail(email: String): Boolean {
	return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}