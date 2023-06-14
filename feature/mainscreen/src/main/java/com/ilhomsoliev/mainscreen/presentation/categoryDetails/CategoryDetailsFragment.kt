package com.ilhomsoliev.mainscreen.presentation.categoryDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ilhomsoliev.core.wrapper.utilities.adapter_delegates.ItemDelegate
import com.ilhomsoliev.core.wrapper.utilities.adapter_delegates.ItemDiffUtil
import com.ilhomsoliev.core.wrapper.utilities.adapter_delegates.ItemsAdapter
import com.ilhomsoliev.core.wrapper.utilities.fragment.repeatWhenViewStarted
import com.ilhomsoliev.core.wrapper.utilities.postDelayed
import com.ilhomsoliev.domain.model.DishModel
import com.ilhomsoliev.mainscreen.viewmodel.CategoryDetailsViewModel
import com.ilhomsoliev.testappcook.feature.mainscreen.R
import com.ilhomsoliev.testappcook.feature.mainscreen.databinding.FragmentCategoryDetailsBinding
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CategoryDetailsFragment : Fragment() {

    private val vm: CategoryDetailsViewModel by sharedViewModel()

    private val controller by lazy { findNavController() }

    private lateinit var binding: FragmentCategoryDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryDetailsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val argument = arguments?.getString("category_name")
        binding.categoryNametextView.text = argument
        binding.categoryDeatilesBackIconImageView.setOnClickListener {
            controller.popBackStack()
        }
        val tagsAdapter = setupTagsAdapter()
        val dishesAdapter = setupDishesAdapter()

        setupTagsList(tagsAdapter)
        setupDishesList(dishesAdapter)

        viewLifecycleOwner.postDelayed(500) {
            observeUiState(tagsAdapter, dishesAdapter)
        }

    }

    private fun setupTagsList(adapter: ItemsAdapter) = with(binding) {
        tagsList.adapter = adapter
        tagsList.layoutManager = LinearLayoutManager(
            binding.root.context,
            RecyclerView.HORIZONTAL,
            false
        )
    }

    private fun setupDishesList(adapter: ItemsAdapter) = with(binding) {
        dishesList.adapter = adapter
        dishesList.layoutManager = GridLayoutManager(this@CategoryDetailsFragment.context, 3)
    }

    private fun setupTagsAdapter(): ItemsAdapter {
        val tagsDelegate = ItemDelegate(
            layout = R.layout.tag_item,
            diffUtil = ItemDiffUtil(itemsTheSamePointer = Tag::id),
            itemViewHolderProducer = {
                TagHolder(it, object : OnTagClickListener {
                    override fun onItemClick(holder: TagHolder) {
                        vm.onEvent(CategoryDetailsEvent.OnTagClick(holder.item.id))
                    }
                })
            }
        )

        val adapter = ItemsAdapter(tagsDelegate)
        return adapter
    }

    private fun setupDishesAdapter(): ItemsAdapter {
        val dishesDelegate = ItemDelegate(
            layout = R.layout.dish_item,
            diffUtil = ItemDiffUtil(itemsTheSamePointer = DishModel::id),
            itemViewHolderProducer = {
                DishHolder(it, object : OnDishClickListener {
                    override fun onItemClick(holder: DishHolder) {
                        val alert = ViewDialog()
                        with(holder.item) {
                            alert.showDialog(
                                activity = activity,
                                dishName = name,
                                imageUrl = image_url.toString(),
                                dishPrice = price.toString() + " ₽",
                                dishMass = weight.toString() + " г",
                                dishDescription = description.toString(),
                                onClose = {

                                },
                                onAddToBasket = {
                                    vm.onEvent(CategoryDetailsEvent.OnAddToBasketClick(holder.item))
                                }
                            )
                        }
                    }
                })
            }
        )

        val adapter = ItemsAdapter(dishesDelegate)
        return adapter
    }

    private fun observeUiState(tagsAdapter: ItemsAdapter, dishesAdapter: ItemsAdapter) =
        repeatWhenViewStarted {
            vm.state.collectLatest { state ->
                tagsAdapter.submitList(state.tags.map { it.copy(isActive = it.id == state.activeTagId) })

                dishesAdapter.submitList(if (state.activeTagId == null) state.dishes else state.dishes.filter {
                    it.tegs?.contains(state.tags.first { it.id == state.activeTagId }.name) ?: false
                })

            }
        }
}