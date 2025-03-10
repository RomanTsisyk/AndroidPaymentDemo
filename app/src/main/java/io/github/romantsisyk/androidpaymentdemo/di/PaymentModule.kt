package io.github.romantsisyk.androidpaymentdemo.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.romantsisyk.androidpaymentdemo.data.manager.PaymentInitializer
import io.github.romantsisyk.androidpaymentdemo.data.repository.PaymentRepositoryImpl
import io.github.romantsisyk.androidpaymentdemo.domain.repository.PaymentRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PaymentModule {

    @Provides
    @Singleton
    fun providePaymentRepository(): PaymentRepository {
        return PaymentRepositoryImpl()
    }

    @Provides
    @Singleton
    fun providePaymentInitializer(): PaymentInitializer {
        return PaymentInitializer()
    }
}