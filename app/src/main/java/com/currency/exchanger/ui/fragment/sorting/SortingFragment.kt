package com.currency.exchanger.ui.fragment.sorting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.currency.exchanger.R
import com.currency.exchanger.databinding.FragmentSortingBinding
import com.currency.exchanger.ui.fragment.popular.PopularViewModel
import com.currency.exchanger.utils.SharedPreferences.sorting
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SortingFragment : Fragment() {

    private lateinit var binding: FragmentSortingBinding
    private val viewModel: PopularViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSortingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val checkedRadioButton = when (requireContext().sorting) {
            1 -> R.id.radio_min_to_max
            2 -> R.id.radio_max_to_min
            3 -> R.id.radio_abc
            else -> R.id.radio_cba
        }
        binding.group.check(checkedRadioButton)
        binding.group.setOnCheckedChangeListener { _, button ->
            when (button) {
                R.id.radio_min_to_max -> {
                    requireContext().sorting = 1
                }
                R.id.radio_max_to_min -> {
                    requireContext().sorting = 2
                }
                R.id.radio_abc -> {
                    requireContext().sorting = 3
                }
                R.id.radio_cba -> {
                    requireContext().sorting = 4
                }
            }
        }
    }
}