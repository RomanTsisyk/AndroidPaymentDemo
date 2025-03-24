package io.github.romantsisyk.androidpaymentdemo.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.github.romantsisyk.androidpaymentdemo.presentation.components.LuxuryButton
import io.github.romantsisyk.androidpaymentdemo.presentation.components.OutlinedLuxuryButton
import io.github.romantsisyk.androidpaymentdemo.presentation.theme.LuxuryTheme
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(
    onNavigateBack: () -> Unit,
    onPaymentComplete: () -> Unit
) {
    var cardNumber by remember { mutableStateOf("") }
    var cardHolder by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }

    var isLoading by remember { mutableStateOf(false) }
    var isPaymentSuccess by remember { mutableStateOf(false) }

    val cardNumberFocusRequester = remember { FocusRequester() }

    LaunchedEffect(key1 = true) {
        delay(300)
        cardNumberFocusRequester.requestFocus()
    }

    // Add LaunchedEffect for payment processing
    LaunchedEffect(isLoading) {
        if (isLoading) {
            delay(2000)
            isLoading = false
            isPaymentSuccess = true
            delay(1500)
            onPaymentComplete()
        }
    }

    val scrollState = rememberScrollState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Оплата",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Назад"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground
                ),
                modifier = Modifier.statusBarsPadding()
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Основний контент
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(horizontal = LuxuryTheme.spacing.medium.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(LuxuryTheme.spacing.medium.dp))

                // Анімована кредитна картка
                PaymentCard(
                    cardNumber = cardNumber,
                    cardHolder = cardHolder,
                    expiryDate = expiryDate
                )

                Spacer(modifier = Modifier.height(LuxuryTheme.spacing.large.dp))

                // Поля для вводу даних
                OutlinedTextField(
                    value = cardNumber,
                    onValueChange = {
                        if (it.length <= 19) {
                            cardNumber = formatCardNumber(it.filter { char -> char.isDigit() })
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(cardNumberFocusRequester),
                    label = { Text("Номер картки") },
                    placeholder = { Text("1234 5678 9012 3456") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    shape = RoundedCornerShape(LuxuryTheme.shapes.medium.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                        focusedIndicatorColor = MaterialTheme.colorScheme.primary
                    )
                )

                Spacer(modifier = Modifier.height(LuxuryTheme.spacing.medium.dp))

                OutlinedTextField(
                    value = cardHolder,
                    onValueChange = { cardHolder = it.uppercase() },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Власник картки") },
                    placeholder = { Text("ІМ'Я ПРІЗВИЩЕ") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    shape = RoundedCornerShape(LuxuryTheme.shapes.medium.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                        focusedIndicatorColor = MaterialTheme.colorScheme.primary
                    )
                )

                Spacer(modifier = Modifier.height(LuxuryTheme.spacing.medium.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(LuxuryTheme.spacing.medium.dp)
                ) {
                    OutlinedTextField(
                        value = expiryDate,
                        onValueChange = {
                            if (it.length <= 5) {
                                expiryDate = formatExpiryDate(it.filter { char -> char.isDigit() })
                            }
                        },
                        modifier = Modifier.weight(1f),
                        label = { Text("Термін дії") },
                        placeholder = { Text("ММ/РР") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        shape = RoundedCornerShape(LuxuryTheme.shapes.medium.dp),
                        colors = TextFieldDefaults.colors(
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                            focusedIndicatorColor = MaterialTheme.colorScheme.primary
                        )
                    )

                    OutlinedTextField(
                        value = cvv,
                        onValueChange = {
                            if (it.length <= 3 && it.all { char -> char.isDigit() }) {
                                cvv = it
                            }
                        },
                        modifier = Modifier.weight(1f),
                        label = { Text("CVV") },
                        placeholder = { Text("123") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.NumberPassword,
                            imeAction = ImeAction.Next
                        ),
                        shape = RoundedCornerShape(LuxuryTheme.shapes.medium.dp),
                        colors = TextFieldDefaults.colors(
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                            focusedIndicatorColor = MaterialTheme.colorScheme.primary
                        )
                    )
                }

                Spacer(modifier = Modifier.height(LuxuryTheme.spacing.medium.dp))

                OutlinedTextField(
                    value = amount,
                    onValueChange = {
                        if (it.isEmpty() || it.matches(Regex("^\\d+(\\.\\d{0,2})?\$"))) {
                            amount = it
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Сума платежу") },
                    placeholder = { Text("0.00") },
                    leadingIcon = {
                        Text(
                            text = "₴",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Done
                    ),
                    shape = RoundedCornerShape(LuxuryTheme.shapes.medium.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                        focusedIndicatorColor = MaterialTheme.colorScheme.primary
                    )
                )

                Spacer(modifier = Modifier.height(LuxuryTheme.spacing.large.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(LuxuryTheme.spacing.medium.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                        modifier = Modifier.size(20.dp)
                    )

                    Text(
                        text = "Безпечно оплачуйте через наш платіжний шлюз. Ваші дані захищені за стандартами PCI DSS.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                    )
                }

                Spacer(modifier = Modifier.height(LuxuryTheme.spacing.large.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(LuxuryTheme.spacing.medium.dp)
                ) {
                    OutlinedLuxuryButton(
                        text = "Скасувати",
                        onClick = onNavigateBack,
                        modifier = Modifier.weight(1f)
                    )

                    LuxuryButton(
                        text = "Оплатити",
                        onClick = { isLoading = true },
                        modifier = Modifier.weight(1f),
                        enabled = cardNumber.length == 19 &&
                                cardHolder.isNotBlank() &&
                                expiryDate.length == 5 &&
                                cvv.length == 3 &&
                                amount.isNotBlank() &&
                                !isLoading
                    )
                }

                Spacer(modifier = Modifier.height(LuxuryTheme.spacing.extraLarge.dp))
            }

            // Індикатор завантаження
            AnimatedVisibility(
                visible = isLoading,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.5f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(64.dp)
                    )
                }
            }

            // Сповіщення про успішний платіж
            AnimatedVisibility(
                visible = isPaymentSuccess,
                enter = fadeIn() + expandVertically(
                    expandFrom = Alignment.CenterVertically
                ),
                exit = fadeOut()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.7f)),
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        modifier = Modifier
                            .padding(LuxuryTheme.spacing.large.dp)
                            .shadow(
                                elevation = 8.dp,
                                shape = RoundedCornerShape(LuxuryTheme.shapes.large.dp)
                            ),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        shape = RoundedCornerShape(LuxuryTheme.shapes.large.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(LuxuryTheme.spacing.large.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(80.dp)
                                    .background(
                                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                        shape = CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(48.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(LuxuryTheme.spacing.medium.dp))

                            Text(
                                text = "Успішно оплачено!",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            Spacer(modifier = Modifier.height(LuxuryTheme.spacing.small.dp))

                            Text(
                                text = "Ваш платіж на суму ${amount} грн успішно оброблено.",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(LuxuryTheme.spacing.large.dp))

                            LuxuryButton(
                                text = "Готово",
                                onClick = onPaymentComplete,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PaymentCard(
    cardNumber: String,
    cardHolder: String,
    expiryDate: String,
    modifier: Modifier = Modifier
) {
    val cardNumberDisplay = if (cardNumber.isEmpty()) "•••• •••• •••• ••••" else cardNumber
    val cardHolderDisplay = if (cardHolder.isEmpty()) "ІМ'Я ПРІЗВИЩЕ" else cardHolder
    val expiryDateDisplay = if (expiryDate.isEmpty()) "MM/YY" else expiryDate

    // Анімація при зміні даних картки
    val scale by animateFloatAsState(
        targetValue = if (cardNumber.isEmpty() && cardHolder.isEmpty() && expiryDate.isEmpty()) 1f else 1.02f,
        animationSpec = spring(dampingRatio = 0.6f, stiffness = 100f),
        label = "card scale"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .graphicsLayer {
                this.scaleX = scale
                this.scaleY = scale
            }
            .shadow(
                elevation = LuxuryTheme.elevations.large.dp,
                shape = RoundedCornerShape(LuxuryTheme.shapes.large.dp),
                spotColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
            ),
        shape = RoundedCornerShape(LuxuryTheme.shapes.large.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                            MaterialTheme.colorScheme.tertiary
                        )
                    )
                )
                .padding(LuxuryTheme.spacing.large.dp)
        ) {
            // Логотип банку/карти
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopStart),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "LUXURY CARD",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }

            // Номер картки
            Text(
                text = cardNumberDisplay,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.align(Alignment.Center)
            )

            // Дані власника та термін дії
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Column {
                    Text(
                        text = "CARD HOLDER",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White.copy(alpha = 0.7f)
                    )

                    Text(
                        text = cardHolderDisplay,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }

                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "EXPIRES",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White.copy(alpha = 0.7f)
                    )

                    Text(
                        text = expiryDateDisplay,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }
            }
        }
    }
}

// Функція для форматування номера картки (додаємо пробіли)
private fun formatCardNumber(cardNumber: String): String {
    val formattedNumber = StringBuilder()
    cardNumber.forEachIndexed { index, char ->
        if (index > 0 && index % 4 == 0) {
            formattedNumber.append(" ")
        }
        formattedNumber.append(char)
    }
    return formattedNumber.toString()
}

// Функція для форматування терміну дії (додаємо /)
private fun formatExpiryDate(expiryDate: String): String {
    val formattedDate = StringBuilder()
    expiryDate.forEachIndexed { index, char ->
        if (index == 2 && index < expiryDate.length) {
            formattedDate.append("/")
        }
        formattedDate.append(char)
    }
    return formattedDate.toString()
}