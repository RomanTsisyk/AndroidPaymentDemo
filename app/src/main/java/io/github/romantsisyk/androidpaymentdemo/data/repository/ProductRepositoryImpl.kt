package io.github.romantsisyk.androidpaymentdemo.data.repository

import io.github.romantsisyk.androidpaymentdemo.data.mock.MockDataSource
import io.github.romantsisyk.androidpaymentdemo.domain.model.Product
import io.github.romantsisyk.androidpaymentdemo.domain.repository.ProductRepository
import io.github.romantsisyk.androidpaymentdemo.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val mockDataSource: MockDataSource
) : ProductRepository {

    override suspend fun getProducts(): Flow<Resource<List<Product>>> = flow {
        emit(Resource.Loading())

        try {
            val products = mockDataSource.getProducts()
            emit(Resource.Success(products))
        } catch (e: Exception) {
            Timber.e(e, "Error fetching products")
            emit(Resource.Error("Couldn't load products. ${e.localizedMessage}"))
        }
    }

    override suspend fun getProductById(id: String): Resource<Product> {
        return try {
            val product = mockDataSource.getProductById(id)
            if (product != null) {
                Resource.Success(product)
            } else {
                Resource.Error("Product not found")
            }
        } catch (e: Exception) {
            Timber.e(e, "Error fetching product with id: $id")
            Resource.Error("Couldn't load product. ${e.localizedMessage}")
        }
    }
}