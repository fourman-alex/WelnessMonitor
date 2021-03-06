package org.salubrious.app.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.salubrious.app.EventDecorator
import org.salubrious.app.R
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.find
import org.threeten.bp.LocalDate

class MainFragment : Fragment() {
    private val log = AnkoLogger(this.javaClass)
    private val uiScope = CoroutineScope(Dispatchers.Main)

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

        val onSelectedDateChanged: (MaterialCalendarView, CalendarDay, Boolean) -> Unit =
            { _, calendarDay, isNowSelected ->
                uiScope.launch {
                    if (isNowSelected) {
                        @SuppressLint("RestrictedApi")
                        if (fab.visibility != View.VISIBLE) {
                            fab.visibility = View.VISIBLE
                            fab.scaleX = 0f
                            fab.scaleY = 0f
                            fab.animate().scaleX(1f).scaleY(1f)

                        }
                        if (viewModel.isFabEditIcon(calendarDay.date))
                            (fab as FloatingActionButton).setImageResource(R.drawable.ic_baseline_edit_24px)
                        else
                            (fab as FloatingActionButton).setImageResource(R.drawable.ic_baseline_add_24px)
                    }
                }
            }
        calendarView.setOnDateChangedListener(onSelectedDateChanged)
        calendarView.state().edit().setMaximumDate(LocalDate.now()).commit()

        viewModel.sickDatesLiveData.observe(this, Observer { sickDateList ->
            calendarView.removeDecorators()
            calendarView.addDecorator(EventDecorator(sickDateList))
        })


        fab.setOnClickListener fabListener@{
            //the next line just makes the listener not do anything in case calendarView.selectedDate is null
            val selectedDate = calendarView.selectedDate ?: run { return@fabListener }
            //using the view model to call the correct listener based on if on the given calendarDay the fab should act as ADD or EDIT
            viewModel.onFabClicked(selectedDate.date,
                onFabAdd = {
                    val builder = AlertDialog.Builder(this.requireContext())
                    val editTextLayout = requireActivity().layoutInflater.inflate(R.layout.alert_input_edit_text, null)
                    builder.setView(editTextLayout)
                        .setPositiveButton("Add") { _, _ ->
                            viewModel.addSickDate(
                                selectedDate.date,
                                editTextLayout.find<TextView>(R.id.notes_editText).text.toString()
                            )
                            onSelectedDateChanged(calendarView, selectedDate, true)
                        }
                        .setNegativeButton("Cancel", null)
                        .show()
                },
                onFabEdit = { notes ->
                    val builder = AlertDialog.Builder(this.requireContext())
                    builder.setMessage(notes).show()
                })
        }
    }

}
