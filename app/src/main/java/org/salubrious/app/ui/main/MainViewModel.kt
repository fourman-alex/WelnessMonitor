package org.salubrious.app.ui.main

import androidx.lifecycle.ViewModel
import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.salubrious.app.data.SickDate
import org.salubrious.app.data.WellnessDatabase
import org.salubrious.app.data.WellnessRepository

class MainViewModel : ViewModel() {
    private val uiScope = CoroutineScope(Dispatchers.Main)

    fun addSickDate(calendarDay: CalendarDay, notes: String) {
        if (calendarDay == CalendarDay.today()) {
            WellnessRepository.insertSickDate(SickDate(calendarDay, notes, true))
        } else WellnessRepository.insertSickDate(SickDate(calendarDay, notes))
    }

    suspend fun getByDate(calendarDay: CalendarDay) = WellnessRepository.getByDate(calendarDay)
    fun onFabClicked(
        calendarDay: CalendarDay,
        onFabAdd: () -> Unit,
        onFabEdit: (notes: String) -> Unit
    ) {
        uiScope.launch {
            WellnessRepository.getByDate(calendarDay)?.also { onFabEdit(it.notes) } ?: run { onFabAdd() }
        }
    }

    val sickDatesLiveData = WellnessDatabase.database.sickDatesDao().getAll()
}
