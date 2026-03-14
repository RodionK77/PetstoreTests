package org.example.api

import io.qameta.allure.okhttp3.AllureOkHttp3
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit

object Retrofit {
    private const val BASE_URL = "https://petstore.swagger.io/v2/"

    private val client = OkHttpClient.Builder()
        .addInterceptor(AllureOkHttp3())
        .build()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val petApi: PetService by lazy { retrofit.create(PetService::class.java)}
    val storeApi: StoreService by lazy { retrofit.create(StoreService::class.java)}
    val userApi: UserService by lazy { retrofit.create(UserService::class.java)}
}