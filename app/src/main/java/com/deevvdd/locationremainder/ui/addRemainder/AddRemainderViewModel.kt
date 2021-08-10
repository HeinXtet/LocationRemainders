package com.deevvdd.locationremainder.ui.addRemainder

import androidx.lifecycle.*
import com.deevvdd.locationremainder.R
import com.deevvdd.locationremainder.data.source.RemaindersRepository
import com.deevvdd.locationremainder.domain.model.Remainder
import com.deevvdd.locationremainder.ui.base.BaseViewModel
import com.deevvdd.locationremainder.utils.Event
import com.google.android.gms.maps.model.PointOfInterest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by heinhtet deevvdd@gmail.com on 19,July,2021
 */

@HiltViewModel
class AddRemainderViewModel @Inject constructor(private val repository: RemaindersRepository) :
    BaseViewModel() {

    private val _selectedPOI = MutableLiveData<PointOfInterest?>()


    var title = MutableLiveData<String>()
    var description = MutableLiveData<String>()


    fun updatePOI(data: PointOfInterest) {
        _selectedPOI.value = data
    }


    private val _savedRemainderEvent = MutableLiveData<Event<Int>>()


    val savedRemainderEvent: LiveData<Event<Int>>
        get() = _savedRemainderEvent


    val poi: LiveData<PointOfInterest?>
        get() = _selectedPOI


    fun isValidToSave(): Boolean {
        if (title.value.orEmpty().isEmpty()) {
            showSnackBarInt.value = Event(R.string.error_message_title_empty)
            return false
        }

        if (description.value.orEmpty().isEmpty()) {
            showSnackBarInt.value = Event(R.string.error_message_description_empty)
            return false
        }

        if (_selectedPOI.value == null) {
            showSnackBarInt.value = Event(R.string.error_message_poi_empty)
            return false
        }

        return title.value.orEmpty().isNotEmpty() && description.value.orEmpty()
            .isNotEmpty() && _selectedPOI.value != null
    }

    fun addNewRemainder() {
        if (isValidToSave()) {
            val remainder = Remainder(
                title = title.value.orEmpty(),
                description = description.value.orEmpty(),
                longitude = _selectedPOI.value?.latLng?.longitude ?: 0.0,
                latitude = _selectedPOI.value?.latLng?.latitude ?: 0.0,
                place = _selectedPOI.value?.name.orEmpty(),
                placeId = _selectedPOI.value?.placeId.orEmpty()
            )
            viewModelScope.launch {
                repository.saveReminder(remainder)
                title.value = ""
                description.value = ""
                savedRemainder()
            }
        }
    }


     fun savedRemainder() {
        _savedRemainderEvent.value = Event(R.string.text_add_new_remainder_sucess)
    }
}