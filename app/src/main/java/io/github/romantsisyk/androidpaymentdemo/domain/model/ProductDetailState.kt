package io.github.romantsisyk.androidpaymentdemo.domain.model

data class ProductDetailState(
    val isLoading: Boolean = false,
    val product: Product? = null,
    val error: String = ""
)