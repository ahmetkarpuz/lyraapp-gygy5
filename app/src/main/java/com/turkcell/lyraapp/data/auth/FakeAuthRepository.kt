package com.turkcell.lyraapp.data.auth

import kotlinx.coroutines.delay
import javax.inject.Inject

/**
 * [AuthRepository]'nin sahte (stub) implementasyonu.
 *
 * Gercek bir ag cagrisi yapmaz; yalnizca MVI akisinin uctan uca calismasini saglamak icin
 * bir gecikme ile ag davranisini taklit eder. Gercek API geldiginde bu sinif bir
 * ag tabanli implementasyonla degistirilir.
 */
class FakeAuthRepository @Inject constructor() : AuthRepository {

    override suspend fun login(phoneNumber: String, password: String): Result<Unit> {
        delay(NETWORK_DELAY_MS)
        return if (password.isNotBlank()) {
            Result.success(Unit)
        } else {
            Result.failure(IllegalArgumentException("Sifre bos olamaz."))
        }
    }

    override suspend fun register(
        firstName: String,
        lastName: String,
        phoneNumber: String,
        password: String,
    ): Result<Unit> {
        delay(NETWORK_DELAY_MS)
        return if (firstName.isNotBlank() && lastName.isNotBlank() && password.isNotBlank()) {
            Result.success(Unit)
        } else {
            Result.failure(IllegalArgumentException("Hesap bilgileri eksik."))
        }
    }

    private companion object {
        const val NETWORK_DELAY_MS = 1_000L
    }
}
