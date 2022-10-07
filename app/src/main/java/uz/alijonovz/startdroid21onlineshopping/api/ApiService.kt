package uz.alijonovz.startdroid21onlineshopping.api

import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import uz.alijonovz.startdroid21onlineshopping.App
import uz.alijonovz.startdroid21onlineshopping.utils.Constants

object ApiService {
    var retrofit: Retrofit? = null

    fun apiClient(): Api {
        if (retrofit == null) {
            val client = OkHttpClient.Builder().addInterceptor(
                ChuckerInterceptor.Builder(App.app).collector(ChuckerCollector(App.app))
                    .maxContentLength(250000L).redactHeaders(emptySet())
                    .alwaysReadResponseBody(false).build()
            ).build()
            retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL).client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
        }
        return retrofit!!.create(Api::class.java)
    }
}