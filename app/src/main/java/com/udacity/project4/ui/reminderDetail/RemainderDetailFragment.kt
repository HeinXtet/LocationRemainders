package com.udacity.project4.ui.reminderDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.udacity.project4.R
import com.udacity.project4.databinding.FragmentRemainderDetailBinding
import com.udacity.project4.geofence.GeofenceUtils
import com.udacity.project4.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by heinhtet deevvdd@gmail.com on 22,July,2021
 */
class RemainderDetailFragment : BaseFragment() {

    private lateinit var binding: FragmentRemainderDetailBinding
    val viewModel: RemainderDetailViewModel by viewModel()
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