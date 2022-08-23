package com.example.citysearch.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.citysearch.R
import com.example.citysearch.databinding.FragmentMapBinding
import org.koin.android.ext.android.bind

class MapFragment : Fragment() {

    private val args: MapFragmentArgs by navArgs()
    private val binding: FragmentMapBinding by lazy {
        FragmentMapBinding.inflate(requireActivity().layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root//inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val coord = " ${args.coordinates.longitude}, ${args.coordinates.latitude}"
        binding.txt.text = coord
    }
}