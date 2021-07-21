package com.deevvdd.locationremainder

import com.deevvdd.locationremainder.domain.model.Remainder

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