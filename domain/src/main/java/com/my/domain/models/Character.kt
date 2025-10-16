package com.my.domain.models

data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String? = "none",
    val gender: String,
    val image: String,
    val origin: String? = "unknown",
    val location: String
)