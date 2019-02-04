package org.alexfourman.welnessmonitor

import android.graphics.Color
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.spans.DotSpan


class EventDecorator(private val sickDates: List<CalendarDay>) : DayViewDecorator {

    override fun shouldDecorate(day: CalendarDay): Boolean {
        return sickDates.contains(day)
    }

    override fun decorate(view: DayViewFacade) {
        view.addSpan(DotSpan(6f, Color.RED))
    }
}