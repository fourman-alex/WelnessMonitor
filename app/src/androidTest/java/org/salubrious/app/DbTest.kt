package org.salubrious.app

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.salubrious.app.data.SickDate
import org.salubrious.app.data.SickDatesDao
import org.salubrious.app.data.WellnessDatabase
import org.threeten.bp.LocalDate
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class DbTest {
    private lateinit var sickDatesDao: SickDatesDao
    private lateinit var db: WellnessDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, WellnessDatabase::class.java
        ).build()
        sickDatesDao = db.sickDatesDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertTest() {
        runBlocking {

            val sickDate = SickDate(
                LocalDate.of(2019, 2, 1),
                "notes",
                LocalDate.of(2019, 2, 3)
            )
            sickDatesDao.insertAll(
                sickDate
            )
            val all = sickDatesDao.getAll()


            assertThat(all[0], equalTo(sickDate))
        }
    }

    @Test
    @Throws(Exception::class)
    fun isSickDateTest() {
        runBlocking {

            val sickDate = SickDate(
                LocalDate.of(2019, 1, 12),
                "notes",
                LocalDate.of(2019, 1, 15)
            )
            val nullSickDate = SickDate(
                LocalDate.of(2019, 4, 12),
                "notes",
                null
            )

            sickDatesDao.insertAll(
                sickDate, nullSickDate
            )

            val insideRangeDate = LocalDate.of(2019, 1, 13)
            assertTrue(sickDatesDao.isSickDate(insideRangeDate).also { System.out.println("insideRangeDate test: $it") } > 0)

            val outsideRangeDate = LocalDate.of(2019, 3, 13)
            assertFalse(sickDatesDao.isSickDate(outsideRangeDate).also { System.out.println("outsideRangeDate test: $it") } > 0)

            val insideRangeOfOpenBoundDate = LocalDate.of(2019, 4, 16)
            assertTrue(sickDatesDao.isSickDate(insideRangeOfOpenBoundDate).also { System.out.println("insideRangeDate test: $it") } > 0)
        }
    }

}