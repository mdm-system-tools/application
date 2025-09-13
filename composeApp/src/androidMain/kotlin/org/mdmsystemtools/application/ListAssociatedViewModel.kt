package org.mdmsystemtools.application

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.mdmsystemtools.application.data.model.AssociatedDto


@HiltViewModel
class ListAssociatedViewModel @Inject constructor() : ViewModel() {
	private val _listAssociateds = MutableStateFlow<List<AssociatedDto>>(emptyList())
	val listAssociateds: StateFlow<List<AssociatedDto>> = _listAssociateds.asStateFlow()


	fun getListAssociateds() {
		for (i in 1..100) {
			_listAssociateds.value += AssociatedDto(10000, 10000, "Jose Maria")
		}
	}

}