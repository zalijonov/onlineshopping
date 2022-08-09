package uz.alijonovz.startdroid21onlineshopping.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "products")
data class ProductModel(
    @PrimaryKey(autoGenerate = true)
    val uid: Int,
    val id: Int,
    val name: String,
    val price: String,
    val image: String,
    val category_id: String,
    var cartCount: Int
): Serializable
