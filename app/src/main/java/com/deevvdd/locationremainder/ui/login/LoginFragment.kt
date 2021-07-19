package com.deevvdd.locationremainder.ui.login

import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.deevvdd.locationremainder.R
import com.deevvdd.locationremainder.databinding.FragmentLoginBinding
import com.deevvdd.locationremainder.ui.base.BaseFragment
import com.deevvdd.locationremainder.utils.safeNavigate
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth

/**
 * Created by heinhtet deevvdd@gmail.com on 19,July,2021
 */
class LoginFragment : BaseFragment() {


    private lateinit var binding: FragmentLoginBinding
    private val viewModel by viewModels<LoginViewModel>()


    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.onSignInResult(res)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        binding.apply {
            vm = viewModel
            lifecycleOwner = this@LoginFragment
            btnLogin.setOnClickListener {
                auth()
            }
        }
        checkAuth()
        return binding.root
    }


    private fun auth() {
        if (viewModel.isLoggedIn.value != true) {
            signInLauncher.launch(viewModel.signInIntent)
        } else {
            viewModel.logout(requireContext())
        }
    }

    private fun checkAuth() {
        viewModel.isLoggedIn.observe(viewLifecycleOwner, { loggedIn ->
            if (loggedIn) {
                findNavController().safeNavigate(LoginFragmentDirections.actionLoginFragmentToRemaindersFragment())
            }
        })
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        if (result.resultCode == RESULT_OK) {
            val user = FirebaseAuth.getInstance().currentUser
            user?.let {
                viewModel.login(it)
                findNavController().safeNavigate(LoginFragmentDirections.actionLoginFragmentToRemaindersFragment())
            }
        }
    }
}