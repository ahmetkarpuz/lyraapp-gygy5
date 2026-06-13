package com.turkcell.lyraapp.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.turkcell.lyraapp.ui.auth.login.LoginRoute
import com.turkcell.lyraapp.ui.auth.register.RegisterRoute
import com.turkcell.lyraapp.ui.home.HomeRoute

/**
 * Uygulamanin iskelet navigasyon yapisi.
 *
 * Tek [NavHost], Auth grafigi ile ana akis sekmelerini barindirir; baslangic hedefi
 * [LyraDestination.Login]'dir. Dis [Scaffold]'in `bottomBar` yuvasindaki [LyraBottomBar]
 * yalnizca ust duzey sekme rotalarinda gorunur; boylece cubuk her ana sayfanin altinda
 * yer alir, Auth ekranlarinda gizlenir.
 *
 * Her ekranin `Route` composable'i, MVI Effect'lerini buradan saglanan navigasyon
 * lambda'larina koprular (ViewModel navigasyon API'si bilmez).
 *
 * Dis Scaffold'in `contentWindowInsets`'i sifirlanir: sistem cubugu boslluklarini her ekran
 * kendisi yonetir (Login/Register'da oldugu gibi); icerik dolgusu yalnizca alt cubugun
 * yuksekligini tasir.
 */
@Composable
fun LyraNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    Scaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            if (isTopLevelRoute(currentRoute)) {
                LyraBottomBar(
                    currentRoute = currentRoute,
                    onTabSelected = navController::navigateToTab,
                )
            }
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = LyraDestination.Login.route,
            modifier = Modifier.padding(innerPadding),
        ) {
            composable(LyraDestination.Login.route) {
                LoginRoute(
                    onNavigateToHome = { navController.navigateToHomeClearingAuth() },
                    onNavigateToRegister = {
                        navController.navigate(LyraDestination.Register.route) {
                            launchSingleTop = true
                        }
                    },
                )
            }

            composable(LyraDestination.Register.route) {
                RegisterRoute(
                    onNavigateToHome = { navController.navigateToHomeClearingAuth() },
                    onNavigateToLogin = {
                        navController.navigate(LyraDestination.Login.route) {
                            popUpTo(LyraDestination.Login.route) { inclusive = false }
                            launchSingleTop = true
                        }
                    },
                    onNavigateBack = { navController.popBackStack() },
                )
            }

            composable(LyraDestination.Home.route) { HomeRoute() }
            composable(LyraDestination.Search.route) { PlaceholderScreen(title = "Ara") }
            composable(LyraDestination.Library.route) { PlaceholderScreen(title = "Kutuphane") }
            composable(LyraDestination.Favorites.route) { PlaceholderScreen(title = "Favoriler") }
            composable(LyraDestination.Profile.route) { PlaceholderScreen(title = "Profil") }
        }
    }
}

/**
 * Alt cubuk sekmesine standart desenle gecis yapar: back stack'te sekme kopyasi birikmez
 * (`launchSingleTop`), sekmeler arasi geciste durum saklanir/geri yuklenir
 * (`saveState`/`restoreState`) ve geri tusu daima Home'a doner (`popUpTo(Home)`).
 */
private fun NavHostController.navigateToTab(destination: LyraDestination) {
    navigate(destination.route) {
        popUpTo(LyraDestination.Home.route) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}

/** Auth akisini back stack'ten temizleyerek Home'a gecer (geri tusu Login'e donmez). */
private fun NavHostController.navigateToHomeClearingAuth() {
    navigate(LyraDestination.Home.route) {
        popUpTo(LyraDestination.Login.route) { inclusive = true }
        launchSingleTop = true
    }
}

/**
 * Gecici sekme icerigi. Sekme ekranlari henuz kapsamda degildir; her biri kendi
 * feature paketinde MVI sozlesmesiyle (Contract + ViewModel + Route/Screen) yazildiginda
 * bu composable kaldirilacak ve rotalar gercek Route'lara baglanacaktir.
 */
@Composable
private fun PlaceholderScreen(
    title: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}
