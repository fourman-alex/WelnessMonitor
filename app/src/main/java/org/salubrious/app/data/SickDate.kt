package org.salubrious.app.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.LocalDate

@Entity(tableName = "sick_dates")
data class SickDate(
    @ColumnInfo(name = "start_date") val startDate: LocalDate,
    @ColumnInfo(name = "notes") val notes: String,
    @ColumnInfo(name = "end_date") val endDate: LocalDate? = null
) {
    @PrimaryKey(autoGenerate = true)
    //todo this is fixed in a later room version. Can be changed to val I think. https://stackoverflow.com/questions/44213446/cannot-find-setter-for-field-using-kotlin-with-room-database
    var rowId: Int = 0
}