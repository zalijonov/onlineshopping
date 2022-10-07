package uz.alijonovz.startdroid21onlineshopping.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import uz.alijonovz.startdroid21onlineshopping.databinding.ProductItemLayoutBinding
import uz.alijonovz.startdroid21onlineshopping.model.ProductModel
import uz.alijonovz.startdroid21onlineshopping.screen.productdetail.ProductDetailActivity
import uz.alijonovz.startdroid21onlineshopping.utils.BaseAdapter
import uz.alijonovz.startdroid21onlineshopping.utils.Constants

class ProductAdapter(var items1: List<ProductModel>) :
    BaseAdapter<ProductItemLayoutBinding>(items1) {

    override fun getBinding(parent: ViewGroup): ProductItemLayoutBinding =
        ProductItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    override fun initItemData(item: Any) {}

    override fun onBindViewHolder(
        holder: BaseAdapter<ProductItemLayoutBinding>.ItemHolder<ProductItemLayoutBinding>,
        position: Int
    ) {
        var item = items1[position]

        holder.binding.cardView.setOnClickListener {
            val intent = Intent(it.context, ProductDetailActivity::class.java)
            intent.putExtra(Constants.EXTRA_DATA, item)
            it.context.startActivity(intent)
        }


        holder.binding.tvPrice.text = item.price
        holder.binding.tvCategory.text = item.category_id
        holder.binding.tvTitle.text = item.name

        Glide.with(holder.itemView.context).load(Constants.HOST_IMAGE + item.image)
            .into(holder.binding.ivProduct)
    }

}