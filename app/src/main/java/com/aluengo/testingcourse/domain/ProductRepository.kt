package com.aluengo.testingcourse.domain

interface ProductRepository {
    suspend fun purchaseProducts(products: List<Product>): Result<List<Product>?>
    suspend fun cancelPurchase(purchaseId: Int): Result<Unit>
}