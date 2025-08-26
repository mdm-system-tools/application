package org.mdmsystemtools.application.data.repository

import android.util.Log
import androidx.compose.ui.autofill.ContentType
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.serialization.json.Json
import org.mdmsystemtools.application.data.model.AssociatedDto
import org.mdmsystemtools.application.domain.repository.AssociatedRepository

//class ApiService : AssociatedRepository {
//	private val client = HttpClient(Android) {
//        HttpClientConfig.install(ContentNegotiation) {
//            json(Json {
//                prettyPrint = true
//                isLenient = true
//                ignoreUnknownKeys = true
//            })
//        }
//    }
//	private val BASE_URL = "http://10.0.2.2:8080"
//	suspend fun getAllAssociated(): List<AssociatedDto> {
//		try {
//			val httpResponse: HttpResponse = client.get("$BASE_URL/associated")
//			if (httpResponse.status.value == HttpStatusCode.Companion.Conflict.value) {
//				Log.e("ConnectAPI", "Failed to get response: ${httpResponse.body() as String}")
//				return emptyList()
//			}
//			if (httpResponse.status.value == HttpStatusCode.Companion.OK.value) {
//				Log.i("ConnectAPI", "Success to Get response")
//			}
//			return httpResponse.body() as List<AssociatedDto>
//		} catch (e: Exception) {
//			Log.e("ConnectAPI", "Failed to get response", e)
//			e.printStackTrace()
//			return emptyList()
//		}
//	}
//
//	suspend fun postAssociated(associated: AssociatedDto): Boolean {
//		try {
//			val httpResponse: HttpResponse = client.post("$BASE_URL/associated") {
//				contentType(ContentType.Application.Json)
//				setBody(associated)
//			}
//			if (httpResponse.status.value == HttpStatusCode.Companion.Conflict.value) {
//				Log.e("ConnectAPI", "Failed to get response: ${httpResponse.body() as String}")
//				return false
//			}
//			if (httpResponse.status.value == HttpStatusCode.Companion.BadRequest.value) {
//				Log.e("ConnectAPI", "Failed to get response: ${httpResponse.body() as String}")
//				return false
//			}
//			if (httpResponse.status.value == HttpStatusCode.Companion.OK.value) {
//				Log.i("ConnectAPI", "Success to Get response")
//				return true
//			}
//			return true
//		} catch (e: Exception) {
//			Log.e("ConnectAPI", "Failed to get response", e)
//			e.printStackTrace()
//			return false
//		}
//	}
//}