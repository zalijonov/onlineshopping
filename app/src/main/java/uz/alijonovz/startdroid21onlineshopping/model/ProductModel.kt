package uz.alijonovz.startdroid21onlineshopping.model

import java.io.Serializable

data class ProductModel(
    val id: Int,
    val name: String,
    val price: String,
    val image: String,
    val category_id: String,
    var cartCount: Int
): Serializable
