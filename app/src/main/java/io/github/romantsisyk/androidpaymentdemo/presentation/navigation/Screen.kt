package io.github.romantsisyk.androidpaymentdemo.presentation.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Payment : Screen("payment")
    data object PaymentSuccess : Screen("payment_success")

    // Навігація
    data object Settings : Screen("settings")
    data object LanguageSettings : Screen("language_settings")

    // Оригінальні екрани
    data object ProductList : Screen("product_list")
    data object ProductDetail : Screen("product_detail/{productId}") {
        fun createRoute(productId: String) = "product_detail/$productId"
    }

    data object Cart : Screen("cart")
    data object Checkout : Screen("checkout")
}