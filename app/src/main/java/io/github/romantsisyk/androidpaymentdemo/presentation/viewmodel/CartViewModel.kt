package io.github.romantsisyk.androidpaymentdemo.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.romantsisyk.androidpaymentdemo.domain.model.Cart
import io.github.romantsisyk.androidpaymentdemo.domain.model.Product
import io.github.romantsisyk.androidpaymentdemo.domain.usecase.AddToCartUseCase
import io.github.romantsisyk.androidpaymentdemo.domain.usecase.ClearCartUseCase
import io.github.romantsisyk.androidpaymentdemo.domain.usecase.GetCartUseCase
import io.github.romantsisyk.androidpaymentdemo.domain.usecase.RemoveFromCartUseCase
import io.github.romantsisyk.androidpaymentdemo.domain.usecase.UpdateCartItemUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartUseCase: GetCartUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val updateCartItemUseCase: UpdateCartItemUseCase,
    private val removeFromCartUseCase: RemoveFromCartUseCase,
    private val clearCartUseCase: ClearCartUseCase
) : ViewModel() {

    val cart: StateFlow<Cart> = getCartUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = Cart()
    )

    fun addToCart(product: Product, quantity: Int = 1) {
        viewModelScope.launch {
            addToCartUseCase(product, quantity)
        }
    }

    fun updateQuantity(productId: String, quantity: Int) {
        viewModelScope.launch {
            updateCartItemUseCase(productId, quantity)
        }
    }

    fun removeFromCart(productId: String, quantity: Int = 1) {
        viewModelScope.launch {
            removeFromCartUseCase(productId, quantity)
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            clearCartUseCase()
        }
    }
}