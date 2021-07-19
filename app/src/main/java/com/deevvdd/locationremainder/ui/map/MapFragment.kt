package com.deevvdd.locationremainder.ui.map

import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.deevvdd.locationremainder.R
import com.deevvdd.locationremainder.databinding.FragmentMapBinding
import com.deevvdd.locationremainder.ui.addRemainder.AddNewRemainderFragment
import com.deevvdd.locationremainder.ui.base.BaseFragment
import com.deevvdd.locationremainder.utils.setBackStackData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import timber.log.Timber
import java.util.*


/**
 * Created by heinhtet deevvdd@gmail.com on 19,July,2021
 */
class MapFragment : BaseFragment(), OnMapReadyCallback {

    private lateinit var binding: FragmentMapBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var map: GoogleMap
    private val viewModel: MapViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_map, container, false)
        binding.apply {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner
            btnSave.setOnClickListener {
                setBackStackData(AddNewRemainderFragment.SELECTED_POI, viewModel.selectPoi.value!!)
            }
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap?) {
        p0?.let {
            map = it
            setupMap()
        }
    }

    @SuppressLint("MissingPermission")
    private fun setupMap() {

        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            val success: Boolean = map.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    requireActivity(), R.raw.map_styled
                )
            )
            if (!success) {
                Timber.d("Style parsing failed.")
            }
        } catch (e: Resources.NotFoundException) {
            Timber.d("Can't find style. Error: $e")
        }

        map.isMyLocationEnabled = true
        zoomToUserLocation()
        map.setOnPoiClickListener { poi ->
            map.clear()
            val snippet = String.format(
                Locale.getDefault(),
                "Lat: %1$.5f, Long: %2$.5f",
                poi.latLng.latitude,
                poi.latLng.longitude
            )
            val poiMarker = map.addMarker(
                MarkerOptions()
                    .position(poi.latLng)
                    .title(poi.name)
                    .snippet(snippet)
            )
            poiMarker.showInfoWindow()
            viewModel.updatePoi(poi)
        }

    }

    @SuppressLint("MissingPermission")
    private fun zoomToUserLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener {
            val currentLocation = LatLng(it.latitude, it.longitude)
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15f))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.map_options, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.normal_map -> {
            map.mapType = GoogleMap.MAP_TYPE_NORMAL
            true
        }
        R.id.hybrid_map -> {
            map.mapType = GoogleMap.MAP_TYPE_HYBRID
            true
        }
        R.id.satellite_map -> {
            map.mapType = GoogleMap.MAP_TYPE_SATELLITE
            true
        }
        R.id.terrain_map -> {
            map.mapType = GoogleMap.MAP_TYPE_TERRAIN
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

}