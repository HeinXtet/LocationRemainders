package com.udacity.project4.ui.map

import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.udacity.project4.R
import com.udacity.project4.databinding.FragmentMapBinding
import com.udacity.project4.ui.addRemainder.AddNewRemainderFragment
import com.udacity.project4.ui.base.BaseFragment
import com.udacity.project4.utils.setBackStackData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.udacity.project4.MainActivity
import com.udacity.project4.domain.model.Point
import com.udacity.project4.geofence.GeofenceUtils
import com.udacity.project4.utils.LocationHandler
import timber.log.Timber
import java.util.*

/**
 * Created by heinhtet deevvdd@gmail.com on 19,July,2021
 */
class MapFragment : BaseFragment(), OnMapReadyCallback {

    private lateinit var binding: FragmentMapBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var map: GoogleMap

    private lateinit var locationHandler: LocationHandler

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
        locationHandler = LocationHandler(requireActivity() as MainActivity, {
            val currentLocation = LatLng(it.latitude, it.longitude)
            zoomToUserLocation(currentLocation)

        }, {})
        viewModel.isPermissionGranted.observe(viewLifecycleOwner, {
            if (it) {
                setupMap()
                locationHandler.requestLocation()

            }
        })
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
        }
    }

    override fun onStart() {
        super.onStart()
        checkUserLocation()
    }

    private fun checkUserLocation() {
        (requireActivity() as MainActivity).checkPermissionsAndStartGeofencing(
            onPermissionGranted = {
                viewModel.updatePermissionStatus(true)
            },
            onPermissionDenied = {
                viewModel.updatePermissionStatus(false)
            })
    }

    @SuppressLint("MissingPermission")
    private fun setupMap() {

        try {
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
        map.setOnMapClickListener { latLng ->
            map.clear()
            val snippet = String.format(
                Locale.getDefault(),
                "Lat: %1$.5f, Long: %2$.5f",
                latLng.latitude,
                latLng.longitude
            )
            val address = GeofenceUtils.getAddress(
                requireContext(),
                latitude = latLng.latitude,
                longitude = latLng.longitude
            )
            val poiMarker = map.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .title(address?.featureName ?: "")
                    .snippet(snippet)
            )
            poiMarker.showInfoWindow()
            viewModel.updatePoi(Point(latLng, address))
        }
    }

    @SuppressLint("MissingPermission")
    private fun zoomToUserLocation(latLng: LatLng) {
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
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