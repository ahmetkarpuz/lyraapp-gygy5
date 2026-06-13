package com.turkcell.lyraapp.data.home

/**
 * Ana sayfa beslemesinin (feed) tamami: tek repository cagrisyila donen aggregate model.
 *
 * Kapak gorselleri henuz bir CDN/gorsel servisi olmadigindan gradyan renk cifti
 * (`artworkStartColor`/`artworkEndColor`, ARGB hex) ile temsil edilir. Gercek API
 * geldiginde bu alanlar gorsel URL'siyle degistirilebilir; UI katmani yalnizca
 * bu modeli cizer.
 */
data class HomeFeed(
    val userInitials: String,
    val quickPicks: List<QuickPick>,
    val recentlyPlayed: List<RecentlyPlayed>,
    val playlistsForYou: List<PlaylistForYou>,
)

/** "Ne dinlemek istersin?" grid'indeki hizli secim ogesi. */
data class QuickPick(
    val id: String,
    val title: String,
    val artworkStartColor: Long,
    val artworkEndColor: Long,
)

/** "Son calınanlar" bolumundeki oge; [subtitle] sanatci/album bilgisini tasir. */
data class RecentlyPlayed(
    val id: String,
    val title: String,
    val subtitle: String,
    val artworkStartColor: Long,
    val artworkEndColor: Long,
)

/** "Senin icin calma listeleri" bolumundeki oge. */
data class PlaylistForYou(
    val id: String,
    val title: String,
    val artworkStartColor: Long,
    val artworkEndColor: Long,
)
