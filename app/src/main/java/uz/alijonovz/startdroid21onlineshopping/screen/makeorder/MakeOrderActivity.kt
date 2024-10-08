package uz.alijonovz.startdroid21onlineshopping.screen.makeorder

import android.annotation.SuppressLint
import android.content.Intent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import uz.alijonovz.startdroid21onlineshopping.MapsActivity
import uz.alijonovz.startdroid21onlineshopping.databinding.ActivityMakeOrderBinding
import uz.alijonovz.startdroid21onlineshopping.model.AddressModel
import uz.alijonovz.startdroid21onlineshopping.model.ProductModel
import uz.alijonovz.startdroid21onlineshopping.utils.BaseActivity
import uz.alijonovz.startdroid21onlineshopping.utils.Constants
import java.math.RoundingMode
import java.text.DecimalFormat

class MakeOrderActivity : BaseActivity<ActivityMakeOrderBinding>() {
    var address: AddressModel? = null
    lateinit var items: List<ProductModel>

    override fun getViewBinding(): ActivityMakeOrderBinding =
        ActivityMakeOrderBinding.inflate(layoutInflater)

    override fun initView() {
        items = intent.getSerializableExtra(Constants.EXTRA_DATA) as List<ProductModel>
        if (!EventBus.getDefault().isRegistered((this))) {
            EventBus.getDefault().register(this)
        }

        var df = DecimalFormat("#.###")
        df.roundingMode = RoundingMode.DOWN
        var s = items.sumOf {
            it.cartCount.toDouble() * (it.price.replace(" ", "").toDoubleOrNull() ?: 0.0)
        }
        binding.tvTotalAmount.text = df.format(s)

        binding.edAddress.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
        }

        binding.cardViewBack.setOnClickListener { finish() }
    }

    override fun loadData() {

    }

    override fun updateData() {

    }

    override fun onDestroy() {
        super.onDestroy()
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
    }

    @SuppressLint("SetTextI18n")
    @Subscribe
    fun onEvent(address: AddressModel) {
        this.address = address
        binding.edAddress.setText("${address.latitude}, ${address.longitude}")
    }
}