package com.ilhomsoliev.core.wrapper.utilities.adapter_delegates

import android.view.View
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import kotlin.reflect.KClass

typealias ItemViewHolderProducer<I> = (view: View) -> ItemViewHolder<I>

class ItemDelegate<I : Any> @Deprecated("") constructor(
    val itemClass: KClass<I>,
    @LayoutRes val layout: Int,
    val diffUtil: DiffUtil.ItemCallback<I>,
    val itemViewHolderProducer: ItemViewHolderProducer<I>
)