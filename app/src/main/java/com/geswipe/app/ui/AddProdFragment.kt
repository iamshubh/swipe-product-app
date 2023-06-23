package com.geswipe.app.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.geswipe.app.R
import com.geswipe.app.databinding.FragmentAddProdBinding

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

    private fun initialize() {}

    private fun observers() {}

    private fun listeners() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}