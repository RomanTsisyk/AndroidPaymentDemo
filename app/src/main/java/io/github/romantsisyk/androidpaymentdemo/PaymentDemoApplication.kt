package io.github.romantsisyk.androidpaymentdemo

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class PaymentDemoApplication : Application() {
    override fun onCreate() {
        super.onCreate()

//        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
//        }
    }
}