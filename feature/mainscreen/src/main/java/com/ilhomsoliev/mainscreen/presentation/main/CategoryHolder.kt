package com.ilhomsoliev.mainscreen.presentation.main

import android.graphics.BitmapFactory
import android.view.View
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.ilhomsoliev.core.wrapper.utilities.adapter_delegates.ItemViewHolder
import com.ilhomsoliev.domain.model.CategoryModel
import com.ilhomsoliev.testappcook.feature.mainscreen.databinding.CategoryItemBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class CategoryHolder(
    view: View,
    private val onClick: OnClickListener,
) : ItemViewHolder<CategoryModel>(view) {

    val binding = CategoryItemBinding.bind(view)

    init { setupOnClickListener() }

    override fun onBind(item: CategoryModel): Unit = with(binding) {
        ViewCompat.setTransitionName(textViewCategotyItem, "${item.id}title")
        textViewCategotyItem.text = item.name
        val url = URL(item.image_url)
        GlobalScope.launch(Dispatchers.IO) {
            val bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream())
            withContext(Dispatchers.Main) {
                imageViewCategoryItem.setImageBitmap(bmp)
            }
        }
    }

    private fun setupOnClickListener() =
        binding.root.setOnClickListener { onClick.onItemClick(this) }

}

fun RecyclerView.ViewHolder.asToDoHolder() = this as CategoryHolder

interface OnClickListener {
    fun onItemClick(holder: CategoryHolder)
}