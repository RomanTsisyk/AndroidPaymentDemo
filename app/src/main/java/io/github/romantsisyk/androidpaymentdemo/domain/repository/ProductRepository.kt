package io.github.romantsisyk.androidpaymentdemo.domain.repository

import io.github.romantsisyk.androidpaymentdemo.domain.model.Product
import io.github.romantsisyk.androidpaymentdemo.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getProducts(): Flow<Resource<List<Product>>>
    suspend fun getProductById(id: String): Resource<Product>
}