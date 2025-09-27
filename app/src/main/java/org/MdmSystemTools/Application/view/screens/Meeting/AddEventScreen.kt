package org.MdmSystemTools.Application.view.screens.Meeting

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Title
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.MdmSystemTools.Application.model.DTO.CalendarDateDto
import org.MdmSystemTools.Application.model.DTO.EventDto
import org.MdmSystemTools.Application.view.components.*
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
// TODO REFACsITORING tentar quebrar as funções em pequenas funções seguindo o Princípio de SRP
// TODO REFACTORING Usar interfaces para não ter acoplamento da tela com a classe de dados em memoria
@Composable
fun AdicionarEventoScreen(
	onNavigateBack: () -> Unit,
	onEventSaved: (EventDto) -> Unit,
	selectedDate: CalendarDateDto? = null
) {
	// Estado da tela
	var visible by remember { mutableStateOf(false) }
	val coroutineScope = rememberCoroutineScope()

	// Estados do formulário
	var titulo by remember { mutableStateOf("") }
	var descricao by remember { mutableStateOf("") }
	var horaInicio by remember { mutableStateOf("09:00") }
	var horaFim by remember { mutableStateOf("10:00") }
	var dataSelecionada by remember {
		mutableStateOf(selectedDate ?: getCurrentDate())
	}
	var corEvento by remember { mutableStateOf(Color(0xFF4CAF50)) }

	// Estados dos diálogos
	var showDatePicker by remember { mutableStateOf(false) }
	var showStartTimePicker by remember { mutableStateOf(false) }
	var showEndTimePicker by remember { mutableStateOf(false) }

	LaunchedEffect(Unit) { visible = true }

	AnimatedVisibility(
		visible = visible,
		enter = slideInHorizontally(
			initialOffsetX = { it },
			animationSpec = tween(300, easing = EaseOutCubic)
		) + fadeIn(animationSpec = tween(300)),
		exit = slideOutHorizontally(
			targetOffsetX = { it },
			animationSpec = tween(300, easing = EaseInCubic)
		) + fadeOut(animationSpec = tween(300))
	) {
		Scaffold(
			topBar = {
				EventTopBar(
					onNavigateBack = {
						animateAndNavigate(
							onSetVisible = { visible = it },
							coroutineScope = coroutineScope,
							onNavigate = onNavigateBack
						)
					}
				)
			},
			bottomBar = {
				SaveBottomBar(
					onSave = {
						if (titulo.isNotBlank()) {
							val novoEvento = createEventDto(
								titulo, descricao, dataSelecionada,
								horaInicio, horaFim, corEvento
							)
							animateAndSave(
								onSetVisible = { visible = it },
								coroutineScope = coroutineScope,
								evento = novoEvento,
								onEventSaved = onEventSaved
							)
						}
					},
					canSave = titulo.isNotBlank()
				)
			}
		) { paddingValues ->
			EventForm(
				paddingValues = paddingValues,
				titulo = titulo,
				onTituloChange = { titulo = it },
				descricao = descricao,
				onDescricaoChange = { descricao = it },
				dataSelecionada = dataSelecionada,
				horaInicio = horaInicio,
				horaFim = horaFim,
				corEvento = corEvento,
				onDateClick = { showDatePicker = true },
				onStartTimeClick = { showStartTimePicker = true },
				onEndTimeClick = { showEndTimePicker = true },
				onColorSelected = { corEvento = it }
			)
		}
	}

	// Diálogos
	EventDialogs(
		showDatePicker = showDatePicker,
		showStartTimePicker = showStartTimePicker,
		showEndTimePicker = showEndTimePicker,
		dataSelecionada = dataSelecionada,
		horaInicio = horaInicio,
		horaFim = horaFim,
		onDateSelected = {
			dataSelecionada = it
			showDatePicker = false
		},
		onStartTimeSelected = { time ->
			horaInicio = time
			horaFim = adjustEndTime(time, horaFim)
			showStartTimePicker = false
		},
		onEndTimeSelected = { time ->
			horaFim = adjustEndTimeIfValid(horaInicio, time)
			showEndTimePicker = false
		},
		onDismissDate = { showDatePicker = false },
		onDismissStartTime = { showStartTimePicker = false },
		onDismissEndTime = { showEndTimePicker = false }
	)
}

// Componentes auxiliares
@Composable
private fun EventForm(
	paddingValues: PaddingValues,
	titulo: String,
	onTituloChange: (String) -> Unit,
	descricao: String,
	onDescricaoChange: (String) -> Unit,
	dataSelecionada: CalendarDateDto,
	horaInicio: String,
	horaFim: String,
	corEvento: Color,
	onDateClick: () -> Unit,
	onStartTimeClick: () -> Unit,
	onEndTimeClick: () -> Unit,
	onColorSelected: (Color) -> Unit
) {
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
			label = "Título",
			value = titulo,
			onValueChange = onTituloChange,
			icon = Icons.Default.Title,
			placeholder = "Adicione um título"
		)

		// Descrição
		EventField(
			label = "Descrição",
			value = descricao,
			onValueChange = onDescricaoChange,
			icon = Icons.Default.Title,
			placeholder = "Adicione uma descrição (opcional)",
			singleLine = false,
			minLines = 3
		)

		// Data e hora
		DateTimeSelector(
			selectedDate = dataSelecionada,
			startTime = horaInicio,
			endTime = horaFim,
			onDateClick = onDateClick,
			onStartTimeClick = onStartTimeClick,
			onEndTimeClick = onEndTimeClick
		)

		// Seletor de cor
		ColorPicker(
			selectedColor = corEvento,
			onColorSelected = onColorSelected
		)

		Spacer(modifier = Modifier.height(24.dp))
	}
}

@Composable
private fun EventDialogs(
	showDatePicker: Boolean,
	showStartTimePicker: Boolean,
	showEndTimePicker: Boolean,
	dataSelecionada: CalendarDateDto,
	horaInicio: String,
	horaFim: String,
	onDateSelected: (CalendarDateDto) -> Unit,
	onStartTimeSelected: (String) -> Unit,
	onEndTimeSelected: (String) -> Unit,
	onDismissDate: () -> Unit,
	onDismissStartTime: () -> Unit,
	onDismissEndTime: () -> Unit
) {
	if (showDatePicker) {
		DatePickerDialog(
			selectedDate = dataSelecionada,
			onDateSelected = onDateSelected,
			onDismiss = onDismissDate
		)
	}

	if (showStartTimePicker) {
		TimePickerDialog(
			title = "Horário de início",
			selectedTime = horaInicio,
			onTimeSelected = onStartTimeSelected,
			onDismiss = onDismissStartTime
		)
	}

	if (showEndTimePicker) {
		TimePickerDialog(
			title = "Horário de fim",
			selectedTime = horaFim,
			onTimeSelected = onEndTimeSelected,
			onDismiss = onDismissEndTime
		)
	}
}

// Funções auxiliares
private fun getCurrentDate(): CalendarDateDto {
	val calendar = Calendar.getInstance()
	return CalendarDateDto(
		calendar.get(Calendar.DAY_OF_MONTH),
		calendar.get(Calendar.MONTH),
		calendar.get(Calendar.YEAR),
		isCurrentMonth = true
	)
}

private fun createEventDto(
	titulo: String,
	descricao: String,
	data: CalendarDateDto,
	horaInicio: String,
	horaFim: String,
	cor: Color
): EventDto {
	return EventDto(
		titulo = titulo,
		descricao = descricao,
		data = data,
		horaInicio = horaInicio,
		horaFim = horaFim,
		cor = cor
	)
}

private fun animateAndNavigate(
	onSetVisible: (Boolean) -> Unit,
	coroutineScope: CoroutineScope,
	onNavigate: () -> Unit
) {
	onSetVisible(false)
	coroutineScope.launch {
		delay(300)
		onNavigate()
	}
}

private fun animateAndSave(
	onSetVisible: (Boolean) -> Unit,
	coroutineScope: CoroutineScope,
	evento: EventDto,
	onEventSaved: (EventDto) -> Unit
) {
	onSetVisible(false)
	coroutineScope.launch {
		delay(300)
		onEventSaved(evento)
	}
}

private fun adjustEndTime(startTime: String, currentEndTime: String): String {
	val startParts = startTime.split(":")
	val endParts = currentEndTime.split(":")
	val startMinutes = startParts[0].toInt() * 60 + startParts[1].toInt()
	val endMinutes = endParts[0].toInt() * 60 + endParts[1].toInt()

	return if (endMinutes <= startMinutes) {
		val newEndMinutes = startMinutes + 60
		val newHour = (newEndMinutes / 60) % 24
		val newMinute = newEndMinutes % 60
		"%02d:%02d".format(newHour, newMinute)
	} else {
		currentEndTime
	}
}

private fun adjustEndTimeIfValid(startTime: String, endTime: String): String {
	val startParts = startTime.split(":")
	val endParts = endTime.split(":")
	val startMinutes = startParts[0].toInt() * 60 + startParts[1].toInt()
	val endMinutes = endParts[0].toInt() * 60 + endParts[1].toInt()

	return if (endMinutes > startMinutes) {
		endTime
	} else {
		val newEndMinutes = startMinutes + 60
		val newHour = (newEndMinutes / 60) % 24
		val newMinute = newEndMinutes % 60
		"%02d:%02d".format(newHour, newMinute)
	}
}
