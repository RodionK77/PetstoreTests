package org.example.api

import okhttp3.ResponseBody
import org.example.models.Order
import retrofit2.Response
import retrofit2.http.*

interface StoreService {
    @GET("store/inventory")
    suspend fun getInventory(): Response<Map<String, Int>>

    @POST("store/order")
    suspend fun placeOrder(@Body order: Order): Response<Order>

    @GET("store/order/{orderId}")
    suspend fun getOrder(@Path("orderId") orderId: Long): Response<Order>

    @DELETE("store/order/{orderId}")
    suspend fun deleteOrder(@Path("orderId") orderId: Long): Response<ResponseBody>
}