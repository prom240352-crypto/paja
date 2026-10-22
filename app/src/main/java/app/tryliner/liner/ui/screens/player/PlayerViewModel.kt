package app.tryliner.liner.ui.screens.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.tryliner.liner.data.model.Track
import app.tryliner.liner.data.player.PlayerState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val playerState: PlayerState
) : ViewModel() {
    
    val currentTrack: StateFlow<Track?> = playerState.currentTrack
    val isPlaying: StateFlow<Boolean> = playerState.isPlaying
    val currentPositionMs: StateFlow<Long> = playerState.currentPositionMs
    
    fun togglePlayPause() {
        playerState.togglePlayPause()
    }
    
    fun seekTo(positionMs: Long) {
        playerState.setPosition(positionMs)
    }
    
    fun formatDuration(durationMs: Long): String {
        val totalSeconds = durationMs / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return String.format("%d:%02d", minutes, seconds)
    }
}
