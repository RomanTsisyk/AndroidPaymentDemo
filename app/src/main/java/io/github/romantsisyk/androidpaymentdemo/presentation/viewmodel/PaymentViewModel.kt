package io.github.romantsisyk.androidpaymentdemo.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.romantsisyk.androidpaymentdemo.data.manager.PaymentInitializer
import io.github.romantsisyk.androidpaymentdemo.data.manager.StripePaymentManager
import io.github.romantsisyk.androidpaymentdemo.domain.model.PaymentIntent
import io.github.romantsisyk.androidpaymentdemo.domain.usecase.ConfirmPaymentUseCase
import io.github.romantsisyk.androidpaymentdemo.domain.usecase.CreatePaymentIntentUseCase
import io.github.romantsisyk.androidpaymentdemo.domain.util.Resource
import io.github.romantsisyk.androidpaymentdemo.presentation.state.PaymentState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val createPaymentIntentUseCase: CreatePaymentIntentUseCase,
    private val confirmPaymentUseCase: ConfirmPaymentUseCase,
    val stripePaymentManager: StripePaymentManager,
    private val paymentInitializer: PaymentInitializer
) : ViewModel() {

    private val _paymentState = MutableStateFlow<PaymentState>(PaymentState.Initial)
    val paymentState: StateFlow<PaymentState> = _paymentState.asStateFlow()

    private var currentPaymentIntentId: String? = null

    fun createPaymentIntent(amount: Long = 1000, currency: String = "usd") {
        _paymentState.value = PaymentState.Loading

        viewModelScope.launch {
            when (val result = createPaymentIntentUseCase(amount, currency)) {
                is Resource.Success -> {
                    val paymentIntent = result.data
                    if (paymentIntent != null) {
                        currentPaymentIntentId = paymentIntent.id
                        _paymentState.value = PaymentState.PaymentReady(paymentIntent.id)
                        Timber.d("Payment intent created successfully: ${paymentIntent.id}")
                    } else {
                        _paymentState.value = PaymentState.Error("Payment intent is null")
                        Timber.e("Payment intent is null")
                    }
                }
                is Resource.Error -> {
                    _paymentState.value = PaymentState.Error(result.message ?: "Unknown error")
                    Timber.e("Error creating payment intent: ${result.message}")
                }

                is Resource.Loading -> TODO()
            }
        }
    }

    fun processPayment(paymentIntent: PaymentIntent, context: Context) {
        _paymentState.value = PaymentState.Processing

        try {
            paymentInitializer.presentPaymentSheet(
                clientSecret = paymentIntent.clientSecret,
                merchantName = "Android Payment Demo"
            )

            Timber.d("Presented Payment Sheet for intent: ${paymentIntent.id}")
        } catch (e: Exception) {
            _paymentState.value = PaymentState.Error("Error processing payment: ${e.message}")
            Timber.e(e, "Error processing payment")
        }
    }

    fun handlePaymentResult(paymentResult: com.stripe.android.paymentsheet.PaymentSheetResult) {
        when (paymentResult) {
            is com.stripe.android.paymentsheet.PaymentSheetResult.Completed -> {
                currentPaymentIntentId?.let { intentId ->
                    confirmPayment(intentId)
                } ?: run {
                    _paymentState.value = PaymentState.Success
                    Timber.d("Payment completed successfully but no payment intent ID to confirm")
                }
            }
            is com.stripe.android.paymentsheet.PaymentSheetResult.Canceled -> {
                _paymentState.value = PaymentState.Initial
                Timber.d("Payment canceled by user")
            }
            is com.stripe.android.paymentsheet.PaymentSheetResult.Failed -> {
                _paymentState.value = PaymentState.Error(
                    paymentResult.error.localizedMessage ?: "Payment failed"
                )
                Timber.e("Payment failed: ${paymentResult.error.localizedMessage}")
            }
        }
    }

    private fun confirmPayment(paymentIntentId: String) {
        viewModelScope.launch {
            when (val result = confirmPaymentUseCase(paymentIntentId)) {
                is Resource.Success -> {
                    if (result.data == true) {
                        _paymentState.value = PaymentState.Success
                        Timber.d("Payment confirmed successfully")
                    } else {
                        _paymentState.value = PaymentState.Error("Payment confirmation failed")
                        Timber.e("Payment confirmation failed")
                    }
                }
                is Resource.Error -> {
                    _paymentState.value = PaymentState.Error(result.message ?: "Unknown error")
                    Timber.e("Error confirming payment: ${result.message}")
                }

                is Resource.Loading -> TODO()
            }
        }
    }
}