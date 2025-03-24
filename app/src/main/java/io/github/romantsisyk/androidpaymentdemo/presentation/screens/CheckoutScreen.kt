package io.github.romantsisyk.androidpaymentdemo.presentation.screens

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import io.github.romantsisyk.androidpaymentdemo.domain.model.PaymentIntent
import io.github.romantsisyk.androidpaymentdemo.presentation.navigation.Screen
import io.github.romantsisyk.androidpaymentdemo.presentation.state.PaymentState
import io.github.romantsisyk.androidpaymentdemo.presentation.viewmodel.CartViewModel
import io.github.romantsisyk.androidpaymentdemo.presentation.viewmodel.PaymentViewModel
import java.text.NumberFormat
import java.util.Locale

@SuppressLint("RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    navController: NavController,
) {
    val cartViewModel: CartViewModel = hiltViewModel()
    val paymentViewModel: PaymentViewModel = hiltViewModel()
    val stripePaymentManager = paymentViewModel.stripePaymentManager

    val cart by cartViewModel.cart.collectAsState()
    val paymentState by paymentViewModel.paymentState.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(paymentState) {
        when (paymentState) {
            is PaymentState.PaymentReady -> {
                val paymentIntentId = (paymentState as PaymentState.PaymentReady).paymentIntentId

                val clientSecret = "${paymentIntentId}_secret_${System.currentTimeMillis()}"

                val intent = PaymentIntent(
                    id = paymentIntentId,
                    clientSecret = clientSecret,  // Use properly formatted client secret
                    amount = cart.totalPrice,
                    currency = "usd"
                )
                paymentViewModel.processPayment(intent, context)
            }

            is PaymentState.Success -> {
                navController.navigate(Screen.PaymentSuccess.route) {
                    popUpTo(Screen.Checkout.route) {
                        inclusive = true
                    }
                }
            }

            else -> {}
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Checkout") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (cart.isEmpty) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Your cart is empty",
                        style = MaterialTheme.typography.titleSmall
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { navController.navigate(Screen.ProductList.route) }
                    ) {
                        Text("BROWSE PRODUCTS")
                    }
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Order Summary",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    cart.items.forEach { cartItem ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "${cartItem.product.name} (x${cartItem.quantity})",
                                modifier = Modifier.weight(1f)
                            )

                            Text(
                                text = NumberFormat.getCurrencyInstance(Locale.US)
                                    .format((cartItem.product.price * cartItem.quantity) / 100.0)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    HorizontalDivider()

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Subtotal")
                        Text(
                            NumberFormat.getCurrencyInstance(Locale.US)
                                .format(cart.totalPrice / 100.0)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Shipping")
                        Text("Free")
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    HorizontalDivider()

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Total",
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = NumberFormat.getCurrencyInstance(Locale.US)
                                .format(cart.totalPrice / 100.0),
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    Text(
                        text = "Payment Method",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "Credit Card (Stripe)",
                                fontWeight = FontWeight.Medium
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "You'll be redirected to the secure Stripe payment form to complete your purchase.",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    Button(
                        onClick = { paymentViewModel.createPaymentIntent() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        enabled = paymentState !is PaymentState.Loading &&
                                paymentState !is PaymentState.Processing
                    ) {
                        when {
                            paymentState is PaymentState.Loading ||
                                    paymentState is PaymentState.Processing -> {
                                CircularProgressIndicator(
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    modifier = Modifier.size(24.dp)
                                )
                            }

                            else -> {
                                Text("PAY NOW")
                            }
                        }
                    }

                    if (paymentState is PaymentState.Error) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = (paymentState as PaymentState.Error).message,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}

fun Context.findActivity(): Activity? {
    var currentContext = this
    while (currentContext is ContextWrapper) {
        if (currentContext is Activity) {
            return currentContext
        }
        currentContext = currentContext.baseContext
    }
    return null
}