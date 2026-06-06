package com.turkcell.lyraapp.ui.screen.admin

/**
 * AdminScreen icin MVI kontrati.
 * State, Event ve Effect tanimlarini icerir.
 */

// Admin paneli UI durumu
data class AdminUiState(
    val welcomeMessage: String = "Admin Paneline Hos Geldiniz"
)

// Admin ekranindan gelen aksiyonlar
sealed class AdminEvent {
    data object OnLogoutClick : AdminEvent()
}

// Tek seferlik yan etkiler
sealed class AdminEffect {
    data object NavigateToLogin : AdminEffect()
}
