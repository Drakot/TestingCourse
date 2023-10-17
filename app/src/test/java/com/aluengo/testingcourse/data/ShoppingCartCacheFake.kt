package com.aluengo.testingcourse.data

import com.aluengo.testingcourse.domain.Product
import com.aluengo.testingcourse.domain.ShoppingCartCache
import org.junit.jupiter.api.Assertions.*

class ShoppingCartCacheFake : ShoppingCartCache {
    private var products = arrayListOf<Product>()
    override fun saveCart(items: List<Product>) {
        products = ArrayList(items)
    }

    override fun loadCart(): List<Product> {
        return products
    }

    override fun clearCart() {
        products.clear()
    }

}