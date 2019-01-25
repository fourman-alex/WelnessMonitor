package org.alexfourman.welnessmonitor.data

import androidx.lifecycle.LiveData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object WellnessRepository {
    fun getAllSickDates(): LiveData<List<SickDate>> = WellnessDatabase.database.sickDatesDao().getAll()

    fun insertSickDate(sickDate: SickDate) {
        GlobalScope.launch { WellnessDatabase.database.sickDatesDao().insertAll(sickDate) }
    }
}