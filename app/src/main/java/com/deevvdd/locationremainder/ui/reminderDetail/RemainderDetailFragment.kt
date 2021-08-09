package com.deevvdd.locationremainder.ui.reminderDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.deevvdd.locationremainder.MainActivity
import com.deevvdd.locationremainder.R
import com.deevvdd.locationremainder.databinding.FragmentRemainderDetailBinding
import com.deevvdd.locationremainder.geofence.GeofenceUtils
import com.deevvdd.locationremainder.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by heinhtet deevvdd@gmail.com on 22,July,2021
 */
@AndroidEntryPoint
class RemainderDetailFragment : BaseFragment() {

    private lateinit var binding: FragmentRemainderDetailBinding
    private val viewModel: RemainderDetailViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_remainder_detail, container, false)
        binding.apply {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        val placeId = arguments?.getString(GeofenceUtils.GEOFENCE_EXTRA)
        viewModel.getRemainderByPlaceId(placeId.orEmpty())
        viewModel.remainder.observe(viewLifecycleOwner, { remainder ->
            (requireActivity() as AppCompatActivity).supportActionBar?.title = remainder.title
        })
        return binding.root
    }
}