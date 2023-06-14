package com.ilhomsoliev.mainscreen.presentation.main

import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ilhomsoliev.core.wrapper.utilities.adapter_delegates.ItemDelegate
import com.ilhomsoliev.core.wrapper.utilities.adapter_delegates.ItemDiffUtil
import com.ilhomsoliev.core.wrapper.utilities.adapter_delegates.ItemsAdapter
import com.ilhomsoliev.core.wrapper.utilities.fragment.repeatWhenViewStarted
import com.ilhomsoliev.core.wrapper.utilities.postDelayed
import com.ilhomsoliev.domain.model.CategoryModel
import com.ilhomsoliev.mainscreen.viewmodel.MainViewModel
import com.ilhomsoliev.testappcook.feature.mainscreen.R
import com.ilhomsoliev.testappcook.feature.mainscreen.databinding.FragmentMainBinding
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MainFragment : Fragment() {

    private val vm: MainViewModel by sharedViewModel()

    private lateinit var binding: FragmentMainBinding

    private val controller by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
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
            layout = R.layout.category_item,
            diffUtil = ItemDiffUtil(itemsTheSamePointer = CategoryModel::name),
            itemViewHolderProducer = {
                CategoryHolder(it, object : OnClickListener {
                    override fun onItemClick(holder: CategoryHolder) {
                        holder.binding.imageViewCategoryItem.setOnClickListener {
                            val request = NavDeepLinkRequest.Builder
                                .fromUri("TestAppCook:category_details/${holder.item.name}".toUri())
                                .build()
                            controller.navigate(request)
                        }
                    }
                })
            }
        )

        val adapter = ItemsAdapter(toDoDelegate)
        return adapter
    }

    private fun setupRecyclerView(adapter: ItemsAdapter) = with(binding) {
        list.adapter = adapter
        list.layoutManager =  LinearLayoutManager(binding.root.context) // StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
    }

    private fun observeUiState(adapter: ItemsAdapter) = repeatWhenViewStarted {
        vm.state.collectLatest { state->
            adapter.submitList(state.categories)
            binding.list.visibility = if(state.isLoading) View.INVISIBLE else View.VISIBLE
            binding.loader.visibility = if(state.isLoading) View.VISIBLE else View.GONE
            // TODO progess
          //  renderResult(it)

            /*lifecycleScope.launchWhenStarted {
                vm.categories.collectLatest {
                    binding.list.adapter = adapter
                    binding.list.layoutManager = LinearLayoutManager(binding.root.context)
                }
            }*/

        }
    }
}