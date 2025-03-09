package io.github.romantsisyk.androidpaymentdemo.domain.usecase

import io.github.romantsisyk.androidpaymentdemo.domain.model.Product
import io.github.romantsisyk.androidpaymentdemo.domain.repository.ProductRepository
import io.github.romantsisyk.androidpaymentdemo.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(): Flow<Resource<List<Product>>> {
        return repository.getProducts()
    }
}