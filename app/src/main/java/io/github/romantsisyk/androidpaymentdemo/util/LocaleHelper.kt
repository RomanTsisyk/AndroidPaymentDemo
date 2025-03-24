package io.github.romantsisyk.androidpaymentdemo.util

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.preference.PreferenceManager
import java.util.Locale

object LocaleHelper {
    private const val SELECTED_LANGUAGE = "Locale.Helper.Selected.Language"
    private const val IS_FIRST_LAUNCH = "Locale.Helper.First.Launch"

    // Доступні мови
    const val LANGUAGE_ENGLISH = "en"
    const val LANGUAGE_UKRAINIAN = "uk"
    const val LANGUAGE_POLISH = "pl"

    // Отримати поточну локаль
    fun getLocale(context: Context): Locale {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.resources.configuration.locales.get(0)
        } else {
            context.resources.configuration.locale
        }
    }

    // Зберегти вибрану мову
    fun setLocale(context: Context, language: String): Context {
        persist(context, language)
        return updateResources(context, language)
    }

    // Ініціалізація з збереженою мовою або за замовчуванням
    fun onAttach(context: Context): Context {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val isFirstLaunch = preferences.getBoolean(IS_FIRST_LAUNCH, true)

        // При першому запуску встановлюємо англійську
        if (isFirstLaunch) {
            preferences.edit().putBoolean(IS_FIRST_LAUNCH, false).apply()
            return setLocale(context, LANGUAGE_ENGLISH)
        }

        val lang = getPersistedData(context, LANGUAGE_ENGLISH)
        return setLocale(context, lang)
    }

    // Збереження вибраної мови в налаштуваннях
    private fun persist(context: Context, language: String) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = preferences.edit()
        editor.putString(SELECTED_LANGUAGE, language)
        editor.apply()
    }

    // Отримання збереженої мови з налаштувань
    private fun getPersistedData(context: Context, defaultLanguage: String): String {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        return preferences.getString(SELECTED_LANGUAGE, defaultLanguage) ?: defaultLanguage
    }

    // Оновлення ресурсів з новою локаллю
    private fun updateResources(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val config = Configuration(context.resources.configuration)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.setLocale(locale)
            return context.createConfigurationContext(config)
        } else {
            config.locale = locale
            context.resources.updateConfiguration(config, context.resources.displayMetrics)
            return context
        }
    }

    // Отримати доступні мови з їх локалізованими назвами
    fun getAvailableLanguages(context: Context): Map<String, String> {
        return mapOf(
            LANGUAGE_ENGLISH to "English",
            LANGUAGE_UKRAINIAN to "Українська",
            LANGUAGE_POLISH to "Polski"
        )
    }

    // Ця функція примусово скидає налаштування локалі до стандартних
    fun resetToDefaultLanguage(context: Context): Context {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        preferences.edit().remove(SELECTED_LANGUAGE).apply()
        preferences.edit().putBoolean(IS_FIRST_LAUNCH, true).apply()

        return setLocale(context, LANGUAGE_ENGLISH)
    }
}