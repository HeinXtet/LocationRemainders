package com.udacity.project4.ui.map

import android.annotation.SuppressLint
import android.content.res.Resources
import android.location.Address
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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
import com.udacity.project4.ui.addRemainder.AddRemainderViewModel
import com.udacity.project4.utils.LocationHandler
import com.udacity.project4.utils.showSnackBar
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
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

    private val viewModel: MapViewModel by inject()
    private val addRemainderViewModel: AddRemainderViewModel by inject()
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
                if (viewModel.selectPoi.value != null) {
                    addRemainderViewModel.updatePOI(viewModel.selectPoi.value!!)
                    findNavController().popBackStack()
                }
//                setBackStackData(AddNewRemainderFragment.SELECTED_POI, viewModel.selectPoi.value!!)
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
                getUserCurrentLocation()
            }
        })
        return binding.root
    }

    @SuppressLint("MissingPermission")
    private fun getUserCurrentLocation() {
        map.isMyLocationEnabled = true
        if (viewModel.selectPoi.value == null) {
            locationHandler.requestLocation()
        }
        map.setOnMyLocationClickListener {
            requestForegroundPermission()
        }
    }

    private fun requestForegroundPermission() {
        (requireActivity() as MainActivity).requestForegroundLocationPermission(
            onPermissionGranted = {
                viewModel.updatePermissionStatus(true)
            },
            onPermissionDenied = {
                binding.root.showSnackBar(getString(R.string.text_unable_to_get_location))
            })
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
            requestForegroundPermission()
        }
    }


    private fun checkUserLocation() {
        if ((requireActivity() as MainActivity).hasLocationPermissions()) {
            viewModel.updatePermissionStatus(true)
        }
    }

    @SuppressLint("MissingPermission")
    private fun setupMap() {
        checkUserLocation()
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
        map.setOnMapClickListener { latLng ->
            updateSelectedLocation(latLng = latLng)
        }
        map.setOnPoiClickListener { updateSelectedLocation(latLng = it.latLng, name = it.name) }
    }

    private fun updateSelectedLocation(latLng: LatLng, name: String = "") {
        map.clear()
        val snippet = String.format(
            Locale.getDefault(),
            "Lat: %1$.5f, Long: %2$.5f",
            latLng.latitude,
            latLng.longitude
        )

        val poiMarker = map.addMarker(
            MarkerOptions()
                .position(latLng)
                .title("Selected Locations")
                .snippet(snippet)
        )
        poiMarker.showInfoWindow()
        val address = Address(Locale.ENGLISH)
        address.featureName = snippet
        if (name.isNotEmpty()) {
            address.featureName = name
        }
        viewModel.updatePoi(Point(latLng, address))
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