package com.turkcell.lyraapp.ui.auth.login

/**
 * Login ekraninin MVI sozlesmesi: State (durum), Intent (kullanici niyeti) ve
 * Effect (tek seferlik olay) tek dosyada toplanmistir.
 */

/**
 * Ekranin gozlemlenebilir tum durumu. Tek bir immutable kaynak (single source of truth).
 *
 * [isLoginEnabled] dogrudan kullanici tarafindan set edilmez; alan degisimlerinde
 * [LoginViewModel] tarafindan turetilir.
 */
data class LoginUiState(
    val phoneNumber: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val isLoginEnabled: Boolean = false,
)

/**
 * Kullanicidan gelen niyetler. UI yalnizca bu tipleri yayimlar; is mantigini calistirmaz.
 */
sealed interface LoginIntent {
    data class PhoneNumberChanged(val value: String) : LoginIntent
    data class PasswordChanged(val value: String) : LoginIntent
    data object TogglePasswordVisibility : LoginIntent
    data object Submit : LoginIntent

    /** "Kayit ol" baglantisi: Register ekranina gecis niyeti. */
    data object RegisterClicked : LoginIntent
}

/**
 * Tek seferlik (one-shot) olaylar: navigasyon, snackbar vb. State icinde tutulmaz,
 * boylece konfigurasyon degisiminde tekrar tetiklenmez.
 */
sealed interface LoginEffect {
    /** Giris basarili; ana akisa gec. */
    data object NavigateToHome : LoginEffect

    /** "Kayit ol" baglantisi: Register ekranina gec. */
    data object NavigateToRegister : LoginEffect

    data class ShowError(val message: String) : LoginEffect
}
