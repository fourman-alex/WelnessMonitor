package org.salubrious.app.data

import androidx.lifecycle.LiveData
import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object WellnessRepository {
    fun getAllSickDates(): LiveData<List<SickDate>> = WellnessDatabase.database.sickDatesDao().getAll()

    fun insertSickDate(sickDate: SickDate) {
        GlobalScope.launch { WellnessDatabase.database.sickDatesDao().insertAll(sickDate) }
    }

    suspend fun getByDate(calendarDay: CalendarDay) = WellnessDatabase.database.sickDatesDao().getByDate(calendarDay)
}