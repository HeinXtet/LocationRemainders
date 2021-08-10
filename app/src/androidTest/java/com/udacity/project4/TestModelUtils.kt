package com.udacity.project4

import com.udacity.project4.domain.model.Remainder

/**
 * Created by heinhtet deevvdd@gmail.com on 21,July,2021
 */
object TestAndroidModelUtils {
    fun getTestRemainder(): Remainder {
        return Remainder(
            "1",
            "Remainder Title",
            "Remainder description",
            0.0,
            0.0,
            "place",
            "1"
        )
    }
}