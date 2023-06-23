package com.geswipe.app.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.geswipe.app.R
import com.geswipe.app.databinding.FragmentAddProdBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class AddProdFragment : Fragment() {

    companion object {
        fun newInstance() = AddProdFragment()
    }

    private val viewModel: AddProdViewModel by viewModels()

    private var _binding: FragmentAddProdBinding? = null
    val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddProdBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
        observers()
        listeners()
    }

    private fun initialize() {

    }

    private fun observers() {
        viewModel.addProductResponse.observe(viewLifecycleOwner) {
            if (it == true) {
                lifecycleScope.launch(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Success, Product added", Toast.LENGTH_SHORT)
                        .show()
                    delay(300)
                    resetUI()
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    "Something went wrong! please try again",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }

        viewModel.validation.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                binding.validText.text = it
                lifecycleScope.launch(Dispatchers.Main) {
                    delay(2000)
                    //binding.validText.text = ""
                }
            } else {
                binding.validText.text = ""
            }
        }
    }

    private fun resetUI() {
        binding.apply {
            nameText.setText("")
            typeText.setText("")
            priceText.setText("")
            taxText.setText("")
            imageText.setText("")
        }
    }

    private fun listeners() {
        binding.addButton.setOnClickListener {
            val name = binding.nameText.text.toString()
            val type = binding.typeText.text.toString()
            val price = binding.priceText.text.toString()
            val tax = binding.taxText.text.toString()
            val image = binding.imageText.text.toString()

            viewModel.addProduct(name, type, price, tax, image)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}