package com.deevvdd.locationremainder.ui.remainderList

import androidx.lifecycle.*
import com.deevvdd.locationremainder.data.source.RemaindersRepository
import com.deevvdd.locationremainder.domain.model.Remainder
import kotlinx.coroutines.launch

/**
 * Created by heinhtet deevvdd@gmail.com on 19,July,2021
 */
class RemainderViewModel constructor(private val repository: RemaindersRepository) : ViewModel() {
    fun addNewRemainder() {
        viewModelScope.launch {
            repository.saveReminder(
                Remainder(
                    title = "New Remainder",
                    description = "This is Test Remainder",
                    latitude = 0L,
                    longitude = 0L,
                    place = "Test"
                )
            )
        }
    }

    private val _remainders: LiveData<List<Remainder>> =
        repository.observeRemainders()

    val remainders: LiveData<List<Remainder>>
        get() = _remainders

    @Suppress("UNCHECKED_CAST")
    class RemaindersViewModelFactory(
        private val remaindersRepository: RemaindersRepository
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>) =
            (RemainderViewModel(remaindersRepository) as T)
    }
}