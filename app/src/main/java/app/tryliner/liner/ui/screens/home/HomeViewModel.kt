package app.tryliner.liner.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import app.tryliner.liner.data.model.Track
import app.tryliner.liner.data.model.DailyMixItem
import app.tryliner.liner.data.repository.MusicRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val musicRepository: MusicRepository
) : ViewModel() {
    
    private val _recentPlays = MutableStateFlow<List<Track>>(emptyList())
    val recentPlays: StateFlow<List<Track>> = _recentPlays.asStateFlow()
    
    private val _dailyMixes = MutableStateFlow<List<DailyMixItem>>(emptyList())
    val dailyMixes: StateFlow<List<DailyMixItem>> = _dailyMixes.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()
    
    init {
        loadHomeData()
    }
    
    fun loadHomeData() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            
            try {
                // Load recent plays
                val recentResult = musicRepository.getRecentPlays()
                recentResult.fold(
                    onSuccess = { response ->
                        _recentPlays.value = response.items.map { it.track }
                    },
                    onFailure = { error ->
                        android.util.Log.e("HomeViewModel", "Failed to load recent plays: ${error.message}")
                    }
                )
                
                // Load daily mixes
                val mixesResult = musicRepository.getDailyMixes()
                mixesResult.fold(
                    onSuccess = { response ->
                        _dailyMixes.value = response.items
                    },
                    onFailure = { error ->
                        android.util.Log.e("HomeViewModel", "Failed to load daily mixes: ${error.message}")
                    }
                )
            } catch (e: Exception) {
                _errorMessage.value = "Failed to load home data: ${e.message}"
                android.util.Log.e("HomeViewModel", "Error loading home data: ${e.message}", e)
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun refresh() {
        loadHomeData()
    }
}
