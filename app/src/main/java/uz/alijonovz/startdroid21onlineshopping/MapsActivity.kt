package uz.alijonovz.startdroid21onlineshopping

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import org.greenrobot.eventbus.EventBus
import uz.alijonovz.startdroid21onlineshopping.databinding.ActivityMapsBinding
import uz.alijonovz.startdroid21onlineshopping.model.AddressModel
import uz.alijonovz.startdroid21onlineshopping.utils.BaseActivity

class MapsActivity : BaseActivity<ActivityMapsBinding>(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun getViewBinding(): ActivityMapsBinding = ActivityMapsBinding.inflate(layoutInflater)

    override fun initView() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.btnConfirm.setOnClickListener {
            val addressModel = AddressModel(
                "", mMap.cameraPosition.target.latitude, mMap.cameraPosition.target.longitude
            )
            EventBus.getDefault().post(addressModel)
            finish()
        }
    }

    override fun loadData() {

    }

    override fun updateData() {

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
    }
}