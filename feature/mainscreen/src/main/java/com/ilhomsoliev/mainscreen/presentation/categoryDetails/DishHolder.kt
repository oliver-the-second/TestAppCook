package com.ilhomsoliev.mainscreen.presentation.categoryDetails

import android.graphics.BitmapFactory
import android.view.View
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.ilhomsoliev.core.wrapper.utilities.adapter_delegates.ItemViewHolder
import com.ilhomsoliev.domain.model.DishModel
import com.ilhomsoliev.testappcook.feature.mainscreen.databinding.DishItemBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class DishHolder(
    view: View,
    private val onClick: OnDishClickListener,
) : ItemViewHolder<DishModel>(view) {

    val binding = DishItemBinding.bind(view)

    init {
        setupOnClickListener()
    }

    override fun onBind(item: DishModel): Unit = with(binding) {
        ViewCompat.setTransitionName(dishItemNameTextView, "${item}title")
        dishItemNameTextView.text = item.name
        item.image_url?.let {
            val url = URL(item.image_url)
            GlobalScope.launch(Dispatchers.IO) {
                val bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream())
                withContext(Dispatchers.Main) {
                    dishItemImageView.setImageBitmap(bmp)
                }
            }
        }
    }

    private fun setupOnClickListener() =
        binding.root.setOnClickListener { onClick.onItemClick(this) }

}

fun RecyclerView.ViewHolder.asDishHolder() = this as DishHolder

interface OnDishClickListener {
    fun onItemClick(holder: DishHolder)
}