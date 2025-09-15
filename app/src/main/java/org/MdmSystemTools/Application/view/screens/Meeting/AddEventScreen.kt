package org.mdmsystemtools.application.presentation.ui.screens.Reunião

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Title
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.MdmSystemTools.Application.model.DTO.CalendarDateDto
import org.MdmSystemTools.Application.model.DTO.EventDto
import java.util.*

// TODO BUG é possivel coloca horario fim antes do inicio exemplo inicio 09:00 fim 6:00
// TODO BUG usando o botão de voltar do proprio celular volta para a tela anterior mas não muda o navigatorbar
// TODO FEATURE adicionar botão para apagar evento
// TODO FEATURE adicionar efeitos de voltar e fechar tela
// TODO VISUAL acho que coloca o botão de salvar em baixo é mais natural
// TODO VISUAL adicionar espaçamento do topo da tela para não deixa o titulo colado 
// TODO VISUAL botão de adicionar ou clica no dia, os dois é estranho
// TODO VISUAL deslizar o dedo para mudar o mês é mais natural do que aperta nas setas
// TODO REFACTORING alterar nomes das variveis, funções e do arquivos para serem mais clara e bem definidas, não é legal misturar inglês com português
// TODO REFACTORING tentar quebrar as funções em pequenas funções seguindo o Princípio de SRP
// TODO REFACTORING Usar interfaces para não ter acoplamento da tela com a classe de dados em memoria
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdicionarEventoScreen(
	onNavigateBack: () -> Unit,
	onEventSaved: (EventDto) -> Unit,
	selectedDate: CalendarDateDto? = null
) {
	var titulo by remember { mutableStateOf("") }
	var descricao by remember { mutableStateOf("") }
	var horaInicio by remember { mutableStateOf("09:00") }
	var horaFim by remember { mutableStateOf("10:00") }
	var dataSelecionada by remember {
		mutableStateOf(selectedDate ?: CalendarDateDto(
			Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
			Calendar.getInstance().get(Calendar.MONTH),
			Calendar.getInstance().get(Calendar.YEAR),
			isCurrentMonth = true
		))
	}
	var corEvento by remember { mutableStateOf(Color(0xFF4CAF50)) }

	// Estados para mostrar os seletores
	var showDatePicker by remember { mutableStateOf(false) }
	var showStartTimePicker by remember { mutableStateOf(false) }
	var showEndTimePicker by remember { mutableStateOf(false) }

	Scaffold(
		topBar = {
			EventTopBar(
				onNavigateBack = onNavigateBack,
				onSave = {
					if (titulo.isNotBlank()) {
						val novoEvento = EventDto(
							titulo = titulo,
							descricao = descricao,
							data = dataSelecionada,
							horaInicio = horaInicio,
							horaFim = horaFim,
							cor = corEvento
						)
						onEventSaved(novoEvento)
					}
				},
				canSave = titulo.isNotBlank()
			)
		}
	) { paddingValues ->
		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(paddingValues)
				.verticalScroll(rememberScrollState())
				.padding(16.dp),
			verticalArrangement = Arrangement.spacedBy(20.dp)
		) {
			// Título do evento
			EventField(
				label = "Título do evento",
				value = titulo,
				onValueChange = { titulo = it },
				icon = Icons.Default.Title,
				placeholder = "Adicione um título"
			)

			// Descrição
			EventField(
				label = "Descrição",
				value = descricao,
				onValueChange = { descricao = it },
				icon = Icons.Default.Title,
				placeholder = "Adicione uma descrição (opcional)",
				singleLine = false,
				minLines = 3
			)

			// Data e hora
			Row(
				modifier = Modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.spacedBy(12.dp)
			) {
				// Data
				DateTimeCard(
					modifier = Modifier.weight(1f),
					label = "Data",
					value = formatDate(dataSelecionada),
					icon = Icons.Default.CalendarToday,
					onClick = { showDatePicker = true }
				)

				// Horário
				Column(modifier = Modifier.weight(1f)) {
					TimeCard(
						label = "Início",
						time = horaInicio,
						onClick = { showStartTimePicker = true }
					)

					Spacer(modifier = Modifier.height(8.dp))

					TimeCard(
						label = "Fim",
						time = horaFim,
						onClick = { showEndTimePicker = true }
					)
				}
			}

			// Seletor de cor
			ColorPicker(
				selectedColor = corEvento,
				onColorSelected = { corEvento = it }
			)

			Spacer(modifier = Modifier.height(24.dp))
		}
	}

	// Date Picker Dialog
	if (showDatePicker) {
		DatePickerDialog(
			selectedDate = dataSelecionada,
			onDateSelected = { newDate ->
				dataSelecionada = newDate
				showDatePicker = false
			},
			onDismiss = { showDatePicker = false }
		)
	}

	// Time Picker Dialogs
	if (showStartTimePicker) {
		TimePickerDialog(
			title = "Horário de início",
			selectedTime = horaInicio,
			onTimeSelected = { time ->
				horaInicio = time
				showStartTimePicker = false
			},
			onDismiss = { showStartTimePicker = false }
		)
	}

	if (showEndTimePicker) {
		TimePickerDialog(
			title = "Horário de fim",
			selectedTime = horaFim,
			onTimeSelected = { time ->
				horaFim = time
				showEndTimePicker = false
			},
			onDismiss = { showEndTimePicker = false }
		)
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EventTopBar(
	onNavigateBack: () -> Unit,
	onSave: () -> Unit,
	canSave: Boolean
) {
	TopAppBar(
		title = {
			Text(
				text = "Novo evento",
				fontSize = 20.sp,
				fontWeight = FontWeight.SemiBold
			)
		},
		navigationIcon = {
			IconButton(onClick = onNavigateBack) {
				Icon(
					Icons.Default.ArrowBack,
					contentDescription = "Voltar"
				)
			}
		},
		actions = {
			TextButton(
				onClick = onSave,
				enabled = canSave
			) {
				Row(
					verticalAlignment = Alignment.CenterVertically,
					horizontalArrangement = Arrangement.spacedBy(4.dp)
				) {
					Icon(
						Icons.Default.Check,
						contentDescription = "Salvar",
						modifier = Modifier.size(18.dp)
					)
					Text(
						text = "Salvar",
						fontWeight = FontWeight.Medium
					)
				}
			}
		},
		colors = TopAppBarDefaults.topAppBarColors(
			containerColor = MaterialTheme.colorScheme.surface
		)
	)
}

@Composable
private fun EventField(
	label: String,
	value: String,
	onValueChange: (String) -> Unit,
	icon: ImageVector,
	placeholder: String,
	singleLine: Boolean = true,
	minLines: Int = 1
) {
	Card(
		modifier = Modifier.fillMaxWidth(),
		shape = RoundedCornerShape(12.dp),
		colors = CardDefaults.cardColors(
			containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
		)
	) {
		Column(
			modifier = Modifier.padding(16.dp)
		) {
			Row(
				verticalAlignment = Alignment.CenterVertically,
				horizontalArrangement = Arrangement.spacedBy(8.dp)
			) {
				Icon(
					imageVector = icon,
					contentDescription = label,
					tint = MaterialTheme.colorScheme.primary,
					modifier = Modifier.size(20.dp)
				)
				Text(
					text = label,
					fontSize = 14.sp,
					fontWeight = FontWeight.Medium,
					color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
				)
			}

			Spacer(modifier = Modifier.height(8.dp))

			TextField(
				value = value,
				onValueChange = onValueChange,
				placeholder = {
					Text(
						text = placeholder,
						color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
					)
				},
				singleLine = singleLine,
				minLines = minLines,
				colors = TextFieldDefaults.colors(
					focusedContainerColor = Color.Transparent,
					unfocusedContainerColor = Color.Transparent,
					focusedIndicatorColor = Color.Transparent,
					unfocusedIndicatorColor = Color.Transparent
				),
				modifier = Modifier.fillMaxWidth()
			)
		}
	}
}

@Composable
private fun DateTimeCard(
	modifier: Modifier = Modifier,
	label: String,
	value: String,
	icon: ImageVector,
	onClick: () -> Unit
) {
	Card(
		modifier = modifier
			.clip(RoundedCornerShape(12.dp))
			.clickable { onClick() },
		shape = RoundedCornerShape(12.dp),
		colors = CardDefaults.cardColors(
			containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
		)
	) {
		Column(
			modifier = Modifier.padding(16.dp),
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			Icon(
				imageVector = icon,
				contentDescription = label,
				tint = MaterialTheme.colorScheme.primary,
				modifier = Modifier.size(24.dp)
			)

			Spacer(modifier = Modifier.height(8.dp))

			Text(
				text = label,
				fontSize = 12.sp,
				color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
			)

			Text(
				text = value,
				fontSize = 16.sp,
				fontWeight = FontWeight.SemiBold,
				color = MaterialTheme.colorScheme.onSurface
			)
		}
	}
}

@Composable
private fun TimeCard(
	label: String,
	time: String,
	onClick: () -> Unit
) {
	Card(
		modifier = Modifier
			.fillMaxWidth()
			.clip(RoundedCornerShape(12.dp))
			.clickable { onClick() },
		shape = RoundedCornerShape(12.dp),
		colors = CardDefaults.cardColors(
			containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
		)
	) {
		Row(
			modifier = Modifier
				.fillMaxWidth()
				.padding(16.dp),
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.SpaceBetween
		) {
			Row(
				verticalAlignment = Alignment.CenterVertically,
				horizontalArrangement = Arrangement.spacedBy(8.dp)
			) {
				Icon(
					Icons.Default.Schedule,
					contentDescription = label,
					tint = MaterialTheme.colorScheme.primary,
					modifier = Modifier.size(20.dp)
				)
				Text(
					text = label,
					fontSize = 14.sp,
					color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
				)
			}

			Text(
				text = time,
				fontSize = 16.sp,
				fontWeight = FontWeight.SemiBold,
				color = MaterialTheme.colorScheme.onSurface
			)
		}
	}
}

@Composable
private fun ColorPicker(
	selectedColor: Color,
	onColorSelected: (Color) -> Unit
) {
	val colors = listOf(
		Color(0xFF4CAF50), // Verde
		Color(0xFF2196F3), // Azul
		Color(0xFFFF9800), // Laranja
		Color(0xFF9C27B0), // Roxo
		Color(0xFFF44336), // Vermelho
		Color(0xFF009688), // Teal
		Color(0xFFFF5722), // Deep Orange
		Color(0xFF795548)  // Marrom
	)

	Card(
		modifier = Modifier.fillMaxWidth(),
		shape = RoundedCornerShape(12.dp),
		colors = CardDefaults.cardColors(
			containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
		)
	) {
		Column(
			modifier = Modifier.padding(16.dp)
		) {
			Text(
				text = "Cor do evento",
				fontSize = 14.sp,
				fontWeight = FontWeight.Medium,
				color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
				modifier = Modifier.padding(bottom = 12.dp)
			)

			Row(
				modifier = Modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.spacedBy(12.dp)
			) {
				colors.forEach { color ->
					Box(
						modifier = Modifier
							.size(40.dp)
							.clip(RoundedCornerShape(10.dp))
							.background(color)
							.clickable { onColorSelected(color) },
						contentAlignment = Alignment.Center
					) {
						if (color == selectedColor) {
							Icon(
								Icons.Default.Check,
								contentDescription = "Selecionado",
								tint = Color.White,
								modifier = Modifier.size(20.dp)
							)
						}
					}
				}
			}
		}
	}
}

private fun formatDate(date: CalendarDateDto): String {
	val monthNames = listOf(
		"Jan", "Fev", "Mar", "Abr", "Mai", "Jun",
		"Jul", "Ago", "Set", "Out", "Nov", "Dez"
	)
	return "${date.day} ${monthNames[date.month]}"
}

@Composable
private fun DatePickerDialog(
	selectedDate: CalendarDateDto,
	onDateSelected: (CalendarDateDto) -> Unit,
	onDismiss: () -> Unit
) {
	var currentMonth by remember { mutableStateOf(selectedDate.month) }
	var currentYear by remember { mutableStateOf(selectedDate.year) }
	var selectedDay by remember { mutableStateOf(selectedDate.day) }

	AlertDialog(
		onDismissRequest = onDismiss,
		title = {
			Text(
				text = "Selecionar data",
				fontWeight = FontWeight.SemiBold
			)
		},
		text = {
			Column {
				// Header do mês/ano
				Row(
					modifier = Modifier.fillMaxWidth(),
					horizontalArrangement = Arrangement.SpaceBetween,
					verticalAlignment = Alignment.CenterVertically
				) {
					IconButton(onClick = {
						if (currentMonth == 0) {
							currentMonth = 11
							currentYear--
						} else {
							currentMonth--
						}
					}) {
						Icon(Icons.Default.ChevronLeft, "Mês anterior")
					}

					Text(
						text = "${getMonthName(currentMonth)} $currentYear",
						fontSize = 18.sp,
						fontWeight = FontWeight.SemiBold
					)

					IconButton(onClick = {
						if (currentMonth == 11) {
							currentMonth = 0
							currentYear++
						} else {
							currentMonth++
						}
					}) {
						Icon(Icons.Default.ChevronRight, "Próximo mês")
					}
				}

				Spacer(modifier = Modifier.height(16.dp))

				// Grade de dias
				SimpleDateGrid(
					month = currentMonth,
					year = currentYear,
					selectedDay = selectedDay,
					onDaySelected = { day -> selectedDay = day }
				)
			}
		},
		confirmButton = {
			TextButton(
				onClick = {
					onDateSelected(CalendarDateDto(selectedDay, currentMonth, currentYear, isCurrentMonth = true))
				}
			) {
				Text("OK")
			}
		},
		dismissButton = {
			TextButton(onClick = onDismiss) {
				Text("Cancelar")
			}
		}
	)
}

@Composable
private fun SimpleDateGrid(
	month: Int,
	year: Int,
	selectedDay: Int,
	onDaySelected: (Int) -> Unit
) {
	val calendar = Calendar.getInstance()
	calendar.set(year, month, 1)
	val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
	val firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1

	Column {
		// Dias da semana
		Row(modifier = Modifier.fillMaxWidth()) {
			listOf("D", "S", "T", "Q", "Q", "S", "S").forEach { day ->
				Text(
					text = day,
					modifier = Modifier.weight(1f),
					textAlign = TextAlign.Center,
					fontSize = 12.sp,
					color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
				)
			}
		}

		Spacer(modifier = Modifier.height(8.dp))

		// Grade de dias
		val totalCells = 42
		val days = (1..daysInMonth).toList()

		for (week in 0 until 6) {
			Row(modifier = Modifier.fillMaxWidth()) {
				for (dayOfWeek in 0 until 7) {
					val cellIndex = week * 7 + dayOfWeek
					val dayNumber = cellIndex - firstDayOfWeek + 1

					Box(
						modifier = Modifier
							.weight(1f)
							.aspectRatio(1f)
							.padding(2.dp)
							.clip(CircleShape)
							.background(
								if (dayNumber in 1..daysInMonth && dayNumber == selectedDay)
									MaterialTheme.colorScheme.primary
								else
									Color.Transparent
							)
							.clickable {
								if (dayNumber in 1..daysInMonth) {
									onDaySelected(dayNumber)
								}
							},
						contentAlignment = Alignment.Center
					) {
						if (dayNumber in 1..daysInMonth) {
							Text(
								text = dayNumber.toString(),
								fontSize = 14.sp,
								color = if (dayNumber == selectedDay)
									MaterialTheme.colorScheme.onPrimary
								else
									MaterialTheme.colorScheme.onSurface
							)
						}
					}
				}
			}
		}
	}
}

@Composable
private fun TimePickerDialog(
	title: String,
	selectedTime: String,
	onTimeSelected: (String) -> Unit,
	onDismiss: () -> Unit
) {
	val timeParts = selectedTime.split(":")
	var hour by remember { mutableStateOf(timeParts[0].toInt()) }
	var minute by remember { mutableStateOf(timeParts[1].toInt()) }

	AlertDialog(
		onDismissRequest = onDismiss,
		title = {
			Text(
				text = title,
				fontWeight = FontWeight.SemiBold
			)
		},
		text = {
			Row(
				modifier = Modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.SpaceEvenly,
				verticalAlignment = Alignment.CenterVertically
			) {
				// Seletor de hora
				Column(horizontalAlignment = Alignment.CenterHorizontally) {
					Text("Hora", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))

					NumberPicker(
						value = hour,
						onValueChange = { hour = it },
						range = 0..23,
						formatter = { "%02d".format(it) }
					)
				}

				Text(
					text = ":",
					fontSize = 24.sp,
					fontWeight = FontWeight.Bold
				)

				// Seletor de minuto
				Column(horizontalAlignment = Alignment.CenterHorizontally) {
					Text("Min", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))

					NumberPicker(
						value = minute,
						onValueChange = { minute = it },
						range = 0..59,
						formatter = { "%02d".format(it) }
					)
				}
			}
		},
		confirmButton = {
			TextButton(
				onClick = {
					onTimeSelected("%02d:%02d".format(hour, minute))
				}
			) {
				Text("OK")
			}
		},
		dismissButton = {
			TextButton(onClick = onDismiss) {
				Text("Cancelar")
			}
		}
	)
}

@Composable
private fun NumberPicker(
	value: Int,
	onValueChange: (Int) -> Unit,
	range: IntRange,
	formatter: (Int) -> String = { it.toString() }
) {
	Column(
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.spacedBy(4.dp)
	) {
		// Botão para aumentar
		IconButton(
			onClick = {
				if (value < range.last) onValueChange(value + 1)
				else onValueChange(range.first)
			}
		) {
			Icon(Icons.Default.KeyboardArrowUp, contentDescription = "Aumentar")
		}

		// Valor atual
		Card(
			colors = CardDefaults.cardColors(
				containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
			)
		) {
			Text(
				text = formatter(value),
				fontSize = 18.sp,
				fontWeight = FontWeight.SemiBold,
				modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
			)
		}

		// Botão para diminuir
		IconButton(
			onClick = {
				if (value > range.first) onValueChange(value - 1)
				else onValueChange(range.last)
			}
		) {
			Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Diminuir")
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
