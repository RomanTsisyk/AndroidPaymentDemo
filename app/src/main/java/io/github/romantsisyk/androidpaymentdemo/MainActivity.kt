package io.github.romantsisyk.androidpaymentdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import io.github.romantsisyk.androidpaymentdemo.presentation.navigation.AppNavigation
import io.github.romantsisyk.androidpaymentdemo.presentation.theme.AndroidPaymentDemoTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidPaymentDemoTheme {
                val navController = rememberNavController()
                AppNavigation(navController)
            }
        }
    }
}