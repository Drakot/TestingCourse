package com.aluengo.testingcourse.data

import com.aluengo.testingcourse.domain.Product
import java.util.UUID

fun product(id: Int = 1): Product {
    return Product(id, "Product $id", 5.0)
}

fun products(size: Int = 10): List<Product> {
    return ((if (size < 1) 1 else size)..size).map {
        product(it)
    }
}