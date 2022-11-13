package com.currency.exchanger.ui.fragment.sorting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.currency.exchanger.R
import com.currency.exchanger.databinding.FragmentSortingBinding
import com.currency.exchanger.utils.DataStoreManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SortingFragment : Fragment() {

    private lateinit var binding: FragmentSortingBinding
    private val viewModel: SortingViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSortingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.dataStoreManager.getFromDataStore().asLiveData().observe(viewLifecycleOwner) {
                val checkedRadioButton =
                    when (it[DataStoreManager.NAME]) {
                        1 -> R.id.radio_min_to_max
                        2 -> R.id.radio_max_to_min
                        3 -> R.id.radio_abc
                        else -> R.id.radio_cba
                    }
                binding.group.check(checkedRadioButton)
            }
        }
        binding.group.setOnCheckedChangeListener { _, button ->
            when (button) {
                R.id.radio_min_to_max -> {
                    lifecycleScope.launch(Dispatchers.Main) {
                        viewModel.dataStoreManager.saveToDataStore(1)
                    }
                }
                R.id.radio_max_to_min -> {
                    lifecycleScope.launch(Dispatchers.Main) {
                        viewModel.dataStoreManager.saveToDataStore(2)
                    }
                }
                R.id.radio_abc -> {
                    lifecycleScope.launch(Dispatchers.Main) {
                        viewModel.dataStoreManager.saveToDataStore(3)
                    }
                }
                R.id.radio_cba -> {
                    lifecycleScope.launch(Dispatchers.Main) {
                        viewModel.dataStoreManager.saveToDataStore(4)
                    }
                }
            }
        }
    }
}