package com.ilhomsoliev.basket.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ilhomsoliev.basket.viewmodel.BasketViewModel
import com.ilhomsoliev.core.wrapper.utilities.adapter_delegates.ItemDelegate
import com.ilhomsoliev.core.wrapper.utilities.adapter_delegates.ItemDiffUtil
import com.ilhomsoliev.core.wrapper.utilities.adapter_delegates.ItemsAdapter
import com.ilhomsoliev.core.wrapper.utilities.fragment.repeatWhenViewStarted
import com.ilhomsoliev.core.wrapper.utilities.postDelayed
import com.ilhomsoliev.domain.model.CategoryModel
import com.ilhomsoliev.domain.model.DishModel
import com.ilhomsoliev.testappcook.feature.basket.R
import com.ilhomsoliev.testappcook.feature.basket.databinding.FragmentBasketBinding
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class BasketFragment : Fragment() {

    private lateinit var binding : FragmentBasketBinding

    private val vm: BasketViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBasketBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = setupAdapter()
        setupRecyclerView(adapter)

        viewLifecycleOwner.postDelayed(500) {
            observeUiState(adapter)
        }
    }
    private fun setupAdapter(): ItemsAdapter {
        val toDoDelegate = ItemDelegate(
            layout = R.layout.dish_basket_item,
            diffUtil = ItemDiffUtil(itemsTheSamePointer = DishModel::id),
            itemViewHolderProducer = {
                DishBasketHolder(it, object : OnClickListener {
                    override fun onItemClick(holder: DishBasketHolder) {

                    }
                })
            }
        )

        val adapter = ItemsAdapter(toDoDelegate)
        return adapter
    }

    private fun setupRecyclerView(adapter: ItemsAdapter) = with(binding) {
        list.adapter = adapter
        list.layoutManager =  LinearLayoutManager(binding.root.context)
    }

    private fun observeUiState(adapter: ItemsAdapter) = repeatWhenViewStarted {
        vm.state.collectLatest { state->
            adapter.submitList(state.dishes)
            Log.d("Hello", state.toString())
        }
    }
}