package uz.alijonovz.startdroid21onlineshopping.api.repository

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.alijonovz.startdroid21onlineshopping.api.ApiService
import uz.alijonovz.startdroid21onlineshopping.model.BaseResponse
import uz.alijonovz.startdroid21onlineshopping.model.CategoryModel
import uz.alijonovz.startdroid21onlineshopping.model.OfferModel
import uz.alijonovz.startdroid21onlineshopping.model.ProductModel
import uz.alijonovz.startdroid21onlineshopping.model.request.GetProductsByIdsRequest
import uz.alijonovz.startdroid21onlineshopping.utils.PrefUtils

class ShopRepository : BaseRepository() {

    fun loadTopProducts(
        error: MutableLiveData<String>,
        success: MutableLiveData<List<ProductModel>>,
        progress: MutableLiveData<Boolean>
    ) {
        sendCall(ApiService.apiClient().getTopProducts(), error, success, progress)
    }

    fun loadOffers(
        error: MutableLiveData<String>,
        progress: MutableLiveData<Boolean>,
        success: MutableLiveData<List<OfferModel>>
    ) {
        progress.value = true
        ApiService.apiClient().getOffers()
            .enqueue(object : Callback<BaseResponse<List<OfferModel>>> {
                override fun onResponse(
                    call: Call<BaseResponse<List<OfferModel>>>,
                    response: Response<BaseResponse<List<OfferModel>>>
                ) {
                    progress.value = false
                    if (response.isSuccessful && response.body()!!.success) {
                        success.value = response.body()?.data ?: emptyList()
                    } else {
                        error.value = response.message()
                    }
                }

                override fun onFailure(call: Call<BaseResponse<List<OfferModel>>>, t: Throwable) {
                    error.value = t.localizedMessage
                }

            })
    }

    fun loadCategories(
        error: MutableLiveData<String>,
        success: MutableLiveData<List<CategoryModel>>,
        progress: MutableLiveData<Boolean>
    ) {
        sendCall(ApiService.apiClient().getCategories(), error, success, progress)
    }

    fun loadCategoryProducts(
        id: Int,
        error: MutableLiveData<String>,
        success: MutableLiveData<List<ProductModel>>,
        progress: MutableLiveData<Boolean>
    ) {
        sendCall(ApiService.apiClient().getCategoryProducts(id), error, success, progress)
    }

    fun loadProductsByIds(
        ids: List<Int>,
        error: MutableLiveData<String>,
        progress: MutableLiveData<Boolean>,
        success: MutableLiveData<List<ProductModel>>
    ) {
        progress.value = true
        compositeDisposable.add(
            ApiService.apiClient().getProductsByIds(GetProductsByIdsRequest(ids))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<BaseResponse<List<ProductModel>>>() {
                    override fun onNext(t: BaseResponse<List<ProductModel>>) {
                        progress.value = false
                        if (t.success) {
                            PrefUtils.getCartList().forEach { cartProducts ->
                                val carts =
                                    t.data.filter { cartProducts.product_id == it.id }.firstOrNull()
                                if (carts != null) {
                                    carts.cartCount = cartProducts.count
                                } else {
//                                    error.value = t.message
                                }
                            }
                            success.value = t.data
                        } else {
                            error.value = t.message
                        }
                    }

                    override fun onError(e: Throwable) {
                        error.value = e.localizedMessage
                    }

                    override fun onComplete() {

                    }
                })
        )
    }

    fun loadFavProductsByIds(
        ids: List<Int>,
        error: MutableLiveData<String>,
        progress: MutableLiveData<Boolean>,
        success: MutableLiveData<List<ProductModel>>
    ) {
        sendCall(
            ApiService.apiClient().getProductsByIds(GetProductsByIdsRequest(ids)),
            error,
            success,
            progress
        )
    }
}