package app.tryliner.liner.ui.screens.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import app.tryliner.liner.data.repository.MusicRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val musicRepository: MusicRepository
) : ViewModel() {
    // TODO: Implement home screen logic
}
