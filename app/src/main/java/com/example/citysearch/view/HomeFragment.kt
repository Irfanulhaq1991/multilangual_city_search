package com.example.citysearch.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.citysearch.R
import com.example.citysearch.databinding.FragmentHomeBinding
import com.example.citysearch.domain.Coordinates
import org.koin.android.ext.android.bind


class HomeFragment : Fragment() {

   private val binding: FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(requireActivity().layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root//inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.btnNav.setOnClickListener {
            val action =
                HomeFragmentDirections.actionHomeFragmentToMapFragment(Coordinates(0.0, 0.0))
            findNavController().navigate(action)
        }
    }
}