package com.turkcell.lyraapp.navigation

/**
 * Uygulamadaki tum ekran rotalarini tanimlar.
 * Her bir alt sinif, NavHost icerisinde kullanilacak benzersiz bir rota (route) icerir.
 */
sealed class Screen(val route: String) {
    data object Login : Screen("login")
    data object AdminPanel : Screen("admin_panel")
    data object UserHome : Screen("user_home")
}
