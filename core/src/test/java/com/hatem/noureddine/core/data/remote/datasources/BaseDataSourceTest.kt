package com.hatem.noureddine.core.data.remote.datasources

import com.hatem.noureddine.core.data.remote.models.Response
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonEncodingException
import kotlinx.coroutines.runBlocking
import okhttp3.Headers
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException
import java.net.SocketTimeoutException

@RunWith(MockitoJUnitRunner::class)
class BaseDataSourceTest {

    @Test
    fun `test success result`() {
        val dataSource = TestBaseDataSource()

        runBlocking {
            val result = dataSource.getResult { retrofit2.Response.success(String) }
            Assert.assertEquals(
                result is Response.Success,
                true
            )
            Assert.assertEquals(
                result is Response.Error,
                false
            )

            val resultBodyNull = dataSource.getResult {
                retrofit2.Response.success<String>(
                    null,
                    Headers.headersOf()
                )
            }
            Assert.assertEquals(
                resultBodyNull is Response.Success,
                false
            )
            Assert.assertEquals(
                resultBodyNull is Response.Error,
                true
            )
        }
    }

    @Test
    fun `test error result`() {
        val response = retrofit2.Response.error<String>(400, "".toResponseBody())
        val dataSource = TestBaseDataSource()

        runBlocking {
            val result = dataSource.getResult { response }
            Assert.assertEquals(
                result is Response.Success,
                false
            )

            Assert.assertEquals(
                result is Response.Error.NoNetworkException,
                false
            )

            Assert.assertEquals(
                result is Response.Error.ErrorException,
                true
            )

            Assert.assertEquals(
                (result as Response.Error.ErrorException).message,
                "Network call has failed for a following reason: 400 :: Response.error()"
            )
        }
    }

    @Test
    fun `test result generic exception`() {
        val dataSource = TestBaseDataSource()

        runBlocking {
            val resultSocket = dataSource.getResult<Nothing> { throw SocketTimeoutException() }
            Assert.assertEquals(
                resultSocket is Response.Success,
                false
            )

            Assert.assertEquals(
                resultSocket is Response.Error.NoNetworkException,
                false
            )

            Assert.assertEquals(
                resultSocket is Response.Error.ErrorException,
                true
            )

            Assert.assertEquals(
                (resultSocket as Response.Error.ErrorException).message,
                "Network call has failed for a following reason: ${SocketTimeoutException::class.java.name}"
            )

            val resultJsonEncoding =
                dataSource.getResult<Nothing> { throw JsonEncodingException(null) }
            Assert.assertEquals(
                resultJsonEncoding is Response.Success,
                false
            )

            Assert.assertEquals(
                resultJsonEncoding is Response.Error.NoNetworkException,
                false
            )

            Assert.assertEquals(
                resultJsonEncoding is Response.Error.ErrorException,
                true
            )

            Assert.assertEquals(
                (resultJsonEncoding as Response.Error.ErrorException).message,
                "Network call has failed for a following reason: ${JsonEncodingException::class.java.name}"
            )

            val resultIoException = dataSource.getResult<Nothing> { throw IOException() }
            Assert.assertEquals(
                resultIoException is Response.Success,
                false
            )

            Assert.assertEquals(
                resultIoException is Response.Error.NoNetworkException,
                false
            )

            Assert.assertEquals(
                resultIoException is Response.Error.ErrorException,
                true
            )

            Assert.assertEquals(
                (resultIoException as Response.Error.ErrorException).message,
                "Network call has failed for a following reason: ${IOException::class.java.name}"
            )
        }
    }

    @Test
    fun `test result no network exception`() {
        val dataSource = TestBaseDataSource()

        runBlocking {
            val resultJsonData = dataSource.getResult<Nothing> { throw JsonDataException() }
            Assert.assertEquals(
                resultJsonData is Response.Success,
                false
            )

            Assert.assertEquals(
                resultJsonData is Response.Error.ErrorException,
                false
            )

            Assert.assertEquals(
                resultJsonData is Response.Error.NoNetworkException,
                true
            )

            val resultException = dataSource.getResult<Nothing> { throw Exception() }
            Assert.assertEquals(
                resultException is Response.Success,
                false
            )

            Assert.assertEquals(
                resultException is Response.Error.ErrorException,
                false
            )

            Assert.assertEquals(
                resultException is Response.Error.NoNetworkException,
                true
            )
        }
    }

    @Test
    fun `test that error fun handle the message`() {
        val dataSource = TestBaseDataSource()
        val message = "test error message"
        val error = dataSource.error<Nothing>(message)

        Assert.assertEquals(error is Response.Error.ErrorException, true)
        Assert.assertEquals(
            (error as Response.Error.ErrorException).message,
            "Network call has failed for a following reason: $message"
        )
    }

    private class TestBaseDataSource : BaseDataSource()
}