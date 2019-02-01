package org.alexfourman.welnessmonitor.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.prolificinteractive.materialcalendarview.CalendarDay

@Dao
interface SickDatesDao {
    @Query("SELECT * FROM sick_dates")
    fun getAll(): LiveData<List<SickDate>>

    @Insert
    fun insertAll(vararg sickDates: SickDate)

    @Query("SELECT * FROM sick_dates WHERE date == (:calendarDay)")
    suspend fun getByDate(calendarDay: CalendarDay): SickDate?

    @Delete
    fun delete(sickDate: SickDate)
}