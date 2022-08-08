package uz.alijonovz.startdroid21onlineshopping.screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.alijonovz.startdroid21onlineshopping.api.repository.ShopRepository
import uz.alijonovz.startdroid21onlineshopping.model.CategoryModel
import uz.alijonovz.startdroid21onlineshopping.model.OfferModel
import uz.alijonovz.startdroid21onlineshopping.model.ProductModel

class MainViewModel: ViewModel() {

    val repository = ShopRepository()
    val error = MutableLiveData<String>()
    val offersData = MutableLiveData<List<OfferModel>>()
    val categoriesData = MutableLiveData<List<CategoryModel>>()
    val topProductData = MutableLiveData<List<ProductModel>>()
    val productData = MutableLiveData<List<ProductModel>>()
    val progress = MutableLiveData<Boolean>()

    fun loadOffers(){
        repository.loadOffers(error, progress, offersData)
    }

    fun loadCategories(){
        repository.loadCategories(error, categoriesData)
    }

    fun loadTopProducts(){
        repository.loadTopProducts(error, topProductData)
    }

    fun loadCategoryProducts(id: Int){
        repository.loadCategoryProducts(id, error, topProductData)
    }

    fun loadProductByIds(ids: List<Int>){
        repository.loadProductsByIds(ids, error, progress, productData)
    }

    fun loadFavProductsByIds(ids: List<Int>){
        repository.loadFavProductsByIds(ids, error, progress, topProductData)
    }
}