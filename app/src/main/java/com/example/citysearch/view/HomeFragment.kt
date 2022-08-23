package com.example.citysearch.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.citysearch.R
import com.example.citysearch.databinding.CityCellBinding
import com.example.citysearch.databinding.FragmentHomeBinding
import com.example.citysearch.domain.City
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
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.citiesLiveData.observe(viewLifecycleOwner, this)
        viewModel.fetchCities()
    }

    override fun onChanged(state: CitiesUIState) {
        binding.progressbar.visibility = if (state.loading) View.VISIBLE else View.GONE
        adaptor.setItems(state.cities)

        state.errorMessage?.let {
            Toast.makeText(requireContext(), state.errorMessage, Toast.LENGTH_SHORT).show()
            viewModel.errorMessageShown()
        }
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

    private fun navigateToMap(view: View) {
        val city = view.tag as City
        val action = HomeFragmentDirections.actionHomeFragmentToMapFragment(city)
        findNavController().navigate(action)
    }
}