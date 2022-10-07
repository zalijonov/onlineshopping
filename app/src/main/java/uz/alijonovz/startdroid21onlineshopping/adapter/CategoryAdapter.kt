package uz.alijonovz.startdroid21onlineshopping.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import uz.alijonovz.startdroid21onlineshopping.R
import uz.alijonovz.startdroid21onlineshopping.databinding.CategoryItemLayoutBinding
import uz.alijonovz.startdroid21onlineshopping.model.CategoryModel
import uz.alijonovz.startdroid21onlineshopping.utils.BaseAdapter

interface CategoryAdapterCallback {
    fun onClickItem(item: CategoryModel)
}

class CategoryAdapter(var items1: List<CategoryModel>, val callback: CategoryAdapterCallback) :
    BaseAdapter<CategoryItemLayoutBinding>(items1) {

    override fun getBinding(parent: ViewGroup): CategoryItemLayoutBinding =
        CategoryItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    override fun onBindViewHolder(
        holder: BaseAdapter<CategoryItemLayoutBinding>.ItemHolder<CategoryItemLayoutBinding>,
        position: Int
    ) {
        var item = items1[position]

        holder.binding.cardView.setOnClickListener {
            items1.forEach {
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
                    holder.itemView.context, R.color.colorPrimary
                )
            )
        } else {
            holder.binding.tvName.setTextColor(Color.BLACK)
            holder.binding.cardView.setCardBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context, R.color.bgWhite
                )
            )
        }
        holder.binding.tvName.text = item.title
    }

    override fun initItemData(item: Any) {

    }
}