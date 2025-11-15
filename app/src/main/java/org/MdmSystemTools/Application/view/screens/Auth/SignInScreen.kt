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
import androidx.compose.material.icons.filled.Badge
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
import androidx.compose.runtime.saveable.rememberSaveable
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
import org.MdmSystemTools.Application.view.components.input.CPFVisualTransformation
import org.MdmSystemTools.Application.view.utils.formatter.CPFUtils
import org.MdmSystemTools.Application.view.utils.validator.LoginValidator
import org.MdmSystemTools.Application.view.utils.state.LoginFormState

@Composable
fun SignInScreen(
    onNavigateToRegister: () -> Unit,
    onNavigateToDashboard: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var cpf by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var visiblePassword by rememberSaveable { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var formState by remember { mutableStateOf(LoginFormState()) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        item {
            Image(
                painter = painterResource(id = org.MdmSystemTools.Application.R.drawable.logo_mdm),
                contentDescription = "Logo MDM",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(180.dp)
                    .padding(bottom = 32.dp),
                alpha = 0.9f
            )
        }

        item {
            Text(
                text = "Bem-vindo de volta!",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        item {
            Text(
                text = "Faça login para continuar",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 32.dp)
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
                        value = cpf,
                        onValueChange = { newValue ->
                            cpf = CPFUtils.formatCPFInput(newValue)
                        },
                        placeholder = { Text("000.000.000-00") },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Badge,
                                contentDescription = "CPF"
                            )
                        },
                        visualTransformation = CPFVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        isError = !formState.cpfError.isSuccess(),
                        supportingText = {
                            if (!formState.cpfError.isSuccess()) {
                                Text(
                                    text = formState.getCpfErrorMessage(),
                                    color = MaterialTheme.colorScheme.error,
                                    fontSize = 12.sp
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
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
                        isError = !formState.passwordError.isSuccess(),
                        supportingText = {
                            if (!formState.passwordError.isSuccess()) {
                                Text(
                                    text = formState.getPasswordErrorMessage(),
                                    color = MaterialTheme.colorScheme.error,
                                    fontSize = 12.sp
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        singleLine = true
                    )

                    Button(
                        onClick = {
                            formState = LoginValidator.validateLoginForm(cpf, password)
                            when {
                                !formState.hasError() -> {
                                    isLoading = true
                                    onNavigateToDashboard()
                                }
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
                                text = "Entrar",
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
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
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
