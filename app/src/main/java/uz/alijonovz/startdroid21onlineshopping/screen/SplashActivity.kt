package uz.alijonovz.startdroid21onlineshopping.screen

import android.content.Intent
import uz.alijonovz.startdroid21onlineshopping.databinding.ActivitySplashBinding
import uz.alijonovz.startdroid21onlineshopping.utils.BaseActivity

class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    override fun getViewBinding(): ActivitySplashBinding =
        ActivitySplashBinding.inflate(layoutInflater)

    override fun initView() {
        binding.animationView.postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 2000)
    }

    override fun loadData() {

    }

    override fun updateData() {

    }

}