package uz.alijonovz.startdroid21onlineshopping.model

data class CategoryModel(
    val id: Int,
    val title: String,
    val icon: String,
    val parent_id: Int,
    var checked: Boolean = false
)