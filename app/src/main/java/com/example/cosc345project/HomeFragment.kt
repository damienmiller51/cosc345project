package com.example.cosc345project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import androidx.fragment.app.Fragment
import com.example.cosc345project.databinding.HomeFragmentBinding
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HomeFragment : Fragment() {

    private var _binding: HomeFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //date formatter
        val sdf = SimpleDateFormat("dd/MM/yyyy")

        //set the label to the current day
        val sb = StringBuilder()
        sb.append(getString(R.string.date_label)).append(" ").append(sdf.format(binding.mainCalendar.date))
        binding.homeDate.text = sb.toString()

        //when the selected date changes
        binding.mainCalendar.setOnDateChangeListener { calView: CalendarView, year: Int, month: Int, dayOfMonth: Int ->

            //Create a calendar instance and set it's date to what was selected
            val calendar: Calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            calView.setDate(calendar.timeInMillis, true, true)

            //build label text
            sb.clear()
            sb.append(getString(R.string.date_label)).append(" ").append(sdf.format(binding.mainCalendar.date))

            //format the selected date and update the label accordingly
            binding.homeDate.text = sb.toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}