package com.deevvdd.locationremainder.ui.remainderList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.deevvdd.locationremainder.R
import com.deevvdd.locationremainder.data.source.RemaindersRepositoryImpl
import com.deevvdd.locationremainder.databinding.RemaindersFragmentBinding
import com.deevvdd.locationremainder.ui.base.BaseFragment

/**
 * Created by heinhtet deevvdd@gmail.com on 18,July,2021
 */
class RemaindersFragment : BaseFragment() {
    private lateinit var binding: RemaindersFragmentBinding
    private val viewModel by viewModels<RemainderViewModel> {
        RemainderViewModel.RemaindersViewModelFactory(
            RemaindersRepositoryImpl.getRepository(requireActivity().application)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.remainders_fragment, container, false)
        viewModel.remainders.observe(viewLifecycleOwner, Observer {
            Log.d("Result -> ", "Remainders ${it.size}")
        })
        binding.fabAddRemainder.setOnClickListener {
            viewModel.addNewRemainder()
        }
        return binding.root
    }
}