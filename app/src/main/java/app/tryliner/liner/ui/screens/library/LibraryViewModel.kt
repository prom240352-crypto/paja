package app.tryliner.liner.ui.screens.library

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
    
    fun loadLibrary() {
        viewModelScope.launch {
            _isLoading.value = true
            
            val result = musicRepository.getLibraryTracks()
            result.fold(
                onSuccess = { tracks ->
                    _libraryTracks.value = tracks
                },
                onFailure = { error ->
                    _libraryTracks.value = emptyList()
                }
            )
            
            _isLoading.value = false
        }
    }
}
