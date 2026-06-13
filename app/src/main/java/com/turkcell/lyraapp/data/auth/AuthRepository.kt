package com.turkcell.lyraapp.data.auth

/**
 * Kimlik dogrulama islemlerinin tek soyutlama noktasi.
 *
 * Backend ekibinin REST API'si henuz tanimli olmadigindan, su an yalnizca sahte bir
 * implementasyon ([FakeAuthRepository]) ile saglanir. Gercek API geldiginde yalnizca
 * implementasyon degisir; cagiran katmanlar (orn. LoginViewModel) etkilenmez.
 */
interface AuthRepository {

    /**
     * Verilen telefon numarasi ve sifreyle giris dener.
     *
     * @return Basariliysa [Result.success], aksi halde hata mesaji tasiyan [Result.failure].
     */
    suspend fun login(phoneNumber: String, password: String): Result<Unit>

    /**
     * Verilen kullanici bilgileriyle yeni bir hesap olusturmayi dener.
     *
     * @return Basariliysa [Result.success], aksi halde hata mesaji tasiyan [Result.failure].
     */
    suspend fun register(
        firstName: String,
        lastName: String,
        phoneNumber: String,
        password: String,
    ): Result<Unit>
}
