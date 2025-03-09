package io.github.romantsisyk.androidpaymentdemo.presentation.navigation

sealed class Screen(val route: String) {
    data object ProductList : Screen("product_list")
    data object ProductDetail : Screen("product_detail/{productId}") {
        fun createRoute(productId: String) = "product_detail/$productId"
    }
}