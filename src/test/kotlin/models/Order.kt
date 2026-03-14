package org.example.models

import com.google.gson.annotations.SerializedName

data class Order(
    val id: Long? = null,
    val petId: Long? = null,
    val quantity: Int? = null,
    val shipDate: String? = null,
    val status: OrderStatus? = null,
    val complete: Boolean? = null
)

enum class OrderStatus {
    @SerializedName("placed")
    PLACED,
    @SerializedName("approved")
    APPROVED,
    @SerializedName("delivered")
    DELIVERED;

    override fun toString(): String {
        return super.toString().lowercase()
    }
}