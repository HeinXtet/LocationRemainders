package com.deevvdd.locationremainder.utils

import android.view.View

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