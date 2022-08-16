package uz.alijonovz.startdroid21onlineshopping.screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uz.alijonovz.startdroid21onlineshopping.api.db.AppDatabase
import uz.alijonovz.startdroid21onlineshopping.api.repository.ShopRepository
import uz.alijonovz.startdroid21onlineshopping.model.CategoryModel
import uz.alijonovz.startdroid21onlineshopping.model.OfferModel
import uz.alijonovz.startdroid21onlineshopping.model.ProductModel

class MainViewModel : ViewModel() {

    val repository = ShopRepository()
    val error = MutableLiveData<String>()
    val offersData = MutableLiveData<List<OfferModel>>()
    val categoriesData = MutableLiveData<List<CategoryModel>>()
    val topProductData = MutableLiveData<List<ProductModel>>()
    val productData = MutableLiveData<List<ProductModel>>()
    val progress = MutableLiveData<Boolean>()

    fun loadOffers() {
        repository.loadOffers(error, progress, offersData)
    }

    fun loadCategories() {
        repository.loadCategories(error, categoriesData)
    }

    fun loadTopProducts() {
        repository.loadTopProducts(error, topProductData)
    }

    fun loadCategoryProducts(id: Int) {
        repository.loadCategoryProducts(id, error, topProductData)
    }

    fun loadProductByIds(ids: List<Int>) {
        repository.loadProductsByIds(ids, error, progress, productData)
    }

    fun loadFavProductsByIds(ids: List<Int>) {
        repository.loadFavProductsByIds(ids, error, progress, topProductData)
    }

    fun insertAllProducts2DB(items: List<ProductModel>) {
        CoroutineScope(Dispatchers.IO).launch {
            AppDatabase.getDatabase().getProductDao().deleteAll()
            AppDatabase.getDatabase().getProductDao().insertAll(items)
        }
    }

    fun insertAllCategories2DB(items: List<CategoryModel>) {
        CoroutineScope(Dispatchers.IO).launch {
            AppDatabase.getDatabase().getCategoryDao().deleteAll()
            AppDatabase.getDatabase().getCategoryDao().insertAll(items)
        }
    }

    fun loadAllDBProducts() {
        CoroutineScope(Dispatchers.Main).launch {
            topProductData.value = withContext(Dispatchers.IO) {
                AppDatabase.getDatabase().getProductDao().getAllProducts()
            }!!
        }
    }

    fun loadAllDBCategories() {
        CoroutineScope(Dispatchers.Main).launch {
            categoriesData.value = withContext(Dispatchers.IO) {
                AppDatabase.getDatabase().getCategoryDao().getAllCategories()
            }!!
        }
    }
}