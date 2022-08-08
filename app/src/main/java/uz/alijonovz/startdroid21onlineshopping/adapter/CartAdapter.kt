package uz.alijonovz.startdroid21onlineshopping.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.alijonovz.startdroid21onlineshopping.databinding.CartItemLayoutBinding
import uz.alijonovz.startdroid21onlineshopping.model.ProductModel
import uz.alijonovz.startdroid21onlineshopping.utils.Constants

class CartAdapter(var items: List<ProductModel>): RecyclerView.Adapter<CartAdapter.ItemHolder>() {

    inner class ItemHolder(var binding:CartItemLayoutBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(CartItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        var item = items[position]

        holder.binding.tvPrice.text = item.price
        holder.binding.tvName.text = item.name

        Glide.with(holder.itemView).load(Constants.HOST_IMAGE + item.image).into(holder.binding.imgProduct)

        holder.binding.tvCount.text = item.cartCount.toString()
    }

    override fun getItemCount(): Int {
        return items.count()
    }
}