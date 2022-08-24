package com.example.citysearch.fetching.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.citysearch.databinding.FragmentMapBinding
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mapView: MapView

    private val args: MapFragmentArgs by navArgs()
    private lateinit var mMap: GoogleMap
    private val position: LatLng by lazy {
        LatLng(args.city.coordinates.latitude, args.city.coordinates.longitude)
    }
    private val cityName:String by lazy {
        args.city.cityName
    }
    private val binding: FragmentMapBinding by lazy {
        FragmentMapBinding.inflate(requireActivity().layoutInflater)
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
        binding.mapView.apply { onCreate(savedInstanceState) }.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {


        with(googleMap) {
            // Add a marker in Sydney and move the camera
            addMarker(
                MarkerOptions()
                    .position(position)
                    .title(cityName)
            )
            moveCamera(CameraUpdateFactory.newLatLng(position))
        }
    }


    override fun onResume() {
        binding.mapView.onResume()
        super.onResume()
    }


    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }
}