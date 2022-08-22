package uz.alijonovz.startdroid21onlineshopping.screen

import android.content.Context
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import uz.alijonovz.startdroid21onlineshopping.R
import uz.alijonovz.startdroid21onlineshopping.databinding.ActivityMainBinding
import uz.alijonovz.startdroid21onlineshopping.screen.changelanguage.ChangeLanguageFragment
import uz.alijonovz.startdroid21onlineshopping.utils.BaseActivity
import uz.alijonovz.startdroid21onlineshopping.utils.LocaleManager

class MainActivity : BaseActivity<ActivityMainBinding>() {
    lateinit var viewModel: MainViewModel

    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun initView() {
        viewModel = MainViewModel()


        viewModel.topProductData.observe(this) {
            viewModel.insertAllProducts2DB(it)
//            HomeFragment.newInstance().loadData()
        }

        viewModel.categoriesData.observe(this) {
            viewModel.insertAllCategories2DB(it)
//            HomeFragment.newInstance().loadData()
        }

        viewModel.error.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }


        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.flContainer) as NavHostFragment
        NavigationUI.setupWithNavController(binding.bottomNaviView, navHostFragment.navController)

        binding.menu.setOnClickListener {
            val fragment = ChangeLanguageFragment.newInstance()
            fragment.show(supportFragmentManager, fragment.tag)
        }
    }

    override fun updateData() {

    }

    override fun loadData() {
        viewModel.loadTopProducts()
        viewModel.loadCategories()
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleManager.setLocale(newBase))
    }
}