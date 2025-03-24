package io.github.romantsisyk.androidpaymentdemo.presentation.screens

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.github.romantsisyk.androidpaymentdemo.MainActivity
import io.github.romantsisyk.androidpaymentdemo.R
import io.github.romantsisyk.androidpaymentdemo.presentation.components.LuxuryButton
import io.github.romantsisyk.androidpaymentdemo.presentation.theme.LuxuryTheme
import io.github.romantsisyk.androidpaymentdemo.util.LocaleHelper
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageSettingsScreen(navController: NavController) {
    val context = LocalContext.current

    // Відображаємо поточну мову та оновлюємо її при зміні
    var currentLocale by remember { mutableStateOf(LocaleHelper.getLocale(context).language) }
    val languages = LocaleHelper.getAvailableLanguages(context)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Language / Мова / Język") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn {
                items(languages.entries.toList()) { (code, name) ->
                    ListItem(
                        headlineContent = { Text(name) },
                        trailingContent = {
                            if (currentLocale == code) {
                                Icon(Icons.Default.Check, contentDescription = "Selected")
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                if (currentLocale != code) {
                                    try {
                                        // Зміна мови
                                        LocaleHelper.setLocale(context, code)
                                        currentLocale = code

                                        // Перезапуск активності для застосування змін
                                        val intent = Intent(context, MainActivity::class.java)
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                                        context.startActivity(intent)
                                        (context as? Activity)?.finishAffinity()
                                    } catch (e: Exception) {
                                        Timber.e(e, "Error changing language to $code")
                                    }
                                }
                            }
                            .padding(vertical = 8.dp)
                    )
                    HorizontalDivider()
                }

                item {
                    Spacer(modifier = Modifier.height(32.dp))

                    // Кнопка для скидання мови до англійської з локалізованим текстом
                    LuxuryButton(
                        text = stringResource(R.string.action_reset_language),
                        onClick = {
                            try {
                                // Скидання до англійської мови
                                LocaleHelper.resetToDefaultLanguage(context)

                                // Перезапуск активності
                                val intent = Intent(context, MainActivity::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                                context.startActivity(intent)
                                (context as? Activity)?.finishAffinity()
                            } catch (e: Exception) {
                                Timber.e(e, "Error resetting language")
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = LuxuryTheme.spacing.large.dp)
                    )
                }
            }
        }
    }
}