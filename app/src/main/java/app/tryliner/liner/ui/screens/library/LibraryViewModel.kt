package app.tryliner.liner.ui.screens.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import app.tryliner.liner.data.model.Track
import app.tryliner.liner.data.repository.MusicRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val musicRepository: MusicRepository
) : ViewModel() {
    
    private val _libraryTracks = MutableStateFlow<List<Track>>(emptyList())
    val libraryTracks: StateFlow<List<Track>> = _libraryTracks.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()
    
    fun loadLibrary() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            
            val result = musicRepository.getLibraryTracks()
            result.fold(
                onSuccess = { response ->
                    _libraryTracks.value = response.items
                },
                onFailure = { error ->
                    _errorMessage.value = "Failed to load library: ${error.message}"
                    _libraryTracks.value = emptyList()
                    android.util.Log.e("LibraryViewModel", "Failed to load library: ${error.message}")
                }
            )
            
            _isLoading.value = false
        }
    }
    
    fun refresh() {
        loadLibrary()
    }
}
