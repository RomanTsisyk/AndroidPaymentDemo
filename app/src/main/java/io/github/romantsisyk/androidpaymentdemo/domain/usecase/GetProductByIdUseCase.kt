package io.github.romantsisyk.androidpaymentdemo.domain.usecase

import io.github.romantsisyk.androidpaymentdemo.domain.model.Product
import io.github.romantsisyk.androidpaymentdemo.domain.repository.ProductRepository
import io.github.romantsisyk.androidpaymentdemo.domain.util.Resource
import javax.inject.Inject

class GetProductByIdUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(id: String): Resource<Product> {
        return repository.getProductById(id)
    }
}