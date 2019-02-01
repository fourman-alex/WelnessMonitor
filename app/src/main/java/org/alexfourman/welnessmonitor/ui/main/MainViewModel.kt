package org.alexfourman.welnessmonitor.ui.main

import androidx.lifecycle.ViewModel
import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.alexfourman.welnessmonitor.data.SickDate
import org.alexfourman.welnessmonitor.data.WellnessDatabase
import org.alexfourman.welnessmonitor.data.WellnessRepository

class MainViewModel : ViewModel() {
    private val uiScope = CoroutineScope(Dispatchers.Main)

    fun addSickDate(sickDate: SickDate) = WellnessRepository.insertSickDate(sickDate)

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
