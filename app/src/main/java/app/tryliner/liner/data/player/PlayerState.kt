package app.tryliner.liner.data.player

import app.tryliner.liner.data.model.Track
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayerState @Inject constructor() {
    
    private val _currentTrack = MutableStateFlow<Track?>(null)
    val currentTrack: StateFlow<Track?> = _currentTrack.asStateFlow()
    
    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()
    
    private val _currentPositionMs = MutableStateFlow(0L)
    val currentPositionMs: StateFlow<Long> = _currentPositionMs.asStateFlow()
    
    fun setTrack(track: Track) {
        _currentTrack.value = track
        _currentPositionMs.value = 0
    }
    
    fun setPlaying(playing: Boolean) {
        _isPlaying.value = playing
    }
    
    fun setPosition(positionMs: Long) {
        _currentPositionMs.value = positionMs
    }
    
    fun togglePlayPause() {
        _isPlaying.value = !_isPlaying.value
    }
    
    fun clear() {
        _currentTrack.value = null
        _isPlaying.value = false
        _currentPositionMs.value = 0
    }
}
