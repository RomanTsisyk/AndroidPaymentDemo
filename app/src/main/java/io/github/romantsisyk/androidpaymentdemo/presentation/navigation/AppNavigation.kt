package io.github.romantsisyk.androidpaymentdemo.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import io.github.romantsisyk.androidpaymentdemo.presentation.screens.ProductDetailScreen
import io.github.romantsisyk.androidpaymentdemo.presentation.screens.ProductListScreen
import io.github.romantsisyk.androidpaymentdemo.presentation.screens.CartScreen
import io.github.romantsisyk.androidpaymentdemo.presentation.screens.CheckoutScreen
import io.github.romantsisyk.androidpaymentdemo.presentation.screens.PaymentSuccessScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.ProductList.route
    ) {
        composable(route = Screen.ProductList.route) {
            ProductListScreen(navController)
        }

        composable(
            route = Screen.ProductDetail.route,
            arguments = listOf(
                navArgument("productId") {
                    type = NavType.StringType
                }
            )
        ) {
            ProductDetailScreen(
                navController = navController,
                productId = it.arguments?.getString("productId").orEmpty()
            )
        }

        composable(route = Screen.Cart.route) {
            CartScreen(navController)
        }

        composable(route = Screen.Checkout.route) {
            CheckoutScreen(navController)
        }

        composable(route = Screen.PaymentSuccess.route) {
            PaymentSuccessScreen(navController)
        }
    }
}
