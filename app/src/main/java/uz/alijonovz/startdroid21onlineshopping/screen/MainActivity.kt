package uz.alijonovz.startdroid21onlineshopping.screen

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import uz.alijonovz.startdroid21onlineshopping.R
import uz.alijonovz.startdroid21onlineshopping.databinding.ActivityMainBinding
import uz.alijonovz.startdroid21onlineshopping.screen.changelanguage.ChangeLanguageFragment
import uz.alijonovz.startdroid21onlineshopping.utils.LocaleManager

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = MainViewModel()


        viewModel.topProductData.observe(this, Observer {
            viewModel.insertAllProducts2DB(it)
//            HomeFragment.newInstance().loadData()
        })

        viewModel.categoriesData.observe(this, Observer {
            viewModel.insertAllCategories2DB(it)
//            HomeFragment.newInstance().loadData()
        })

        viewModel.error.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })


        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.flContainer) as NavHostFragment
        NavigationUI.setupWithNavController(binding.bottomNaviView, navHostFragment.navController)

        binding.menu.setOnClickListener {
            val fragment = ChangeLanguageFragment.newInstance()
            fragment.show(supportFragmentManager, fragment.tag)
        }

        loadData()

    }

    fun loadData() {
        viewModel.loadTopProducts()
        viewModel.loadCategories()
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleManager.setLocale(newBase))
    }
}