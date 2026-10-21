package app.tryliner.liner.ui.screens.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import app.tryliner.liner.data.repository.AuthRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    
    var isLoggedIn by mutableStateOf(authRepository.isLoggedIn())
        private set
    
    fun login(email: String, password: String, onComplete: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            val result = authRepository.login(email, password)
            result.fold(
                onSuccess = {
                    isLoggedIn = true
                    onComplete(true, null)
                },
                onFailure = { error ->
                    onComplete(false, error.message)
                }
            )
        }
    }
}
