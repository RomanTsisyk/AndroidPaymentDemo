package io.github.romantsisyk.androidpaymentdemo.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import io.github.romantsisyk.androidpaymentdemo.presentation.screens.CartScreen
import io.github.romantsisyk.androidpaymentdemo.presentation.screens.CheckoutScreen
import io.github.romantsisyk.androidpaymentdemo.presentation.screens.HomeScreen
import io.github.romantsisyk.androidpaymentdemo.presentation.screens.LanguageSettingsScreen
import io.github.romantsisyk.androidpaymentdemo.presentation.screens.PaymentScreen
import io.github.romantsisyk.androidpaymentdemo.presentation.screens.PaymentSuccessScreen
import io.github.romantsisyk.androidpaymentdemo.presentation.screens.ProductDetailScreen
import io.github.romantsisyk.androidpaymentdemo.presentation.screens.ProductListScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        // Розкішний інтерфейс
        composable(route = Screen.Home.route) {
            HomeScreen(
                onNavigateToPayment = {
                    navController.navigate(Screen.Payment.route)
                },
                onNavigateToLanguageSettings = {
                    navController.navigate(Screen.LanguageSettings.route)
                }
            )
        }

        composable(route = Screen.Payment.route) {
            PaymentScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onPaymentComplete = {
                    navController.navigate(Screen.PaymentSuccess.route) {
                        popUpTo(Screen.Home.route) {
                            inclusive = false
                        }
                    }
                }
            )
        }

        composable(route = Screen.PaymentSuccess.route) {
            PaymentSuccessScreen(
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        // Екран налаштувань мови
        composable(route = Screen.LanguageSettings.route) {
            LanguageSettingsScreen(navController = navController)
        }

        // Оригінальні екрани
        composable(route = Screen.ProductList.route) {
            ProductListScreen(navController = navController)
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
            CartScreen(navController = navController)
        }

        composable(route = Screen.Checkout.route) {
            CheckoutScreen(navController = navController)
        }
    }
}