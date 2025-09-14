package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.AssociatedDto
import com.example.myapplication.repository.ListAssociatedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ListAssociatedViewModel @Inject constructor(
  private val repository: ListAssociatedRepository
) : ViewModel() {
  private val _listAssociateds = MutableStateFlow<List<AssociatedDto>>(emptyList())
  val listAssociateds: StateFlow<List<AssociatedDto>> = _listAssociateds.asStateFlow()

  init {
    iniciarList()
  }

  private fun iniciarList() {
    viewModelScope.launch {
      try {
        _listAssociateds.value = repository.getListAssociateds()
      } catch (e: Exception) {
        e.printStackTrace()
      }
    }
  }
}