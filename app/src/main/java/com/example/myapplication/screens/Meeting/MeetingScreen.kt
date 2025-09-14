package org.mdmsystemtools.application.presentation.ui.screens.Reunião

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import org.mdmsystemtools.application.presentation.ui.components.ButtonFormAdd
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.model.CalendarDateDto
import com.example.myapplication.model.EventDto
import java.util.*

//TODO REFACTORING está tela precisa de um viewmodel, olhe como foi feito no homescreen
@Composable
fun ReuniaoScreen(
	modifier: Modifier = Modifier,
	// TODO REFACTORING troca a implementação pela interface
	eventRepositoryImpl: EventRepositoryImpl? = null,
	onNavigateToAddEvent: ((CalendarDateDto) -> Unit)? = null
) {
	var currentMonth by remember { mutableStateOf(Calendar.getInstance().get(Calendar.MONTH)) }
	var currentYear by remember { mutableStateOf(Calendar.getInstance().get(Calendar.YEAR)) }
	val today = Calendar.getInstance()

	Box(modifier = modifier.fillMaxSize()) {
		Column(
			modifier = Modifier.fillMaxSize()
		) {
			// Header do calendário
			CalendarHeader(
				month = currentMonth,
				year = currentYear,
				onPreviousMonth = {
					if (currentMonth == 0) {
						currentMonth = 11
						currentYear--
					} else {
						currentMonth--
					}
				},
				onNextMonth = {
					if (currentMonth == 11) {
						currentMonth = 0
						currentYear++
					} else {
						currentMonth++
					}
				}
			)

			// Grid do calendário
			CalendarGrid(
				month = currentMonth,
				year = currentYear,
				eventRepositoryImpl = eventRepositoryImpl,
				onDateClick = { date ->
					onNavigateToAddEvent?.invoke(date)
				}
			)

			// Lista de eventos do mês
			if (eventRepositoryImpl != null) {
				EventsList(
					eventRepositoryImpl = eventRepositoryImpl,
					month = currentMonth,
					year = currentYear
				)
			}
		}

		// Botão flutuante customizado
		ButtonFormAdd(
			onClick = {
				// Adicionar evento para hoje por padrão
				val nowCalendar = Calendar.getInstance()
				val todayDate = CalendarDateDto(
					nowCalendar.get(Calendar.DAY_OF_MONTH),
					nowCalendar.get(Calendar.MONTH),
					nowCalendar.get(Calendar.YEAR),
					true
				)
				onNavigateToAddEvent?.invoke(todayDate)
			}
		)
	}
}

@Composable
private fun CalendarHeader(
	month: Int,
	year: Int,
	onPreviousMonth: () -> Unit,
	onNextMonth: () -> Unit
) {
	Column {
		// Título principal
		Row(
			modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 24.dp, vertical = 16.dp),
			horizontalArrangement = Arrangement.SpaceBetween,
			verticalAlignment = Alignment.CenterVertically
		) {
			Text(
				text = "${getMonthName(month)} $year",
				fontSize = 28.sp,
				fontWeight = FontWeight.Bold,
				color = MaterialTheme.colorScheme.onBackground
			)

			Row {
				IconButton(
					onClick = onPreviousMonth,
					modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
				) {
					Icon(
						Icons.Default.ChevronLeft,
						contentDescription = "Mês anterior",
						tint = MaterialTheme.colorScheme.onSurfaceVariant
					)
				}

				IconButton(
					onClick = onNextMonth,
					modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
				) {
					Icon(
						Icons.Default.ChevronRight,
						contentDescription = "Próximo mês",
						tint = MaterialTheme.colorScheme.onSurfaceVariant
					)
				}
			}
		}
	}
}

@Composable
private fun CalendarGrid(
	month: Int,
	year: Int,
	eventRepositoryImpl: EventRepositoryImpl?,
	onDateClick: (CalendarDateDto) -> Unit
) {
	val calendar = Calendar.getInstance()
	calendar.set(year, month, 1)
	val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
	val firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1

	// Get data atual sempre fresh
	val todayCalendar = remember { Calendar.getInstance() }
	// Força refresh da data atual a cada recomposição
	val currentDate = Calendar.getInstance()
	val actualToday = currentDate.get(Calendar.DAY_OF_MONTH)
	val actualMonth = currentDate.get(Calendar.MONTH)
	val actualYear = currentDate.get(Calendar.YEAR)

	val isShowingCurrentMonth = actualMonth == month && actualYear == year
	val todayDay = if (isShowingCurrentMonth) actualToday else -1

	Column(
		modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp)
	) {
		// Cabeçalho dos dias da semana
		Row(
			modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp)
		) {
			listOf("D", "S", "T", "Q", "Q", "S", "S").forEach { day ->
				Text(
					text = day,
					modifier = Modifier.weight(1f),
					textAlign = TextAlign.Center,
					fontSize = 14.sp,
					fontWeight = FontWeight.Medium,
					color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
				)
			}
		}

		// Grid dos dias
		for (week in 0 until 6) {
			Row(modifier = Modifier.fillMaxWidth()) {
				for (dayOfWeek in 0 until 7) {
					val dayNumber = week * 7 + dayOfWeek - firstDayOfWeek + 1

					CalendarDay(
						day = dayNumber,
						isCurrentMonth = dayNumber in 1..daysInMonth,
						isToday = dayNumber == todayDay,
						hasEvents = if (eventRepositoryImpl != null && dayNumber in 1..daysInMonth) {
							val date = CalendarDateDto(dayNumber, month, year, true)
							eventRepositoryImpl.temEventosNaData(date)
						} else false,
						eventCount = if (eventRepositoryImpl != null && dayNumber in 1..daysInMonth) {
							val date = CalendarDateDto(dayNumber, month, year, true)
							eventRepositoryImpl.obterEventosPorData(date).size
						} else 0,
						onClick = {
							if (dayNumber in 1..daysInMonth) {
								val date = CalendarDateDto(dayNumber, month, year, true)
								onDateClick(date)
							}
						},
						modifier = Modifier.weight(1f)
					)
				}
			}
		}
	}
}

@Composable
private fun CalendarDay(
	day: Int,
	isCurrentMonth: Boolean,
	isToday: Boolean,
	hasEvents: Boolean,
	eventCount: Int,
	onClick: () -> Unit,
	modifier: Modifier = Modifier
) {
	Box(
		modifier = modifier
      .aspectRatio(1f)
      .padding(2.dp)
      .clip(CircleShape)
      .background(
        when {
          isToday && isCurrentMonth -> MaterialTheme.colorScheme.primary
          hasEvents && isCurrentMonth -> MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f)
          else -> Color.Transparent
        }
      )
      .clickable { if (isCurrentMonth) onClick() },
		contentAlignment = Alignment.Center
	) {
		if (isCurrentMonth) {
			Column(
				horizontalAlignment = Alignment.CenterHorizontally,
				verticalArrangement = Arrangement.Center
			) {
				Text(
					text = day.toString(),
					fontSize = 16.sp,
					color = when {
						isToday -> MaterialTheme.colorScheme.onPrimary
						hasEvents -> MaterialTheme.colorScheme.primary
						else -> MaterialTheme.colorScheme.onSurface
					},
					fontWeight = when {
						isToday -> FontWeight.Bold
						hasEvents -> FontWeight.SemiBold
						else -> FontWeight.Normal
					}
				)

				// Indicador de eventos - pontos pequenos
				if (hasEvents && eventCount > 0) {
					Spacer(modifier = Modifier.height(2.dp))
					Row(
						horizontalArrangement = Arrangement.spacedBy(2.dp)
					) {
						repeat(minOf(eventCount, 3)) {
							Box(
								modifier = Modifier
                  .size(4.dp)
                  .background(
                    if (isToday) MaterialTheme.colorScheme.onPrimary
                    else MaterialTheme.colorScheme.primary,
                    CircleShape
                  )
							)
						}
						// Se tem mais de 3 eventos, mostra "+"
						if (eventCount > 3) {
							Text(
								text = "+",
								fontSize = 8.sp,
								color = if (isToday) MaterialTheme.colorScheme.onPrimary
								else MaterialTheme.colorScheme.primary,
								fontWeight = FontWeight.Bold
							)
						}
					}
				}
			}
		} else {
			// Dias de outros meses - cinza claro
			Text(
				text = if (day > 0) day.toString() else "",
				fontSize = 16.sp,
				color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
				fontWeight = FontWeight.Normal
			)
		}
	}
}

@Composable
private fun EventsList(
	eventRepositoryImpl: EventRepositoryImpl,
	month: Int,
	year: Int
) {
	val eventsThisMonth = remember(eventRepositoryImpl.eventos, month, year) {
		eventRepositoryImpl.eventos.filter { evento ->
			evento.data.month == month && evento.data.year == year
		}.sortedWith(compareBy({ it.data.day }, { it.horaInicio }))
	}

	Column(
		modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 8.dp)
	) {
		Row(
			modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 16.dp),
			horizontalArrangement = Arrangement.SpaceBetween,
			verticalAlignment = Alignment.CenterVertically
		) {
			Text(
				text = "Eventos",
				fontSize = 20.sp,
				fontWeight = FontWeight.Bold,
				color = MaterialTheme.colorScheme.onBackground
			)

			if (eventsThisMonth.isNotEmpty()) {
				Text(
					text = "${eventsThisMonth.size} eventos",
					fontSize = 14.sp,
					color = MaterialTheme.colorScheme.onSurfaceVariant,
					fontWeight = FontWeight.Medium
				)
			}
		}

		if (eventsThisMonth.isEmpty()) {
			Box(
				modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 32.dp),
				contentAlignment = Alignment.Center
			) {
				Column(
					horizontalAlignment = Alignment.CenterHorizontally
				) {
					Text(
						text = "📅",
						fontSize = 48.sp
					)
					Spacer(modifier = Modifier.height(8.dp))
					Text(
						text = "Nenhum evento neste mês",
						color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
						textAlign = TextAlign.Center,
						fontSize = 16.sp
					)
					Text(
						text = "Toque no botão + para adicionar",
						color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
						textAlign = TextAlign.Center,
						fontSize = 14.sp
					)
				}
			}
		} else {
			LazyColumn(
				verticalArrangement = Arrangement.spacedBy(12.dp),
				modifier = Modifier.heightIn(max = 300.dp)
			) {
				items(eventsThisMonth) { evento ->
					EventItem(evento = evento)
				}
			}
		}
	}
}

@Composable
private fun EventItem(evento: EventDto) {
	Card(
		colors = CardDefaults.cardColors(
			containerColor = MaterialTheme.colorScheme.surface
		),
		elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
		modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(16.dp))
	) {
		Row(
			modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
			verticalAlignment = Alignment.CenterVertically
		) {
			// Indicador colorido vertical
			Box(
				modifier = Modifier
          .width(4.dp)
          .height(40.dp)
          .background(evento.cor, RoundedCornerShape(2.dp))
			)

			Spacer(modifier = Modifier.width(16.dp))

			Column(modifier = Modifier.weight(1f)) {
				Text(
					text = evento.titulo,
					fontWeight = FontWeight.Bold,
					fontSize = 16.sp,
					color = MaterialTheme.colorScheme.onSurface
				)

				Spacer(modifier = Modifier.height(4.dp))

				Row(
					verticalAlignment = Alignment.CenterVertically,
					horizontalArrangement = Arrangement.spacedBy(8.dp)
				) {
					Text(
						text = "${evento.data.day}",
						fontSize = 14.sp,
						fontWeight = FontWeight.SemiBold,
						color = MaterialTheme.colorScheme.primary
					)

					Text(
						text = "•",
						fontSize = 12.sp,
						color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
					)

					Text(
						text = "${evento.horaInicio} - ${evento.horaFim}",
						fontSize = 14.sp,
						color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
					)
				}

				if (evento.descricao.isNotBlank()) {
					Spacer(modifier = Modifier.height(4.dp))
					Text(
						text = evento.descricao,
						fontSize = 14.sp,
						color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
						maxLines = 2
					)
				}
			}

			// Círculo colorido à direita
			Box(
				modifier = Modifier
          .size(16.dp)
          .background(evento.cor, CircleShape)
			)
		}
	}
}

private fun getMonthName(month: Int): String {
	val monthNames = listOf(
		"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
		"Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"
	)
	return monthNames[month]
}
