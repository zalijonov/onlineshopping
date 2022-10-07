package uz.alijonovz.startdroid21onlineshopping.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class CategoryModel(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    val id: Int,
    val title: String,
    val icon: String,
    val parent_id: Int,
    var checked: Boolean = false
)