package io.github.romantsisyk.androidpaymentdemo.data.repository

import io.github.romantsisyk.androidpaymentdemo.domain.model.PaymentIntent
import io.github.romantsisyk.androidpaymentdemo.domain.repository.PaymentRepository
import io.github.romantsisyk.androidpaymentdemo.domain.util.Resource
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PaymentRepositoryImpl @Inject constructor() : PaymentRepository {

    override suspend fun createPaymentIntent(
        amount: Long,
        currency: String
    ): Resource<PaymentIntent> {
        return try {
            val mockId = "pi_${System.currentTimeMillis()}"
            val mockClientSecret = "${mockId}_secret_${System.currentTimeMillis()}"
            val paymentIntent = PaymentIntent(
                id = mockId,
                clientSecret = mockClientSecret,
                amount = amount,
                currency = currency
            )

            Timber.d("Created payment intent: $mockId with client secret: $mockClientSecret")
            Resource.Success(paymentIntent)
        } catch (e: Exception) {
            Timber.e(e, "Error creating payment intent")
            Resource.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun confirmPayment(paymentIntentId: String): Resource<Boolean> {
        return try {
            Timber.d("Confirmed payment intent: $paymentIntentId")
            Resource.Success(true)
        } catch (e: Exception) {
            Timber.e(e, "Error confirming payment")
            Resource.Error(e.message ?: "Unknown error occurred")
        }
    }
}