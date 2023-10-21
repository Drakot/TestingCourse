package com.aluengo.testingcourse.data

import com.aluengo.testingcourse.domain.Product
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface ProductApi {

    @POST("/purchase")
    suspend fun purchaseProducts(
        @Body products: ProductsDto
    ): List<Product>

    @POST("/cancelPurchase/{id}")
    suspend fun cancelPurchase(
        @Path("id") purchaseId: String
    )
}