package org.MdmSystemTools.Application.view.screens.Contact.projetc

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.MdmSystemTools.Application.model.repository.ProjectRepository

data class ProjetoDetalhesUiState(
    val projetoNome: String = "Projeto Vila Nova",
    val dataInicio: String = "01/01/2025",
    val status: String = "Ativo",
    val regiao: String = "Zona sul",
    val valor: String = "R$ 450,00",
    val totalGrupos: Int = 4,
    val totalAssociados: Int = 48,
    val grupos: List<GrupoProjetoUi> = emptyList(),
    val isLoading: Boolean = false
)

@HiltViewModel
class ProjetoDetalhesViewModel
@Inject
constructor(
    private val repository: ProjectRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProjetoDetalhesUiState())
    val uiState: StateFlow<ProjetoDetalhesUiState> = _uiState.asStateFlow()

    fun loadProjetoDetalhes(projetoId: Int) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)

                // TODO: Aqui virá a busca no banco de dados
                // Por enquanto mantendo dados fake
                val gruposFake = listOf(
                    GrupoProjetoUi("Grupo Manhã - 09:00", 12),
                    GrupoProjetoUi("Grupo Manhã - 09:00", 45),
                    GrupoProjetoUi("Grupo Manhã - 09:00", 86),
                    GrupoProjetoUi("Grupo Manhã - 09:00", 65),
                )

                _uiState.value = _uiState.value.copy(
                    grupos = gruposFake,
                    isLoading = false
                )

                Log.d("ProjetoDetalhesViewModel", "Projeto ID: $projetoId carregado com sucesso")
            } catch (e: Exception) {
                Log.e("ProjetoDetalhesViewModel", "Erro ao carregar projeto: ${e.message}")
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }
}
