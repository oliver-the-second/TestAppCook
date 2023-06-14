@file:Suppress("FunctionName", "DEPRECATION")

package com.ilhomsoliev.core.wrapper.utilities.adapter_delegates

import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil


fun ItemsAdapter(vararg args: ItemDelegate<out Any>): ItemsAdapter {
    val builder = MediatorItemDelegate.Builder()
    args.forEach {
        builder.addItemDelegate(it)
    }
    return ItemsAdapter(builder.build())
}

inline fun <reified I : Any> ItemDelegate(
    @LayoutRes layout: Int,
    diffUtil: DiffUtil.ItemCallback<I>,
    noinline itemViewHolderProducer: ItemViewHolderProducer<I>
): ItemDelegate<I> = ItemDelegate(I::class, layout, diffUtil, itemViewHolderProducer)