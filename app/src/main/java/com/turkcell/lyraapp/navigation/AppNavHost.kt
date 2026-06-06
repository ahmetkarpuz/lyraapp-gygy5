package com.turkcell.lyraapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.turkcell.lyraapp.model.UserRole
import com.turkcell.lyraapp.ui.screen.admin.AdminScreen
import com.turkcell.lyraapp.ui.screen.login.LoginScreen
import com.turkcell.lyraapp.ui.screen.user.UserScreen

/**
 * Uygulamanin ana navigasyon grafi.
 * Login ekranindan baslar, role gore AdminPanel veya UserHome ekranina yonlendirir.
 * Logout isleminde back stack temizlenerek Login ekranina geri donulur.
 */
@Composable
fun AppNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route,
        modifier = modifier
    ) {
        // Login ekrani
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = { role ->
                    val targetRoute = when (role) {
                        UserRole.ADMIN -> Screen.AdminPanel.route
                        UserRole.USER -> Screen.UserHome.route
                    }
                    navController.navigate(targetRoute) {
                        // Login ekranini back stack'ten temizle
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        // Admin Paneli ekrani
        composable(Screen.AdminPanel.route) {
            AdminScreen(
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        // Tum back stack'i temizle
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        // Kullanici Ana Sayfa ekrani
        composable(Screen.UserHome.route) {
            UserScreen(
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        // Tum back stack'i temizle
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}
