package com.example.citysearch.view

import android.os.Bundle
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.citysearch.R
import com.example.citysearch.databinding.CityCellBinding
import com.example.citysearch.databinding.FragmentHomeBinding
import com.example.citysearch.fetching.domain.City
import org.koin.android.ext.android.bind
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(), Observer<CitiesUIState>, ItemLayoutManger<City> {


    private val viewModel: CitiesViewModel by viewModel()

    private val binding: FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(requireActivity().layoutInflater)
    }

    private val adaptor: RcAdaptor<City> by lazy {
        RcAdaptor(this).apply { bindRecyclerView(binding.recyclerView) }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.citiesLiveData
            .observe(viewLifecycleOwner, this)
        binding.searchView.doOnTextChanged { query, _, _, _ ->
            viewModel.search(query.toString())
        }
        adaptor
    }


    override fun onRcAdapterReady() {
        viewModel.fetchCities()
    }

    override fun getLayoutId(position: Int): Int {
        return R.layout.city_cell
    }

    override fun bindView(view: View, position: Int, item: City) {
        val binding = DataBindingUtil.bind<CityCellBinding>(view)!!
        binding.city = item
        binding.root.tag = item
        binding.root.setOnClickListener(::navigateToMap)

    }

    override fun onChanged(state: CitiesUIState) {

        binding.progressbar.visibility = if (state.loading) View.VISIBLE else View.GONE
        adaptor.update(state.cities, state.isUpdates)
        showErrorMessage(state.errorMessage)
        stateRendered(state)
    }


    private fun navigateToMap(view: View) {
        val city = view.tag as City
        val action = HomeFragmentDirections.actionHomeFragmentToMapFragment(city)
        findNavController().navigate(action)
    }


    private fun showErrorMessage(message: String?) {
        message?.let { Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show() }
    }

    private fun stateRendered(state: CitiesUIState) {
        if (state.isUpdates || state.errorMessage != null)
            viewModel.stateRendered()
    }
}
