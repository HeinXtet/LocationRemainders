package com.udacity.project4.ui.remainderList

import android.Manifest
import android.annotation.TargetApi
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.*
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.udacity.project4.BuildConfig
import com.udacity.project4.R
import com.udacity.project4.databinding.FragmentRemaindersBinding
import com.udacity.project4.domain.model.Remainder
import com.udacity.project4.geofence.GeofenceUtils
import com.udacity.project4.ui.base.BaseFragment
import com.udacity.project4.utils.safeNavigate
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.material.snackbar.Snackbar
import com.udacity.project4.MainActivity
import com.udacity.project4.utils.showSnackBar
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

/**
 * Created by heinhtet deevvdd@gmail.com on 18,July,2021
 */

class RemaindersFragment : BaseFragment(), RemainderAdapterCallback {
    private lateinit var binding: FragmentRemaindersBinding
    val viewModel: RemainderViewModel by viewModel()
    private lateinit var adapter: RemainderAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_remainders, container, false)
        adapter = RemainderAdapter(this)
        binding.apply {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner
            adapter = this@RemaindersFragment.adapter
        }
        binding.fabAddRemainder.setOnClickListener {
            findNavController().safeNavigate(RemaindersFragmentDirections.actionRemaindersFragmentToAddNewRemainder())
        }
        initObserver()
        return binding.root
    }


    override fun onResume() {
        super.onResume()
        viewModel.loadReminders()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.remainder_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuLogout -> {
                viewModel.logout(requireActivity())
                true
            }
            else -> false
        }
    }


    private fun initObserver() {
        viewModel.logoutEvent.observe(viewLifecycleOwner, { logout ->
            findNavController().navigate(RemaindersFragmentDirections.actionRemaindersFragmentToLoginFragment())
        })
        viewModel.showSnackBarInt.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let {
                binding.root.showSnackBar(getString(it))
            }
        })
    }


    override fun itemDelete(remainder: Remainder) {
        viewModel.deleteRemainder(remainder)
    }

    override fun onItemClick(remainder: Remainder) {
        val bundle = Bundle()
        bundle.putString(GeofenceUtils.GEOFENCE_EXTRA, remainder.id)
        findNavController().safeNavigate(
            RemaindersFragmentDirections.actionRemaindersFragmentToRemainderDetailFragment(
                remainder.id
            )
        )
    }
}

private const val TAG = "RemaindersFragment"

