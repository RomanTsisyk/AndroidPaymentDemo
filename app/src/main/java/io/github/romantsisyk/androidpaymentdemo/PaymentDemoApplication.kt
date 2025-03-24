package io.github.romantsisyk.androidpaymentdemo

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import dagger.hilt.android.HiltAndroidApp
import io.github.romantsisyk.androidpaymentdemo.util.LocaleHelper
import timber.log.Timber
import java.util.Locale

@HiltAndroidApp
class PaymentDemoApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Налаштування Timber для логування
        Timber.plant(Timber.DebugTree())

        // Встановлення англійської мови за замовчуванням
        setDefaultLanguage()
    }

    private fun setDefaultLanguage() {
        try {
            // Forced setting of English as the default language
            val locale = Locale("en")
            Locale.setDefault(locale)

            val config = Configuration(resources.configuration)
            config.setLocale(locale)

            createConfigurationContext(config)
            resources.updateConfiguration(config, resources.displayMetrics)

            Timber.d("Default language set to English")
        } catch (e: Exception) {
            Timber.e(e, "Error setting default language")
        }
    }

    override fun attachBaseContext(base: Context) {
        // Override saved locale with English for first launch
        val context = LocaleHelper.setLocale(base, "en")
        super.attachBaseContext(context)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        // Reset to English on configuration change unless explicitly changed by user
        LocaleHelper.onAttach(this)
    }
}