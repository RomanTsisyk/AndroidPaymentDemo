package io.github.romantsisyk.androidpaymentdemo.domain.repository

import io.github.romantsisyk.androidpaymentdemo.domain.model.PaymentIntent
import io.github.romantsisyk.androidpaymentdemo.domain.util.Resource

interface PaymentRepository {
    suspend fun createPaymentIntent(amount: Long, currency: String): Resource<PaymentIntent>
    suspend fun confirmPayment(paymentIntentId: String): Resource<Boolean>
}