package io.github.romantsisyk.androidpaymentdemo.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.romantsisyk.androidpaymentdemo.data.repository.ProductRepositoryImpl
import io.github.romantsisyk.androidpaymentdemo.domain.repository.ProductRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindProductRepository(
        productRepositoryImpl: ProductRepositoryImpl
    ): ProductRepository
}