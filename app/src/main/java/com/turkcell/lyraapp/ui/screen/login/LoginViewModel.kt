package com.turkcell.lyraapp.ui.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turkcell.lyraapp.model.UserRole
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Login ekraninin is mantigi.
 * Hardcoded kullanici bilgileriyle dogrulama yapar ve role gore navigasyon effect gonderir.
 */
class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _effect = Channel<LoginEffect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnEmailChange -> {
                _uiState.update { it.copy(email = event.email, errorMessage = null) }
            }
            is LoginEvent.OnPasswordChange -> {
                _uiState.update { it.copy(password = event.password, errorMessage = null) }
            }
            is LoginEvent.OnLoginClick -> {
                login()
            }
        }
    }

    private fun login() {
        val currentState = _uiState.value

        // Bos alan kontrolu
        if (currentState.email.isBlank() || currentState.password.isBlank()) {
            _uiState.update { it.copy(errorMessage = "E-posta ve sifre alanlari bos birakilamaz.") }
            return
        }

        _uiState.update { it.copy(isLoading = true, errorMessage = null) }

        viewModelScope.launch {
            // Hardcoded kullanici dogrulamasi
            val role = authenticate(currentState.email.trim(), currentState.password)

            if (role != null) {
                _uiState.update { it.copy(isLoading = false) }
                _effect.send(LoginEffect.NavigateTo(role))
            } else {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Gecersiz e-posta veya sifre."
                    )
                }
            }
        }
    }

    /**
     * Hardcoded kullanici dogrulamasi.
     * admin@ticket.com / admin123 -> ADMIN
     * user@ticket.com  / user123  -> USER
     */
    private fun authenticate(email: String, password: String): UserRole? {
        return when {
            email == "admin@ticket.com" && password == "admin123" -> UserRole.ADMIN
            email == "user@ticket.com" && password == "user123" -> UserRole.USER
            else -> null
        }
    }
}
