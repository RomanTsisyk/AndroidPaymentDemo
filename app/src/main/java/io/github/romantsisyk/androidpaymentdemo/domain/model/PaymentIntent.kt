package io.github.romantsisyk.androidpaymentdemo.domain.model

data class PaymentIntent(
    val id: String,
    val clientSecret: String,
    val amount: Long,
    val currency: String
)