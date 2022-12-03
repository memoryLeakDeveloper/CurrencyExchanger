package com.currency.exchanger.ui.fragment.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.currency.exchanger.R
import com.currency.exchanger.data.currency.CurrencyData
import com.currency.exchanger.data.currency.CurrencyResponse
import com.currency.exchanger.data.favourite.FavouriteData
import com.currency.exchanger.databinding.FragmentFavouriteBinding
import com.currency.exchanger.ui.fragment.favourite.FavouriteFragment.DeleteFavouriteCallback
import com.currency.exchanger.utils.DataStoreManager
import com.currency.exchanger.utils.showToastLong
import com.currency.exchanger.utils.toGone
import com.currency.exchanger.utils.toVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavouriteFragment : Fragment() {

    private lateinit var binding: FragmentFavouriteBinding
    private val viewModel: FavouriteViewModel by viewModels()
    private var adapter: FavouriteAdapter? = null
    private var spinnerAdapter: ArrayAdapter<String>? = null
    private val callback = DeleteFavouriteCallback {
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.deleteFavourite(FavouriteData(it))
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeSortPref()
    }

    private fun initView() {
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.getPopularCurrency(null).observe(viewLifecycleOwner) { response ->
                when (response) {
                    is CurrencyResponse.Loading -> {
                        binding.progressBar.toVisible()
                    }
                    is CurrencyResponse.Success -> {
                        if (response.data.rates?.isNotEmpty() == true) {
                            if (spinnerAdapter == null)
                                initSpinner(response.data.rates.map { it.key }.toMutableList())
                        }
                        observe()
                    }
                    is CurrencyResponse.Failure -> {
                        requireContext().showToastLong("Ошибка")
                    }
                }
            }
        }
        binding.sorting.setOnClickListener {
            findNavController().navigate(R.id.action_currencyFragment_to_sortingFragment)
        }
    }

    private fun observeSortPref() {
        viewModel.dataStoreManager.getFromDataStore().asLiveData().observe(viewLifecycleOwner) { preferences ->
            preferences[DataStoreManager.NAME]?.let { pref ->
                if (viewModel.mapPopular.isNotEmpty() && viewModel.lastSortPref != pref) {
                    viewModel.lastSortPref = pref
                    val list = viewModel.sortListContainsInDb()
                    adapter?.repopulateData(viewModel.sortListByPref(list))
                }
            }
        }
    }

    private fun observe() {
        viewModel.getAllFavourite().observe(viewLifecycleOwner) { it ->
            viewModel.listFavourites = it.map { it.name }.toMutableList()
            val list = viewModel.sortListContainsInDb()
            val result = viewModel.sortListByPref(list)
            adapter?.repopulateData(result)
        }
        viewModel.popularLiveData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is CurrencyResponse.Loading -> {
                    binding.progressBar.toVisible()
                }
                is CurrencyResponse.Success -> {
                    val rates = response.data.rates
                    if (rates?.isNotEmpty() == true) {
                        viewModel.mapPopular = rates.toMutableMap()
                        val list = viewModel.sortListContainsInDb()
                        val result = viewModel.sortListByPref(list)
                        adapter?.repopulateData(result) ?: run {
                            binding.recycler.layoutManager = LinearLayoutManager(requireContext())
                            adapter = FavouriteAdapter(result, callback)
                            binding.recycler.adapter = adapter
                        }
                        binding.progressBar.toGone()
                    }
                }
                is CurrencyResponse.Failure -> {
                    requireContext().showToastLong("Ошибка")
                    binding.progressBar.toGone()
                }
            }
        }
    }

    private fun initSpinner(list: MutableList<String>) {
        spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, list)
        spinnerAdapter!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = spinnerAdapter
        val listener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                getNewRates(p0?.getItemAtPosition(p2).toString())
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
        binding.spinner.onItemSelectedListener = listener
        binding.spinner.setSelection(list.indexOf("EUR"))
    }

    private fun getNewRates(currency: String) {
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.getPopularCurrency(currency)

        }
    }

    fun interface DeleteFavouriteCallback {
        fun delete(name: String)
    }
}