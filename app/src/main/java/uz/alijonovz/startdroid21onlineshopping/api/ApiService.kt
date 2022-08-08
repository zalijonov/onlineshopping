package uz.alijonovz.startdroid21onlineshopping.api

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import uz.alijonovz.startdroid21onlineshopping.utils.Constants

object ApiService {
    var retrofit: Retrofit? = null

    fun apiClient(): Api{
        if (retrofit == null){
            retrofit =Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }
        return retrofit!!.create(Api::class.java)
    }
}