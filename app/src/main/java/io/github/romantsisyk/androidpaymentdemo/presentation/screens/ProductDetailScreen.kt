package io.github.romantsisyk.androidpaymentdemo.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun ProductDetailScreen(
    navController: NavController,
    productId: String
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Product Detail for ID: $productId (Coming in Stage 2)",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}