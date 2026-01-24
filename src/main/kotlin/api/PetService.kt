package org.example.api

import okhttp3.MultipartBody
import okhttp3.ResponseBody
import org.example.models.Pet
import retrofit2.Response
import retrofit2.http.*

interface PetService {

    @POST("pet")
    suspend fun createPet(@Body pet: Pet): Response<Pet>

    @GET("pet/{id}")
    suspend fun getPet(@Path("id") id: Long): Response<Pet>

    @PUT("pet")
    suspend fun updatePet(@Body pet: Pet): Response<Pet>

    @DELETE("pet/{id}")
    suspend fun deletePet(@Path("id") id: Long): Response<ResponseBody>

    @GET("pet/findByStatus")
    suspend fun findPetsByStatus(@Query("status") status: String): Response<List<Pet>>

    @FormUrlEncoded
    @POST("pet/{id}")
    suspend fun updatePetWithForm(
        @Path("id") id: Long,
        @Field("name") name: String,
        @Field("status") status: String
    ): Response<ResponseBody>

    @Multipart
    @POST("pet/{id}/uploadImage")
    suspend fun uploadImage(
        @Path("id") id: Long,
        @Part image: MultipartBody.Part
    ): Response<ResponseBody>
}