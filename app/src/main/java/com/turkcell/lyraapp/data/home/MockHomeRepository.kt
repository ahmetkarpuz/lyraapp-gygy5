package com.turkcell.lyraapp.data.home

import kotlinx.coroutines.delay
import javax.inject.Inject

/**
 * [HomeRepository]'nin MOCK (statik veri) implementasyonu.
 *
 * Gercek bir ag cagrisi yapmaz; tasarim ekran goruntusundeki icerigi statik olarak dondurur
 * ve `delay(...)` ile ag davranisini taklit eder. Gercek API geldiginde bu sinif ag tabanli
 * bir implementasyonla degistirilir; ViewModel ve Contract etkilenmez.
 */
class MockHomeRepository @Inject constructor() : HomeRepository {

    override suspend fun getHomeFeed(): Result<HomeFeed> {
        delay(NETWORK_DELAY_MS)
        return Result.success(
            HomeFeed(
                userInitials = "ZK",
                quickPicks = QUICK_PICKS,
                recentlyPlayed = RECENTLY_PLAYED,
                playlistsForYou = PLAYLISTS_FOR_YOU,
            ),
        )
    }

    private companion object {
        const val NETWORK_DELAY_MS = 800L

        val QUICK_PICKS = listOf(
            QuickPick("qp-1", "Gece Surusu", 0xFF8B6FB8, 0xFF4A3D6B),
            QuickPick("qp-2", "Sabah Kahvesi", 0xFF7C83D9, 0xFF3E4486),
            QuickPick("qp-3", "Neon Sokaklar", 0xFFD98E4A, 0xFF8A5526),
            QuickPick("qp-4", "Odaklan", 0xFF4AC2A8, 0xFF1F6E5C),
            QuickPick("qp-5", "Derin Mavi", 0xFF6FBF5A, 0xFF356B2A),
            QuickPick("qp-6", "Yaz Anilari", 0xFF5AAFC9, 0xFF2A5F73),
        )

        val RECENTLY_PLAYED = listOf(
            RecentlyPlayed("rp-1", "Neon Sokaklar", "Sehir Isiklari", 0xFFD98E4A, 0xFF8A5526),
            RecentlyPlayed("rp-2", "Derin Mavi", "Okyanus", 0xFF6FBF5A, 0xFF356B2A),
            RecentlyPlayed("rp-3", "Yildiz Tozu", "Polaris", 0xFF3D5A80, 0xFF1B2A45),
        )

        val PLAYLISTS_FOR_YOU = listOf(
            PlaylistForYou("pl-1", "Haftalik Kesif", 0xFF9B7FC4, 0xFF5A4480),
            PlaylistForYou("pl-2", "Sakin Aksamlar", 0xFF6B5FB8, 0xFF3A3270),
            PlaylistForYou("pl-3", "Enerji Ver", 0xFF3FAE9C, 0xFF1E5D52),
        )
    }
}
