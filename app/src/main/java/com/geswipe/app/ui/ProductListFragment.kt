package com.geswipe.app.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.geswipe.app.R
import com.geswipe.app.data.model.ProductItem
import com.geswipe.app.databinding.FragmentProductListBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * This screen deals with listing of product
 */
@AndroidEntryPoint
class ProductListFragment : Fragment() {

    companion object {
        fun newInstance() = ProductListFragment()
    }

    private val viewModel: ProductListViewModel by viewModels()


    private var _binding: FragmentProductListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //initialize()
        observers()
        listeners()
    }

    private fun initialize() {
        viewModel.loadProducts()
    }

    override fun onStart() {
        super.onStart()
        initialize()
    }


    private fun observers() {
        viewModel.uiResponse.observe(viewLifecycleOwner) {
            it.render()
        }

        viewModel.filterResponse.observe(viewLifecycleOwner) {
            renderProducts(it)
        }
    }

    private fun listeners() {
        binding.buttonRetry.setOnClickListener {
            viewModel.loadProducts()
        }

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addFragment)
        }

        binding.searchButton.setOnClickListener {
            val query = binding.searchText.text
            if (query.isNullOrBlank()) {
                Toast.makeText(requireContext(), "Please enter search query", Toast.LENGTH_SHORT)
                    .show()
                viewModel.searchProducts("")
            } else {
                viewModel.searchProducts(query.toString())
            }
        }
    }

    private fun ProductListViewModel.ProductsUiState?.render() {
        when (this) {
            is ProductListViewModel.ProductsUiState.Loading -> showLoader()
            is ProductListViewModel.ProductsUiState.Success -> {
                hideLoader()
                if (data.isEmpty()) {
                    showEmptyView()
                } else {
                    renderProducts(data)
                }
            }

            else -> {
                hideLoader()
                showErrorScreen()
            }
        }
    }

    private fun showEmptyView() {
        binding.containerProducts.isVisible = false
        binding.emptyView.isVisible = true
    }

    private fun showLoader() {
        binding.loaderProducts.isVisible = true
        binding.containerError.isVisible = false
    }

    private fun hideLoader() {
        binding.loaderProducts.isVisible = false
    }

    private fun renderProducts(items: List<ProductItem>) {
        binding.emptyView.isVisible = false
        binding.containerProducts.isVisible = true
        binding.rvProducts.apply {
            adapter = RecyclerViewProductAdapter(items)
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun showErrorScreen() {
        binding.containerProducts.isVisible = false
        binding.containerError.isVisible = true
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}