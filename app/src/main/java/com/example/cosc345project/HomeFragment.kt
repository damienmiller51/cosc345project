package com.example.cosc345project

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.CalendarView
import androidx.fragment.app.Fragment
import com.example.cosc345project.databinding.HomeFragmentBinding
import com.google.android.material.slider.Slider
import it.beppi.knoblibrary.Knob.OnStateChanged
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HomeFragment : Fragment() {

    //date formatter
    val sdf = SimpleDateFormat("dd/MM/yyyy")

    var selectedDate = sdf.format(Date()).replace("/", "")

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

        //set the label to the current day
        val sb = StringBuilder()
        sb.append(getString(R.string.date_label)).append(" ").append(sdf.format(binding.mainCalendar.date))
        binding.homeDate.text = sb.toString()

        //set the data to what the storage contains
        val dateData = binding.root.context.getSharedPreferences(selectedDate, MODE_PRIVATE)

        if(dateData.contains("sleep"))
            binding.homeSleepData.text = dateData.getInt("sleep", -1).toString()
        else
            binding.homeSleepData.text = "0"

        if(dateData.contains("steps"))
            binding.homeStepData.text = dateData.getInt("steps", -1).toString()
        else
            binding.homeStepData.text = "0"

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
            selectedDate = sdf.format(binding.mainCalendar.date).replace("/", "")

            val dateData = binding.root.context.getSharedPreferences(selectedDate, MODE_PRIVATE)

            //check if we have data for this date
            if(dateData.contains("sleep"))
                binding.homeSleepData.text = dateData.getInt("sleep", -1).toString()
            else
                binding.homeSleepData.text = "0"

            if(dateData.contains("steps"))
                binding.homeStepData.text = dateData.getInt("steps", -1).toString()
            else
                binding.homeStepData.text = "0"
        }

        binding.homeSleepButton.setOnClickListener() {
            loadSleepInput()
        }

        binding.homeStepButton.setOnClickListener() {
            loadStepInput()
        }

        //touch listener for our slider
        binding.sleepSlider.addOnSliderTouchListener(object: Slider.OnSliderTouchListener {
            //required for abstract class
            override fun onStartTrackingTouch(slider: Slider) {

            }

            //when a value is selected, hide the input
            override fun onStopTrackingTouch(slider: Slider) {
                hideSleepInput()

                //update the sleep hours display
                binding.homeSleepData.text = slider.value.toInt().toString()

                //update the shared preferences
                val prefs = binding.root.context.getSharedPreferences(selectedDate, MODE_PRIVATE)
                val editor = prefs.edit()
                editor.putInt("sleep", slider.value.toInt())
                editor.apply()
            }
        })

        binding.sleepSlider.addOnChangeListener() { slider, value, fromUSer ->
            //update the display
            binding.sleepDisplay.text = (slider.value).toString()
        }

        binding.stepsKnob.setOnStateChanged(OnStateChanged {
            binding.stepDisplay.text = (binding.stepsKnob.state*1000).toString()
        })

        binding.homeSubmitSteps.setOnClickListener() {
            //update the display
            binding.homeStepData.text = binding.stepDisplay.text

            //update the storage
            val prefs = binding.root.context.getSharedPreferences(selectedDate, MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putInt("steps", (binding.stepsKnob.state*1000))
            editor.apply()

            hideStepInput()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //load the ui for sleep input
    fun loadSleepInput() {
        binding.inputLayout.visibility = View.GONE
        binding.sleepEntry.visibility = View.VISIBLE
        binding.sleepSlider.value = 0.0F
    }

    //hide the ui for sleep input
    fun hideSleepInput() {
        binding.inputLayout.visibility = View.VISIBLE
        binding.sleepEntry.visibility = View.GONE
    }

    //load the ui for step input
    fun loadStepInput() {
        binding.inputLayout.visibility = View.GONE
        binding.stepEntry.visibility = View.VISIBLE
    }

    fun hideStepInput() {
        binding.inputLayout.visibility = View.VISIBLE
        binding.stepEntry.visibility = View.GONE
    }

}