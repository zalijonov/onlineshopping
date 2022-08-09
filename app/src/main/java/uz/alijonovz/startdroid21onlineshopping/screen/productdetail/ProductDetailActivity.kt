package uz.alijonovz.startdroid21onlineshopping.screen.productdetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import uz.alijonovz.startdroid21onlineshopping.R
import uz.alijonovz.startdroid21onlineshopping.databinding.ActivityProductDetailBinding
import uz.alijonovz.startdroid21onlineshopping.model.ProductModel
import uz.alijonovz.startdroid21onlineshopping.utils.Constants
import uz.alijonovz.startdroid21onlineshopping.utils.PrefUtils

class ProductDetailActivity : AppCompatActivity() {
    lateinit var item: ProductModel
    lateinit var binding:ActivityProductDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var count = 0
        binding.cardViewBack.setOnClickListener {
            finish()
        }

        item = intent.getSerializableExtra(Constants.EXTRA_DATA) as ProductModel

        binding.tvTitle.text = item.name
        binding.tvProductName.text = item.name
        binding.tvProductPrice.text = item.price
        if(PrefUtils.checkFavorite(item)){
            binding.imgFav.setImageResource(R.drawable.ic_favorite_filled)
        } else {
            binding.imgFav.setImageResource(R.drawable.ic_favorite_not_filled)
        }

        if(PrefUtils.getCartCount(item) > 0){
            binding.btnAddToCart.visibility = View.GONE
        }

        binding.btnAddToCart.setOnClickListener {
            count=1
            item.cartCount=count
            PrefUtils.setCart(item)
            Toast.makeText(this, "Product added to cart", Toast.LENGTH_LONG).show()
            finish()
        }
        Glide.with(this).load(Constants.HOST_IMAGE+item.image).into(binding.imgProduct)

        binding.cardViewFavorite.setOnClickListener {
            PrefUtils.setFavorite(item)
            if(PrefUtils.checkFavorite(item)){
                binding.imgFav.setImageResource(R.drawable.ic_favorite_filled)
            } else {
                binding.imgFav.setImageResource(R.drawable.ic_favorite_not_filled)
            }
        }
    }
}