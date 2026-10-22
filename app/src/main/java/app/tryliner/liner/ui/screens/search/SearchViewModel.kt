package app.tryliner.liner.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import app.tryliner.liner.data.model.SearchResponse
import app.tryliner.liner.data.repository.MusicRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val musicRepository: MusicRepository
) : ViewModel() {
    
    private val _searchResults = MutableStateFlow<SearchResponse?>(null)
    val searchResults: StateFlow<SearchResponse?> = _searchResults.asStateFlow()
    
    private val _isSearching = MutableStateFlow(false)
    val isSearching: StateFlow<Boolean> = _isSearching.asStateFlow()
    
    private var searchJob: Job? = null
    
    fun search(query: String) {
        searchJob?.cancel()
        
        if (query.isBlank()) {
            _searchResults.value = null
            _isSearching.value = false
            return
        }
        
        searchJob = viewModelScope.launch {
            delay(300) // Debounce
            _isSearching.value = true
            
            val result = musicRepository.search(query)
            result.fold(
                onSuccess = { response ->
                    _searchResults.value = response
                },
                onFailure = { error ->
                    _searchResults.value = null
                    android.util.Log.e("SearchViewModel", "Search failed: ${error.message}")
                }
            )
            
            _isSearching.value = false
        }
    }
}
