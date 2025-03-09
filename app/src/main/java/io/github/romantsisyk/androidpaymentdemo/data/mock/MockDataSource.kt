package io.github.romantsisyk.androidpaymentdemo.data.mock

import io.github.romantsisyk.androidpaymentdemo.domain.model.Product
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockDataSource @Inject constructor() {

    private val productCatalog = listOf(
        Product(
            id = "prod_1",
            name = "Premium Subscription",
            description = "Access to all premium features for one month",
            price = 1999, // $19.99
            imageUrl = "https://images.unsplash.com/photo-1626379953822-baec19c3accd"
        ),
        Product(
            id = "prod_2",
            name = "Basic Package",
            description = "Essential features for beginners",
            price = 999, // $9.99
            imageUrl = "https://images.unsplash.com/photo-1626379953822-baec19c3accd"
        ),
        Product(
            id = "prod_3",
            name = "Pro Package",
            description = "Advanced features for professionals",
            price = 2999, // $29.99
            imageUrl = "https://images.unsplash.com/photo-1626379953822-baec19c3accd"
        ),
        Product(
            id = "prod_4",
            name = "Enterprise Solution",
            description = "Complete solution for businesses",
            price = 4999, // $49.99
            imageUrl = "https://images.unsplash.com/photo-1626379953822-baec19c3accd"
        )
    )

    suspend fun getProducts(): List<Product> {
        delay(800)
        return productCatalog
    }

    suspend fun getProductById(id: String): Product? {
        delay(300)
        return productCatalog.find { it.id == id }
    }
}