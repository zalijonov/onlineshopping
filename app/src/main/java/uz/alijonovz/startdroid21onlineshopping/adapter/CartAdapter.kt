package uz.alijonovz.startdroid21onlineshopping.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import uz.alijonovz.startdroid21onlineshopping.databinding.CartItemLayoutBinding
import uz.alijonovz.startdroid21onlineshopping.model.ProductModel
import uz.alijonovz.startdroid21onlineshopping.utils.BaseAdapter
import uz.alijonovz.startdroid21onlineshopping.utils.Constants

class CartAdapter(var items1: List<ProductModel>) : BaseAdapter<CartItemLayoutBinding>(items1) {

    override fun getBinding(parent: ViewGroup): CartItemLayoutBinding =
        CartItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    override fun onBindViewHolder(
        holder: BaseAdapter<CartItemLayoutBinding>.ItemHolder<CartItemLayoutBinding>,
        position: Int
    ) {
        var item = items1[position]

        holder.binding.tvPrice.text = item.price
        holder.binding.tvName.text = item.name

        Glide.with(holder.itemView).load(Constants.HOST_IMAGE + item.image)
            .into(holder.binding.imgProduct)

        holder.binding.tvCount.text = item.cartCount.toString()
    }

    override fun initItemData(item: Any) {

    }
}