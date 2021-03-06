package org.salubrious.app

import android.graphics.Color
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.spans.DotSpan
import org.salubrious.app.data.SickDate


class EventDecorator(private val sickDates: List<SickDate>) : DayViewDecorator {

    override fun shouldDecorate(day: CalendarDay): Boolean {
        return sickDates.find { it.startDate <= day.date && (it.endDate == null || it.endDate >= day.date) } != null
    }

    override fun decorate(view: DayViewFacade) {
        view.addSpan(DotSpan(6f, Color.RED))
    }
}