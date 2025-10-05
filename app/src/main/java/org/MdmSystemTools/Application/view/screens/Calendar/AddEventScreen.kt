package org.MdmSystemTools.Application.view.screens.Calendar

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Title
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.MdmSystemTools.Application.model.DTO.EventDto
import org.MdmSystemTools.Application.model.DTO.EventDate
import org.MdmSystemTools.Application.model.DTO.GroupDto
import org.MdmSystemTools.Application.view.components.*
import org.MdmSystemTools.Application.view.components.Forms.LocalSelector
import org.MdmSystemTools.Application.view.components.Forms.RegiaoSelector
import org.MdmSystemTools.Application.view.components.Forms.ProjetoSelector
import org.MdmSystemTools.Application.view.components.Forms.GroupSelector
import org.MdmSystemTools.Application.view.theme.AppConstants
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
fun AddEventScreen(
	onNavigateBack: () -> Unit,
	onEventSaved: (EventDto) -> Unit = {},
	selectedDate: Triple<Int, Int, Int>? = null
) {
	// Estado da tela
	var visible by remember { mutableStateOf(false) }
	val coroutineScope = rememberCoroutineScope()

	// Estados do formulário
	var descricao by remember { mutableStateOf("") }
	var horaInicio by remember { mutableStateOf(AppConstants.TimeConfig.defaultStartTime) }
	var horaFim by remember { mutableStateOf(AppConstants.TimeConfig.defaultEndTime) }
	var corEvento by remember { mutableStateOf(AppConstants.AppColors.success) }

	// Data selecionada - usa a data passada como parâmetro ou data atual se nenhuma foi passada
	val dataSelecionada = selectedDate?.let { (day, month, year) ->
		EventDate(day, month, year)
	} ?: getCurrentDate()
	var localEvento by remember { mutableStateOf("") }
	var regiaoEvento by remember { mutableStateOf("") }
	var projetoEvento by remember { mutableStateOf("") }
	var grupoEvento by remember { mutableStateOf<GroupDto?>(null) }

	// Estados dos diálogos
	var showStartTimePicker by remember { mutableStateOf(false) }
	var showEndTimePicker by remember { mutableStateOf(false) }

	LaunchedEffect(Unit) { visible = true }

	AnimatedVisibility(
		visible = visible,
		enter = slideInHorizontally(
			initialOffsetX = { it },
			animationSpec = tween(AppConstants.Animation.defaultDurationMs, easing = EaseOutCubic)
		) + fadeIn(animationSpec = tween(AppConstants.Animation.defaultDurationMs)),
		exit = slideOutHorizontally(
			targetOffsetX = { it },
			animationSpec = tween(AppConstants.Animation.defaultDurationMs, easing = EaseInCubic)
		) + fadeOut(animationSpec = tween(AppConstants.Animation.defaultDurationMs))
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
					})
			}) { paddingValues ->
			EventForm(
				paddingValues = paddingValues,
				descricao = descricao,
				onDescricaoChange = { descricao = it },
				horaInicio = horaInicio,
				horaFim = horaFim,
				localEvento = localEvento,
				onLocalChange = { localEvento = it },
				regiaoEvento = regiaoEvento,
				onRegiaoChange = { regiaoEvento = it },
				projetoEvento = projetoEvento,
				onProjetoChange = { projetoEvento = it },
				grupoEvento = grupoEvento,
				onGrupoChange = {
					grupoEvento = it
					// Atualizar cor do evento quando grupo for selecionado
					it?.let { grupo -> corEvento = grupo.cor }
				},
				onStartTimeClick = { showStartTimePicker = true },
				onEndTimeClick = { showEndTimePicker = true },
				onSave = {
					val event = EventDto(
						title = AppConstants.Strings.meeting,
						description = descricao,
						date = dataSelecionada,
						hourStart = horaInicio,
						hourEnd = horaFim,
						local = localEvento,
						region = regiaoEvento,
						project = projetoEvento,
						groups = grupoEvento,
						color = corEvento
					)
					animateAndSave(
						onSetVisible = { visible = it },
						coroutineScope = coroutineScope,
						evento = event,
						onEventSaved = onEventSaved
					)
				},
				onCancel = {
					animateAndNavigate(
						onSetVisible = { visible = it },
						coroutineScope = coroutineScope,
						onNavigate = onNavigateBack
					)
				})
		}
	}

	// Diálogos
	EventDialogs(
		showStartTimePicker = showStartTimePicker,
		showEndTimePicker = showEndTimePicker,
		horaInicio = horaInicio,
		horaFim = horaFim,
		onStartTimeSelected = { time ->
			horaInicio = time
			horaFim = adjustEndTime(time, horaFim)
			showStartTimePicker = false
		},
		onEndTimeSelected = { time ->
			horaFim = adjustEndTimeIfValid(horaInicio, time)
			showEndTimePicker = false
		},
		onDismissStartTime = { showStartTimePicker = false },
		onDismissEndTime = { showEndTimePicker = false })
}

// Componentes auxiliares
@Composable
private fun EventForm(
	paddingValues: PaddingValues,
	descricao: String,
	onDescricaoChange: (String) -> Unit,
	horaInicio: String,
	horaFim: String,
	localEvento: String,
	onLocalChange: (String) -> Unit,
	regiaoEvento: String,
	onRegiaoChange: (String) -> Unit,
	projetoEvento: String,
	onProjetoChange: (String) -> Unit,
	grupoEvento: GroupDto?,
	onGrupoChange: (GroupDto?) -> Unit,
	onStartTimeClick: () -> Unit,
	onEndTimeClick: () -> Unit,
	onSave: () -> Unit,
	onCancel: () -> Unit
) {
	Column(
		modifier = Modifier
			.fillMaxSize()
			.padding(paddingValues)
			.verticalScroll(rememberScrollState())
			.padding(horizontal = 16.dp)
			.padding(top = 16.dp, bottom = 24.dp),
		verticalArrangement = Arrangement.spacedBy(16.dp)
	) {
		// Descrição
		EventField(
			label = "Descrição",
			value = descricao,
			onValueChange = onDescricaoChange,
			icon = Icons.Default.Title,
			placeholder = "Adicione uma descrição (opcional)",
			singleLine = false,
			minLines = 2
		)

		// Local do evento
		LocalSelector(
			selectedLocal = localEvento, onLocalChange = onLocalChange
		)

		// Região
		RegiaoSelector(
			selectedRegiao = regiaoEvento, onRegiaoChange = onRegiaoChange
		)

		// Projeto
		ProjetoSelector(
			selectedProjeto = projetoEvento, onProjetoChange = onProjetoChange
		)

		// Grupo
		GroupSelector(
			selectedGrupo = grupoEvento, onGrupoChange = onGrupoChange
		)

		// Horários
		Row(
			modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)
		) {
			// Horário de início
			Card(
				modifier = Modifier.weight(1f),
				shape = RoundedCornerShape(12.dp),
				colors = CardDefaults.cardColors(
					containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
				)
			) {
				Column(modifier = Modifier
					.fillMaxWidth()
					.clickable { onStartTimeClick() }
					.padding(16.dp)) {
					Text(
						text = "Horário Início",
						fontSize = 14.sp,
						fontWeight = FontWeight.Medium,
						color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
					)
					Spacer(modifier = Modifier.height(8.dp))
					Text(
						text = horaInicio,
						fontSize = 18.sp,
						fontWeight = FontWeight.SemiBold,
						color = MaterialTheme.colorScheme.primary
					)
				}
			}

			// Horário de fim
			Card(
				modifier = Modifier.weight(1f),
				shape = RoundedCornerShape(12.dp),
				colors = CardDefaults.cardColors(
					containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
				)
			) {
				Column(modifier = Modifier
					.fillMaxWidth()
					.clickable { onEndTimeClick() }
					.padding(16.dp)) {
					Text(
						text = "Horário Fim",
						fontSize = 14.sp,
						fontWeight = FontWeight.Medium,
						color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
					)
					Spacer(modifier = Modifier.height(8.dp))
					Text(
						text = horaFim,
						fontSize = 18.sp,
						fontWeight = FontWeight.SemiBold,
						color = MaterialTheme.colorScheme.primary
					)
				}
			}
		}

		// Botões Salvar e Cancelar
		Row(
			modifier = Modifier
				.fillMaxWidth()
				.padding(top = 24.dp)
				.padding(horizontal = 8.dp),
			horizontalArrangement = Arrangement.spacedBy(16.dp)
		) {
			// Botão Cancelar
			OutlinedButton(
				onClick = onCancel,
				modifier = Modifier
					.weight(1f)
					.height(56.dp),
				shape = RoundedCornerShape(12.dp)
			) {
				Text(
					text = "Cancelar", fontSize = 16.sp, fontWeight = FontWeight.Medium
				)
			}

			// Botão Salvar
			Button(
				onClick = onSave,
				modifier = Modifier
					.weight(1f)
					.height(56.dp),
				shape = RoundedCornerShape(12.dp)
			) {
				Row(
					horizontalArrangement = Arrangement.spacedBy(8.dp),
					verticalAlignment = Alignment.CenterVertically
				) {
					Icon(
						Icons.Default.Check, contentDescription = "Salvar", modifier = Modifier.size(18.dp)
					)
					Text(
						text = "Salvar", fontSize = 16.sp, fontWeight = FontWeight.SemiBold
					)
				}
			}
		}

		// Espaçamento dinâmico para permitir scroll completo
		val density = LocalDensity.current
		val bottomPadding = with(density) {
			WindowInsets.navigationBars.getBottom(density).toDp() + WindowInsets.ime.getBottom(density)
				.toDp() + AppConstants.Spacing.dynamicBottomPadding // Espaçamento extra para garantir visibilidade
		}
		Spacer(modifier = Modifier.height(bottomPadding))
	}
}

@Composable
private fun EventDialogs(
	showStartTimePicker: Boolean,
	showEndTimePicker: Boolean,
	horaInicio: String,
	horaFim: String,
	onStartTimeSelected: (String) -> Unit,
	onEndTimeSelected: (String) -> Unit,
	onDismissStartTime: () -> Unit,
	onDismissEndTime: () -> Unit
) {
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
private fun getCurrentDate(): EventDate {
	val calendar = Calendar.getInstance()
	return EventDate(
		calendar.get(Calendar.DAY_OF_MONTH),
		calendar.get(Calendar.MONTH),
		calendar.get(Calendar.YEAR)
	)
}

private fun animateAndNavigate(
	onSetVisible: (Boolean) -> Unit, coroutineScope: CoroutineScope, onNavigate: () -> Unit
) {
	onSetVisible(false)
	coroutineScope.launch {
		delay(AppConstants.Animation.defaultDurationMs.toLong())
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
		delay(AppConstants.Animation.defaultDurationMs.toLong())
		onEventSaved(evento)
	}
}

private fun adjustEndTime(startTime: String, currentEndTime: String): String {
	val startParts = startTime.split(":")
	val endParts = currentEndTime.split(":")
	val startMinutes = startParts[0].toInt() * 60 + startParts[1].toInt()
	val endMinutes = endParts[0].toInt() * 60 + endParts[1].toInt()

	return if (endMinutes <= startMinutes) {
		val newEndMinutes = startMinutes + AppConstants.TimeConfig.defaultMeetingDurationMinutes
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
		val newEndMinutes = startMinutes + AppConstants.TimeConfig.defaultMeetingDurationMinutes
		val newHour = (newEndMinutes / 60) % 24
		val newMinute = newEndMinutes % 60
		"%02d:%02d".format(newHour, newMinute)
	}
}
