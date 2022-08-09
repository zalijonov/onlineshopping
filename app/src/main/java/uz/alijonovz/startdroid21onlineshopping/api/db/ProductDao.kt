package uz.alijonovz.startdroid21onlineshopping.api.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uz.alijonovz.startdroid21onlineshopping.model.ProductModel

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(items: List<ProductModel>)

    @Query("select * from products")
    fun getAllProducts(): List<ProductModel>

    @Query("DELETE from products")
    fun deleteAll()
}