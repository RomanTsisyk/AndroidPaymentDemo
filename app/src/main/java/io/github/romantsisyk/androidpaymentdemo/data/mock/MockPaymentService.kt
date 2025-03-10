package io.github.romantsisyk.androidpaymentdemo.data.mock

import io.github.romantsisyk.androidpaymentdemo.domain.model.PaymentIntent
import kotlinx.coroutines.delay
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

@Singleton
class MockPaymentService @Inject constructor() {

    suspend fun createPaymentIntent(amount: Long, currency: String): PaymentIntent {
        delay(1000)

        val id = "pi_" + generateRandomString(24)
        val clientSecret = "pi_" + generateRandomString(24) + "_secret_" + generateRandomString(24)

        Timber.d("Created payment intent with ID: $id and amount: $amount $currency")

        return PaymentIntent(
            id = id,
            clientSecret = clientSecret,
            amount = amount,
            currency = currency
        )
    }

    suspend fun confirmPayment(paymentIntentId: String): Boolean {
        delay(1200)

        val isSuccessful = Random.nextDouble() < 0.9

        Timber.d("Payment confirmation for $paymentIntentId: ${if (isSuccessful) "Success" else "Failed"}")

        return isSuccessful
    }

    private fun generateRandomString(length: Int): String {
        val charset = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        return (1..length)
            .map { charset.random() }
            .joinToString("")
    }
}