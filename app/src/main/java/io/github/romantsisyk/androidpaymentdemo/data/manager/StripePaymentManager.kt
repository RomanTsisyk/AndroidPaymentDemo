package io.github.romantsisyk.androidpaymentdemo.data.manager

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StripePaymentManager @Inject constructor() {

    companion object {
        const val PUBLISHABLE_KEY = "pk_test_51R0pUwF1xhAX8yoB85q2PztqBA1BIpX0ULRPdQfGLWw7FCEnMHXQyttuzIU8l7ZmcLmlWtrHMfBtUmqBA6Xetofd00zst6Ky5l"
    }
}