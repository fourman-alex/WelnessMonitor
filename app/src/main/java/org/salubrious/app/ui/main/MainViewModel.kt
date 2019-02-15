package org.salubrious.app.ui.main

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.salubrious.app.data.SickDate
import org.salubrious.app.data.WellnessRepository
import org.threeten.bp.LocalDate

class MainViewModel : ViewModel() {
    private val uiScope = CoroutineScope(Dispatchers.Main)

    fun addSickDate(startDate: LocalDate, notes: String, endDate: LocalDate? = null) {
        if (LocalDate.now() > startDate)
            WellnessRepository.insertSickDate(SickDate(startDate, notes, startDate))
        else
            WellnessRepository.insertSickDate(SickDate(startDate, notes))
    }

    suspend fun getByDate(date: LocalDate) = WellnessRepository.getByDate(date)

    fun onFabClicked(
        selectedDate: LocalDate,

        onFabAdd: () -> Unit,
        onFabEdit: (notes: String) -> Unit
    ) {
        uiScope.launch {

            val sickDate = WellnessRepository.getByDate(selectedDate).firstOrNull()
            if (sickDate != null) onFabEdit(sickDate.notes) else onFabAdd()
        }
    }

    suspend fun isFabEditIcon(selectedDate: LocalDate): Boolean {
        return WellnessRepository.isSickDate(selectedDate)
    }

    val sickDatesLiveData = WellnessRepository.getAllSickDatesAsLive()
}