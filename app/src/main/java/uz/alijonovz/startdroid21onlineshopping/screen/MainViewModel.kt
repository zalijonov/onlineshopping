package uz.alijonovz.startdroid21onlineshopping.screen

import androidx.lifecycle.LiveData
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
    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private val _offersData = MutableLiveData<List<OfferModel>>()
    val offersData: LiveData<List<OfferModel>>
        get() = _offersData

    private val _categoriesData = MutableLiveData<List<CategoryModel>>()
    val categoriesData: LiveData<List<CategoryModel>>
        get() = _categoriesData

    private val _topProductData = MutableLiveData<List<ProductModel>>()
    val topProductData: LiveData<List<ProductModel>>
        get() = _topProductData

    private val _productData = MutableLiveData<List<ProductModel>>()
    val productData: LiveData<List<ProductModel>>
        get() = _productData

    private val _progress = MutableLiveData<Boolean>()
    val progress: LiveData<Boolean>
        get() = _progress

    fun loadOffers() {
        repository.loadOffers(_error, _progress, _offersData)
    }

    fun loadCategories() {
        repository.loadCategories(_error, _categoriesData, _progress)
    }

    fun loadTopProducts() {
        repository.loadTopProducts(_error, _topProductData, _progress)
    }

    fun loadCategoryProducts(id: Int) {
        repository.loadCategoryProducts(id, _error, _topProductData, _progress)
    }

    fun loadProductByIds(ids: List<Int>) {
        repository.loadProductsByIds(ids, _error, _progress, _productData)
    }

    fun loadFavProductsByIds(ids: List<Int>) {
        repository.loadFavProductsByIds(ids, _error, _progress, _topProductData)
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
            _topProductData.value = withContext(Dispatchers.IO) {
                AppDatabase.getDatabase().getProductDao().getAllProducts()
            }!!
        }
    }

    fun loadAllDBCategories() {
        CoroutineScope(Dispatchers.Main).launch {
            _categoriesData.value = withContext(Dispatchers.IO) {
                AppDatabase.getDatabase().getCategoryDao().getAllCategories()
            }!!
        }
    }
}