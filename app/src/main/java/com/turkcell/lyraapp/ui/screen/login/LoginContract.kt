package com.turkcell.lyraapp.ui.screen.login

import com.turkcell.lyraapp.model.UserRole

/**
 * LoginScreen icin MVI kontrati.
 * State, Event ve Effect tanimlarini icerir.
 */

// UI durumunu temsil eder
data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

// Kullanicidan gelen aksiyonlar
sealed class LoginEvent {
    data class OnEmailChange(val email: String) : LoginEvent()
    data class OnPasswordChange(val password: String) : LoginEvent()
    data object OnLoginClick : LoginEvent()
}

// Tek seferlik yan etkiler (navigasyon vb.)
sealed class LoginEffect {
    data class NavigateTo(val role: UserRole) : LoginEffect()
}
