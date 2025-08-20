package org.mdmsystemtools.application.Telas.Api

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.collections.emptyList


@Serializable
object ApiService {
	private val client = HttpClient(Android) {
		install(ContentNegotiation) {
			json(Json {
				prettyPrint = true
				isLenient = true
				ignoreUnknownKeys = true
			})
		}
	}
	private const val BASE_URL = "http://10.0.2.2:8080"
	suspend fun getAllAssociated(): List<Associated> {
		try {
			val httpResponse: HttpResponse = client.get("$BASE_URL/associated")
			if (httpResponse.status.value in 300..600) {
				Log.e("ConnectAPI", "Failed to get response")
			}
			if (httpResponse.status.value in 200..299) {
				Log.i("ConnectAPI", "Success to Get response")
			}
			return httpResponse.body() as List<Associated>
		} catch (e: Exception) {
			Log.e("ConnectAPI", "Failed to get response", e)
			e.printStackTrace()
			return emptyList()
		}
	}
}


@Composable
fun FactoryAssociated(associated: Associated?) {
	Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(16.dp)) {
		Text("FOTO AQUI!!!!", modifier = Modifier.padding(end = 16.dp))
		Column {
			Text("Nome: ${associated?.name}")
			Text("GroupID: ${associated?.groupId}")
			Text("Numero Cartão: ${associated?.numberCard}")
		}
	}
}

@Composable
fun ConnectAPI(modifier: Modifier = Modifier) {
	var associatesList by remember { mutableStateOf<List<Associated>>(emptyList()) }
	var isLoading by remember { mutableStateOf(true) }
	var errorMessage by remember { mutableStateOf<String?>(null) }
	val scope = rememberCoroutineScope()

	suspend fun loadAssociated() {
		isLoading = true
		errorMessage = null
		try {
			val result = withContext(Dispatchers.IO) {
				ApiService.getAllAssociated()
			}
			associatesList = result
		} catch (e: Exception) {
			errorMessage = "Falha ao carregar dados: ${e.message}"
			Log.e("ConnectAPI", "Erro na chamada da API", e)
		} finally {
			isLoading = false
		}
	}

	LaunchedEffect(Unit) {
		loadAssociated()
	}

	Scaffold(modifier = modifier) { paddingValues ->
		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(paddingValues)
		) {
			Row(
				modifier = Modifier
					.fillMaxWidth()
					.padding(8.dp),
				horizontalArrangement = Arrangement.End
			) {
				IconButton(
					onClick = {
						scope.launch { loadAssociated() }
					},
					enabled = !isLoading
				) {
					Icon(
						imageVector = Icons.Filled.Refresh,
						contentDescription = "Recarregar"
					)
				}
			}

			Box(
				modifier = Modifier
					.fillMaxSize()
					.weight(1f),
				contentAlignment = Alignment.Center
			) {
				when {
					isLoading -> {
						CircularProgressIndicator()
					}

					errorMessage != null -> {
						Column(horizontalAlignment = Alignment.CenterHorizontally) {
							Text("Erro: $errorMessage")
							Button(onClick = {
								scope.launch { loadAssociated() }
							}) {
								Text("Tentar Novamente")
							}
						}
					}

					associatesList.isEmpty() && !isLoading -> {
						Text("Nenhuma pessoa encontrada.")
					}

					else -> {
						LazyColumn(modifier = Modifier.fillMaxSize()) {
							items(associatesList) { associated ->
								FactoryAssociated(associated)
							}
						}
					}
				}
			}
		}
	}
}