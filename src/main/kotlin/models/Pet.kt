package org.example.models

data class Pet (
    val id: Long? = null,
    val category: Category? = null,
    val name: String? = null,
    val photoUrls: List<String>? = null,
    val tags: List<Tag>? = null,
    val status: String? = null,
)

data class Category(
    val id: Long? = null,
    val name: String? = null
)

data class Tag(
    val id: Long? = null,
    val name: String? = null
)