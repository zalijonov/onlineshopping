package uz.alijonovz.startdroid21onlineshopping.model

data class BaseResponse<T>(
    val success: Boolean, val data: T, val message: String, val error_code: Int
)
