package com.turkcell.lyraapp.ui.auth.register

/**
 * Register ("Hesap olustur") ekraninin MVI sozlesmesi: State (durum), Intent (kullanici niyeti)
 * ve Effect (tek seferlik olay) tek dosyada toplanmistir.
 */

/**
 * Ekranin gozlemlenebilir tum durumu. Tek bir immutable kaynak (single source of truth).
 *
 * [passwordStrength] ve [isRegisterEnabled] dogrudan kullanici tarafindan set edilmez;
 * alan degisimlerinde [RegisterViewModel] tarafindan turetilir.
 */
data class RegisterUiState(
    val firstName: String = "",
    val lastName: String = "",
    val phoneNumber: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val isTermsAccepted: Boolean = false,
    val isLoading: Boolean = false,
    /** Sifre gucu gostergesi icin 0..[PASSWORD_STRENGTH_MAX] arasi turetilmis deger. */
    val passwordStrength: Int = 0,
    val isRegisterEnabled: Boolean = false,
) {
    companion object {
        /** Sifre guc gostergesindeki segment sayisi (ekran tasarimindaki uc cubuk). */
        const val PASSWORD_STRENGTH_MAX = 3
    }
}

/**
 * Kullanicidan gelen niyetler. UI yalnizca bu tipleri yayimlar; is mantigini calistirmaz.
 */
sealed interface RegisterIntent {
    data class FirstNameChanged(val value: String) : RegisterIntent
    data class LastNameChanged(val value: String) : RegisterIntent
    data class PhoneNumberChanged(val value: String) : RegisterIntent
    data class PasswordChanged(val value: String) : RegisterIntent
    data class TermsAcceptedChanged(val value: Boolean) : RegisterIntent
    data object TogglePasswordVisibility : RegisterIntent
    data object Submit : RegisterIntent
    data object BackClicked : RegisterIntent
    data object LoginClicked : RegisterIntent
}

/**
 * Tek seferlik (one-shot) olaylar: navigasyon, snackbar vb. State icinde tutulmaz,
 * boylece konfigurasyon degisiminde tekrar tetiklenmez.
 */
sealed interface RegisterEffect {
    /** Kayit basarili; ana akisa gec. */
    data object NavigateToHome : RegisterEffect

    /** "Giris yap" baglantisi: Login ekranina gec. */
    data object NavigateToLogin : RegisterEffect

    /** Geri oku: bir onceki ekrana don. */
    data object NavigateBack : RegisterEffect

    data class ShowError(val message: String) : RegisterEffect
}
