package org.alexfourman.welnessmonitor.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.prolificinteractive.materialcalendarview.CalendarDay

@Entity(tableName = "sick_dates")
data class SickDate(
    @ColumnInfo val calendarDay: CalendarDay,
    @ColumnInfo(name = "notes") val notes: String,
    @ColumnInfo(name = "status_open") val statusOpen: Boolean = false
) {
    @PrimaryKey(autoGenerate = true)
    //todo this is fixed in a later room version. Can be changed to val I think. https://stackoverflow.com/questions/44213446/cannot-find-setter-for-field-using-kotlin-with-room-database
    var rowId: Int = 0
}