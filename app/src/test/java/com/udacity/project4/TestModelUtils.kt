package com.udacity.project4

import com.udacity.project4.domain.model.Remainder

/**
 * Created by heinhtet deevvdd@gmail.com on 21,July,2021
 */
object TestModelUtils {
    fun getTestRemainder(): Remainder {
        return Remainder(
            "1",
            "title",
            "description",
            0.0,
            0.0,
            "place",
            "placeId"
        )
    }
}