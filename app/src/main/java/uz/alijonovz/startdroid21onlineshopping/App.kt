package uz.alijonovz.startdroid21onlineshopping

import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.orhanobut.hawk.Hawk
import uz.alijonovz.startdroid21onlineshopping.api.db.AppDatabase


class App: MultiDexApplication() {
    companion object{
        lateinit var app: App
    }
    override fun onCreate() {
        super.onCreate()
        app = this
        MultiDex.install(this)
        Hawk.init(this).build()
        AppDatabase.initDatabase(this)
    }
}