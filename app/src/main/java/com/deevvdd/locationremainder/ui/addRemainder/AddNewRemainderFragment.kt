package com.deevvdd.locationremainder.ui.addRemainder

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.deevvdd.locationremainder.R
import com.deevvdd.locationremainder.databinding.FragmentAddNewRemainderBinding
import com.deevvdd.locationremainder.geofence.GeofenceBroadcastReceiver
import com.deevvdd.locationremainder.geofence.GeofenceUtils
import com.deevvdd.locationremainder.ui.base.BaseFragment
import com.deevvdd.locationremainder.utils.getBackStackData
import com.deevvdd.locationremainder.utils.safeNavigate
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.PointOfInterest
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

/**
 * Created by heinhtet deevvdd@gmail.com on 19,July,2021
 */
@AndroidEntryPoint
class AddNewRemainderFragment : BaseFragment() {

    private val viewModel: AddRemainderViewModel by viewModels()
    private lateinit var binding: FragmentAddNewRemainderBinding

    private lateinit var geofencingClient: GeofencingClient

    private val geofencePendingIntent: PendingIntent by lazy {
        val intent =
            Intent(requireActivity().applicationContext, GeofenceBroadcastReceiver::class.java)
        intent.action = ACTION_GEOFENCE_EVENT
        PendingIntent.getBroadcast(
            requireActivity().applicationContext,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_add_new_remainder, container, false)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel
        }
        setupActions()
        // listen when selected POI from map
        getBackStackData<PointOfInterest>(SELECTED_POI) { data ->
            Toast.makeText(requireContext(), data.name, Toast.LENGTH_SHORT).show()
            viewModel.updatePOI(data)
        }
        viewModel.savedRemainderEvent.observe(viewLifecycleOwner, {
            Toast.makeText(
                requireContext(),
                getString(R.string.text_saved_remainder),
                Toast.LENGTH_SHORT
            ).show()
            addGeofence()
        })

        viewModel.showSnackBarInt.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let {
                Snackbar.make(binding.root, getString(it), 2000).show()
            }
        })
        viewModel.toastInt.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(
                    requireContext(),
                    getString(it),
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
        init()
        return binding.root
    }


    private fun setupActions() {
        with(binding) {
            btnLocation.setOnClickListener {
                findNavController().safeNavigate(AddNewRemainderFragmentDirections.actionAddNewRemainderToMapFragment())
            }
            fabAddRemainder.setOnClickListener {
                viewModel.addNewRemainder()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun addGeofence() {
        val geofenceData = viewModel.poi.value
        geofenceData?.let {
            Timber.d("Geofence place ID ${geofenceData.placeId}")
            val geofence = Geofence.Builder()
                .setRequestId(geofenceData.placeId)
                .setCircularRegion(
                    geofenceData.latLng.latitude,
                    geofenceData.latLng.longitude,
                    GeofenceUtils.GEOFENCE_RADIUS_IN_METERS
                )
                .setExpirationDuration(GeofenceUtils.GEOFENCE_EXPIRATION_IN_MILLISECONDS)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                .build()

            val geofencingRequest = GeofencingRequest.Builder()
                .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
                .addGeofence(geofence)
                .build()


            geofencingClient.removeGeofences(geofencePendingIntent)?.run {
                addOnCompleteListener {
                    geofencingClient.addGeofences(geofencingRequest, geofencePendingIntent)?.run {
                        addOnSuccessListener {
                            Toast.makeText(
                                requireActivity(), R.string.geofences_added,
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            goToRemainders()
                            Timber.d("Add Geofence ${geofence.requestId}")
                        }
                        addOnFailureListener {
                            Toast.makeText(
                                requireActivity(), R.string.geofences_not_added,
                                Toast.LENGTH_SHORT
                            ).show()
                            if ((it.message != null)) {
                                Timber.d("geofence not added ${it.message}")
                            }
                        }
                    }
                }
            }
        }
    }

    private fun goToRemainders() {
        findNavController().popBackStack()
    }


    private fun init() {
        geofencingClient =
            LocationServices.getGeofencingClient(requireActivity().applicationContext)
    }

    companion object {
        const val SELECTED_POI = "SELECTED_POI"
        internal const val ACTION_GEOFENCE_EVENT =
            "AddNewRemainderFragment.action.ACTION_GEOFENCE_EVENT"
    }
}