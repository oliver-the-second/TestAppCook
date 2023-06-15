package com.ilhomsoliev.basket.presentation

import android.graphics.BitmapFactory
import android.view.View
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.ilhomsoliev.core.wrapper.utilities.adapter_delegates.ItemViewHolder
import com.ilhomsoliev.domain.model.CategoryModel
import com.ilhomsoliev.domain.model.DishModel
import com.ilhomsoliev.testappcook.feature.basket.databinding.DishBasketItemBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class DishBasketHolder(
    view: View,
    private val onClick: OnClickListener,
) : ItemViewHolder<DishModel>(view) {

    val binding = DishBasketItemBinding.bind(view)

    init { setupOnClickListener() }

    override fun onBind(item: DishModel): Unit = with(binding) {
        ViewCompat.setTransitionName(dishItemBasketName, "${item.id}title")
        dishItemBasketName.text = item.name
        dishItemBasketPrice.text = "${item.price} ₽"

        val url = URL(item.image_url)
        GlobalScope.launch(Dispatchers.IO) {
            val bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream())
            withContext(Dispatchers.Main) {
                dishBasketItemImageView.setImageBitmap(bmp)
            }
        }
    }

    private fun setupOnClickListener() =
        binding.root.setOnClickListener { onClick.onItemClick(this) }

}

fun RecyclerView.ViewHolder.asToDoHolder() = this as DishBasketHolder

interface OnClickListener {
    fun onItemClick(holder: DishBasketHolder)
}