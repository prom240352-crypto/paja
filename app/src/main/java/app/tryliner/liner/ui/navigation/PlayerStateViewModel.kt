package app.tryliner.liner.ui.navigation

import androidx.lifecycle.ViewModel
import app.tryliner.liner.data.player.PlayerState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlayerStateViewModel @Inject constructor(
    val playerState: PlayerState
) : ViewModel()
