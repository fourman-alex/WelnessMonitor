package org.salubrious.app.data

import androidx.lifecycle.LiveData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate

object WellnessRepository {
    fun getAllSickDatesAsLive(): LiveData<List<SickDate>> = WellnessDatabase.database.sickDatesDao().getAllLive()

    suspend fun getAllSickDates() : List<SickDate> = WellnessDatabase.database.sickDatesDao().getAll()

    fun insertSickDate(sickDate: SickDate) {
        GlobalScope.launch { WellnessDatabase.database.sickDatesDao().insertAll(sickDate) }
    }

    suspend fun isSickDate(date: LocalDate) = WellnessDatabase.database.sickDatesDao().isSickDate(date) > 0

    suspend fun getByDate(date: LocalDate) = WellnessDatabase.database.sickDatesDao().getByDate(date)
}