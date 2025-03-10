package io.github.romantsisyk.androidpaymentdemo.data.manager

import android.content.Context
import androidx.activity.ComponentActivity
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import io.github.romantsisyk.androidpaymentdemo.data.manager.StripePaymentManager.Companion.PUBLISHABLE_KEY
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PaymentInitializer @Inject constructor() {

    private var paymentSheet: PaymentSheet? = null

    private var paymentResultCallback: ((PaymentSheetResult) -> Unit)? = null

    fun initializeStripe(context: Context) {
        PaymentConfiguration.init(context, PUBLISHABLE_KEY)
        Timber.d("Stripe SDK initialized with publishable key")
    }

    fun initializePaymentSheet(
        activity: ComponentActivity,
        callback: (PaymentSheetResult) -> Unit
    ) {
        try {
            paymentResultCallback = callback

            paymentSheet = PaymentSheet(activity) { result ->
                paymentResultCallback?.invoke(result)
            }

            Timber.d("Payment Sheet initialized successfully in Activity onCreate")
        } catch (e: Exception) {
            Timber.e(e, "Failed to initialize Payment Sheet")
        }
    }

    fun presentPaymentSheet(
        clientSecret: String,
        merchantName: String = "Android Payment Demo"
    ) {
        val sheet = paymentSheet
        if (sheet == null) {
            Timber.e("Cannot present Payment Sheet - not initialized")
            return
        }

        try {
            val configuration = PaymentSheet.Configuration(
                merchantDisplayName = merchantName,
                allowsDelayedPaymentMethods = true
            )

            sheet.presentWithPaymentIntent(
                paymentIntentClientSecret = clientSecret,
                configuration = configuration
            )

            Timber.d("Presented payment sheet with client secret")
        } catch (e: Exception) {
            Timber.e(e, "Error presenting Payment Sheet")
        }
    }
}