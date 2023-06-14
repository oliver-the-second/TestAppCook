package com.ilhomsoliev.mainscreen.presentation.categoryDetails

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.ilhomsoliev.core.wrapper.utilities.adapter_delegates.ItemViewHolder
import com.ilhomsoliev.testappcook.feature.mainscreen.R
import com.ilhomsoliev.testappcook.feature.mainscreen.databinding.TagItemBinding

class TagHolder(
    view: View,
    private val onClick: OnTagClickListener,
) : ItemViewHolder<Tag>(view) {

    val binding = TagItemBinding.bind(view)

    init {
        setupOnClickListener()
    }

    override fun onBind(item: Tag): Unit = with(binding) {
        ViewCompat.setTransitionName(tagItemNameTextView, "${item}title")
        tagItemNameTextView.text = item.name
        if(item.isActive){
            tagItemCardView.background =  ContextCompat.getDrawable(this.root.context, R.drawable.active_tag_item_background)
            tagItemNameTextView.setTextColor(ContextCompat.getColor(this.root.context, R.color.white))
        }else {
            tagItemCardView.background =  ContextCompat.getDrawable(this.root.context, R.drawable.inactive_tag_item_background)
            tagItemNameTextView.setTextColor(ContextCompat.getColor(this.root.context, R.color.black))
        }
    }

    private fun setupOnClickListener() =
        binding.root.setOnClickListener { onClick.onItemClick(this) }

}

fun RecyclerView.ViewHolder.asTagHolder() = this as TagHolder

interface OnTagClickListener {
    fun onItemClick(holder: TagHolder)
}

data class Tag(
    val id: Int,
    val name: String,
    val isActive: Boolean,
)