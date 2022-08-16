package uz.alijonovz.startdroid21onlineshopping.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import uz.alijonovz.startdroid21onlineshopping.R
import uz.alijonovz.startdroid21onlineshopping.databinding.CategoryItemLayoutBinding
import uz.alijonovz.startdroid21onlineshopping.model.CategoryModel

interface CategoryAdapterCallback {
    fun onClickItem(item: CategoryModel)
}

class CategoryAdapter(var items: List<CategoryModel>, val callback: CategoryAdapterCallback) :
    RecyclerView.Adapter<CategoryAdapter.ItemHolder>() {

    inner class ItemHolder(var binding: CategoryItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            CategoryItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        var item = items[position]

        holder.binding.cardView.setOnClickListener {
            items.forEach {
                it.checked = false
            }
            item.checked = true

            callback.onClickItem(item)
            notifyDataSetChanged()
        }
        if (item.checked) {
            holder.binding.tvName.setTextColor(Color.WHITE)
            holder.binding.cardView.setCardBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.colorPrimary
                )
            )
        } else {
            holder.binding.tvName.setTextColor(Color.BLACK)
            holder.binding.cardView.setCardBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.bgWhite
                )
            )
        }
        holder.binding.tvName.text = item.title
    }

    override fun getItemCount(): Int {
        return items.count()
    }
}