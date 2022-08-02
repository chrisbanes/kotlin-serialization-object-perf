package com.example.serialization

import kotlinx.serialization.Serializable

@Serializable
data class DataClassArgs(
    val name: String,
    val age: Int,
) : NavigationArgs