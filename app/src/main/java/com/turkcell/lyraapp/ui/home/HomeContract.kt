package com.turkcell.lyraapp.ui.home

import com.turkcell.lyraapp.data.home.PlaylistForYou
import com.turkcell.lyraapp.data.home.QuickPick
import com.turkcell.lyraapp.data.home.RecentlyPlayed

/**
 * Home ekraninin MVI sozlesmesi: UiState + Intent + Effect.
 *
 * Bu iterasyonda kartlara tiklama ve "Tumu" davranissizdir (hedef ekranlar henuz yok);
 * bu nedenle yalnizca yukleme akisina ait niyetler tanimlidir.
 */
data class HomeUiState(
    val isLoading: Boolean = false,
    val greeting: String = "",
    val userInitials: String = "",
    val quickPicks: List<QuickPick> = emptyList(),
    val recentlyPlayed: List<RecentlyPlayed> = emptyList(),
    val playlistsForYou: List<PlaylistForYou> = emptyList(),
)

sealed interface HomeIntent {
    /** Besleme yuklemesi basarisiz oldugunda kullanici yeniden dener. */
    data object Retry : HomeIntent
}

sealed interface HomeEffect {
    data class ShowError(val message: String) : HomeEffect
}
