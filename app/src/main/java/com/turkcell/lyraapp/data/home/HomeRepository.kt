package com.turkcell.lyraapp.data.home

/**
 * Ana sayfa iceriginin veri kaynagi soyutlamasi.
 *
 * Backend REST API'si hazir olmadigindan gecici implementasyon [MockHomeRepository]'dir;
 * gercek API geldiginde yalnizca implementasyon ve `di/HomeModule.kt` baglamasi degisir.
 */
interface HomeRepository {

    /** Ana sayfa beslemesinin tamamini tek seferde dondurur. */
    suspend fun getHomeFeed(): Result<HomeFeed>
}
