package org.salubrious.app.data

import androidx.room.*
import org.salubrious.app.appContext
import org.threeten.bp.LocalDate

@Database(entities = [SickDate::class], version = 1)
@TypeConverters(Converters::class)
abstract class WellnessDatabase : RoomDatabase() {
    abstract fun sickDatesDao(): SickDatesDao

    companion object {
        val database: WellnessDatabase by lazy {
            Room.databaseBuilder(appContext, WellnessDatabase::class.java, "wellness_db").build()
        }
    }
}

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): LocalDate? {
        return value?.let { LocalDate.ofEpochDay(it) }
    }

    @TypeConverter
    fun dateToTimestamp(localDate: LocalDate?): Long? {
        return localDate?.toEpochDay()
    }
}