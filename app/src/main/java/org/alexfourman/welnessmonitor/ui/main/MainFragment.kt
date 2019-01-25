package org.alexfourman.welnessmonitor.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.*
import org.alexfourman.welnessmonitor.EventDecorator
import org.alexfourman.welnessmonitor.R
import org.alexfourman.welnessmonitor.data.SickDate
import org.alexfourman.welnessmonitor.data.WellnessDatabase
import org.alexfourman.welnessmonitor.data.WellnessRepository
import org.alexfourman.welnessmonitor.uiScope
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class MainFragment : Fragment() {
    private val log = AnkoLogger(this.javaClass)

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        calendarView.setOnDateChangedListener { _, calendarDay, isNowSelected ->
            if (isNowSelected) viewModel.addSickDate(SickDate(calendarDay, "some notes"))
        }

        viewModel.sickDatesLiveData.observe(this, Observer { sickDateList ->
            calendarView.removeDecorators()
            calendarView.addDecorator(EventDecorator(sickDateList.map { it.date }))
        })
    }

}
