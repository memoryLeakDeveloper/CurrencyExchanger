package com.currency.exchanger.ui.fragment.popular

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
import com.currency.exchanger.databinding.FragmentPopularBinding
import com.currency.exchanger.ui.fragment.popular.PopularFragment.AddFavouriteCallback
import com.currency.exchanger.utils.DataStoreManager
import com.currency.exchanger.utils.showToastLong
import com.currency.exchanger.utils.toGone
import com.currency.exchanger.utils.toVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PopularFragment : Fragment() {

    private lateinit var binding: FragmentPopularBinding
    private val viewModel: PopularViewModel by viewModels()
    private var spinnerAdapter: ArrayAdapter<String>? = null
    private var adapter: PopularAdapter? = null
    private var lastSortPref = 3
    private var mapPopular = mutableMapOf<String, Double>()
    private var listFavourites = mutableListOf<String>()
    private val callback = AddFavouriteCallback {
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.saveFavourite(FavouriteData(it))
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentPopularBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iniView()
        observeSortPref()
    }

    private fun iniView() {
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
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.dataStoreManager.getFromDataStore().asLiveData().observe(viewLifecycleOwner) { preferences ->
                preferences[DataStoreManager.NAME]?.let { pref ->
                    if (mapPopular.isNotEmpty() && lastSortPref != pref) {
                        lastSortPref = pref
                        val list = mapPopular.map {
                            CurrencyData(it.key, it.value, listFavourites.contains(it.key))
                        }
                        val result = sortListByPref(list)
                        adapter?.repopulateData(result)
                        binding.recycler.scrollToPosition(0)
                    }
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

    private fun observe() {
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.getAllFavourite().observe(viewLifecycleOwner) { it ->
                listFavourites = it.map { it.name }.toMutableList()
                val list = mapPopular.map {
                    CurrencyData(
                        it.key,
                        it.value,
                        listFavourites.contains(it.key)
                    )
                }
                val result = sortListByPref(list)
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
                            mapPopular = rates.toMutableMap()
                            val list = rates.map {
                                CurrencyData(it.key, it.value, listFavourites.contains(it.key))
                            }
                            val result = sortListByPref(list)
                            adapter?.repopulateData(result) ?: run {
                                binding.recycler.layoutManager = LinearLayoutManager(requireContext())
                                adapter = PopularAdapter(result, callback)
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
    }

    private fun getNewRates(currency: String) {
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.getPopularCurrency(currency)
        }
    }

    private fun sortListByPref(list: List<CurrencyData>): List<CurrencyData> {
        val result = mutableListOf<CurrencyData>()
        when (lastSortPref) {
            1 -> result.addAll(list.sortedBy { it.rate })
            2 -> result.addAll(list.sortedBy { it.rate }.reversed())
            3 -> result.addAll(list.sortedBy { it.name })
            4 -> result.addAll(list.sortedBy { it.name }.reversed())
        }
        return result
    }

    fun interface AddFavouriteCallback {
        fun add(name: String)
    }
}