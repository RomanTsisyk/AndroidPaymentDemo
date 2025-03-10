package io.github.romantsisyk.androidpaymentdemo.domain.usecase

import io.github.romantsisyk.androidpaymentdemo.domain.model.PaymentIntent
import io.github.romantsisyk.androidpaymentdemo.domain.repository.PaymentRepository
import io.github.romantsisyk.androidpaymentdemo.domain.util.Resource
import javax.inject.Inject

class CreatePaymentIntentUseCase @Inject constructor(
    private val paymentRepository: PaymentRepository
) {
    suspend operator fun invoke(amount: Long, currency: String = "usd"): Resource<PaymentIntent> {
        return paymentRepository.createPaymentIntent(amount, currency)
    }
}