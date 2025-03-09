package io.github.romantsisyk.androidpaymentdemo.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.romantsisyk.androidpaymentdemo.domain.model.ProductDetailState
import io.github.romantsisyk.androidpaymentdemo.domain.usecase.GetProductByIdUseCase
import io.github.romantsisyk.androidpaymentdemo.domain.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val getProductByIdUseCase: GetProductByIdUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(ProductDetailState())
    val state: StateFlow<ProductDetailState> = _state

    init {
        savedStateHandle.get<String>("productId")?.let { productId ->
            getProduct(productId)
        }
    }

    private fun getProduct(productId: String) {
        viewModelScope.launch {
            _state.value = ProductDetailState(isLoading = true)

            when (val result = getProductByIdUseCase(productId)) {
                is Resource.Success -> {
                    _state.value = ProductDetailState(product = result.data)
                }
                is Resource.Error -> {
                    _state.value = ProductDetailState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _state.value = ProductDetailState(isLoading = true)
                }
            }
        }
    }
}