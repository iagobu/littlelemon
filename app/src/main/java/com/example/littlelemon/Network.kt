package com.example.littlelemon

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable data class MenuItemNetwork(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val description: String,
    @SerialName("price")
    val price:  String,
    @SerialName("image")
    val image: String,
    @SerialName("category")
    val category: String
) {
    fun toMenuItemRoom() = MenuItemRoom(
        id = id,
        title = title,
        description = description,
        price = price,
        image = image,
        category = category
    )
}

@Serializable data class MenuNetwork(
    @SerialName("menu")
    val menu: List<MenuItemNetwork>
)