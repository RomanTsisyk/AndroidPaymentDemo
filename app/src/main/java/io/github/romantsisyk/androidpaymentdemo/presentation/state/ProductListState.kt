package io.github.romantsisyk.androidpaymentdemo.presentation.state

import io.github.romantsisyk.androidpaymentdemo.domain.model.Product

data class ProductListState(
    val isLoading: Boolean = false,
    val products: List<Product> = emptyList(),
    val error: String = ""
)