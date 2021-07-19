package com.deevvdd.locationremainder.utils

import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText

/**
 * Created by heinhtet deevvdd@gmail.com on 18,July,2021
 */


fun View.show() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.isVisible() = visibility == View.VISIBLE

fun NavController.safeNavigate(direction: NavDirections) {
    currentDestination?.getAction(direction.actionId)?.run {
        navigate(direction)
    }
}


fun <T : Any> Fragment.setBackStackData(key: String, data : T, doBack : Boolean = true) {
    findNavController().previousBackStackEntry?.savedStateHandle?.set(key, data)
    if(doBack)
        findNavController().popBackStack()
}

fun <T : Any> Fragment.getBackStackData(key: String, result: (T) -> (Unit)) {
    findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<T>(key)?.observe(viewLifecycleOwner) {
        result(it)
    }
}
