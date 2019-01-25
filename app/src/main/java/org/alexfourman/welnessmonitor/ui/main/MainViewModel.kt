package org.alexfourman.welnessmonitor.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import org.alexfourman.welnessmonitor.data.SickDate
import org.alexfourman.welnessmonitor.data.WellnessDatabase
import org.alexfourman.welnessmonitor.data.WellnessRepository

class MainViewModel : ViewModel() {
    fun addSickDate(sickDate: SickDate) = WellnessRepository.insertSickDate(sickDate)

    val sickDatesLiveData = WellnessDatabase.database.sickDatesDao().getAll()
}
