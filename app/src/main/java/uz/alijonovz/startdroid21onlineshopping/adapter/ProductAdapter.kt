package uz.alijonovz.startdroid21onlineshopping.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.alijonovz.startdroid21onlineshopping.databinding.ProductItemLayoutBinding
import uz.alijonovz.startdroid21onlineshopping.model.ProductModel
import uz.alijonovz.startdroid21onlineshopping.screen.productdetail.ProductDetailActivity
import uz.alijonovz.startdroid21onlineshopping.utils.Constants

class ProductAdapter(var items: List<ProductModel>):RecyclerView.Adapter<ProductAdapter.ItemHolder>() {

    inner class ItemHolder(var binding: ProductItemLayoutBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(ProductItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        var item = items[position]

        holder.binding.cardView.setOnClickListener{
            val intent = Intent(it.context, ProductDetailActivity::class.java)
            intent.putExtra(Constants.EXTRA_DATA, item)
            it.context.startActivity(intent)
        }


        holder.binding.tvPrice.text = item.price
        holder.binding.tvCategory.text = item.category_id.toString()
        holder.binding.tvTitle.text = item.name

        Glide.with(holder.itemView.context).load(Constants.HOST_IMAGE+item.image).into(holder.binding.ivProduct)
    }

    override fun getItemCount(): Int {
        return items.count()
    }
}