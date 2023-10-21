package com.aluengo.testingcourse.data

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import com.aluengo.testingcourse.domain.AnalyticsLogger
import com.aluengo.testingcourse.domain.LogParam
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import retrofit2.HttpException
import retrofit2.http.HTTP
import java.io.IOException
import java.net.HttpURLConnection
import java.util.concurrent.CancellationException

internal class ProductRepositoryImplTest {

    private lateinit var repository: ProductRepositoryImpl
    private lateinit var productApi: ProductApi
    private lateinit var analyticsLogger: AnalyticsLogger

    @BeforeEach
    fun setUp() {
        productApi = mockk(relaxed = true)
        analyticsLogger = mockk(relaxed = true)
        repository = ProductRepositoryImpl(productApi, analyticsLogger)
    }

    @Test
    fun `Response error, exception is logged`() = runBlocking {
        coEvery { productApi.purchaseProducts(any()) } throws mockk<HttpException> {
            every { code() } returns HttpURLConnection.HTTP_NOT_FOUND //404
            every { message() } returns "Test message"
        }

        val result = repository.purchaseProducts(listOf())

        assertThat(result.isFailure).isTrue()

        verify {
            analyticsLogger.logEvent(
                "http_error",
                LogParam("code", HttpURLConnection.HTTP_NOT_FOUND),
                LogParam("message", "Test message"),
            )
        }
    }

    @Test
    fun `Response error, IOException is logged`() = runBlocking {
        coEvery { productApi.purchaseProducts(any()) } throws mockk<IOException> {
            every { message } returns "Test message"
        }

        val result = repository.purchaseProducts(listOf())

        assertThat(result.isFailure).isTrue()

        verify {
            analyticsLogger.logEvent(
                "io_error",
                LogParam("message", "Test message"),
            )
        }
    }

    @Test
    fun `Response error, CancellationException is thrown`(): Unit = runBlocking {
        coEvery { productApi.purchaseProducts(any()) } throws mockk<CancellationException> {
            every { message } returns "Test message"
        }
        assertThrows<CancellationException> {
            val result = repository.purchaseProducts(listOf())
            assertThat(result.isFailure).isTrue()
        }
    }

    @Test
    fun `Response error, Exception is thrown`(): Unit = runBlocking {
        coEvery { productApi.purchaseProducts(any()) } throws mockk<Exception> {
            every { message } returns "Test message"
        }
        val result = repository.purchaseProducts(listOf())
        assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `Response success, Purchased products`(): Unit = runBlocking {

        coEvery { productApi.purchaseProducts(any()) } returns mockk {
            listOf(product())
        }
        val result = repository.purchaseProducts( listOf(product()))
        assertThat(result.isSuccess).isTrue()
    }
}