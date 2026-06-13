package com.turkcell.lyraapp.di

import com.turkcell.lyraapp.data.auth.AuthRepository
import com.turkcell.lyraapp.data.auth.FakeAuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * [AuthRepository] arayuzunu somut implementasyonuna ([FakeAuthRepository]) baglar.
 *
 * `@Binds` ile yapildigindan Hilt fazladan kod uretmez; gercek API implementasyonu
 * eklendiginde yalnizca buradaki baglama hedefi degistirilir.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class AuthModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl: FakeAuthRepository): AuthRepository
}
