package uz.alijonovz.startdroid21onlineshopping.screen

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import uz.alijonovz.startdroid21onlineshopping.R
import uz.alijonovz.startdroid21onlineshopping.databinding.ActivityMainBinding
import uz.alijonovz.startdroid21onlineshopping.screen.changelanguage.ChangeLanguageFragment
import uz.alijonovz.startdroid21onlineshopping.utils.LocaleManager

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.flContainer) as NavHostFragment
        NavigationUI.setupWithNavController(binding.bottomNaviView, navHostFragment.navController)

        binding.menu.setOnClickListener{
            val fragment = ChangeLanguageFragment.newInstance()
            fragment.show(supportFragmentManager, fragment.tag)
        }

}

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleManager.setLocale(newBase))
    }
}