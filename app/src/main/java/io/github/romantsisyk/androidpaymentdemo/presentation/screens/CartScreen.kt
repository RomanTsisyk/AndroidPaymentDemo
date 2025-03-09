package io.github.romantsisyk.androidpaymentdemo.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import io.github.romantsisyk.androidpaymentdemo.domain.model.CartItem
import io.github.romantsisyk.androidpaymentdemo.presentation.navigation.Screen
import io.github.romantsisyk.androidpaymentdemo.presentation.viewmodel.CartViewModel
import java.text.NumberFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    navController: NavController,
    viewModel: CartViewModel = hiltViewModel()
) {
    val cart by viewModel.cart.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Shopping Cart") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
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
                        style = MaterialTheme.typography.titleMedium
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
                    modifier = Modifier.fillMaxSize()
                ) {
                    LazyColumn(
                        modifier = Modifier.weight(1f)
                    ) {
                        items(cart.items) { cartItem ->
                            CartItemRow(
                                cartItem = cartItem,
                                onQuantityIncrease = {
                                    viewModel.updateQuantity(cartItem.product.id, cartItem.quantity + 1)
                                },
                                onQuantityDecrease = {
                                    if (cartItem.quantity > 1) {
                                        viewModel.updateQuantity(cartItem.product.id, cartItem.quantity - 1)
                                    } else {
                                        viewModel.removeFromCart(cartItem.product.id)
                                    }
                                },
                                onRemove = { viewModel.removeFromCart(cartItem.product.id, cartItem.quantity) }
                            )

                            Divider()
                        }
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth(),
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Total:",
                                    style = MaterialTheme.typography.titleMedium
                                )

                                Text(
                                    text = NumberFormat.getCurrencyInstance(Locale.US)
                                        .format(cart.totalPrice / 100.0),
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Button(
                                onClick = { navController.navigate(Screen.Checkout.route) },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("PROCEED TO CHECKOUT")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CartItemRow(
    cartItem: CartItem,
    onQuantityIncrease: () -> Unit,
    onQuantityDecrease: () -> Unit,
    onRemove: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = cartItem.product.imageUrl,
            contentDescription = cartItem.product.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(80.dp)
                .padding(end = 16.dp)
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = cartItem.product.name,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = NumberFormat.getCurrencyInstance(Locale.US)
                    .format(cartItem.product.price / 100.0),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onQuantityDecrease,
                    modifier = Modifier.size(24.dp)
                ) {
                    Text(
                        text = "−",
                        textAlign = TextAlign.Center
                    )
                }

                Text(
                    text = cartItem.quantity.toString(),
                    modifier = Modifier.width(24.dp),
                    textAlign = TextAlign.Center
                )

                IconButton(
                    onClick = onQuantityIncrease,
                    modifier = Modifier.size(24.dp)
                ) {
                    Text(
                        text = "+",
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        IconButton(onClick = onRemove) {
            Icon(
                Icons.Default.Delete,
                contentDescription = "Remove",
                tint = MaterialTheme.colorScheme.error
            )
        }
    }
}