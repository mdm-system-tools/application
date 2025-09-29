package org.MdmSystemTools.Application.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Constantes centralizadas da aplicação
 * Centraliza valores de dimensões, cores, e outras constantes
 * para facilitar manutenção e consistência visual
 */
object AppConstants {

    // DIMENSÕES - ESPAÇAMENTOS
    object Spacing {
        val extraSmall = 4.dp
        val small = 8.dp
        val medium = 16.dp
        val large = 24.dp
        val extraLarge = 32.dp
        val bottomSafeArea = 80.dp
        val dynamicBottomPadding = 24.dp
    }

    // DIMENSÕES - COMPONENTES
    object ComponentSize {
        val buttonHeight = 56.dp
        val cardElevation = 4.dp
        val iconSmall = 16.dp
        val iconMedium = 20.dp
        val iconLarge = 24.dp
        val avatarSize = 40.dp
        val calendarDaySize = 48.dp
        val eventIndicatorSize = 4.dp
    }

    // RAIOS DE BORDA
    object CornerRadius {
        val small = 8.dp
        val medium = 12.dp
        val large = 16.dp
        val extraLarge = 20.dp
    }

    // TAMANHOS DE FONTE
    object FontSize {
        val small = 12.sp
        val medium = 14.sp
        val large = 16.sp
        val extraLarge = 18.sp
        val title = 20.sp
        val largeTitle = 32.sp
        val tiny = 8.sp
    }

    // CORES DO SISTEMA
    object AppColors {
        val primary = Color(0xFF2196F3)
        val secondary = Color(0xFF03DAC5)
        val error = Color(0xFFB00020)
        val success = Color(0xFF4CAF50)

        // Cores específicas dos grupos
        val groupDevelopment = Color(0xFF2196F3)  // Azul
        val groupDesign = Color(0xFFE91E63)       // Rosa
        val groupMarketing = Color(0xFF9C27B0)    // Roxo
        val groupSales = Color(0xFF4CAF50)        // Verde
        val groupSupport = Color(0xFFFF9800)      // Laranja
        val groupHR = Color(0xFF795548)           // Marrom
        val groupFinance = Color(0xFF607D8B)      // Azul Acinzentado
        val groupOperations = Color(0xFFF44336)   // Vermelho
    }

    // STRINGS COMUNS
    object Strings {
        const val save = "Salvar"
        const val cancel = "Cancelar"
        const val delete = "Remover"
        const val edit = "Editar"
        const val close = "Fechar"
        const val confirm = "Confirmar"
        const val back = "Voltar"
        const val next = "Próximo"
        const val previous = "Anterior"
        const val loading = "Carregando..."
        const val error = "Erro"
        const val success = "Sucesso"
        const val warning = "Aviso"
        const val info = "Informação"

        // Específicas da aplicação
        const val meeting = "Reunião"
        const val addMeeting = "Adicionar Reunião"
        const val editMeeting = "Editar Reunião"
        const val deleteMeeting = "Remover Reunião"
        const val meetingsOfMonth = "Eventos do mês"
        const val description = "Descrição"
        const val location = "Local"
        const val region = "Região"
        const val project = "Projeto"
        const val group = "Grupo"
        const val startTime = "Horário Início"
        const val endTime = "Horário Fim"
        const val date = "Data"
        const val today = "Hoje"
        const val selectGroup = "Selecionar Grupo"
        const val noEventsInMonth = "Nenhum evento neste mês"
    }

    // CONFIGURAÇÕES DE TEMPO
    object TimeConfig {
        const val defaultStartTime = "09:00"
        const val defaultEndTime = "10:00"
        const val timePickerTitle = "Selecionar Horário"
        const val defaultMeetingDurationMinutes = 60
    }

    // CONFIGURAÇÕES DE ANIMAÇÃO
    object Animation {
        const val defaultDurationMs = 300
        const val fastDurationMs = 150
        const val slowDurationMs = 500
        const val swipeThreshold = 80f
        const val swipeOffsetMax = 100f
        const val swipeOffsetMin = -100f
    }

    // CONFIGURAÇÕES DO CALENDÁRIO
    object Calendar {
        val monthNames = listOf(
            "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
            "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"
        )

        val weekDayAbbreviations = listOf("D", "S", "T", "Q", "Q", "S", "S")

        const val maxEventsToShow = 3
        const val weeksInMonth = 6
        const val daysInWeek = 7
    }

    // VALIDAÇÕES
    object Validation {
        const val minDescriptionLength = 0
        const val maxDescriptionLength = 500
        const val minTitleLength = 1
        const val maxTitleLength = 100
    }

    // CONFIGURAÇÕES DE LAYOUT
    object Layout {
        val screenPadding = Spacing.medium
        val cardPadding = Spacing.medium
        val sectionSpacing = Spacing.large
        val itemSpacing = Spacing.small
        val contentSpacing = Spacing.medium
    }
}

/**
 * Extension functions para facilitar o uso das constantes
 */

// Extensão para acessar cores dos grupos por índice
fun Int.toGroupColor(): Color {
    return when (this % 8) {
        0 -> AppConstants.AppColors.groupDevelopment
        1 -> AppConstants.AppColors.groupDesign
        2 -> AppConstants.AppColors.groupMarketing
        3 -> AppConstants.AppColors.groupSales
        4 -> AppConstants.AppColors.groupSupport
        5 -> AppConstants.AppColors.groupHR
        6 -> AppConstants.AppColors.groupFinance
        7 -> AppConstants.AppColors.groupOperations
        else -> AppConstants.AppColors.primary
    }
}

// Extensão para validar horários
fun String.isValidTime(): Boolean {
    return try {
        val parts = this.split(":")
        if (parts.size != 2) return false
        val hour = parts[0].toInt()
        val minute = parts[1].toInt()
        hour in 0..23 && minute in 0..59
    } catch (e: Exception) {
        false
    }
}

// Extensão para converter minutos em string de horário
fun Int.toTimeString(): String {
    val hours = this / 60
    val minutes = this % 60
    return "%02d:%02d".format(hours, minutes)
}

// Extensão para converter string de horário em minutos
fun String.toMinutes(): Int {
    return try {
        val parts = this.split(":")
        parts[0].toInt() * 60 + parts[1].toInt()
    } catch (e: Exception) {
        0
    }
}