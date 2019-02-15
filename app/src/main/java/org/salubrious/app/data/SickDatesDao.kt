package org.salubrious.app.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import org.threeten.bp.LocalDate

@Dao
interface SickDatesDao {
    @Query("SELECT * FROM sick_dates ORDER BY start_date ASC ")
    fun getAllLive(): LiveData<List<SickDate>>

    @Query("SELECT * FROM sick_dates ORDER BY start_date ASC ")
    suspend fun getAll(): List<SickDate>

    @Insert
    fun insertAll(vararg sickDates: SickDate)

    @Delete
    fun delete(sickDate: SickDate)

    @Query("SELECT * FROM sick_dates WHERE start_date <= :date AND (end_date IS NULL OR end_date >= :date)")
    suspend fun getByDate(date: LocalDate): List<SickDate>

    @Query("SELECT COUNT (`rowId`) FROM sick_dates WHERE start_date <= :date AND (end_date IS NULL OR end_date >= :date)")
    suspend fun isSickDate(date: LocalDate): Int
}