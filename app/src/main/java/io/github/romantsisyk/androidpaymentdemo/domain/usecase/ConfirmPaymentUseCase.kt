package io.github.romantsisyk.androidpaymentdemo.domain.usecase

import io.github.romantsisyk.androidpaymentdemo.domain.repository.PaymentRepository
import io.github.romantsisyk.androidpaymentdemo.domain.util.Resource
import javax.inject.Inject

class ConfirmPaymentUseCase @Inject constructor(
    private val paymentRepository: PaymentRepository
) {
    suspend operator fun invoke(paymentIntentId: String): Resource<Boolean> {
        return paymentRepository.confirmPayment(paymentIntentId)
    }
}