package org.example.api

import okhttp3.ResponseBody
import org.example.models.User
import retrofit2.Response
import retrofit2.http.*

interface UserService {
    @POST("user")
    suspend fun createUser(@Body user: User): Response<ResponseBody>

    @GET("user/login")
    suspend fun login(
        @Query("username") username: String,
        @Query("password") password: String
    ): Response<ResponseBody>

    @GET("user/logout")
    suspend fun logout(): Response<ResponseBody>

    @GET("user/{username}")
    suspend fun getUser(@Path("username") username: String): Response<User>

    @PUT("user/{username}")
    suspend fun updateUser(
        @Path("username") username: String,
        @Body user: User
    ): Response<ResponseBody>

    @DELETE("user/{username}")
    suspend fun deleteUser(@Path("username") username: String): Response<ResponseBody>

    @POST("user/createWithList")
    suspend fun createUsersWithList(@Body users: List<User>): Response<ResponseBody>

    @POST("user/createWithArray")
    suspend fun createUsersWithArray(@Body users: List<User>): Response<ResponseBody>
}