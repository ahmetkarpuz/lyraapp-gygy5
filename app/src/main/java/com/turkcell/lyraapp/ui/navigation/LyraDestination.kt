package com.turkcell.lyraapp.ui.navigation

/**
 * Uygulamadaki navigasyon hedeflerinin tek dogruluk kaynagi.
 *
 * Her hedef benzersiz bir [route] string'iyle temsil edilir; [LyraNavHost] bu route'lar
 * uzerinden composable'lari baglar. Yeni bir ekran eklendiginde buraya bir hedef eklenir.
 */
enum class LyraDestination(val route: String) {
    Login("login"),
    Register("register"),
    Home("home"),
    Search("search"),
    Library("library"),
    Favorites("favorites"),
    Profile("profile"),
}
