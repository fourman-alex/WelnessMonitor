package org.alexfourman.welnessmonitor.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.prolificinteractive.materialcalendarview.CalendarDay
import java.time.LocalDate
import java.util.*

@Entity(tableName = "sick_dates")
data class SickDate(
    @ColumnInfo val date: CalendarDay,
    @ColumnInfo(name = "notes") val notes: String
) {
    @PrimaryKey(autoGenerate = true)
    var rowId: Int = 0 //todo this is fixed in a later room version. Can be changed to val I think. https://stackoverflow.com/questions/44213446/cannot-find-setter-for-field-using-kotlin-with-room-database
}