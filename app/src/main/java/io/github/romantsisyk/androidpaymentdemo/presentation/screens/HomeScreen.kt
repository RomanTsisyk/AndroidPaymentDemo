package io.github.romantsisyk.androidpaymentdemo.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.github.romantsisyk.androidpaymentdemo.R
import io.github.romantsisyk.androidpaymentdemo.presentation.components.FavoriteButton
import io.github.romantsisyk.androidpaymentdemo.presentation.components.GradientCard
import io.github.romantsisyk.androidpaymentdemo.presentation.components.LuxuryButton
import io.github.romantsisyk.androidpaymentdemo.presentation.components.LuxuryCard
import io.github.romantsisyk.androidpaymentdemo.presentation.components.LuxurySearchBar
import io.github.romantsisyk.androidpaymentdemo.presentation.theme.LuxuryTheme
import kotlinx.coroutines.delay

data class BottomNavItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

@Composable
fun HomeScreen(
    onNavigateToPayment: () -> Unit,
    onNavigateToLanguageSettings: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedTab by remember { mutableIntStateOf(0) }

    // Стан для контролю анімацій появи елементів
    val featuredItemsVisible = remember { MutableTransitionState(false) }
    val popularItemsVisible = remember { MutableTransitionState(false) }
    val recommendedItemsVisible = remember { MutableTransitionState(false) }

    // Активуємо анімації послідовно
    LaunchedEffect(Unit) {
        featuredItemsVisible.targetState = true
        delay(200)
        popularItemsVisible.targetState = true
        delay(200)
        recommendedItemsVisible.targetState = true
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(top = LuxuryTheme.spacing.medium.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = LuxuryTheme.spacing.medium.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.app_name),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Row {
                        // Додаємо кнопку мови
                        IconButton(onClick = { onNavigateToLanguageSettings() }) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = stringResource(R.string.action_language_settings),
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(LuxuryTheme.spacing.small.dp))

                        Surface(
                            modifier = Modifier.size(48.dp),
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.surface,
                            shadowElevation = LuxuryTheme.elevations.small.dp
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = stringResource(R.string.nav_profile),
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(LuxuryTheme.spacing.medium.dp))

                LuxurySearchBar(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    hint = stringResource(R.string.search_hint)
                )
            }
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier
                    .shadow(
                        elevation = LuxuryTheme.elevations.large.dp,
                        shape = RoundedCornerShape(
                            topStart = LuxuryTheme.shapes.large.dp,
                            topEnd = LuxuryTheme.shapes.large.dp
                        )
                    ),
                containerColor = MaterialTheme.colorScheme.surface,
                contentPadding = PaddingValues(horizontal = LuxuryTheme.spacing.medium.dp),
                tonalElevation = 0.dp
            ) {
                val items = listOf(
                    BottomNavItem(
                        title = stringResource(R.string.nav_home),
                        selectedIcon = Icons.Filled.Home,
                        unselectedIcon = Icons.Outlined.Home
                    ),
                    BottomNavItem(
                        title = stringResource(R.string.nav_explore),
                        selectedIcon = Icons.Filled.Search,
                        unselectedIcon = Icons.Outlined.Search
                    ),
                    BottomNavItem(
                        title = stringResource(id = R.string.action_add),
                        selectedIcon = Icons.Filled.ShoppingCart,
                        unselectedIcon = Icons.Outlined.ShoppingCart
                    ),
                    BottomNavItem(
                        title = stringResource(R.string.nav_profile),
                        selectedIcon = Icons.Filled.Person,
                        unselectedIcon = Icons.Outlined.Person
                    )
                )

                items.forEachIndexed { index, item ->
                    val selected = index == selectedTab
                    NavigationBarItem(
                        selected = selected,
                        onClick = { selectedTab = index },
                        icon = {
                            Icon(
                                imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                                contentDescription = item.title,
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        label = {
                            Text(
                                text = item.title,
                                style = MaterialTheme.typography.labelMedium
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.surface
                        )
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onNavigateToPayment() },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                shape = CircleShape,
                modifier = Modifier.shadow(
                    elevation = LuxuryTheme.elevations.large.dp,
                    shape = CircleShape,
                    spotColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.action_add),
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(LuxuryTheme.spacing.medium.dp),
            contentPadding = PaddingValues(
                top = LuxuryTheme.spacing.medium.dp,
                bottom = LuxuryTheme.spacing.large.dp
            )
        ) {
            // Секція головних карток
            item {
                AnimatedVisibility(
                    visibleState = featuredItemsVisible,
                    enter = fadeIn(spring(stiffness = Spring.StiffnessLow)) +
                            slideInVertically(
                                initialOffsetY = { it / 2 },
                                animationSpec = spring(stiffness = Spring.StiffnessLow)
                            ),
                    exit = fadeOut()
                ) {
                    FeaturedItemsSection(onNavigateToPayment)
                }
            }

            // Секція популярних платежів
            item {
                AnimatedVisibility(
                    visibleState = popularItemsVisible,
                    enter = fadeIn(spring(stiffness = Spring.StiffnessLow)) +
                            slideInVertically(
                                initialOffsetY = { it / 2 },
                                animationSpec = spring(stiffness = Spring.StiffnessLow)
                            ),
                    exit = fadeOut()
                ) {
                    PopularPaymentsSection()
                }
            }

            // Секція рекомендованих платежів
            item {
                AnimatedVisibility(
                    visibleState = recommendedItemsVisible,
                    enter = fadeIn(spring(stiffness = Spring.StiffnessLow)) +
                            slideInVertically(
                                initialOffsetY = { it / 2 },
                                animationSpec = spring(stiffness = Spring.StiffnessLow)
                            ),
                    exit = fadeOut()
                ) {
                    RecommendedSection(onNavigateToPayment)
                }
            }

            // Кнопка для нового платежу
            item {
                Spacer(modifier = Modifier.height(LuxuryTheme.spacing.medium.dp))

                PaymentButton(onNavigateToPayment)

                Spacer(modifier = Modifier.height(LuxuryTheme.spacing.extraLarge.dp))
            }
        }
    }
}

@Composable
fun FeaturedItemsSection(onNavigateToPayment: () -> Unit) {

    val gradients = listOf(
        LuxuryTheme.gradients.primaryGradient,
        LuxuryTheme.gradients.purpleGradient,
        LuxuryTheme.gradients.blueGradient,
        LuxuryTheme.gradients.orangeGradient
    )

    val featuredItems = listOf(
        stringResource(R.string.featured_premium_card_title) to stringResource(R.string.featured_premium_card_desc),
        stringResource(R.string.featured_fast_transfers_title) to stringResource(R.string.featured_fast_transfers_desc),
        stringResource(R.string.featured_investments_title) to stringResource(R.string.featured_investments_desc),
        stringResource(R.string.featured_credit_line_title) to stringResource(R.string.featured_credit_line_desc)
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = LuxuryTheme.spacing.medium.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.header_featured),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = stringResource(R.string.action_see_all),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.height(LuxuryTheme.spacing.small.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(LuxuryTheme.spacing.small.dp),
            contentPadding = PaddingValues(horizontal = LuxuryTheme.spacing.medium.dp)
        ) {
            // Використовуємо локалізовані рядки для елементів


            itemsIndexed(featuredItems) { index, (title, subtitle) ->
                GradientCard(
                    title = title,
                    subtitle = subtitle,
                    onClick = { onNavigateToPayment() },
                    modifier = Modifier.width(300.dp),
                    gradient = gradients[index % gradients.size]
                )
            }
        }
    }
}

@Composable
fun PopularPaymentsSection() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.height(LuxuryTheme.spacing.medium.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = LuxuryTheme.spacing.medium.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.header_popular),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = stringResource(R.string.action_see_all),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.height(LuxuryTheme.spacing.small.dp))

        // Використовуємо локалізовані рядки для популярних платежів
        val popularItems = listOf(
            stringResource(R.string.popular_utilities_title) to stringResource(R.string.popular_utilities_desc),
            stringResource(R.string.popular_mobile_title) to stringResource(R.string.popular_mobile_desc),
            stringResource(R.string.popular_internet_title) to stringResource(R.string.popular_internet_desc),
            stringResource(R.string.popular_insurance_title) to stringResource(R.string.popular_insurance_desc)
        )

        popularItems.forEach { (title, subtitle) ->
            var isFavorite by remember { mutableStateOf(false) }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = LuxuryTheme.spacing.medium.dp,
                        vertical = LuxuryTheme.spacing.small.dp
                    ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = LuxuryTheme.elevations.small.dp
                ),
                shape = RoundedCornerShape(LuxuryTheme.shapes.medium.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(LuxuryTheme.spacing.medium.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = subtitle,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                    }

                    FavoriteButton(
                        isFavorite = isFavorite,
                        onClick = { isFavorite = !isFavorite }
                    )
                }
            }
        }
    }
}

@Composable
fun RecommendedSection(onNavigateToPayment: () -> Unit) {
    val recommendedItems = listOf(
        stringResource(R.string.recommended_charity_title) to stringResource(R.string.recommended_charity_desc),
        stringResource(R.string.recommended_entertainment_title) to stringResource(R.string.recommended_entertainment_desc),
        stringResource(R.string.recommended_taxi_title) to stringResource(R.string.recommended_taxi_desc),
        stringResource(R.string.recommended_restaurants_title) to stringResource(R.string.recommended_restaurants_desc)
    )
    Column(modifier = Modifier.fillMaxWidth()) {


        Spacer(modifier = Modifier.height(LuxuryTheme.spacing.medium.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = LuxuryTheme.spacing.medium.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.header_recommended),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = stringResource(R.string.action_see_all),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.height(LuxuryTheme.spacing.small.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(LuxuryTheme.spacing.small.dp),
            contentPadding = PaddingValues(horizontal = LuxuryTheme.spacing.medium.dp)
        ) {


            items(recommendedItems) { (title, subtitle) ->
                LuxuryCard(
                    title = title,
                    subtitle = subtitle,
                    onClick = { onNavigateToPayment() },
                    modifier = Modifier.width(220.dp)
                )
            }
        }
    }
}

@Composable
fun PaymentButton(onNavigateToPayment: () -> Unit) {
    LuxuryButton(
        text = stringResource(id = R.string.action_add),
        onClick = { onNavigateToPayment() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = LuxuryTheme.spacing.large.dp)
    )
}