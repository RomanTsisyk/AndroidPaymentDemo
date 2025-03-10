package io.github.romantsisyk.androidpaymentdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.stripe.android.paymentsheet.PaymentSheetResult
import dagger.hilt.android.AndroidEntryPoint
import io.github.romantsisyk.androidpaymentdemo.data.manager.PaymentInitializer
import io.github.romantsisyk.androidpaymentdemo.presentation.navigation.AppNavigation
import io.github.romantsisyk.androidpaymentdemo.presentation.theme.AndroidPaymentDemoTheme
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var paymentInitializer: PaymentInitializer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        paymentInitializer.initializeStripe(applicationContext)
        paymentInitializer.initializePaymentSheet(this) { result ->
            handlePaymentResult(result)
        }

        setContent {
            AndroidPaymentDemoTheme {
                val navController = rememberNavController()
                AppNavigation(navController)
            }
        }
    }

    private fun handlePaymentResult(result: PaymentSheetResult) {
        when (result) {
            is PaymentSheetResult.Completed -> {
                Timber.d("Payment completed successfully")
            }
            is PaymentSheetResult.Canceled -> {
                Timber.d("Payment canceled")
            }
            is PaymentSheetResult.Failed -> {
                Timber.e("Payment failed: ${result.error}")
            }
        }
    }
}