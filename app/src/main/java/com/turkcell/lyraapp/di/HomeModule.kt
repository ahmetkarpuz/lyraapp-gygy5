package com.turkcell.lyraapp.di

import com.turkcell.lyraapp.data.home.HomeRepository
import com.turkcell.lyraapp.data.home.MockHomeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Home feature'inin repository baglamalari.
 *
 * Backend hazir olmadigindan [HomeRepository], MOCK implementasyona ([MockHomeRepository])
 * baglanir. Gercek API geldiginde yalnizca bu baglamanin hedefi degistirilir.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class HomeModule {

    @Binds
    @Singleton
    abstract fun bindHomeRepository(impl: MockHomeRepository): HomeRepository
}
