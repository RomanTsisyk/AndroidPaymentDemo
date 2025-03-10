package io.github.romantsisyk.androidpaymentdemo.presentation.state

sealed class PaymentState {
    data object Initial : PaymentState()
    data object Loading : PaymentState()
    data class PaymentReady(val paymentIntentId: String) : PaymentState()
    data object Processing : PaymentState()
    data object Success : PaymentState()
    data class Error(val message: String) : PaymentState()
}