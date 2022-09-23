package com.example.cosc345project

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.CalendarView
import androidx.fragment.app.Fragment
import com.example.cosc345project.databinding.HomeFragmentBinding
import com.google.android.material.slider.Slider
import it.beppi.knoblibrary.Knob.OnStateChanged
import java.text.SimpleDateFormat
import java.util.*


/**
 * The fragment class containing the framework for the Home page. The default destination for the app.
 * @author Damien Miller
 */
class HomeFragment : Fragment() {
    private var _binding: HomeFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    //get the settings for the app


    /**
     * The app's date formatter.
     */
    var sdf = SimpleDateFormat("dd/MM/yyyy")

    /**
     * The date currently selected in the calendar.
     */
    var selectedDate = sdf.format(Date())

    /**
     * Create the Home page and inflate the layout.
     * @param inflater The layout inflater.
     * @param container The view container.
     * @param savedInstanceState -
     * @return The outermost view from the layout file.
     */
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    /**
     * Called immediately after the Home page is created.
     * @param view The Home view.
     * @param savedInstanceState -
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var labelSdf = SimpleDateFormat("dd/MM/yyyy")

        //set the max date on the calendar
        binding.mainCalendar.maxDate = Date().time

        //update the sdf if a setting is found.
        val settings = binding.root.context.getSharedPreferences("settings", MODE_PRIVATE)
        if(settings.contains("format"))
            labelSdf = SimpleDateFormat(settings.getString("format", ""))


        //set the label to the current day
        val sb = StringBuilder()
        sb.append(getString(R.string.date_label)).append(" ").append(labelSdf.format(binding.mainCalendar.date))
        binding.homeDate.text = sb.toString()

        //set the data to what the storage contains
        val dateData = binding.root.context.getSharedPreferences(selectedDate.replace("/", ""), MODE_PRIVATE)

        if(dateData.contains("sleep"))
            binding.homeSleepData.text = dateData.getInt("sleep", -1).toString()
        else
            binding.homeSleepData.text = "0"

        if(dateData.contains("steps"))
            binding.homeStepData.text = dateData.getInt("steps", -1).toString()
        else
            binding.homeStepData.text = "0"

        if(dateData.contains("mood")) {
            if (dateData.getInt("mood", -1) == 1) {
                binding.homeMood.setImageResource(R.drawable.mood_frown)
            } else if (dateData.getInt("mood", -1) == 2) {
                binding.homeMood.setImageResource(R.drawable.mood_average)
            } else {
                binding.homeMood.setImageResource(R.drawable.mood_smile)
            }
        } else {
            binding.homeMood.setImageResource(it.beppi.knoblibrary.R.drawable.bg_circle)
        }

        //when the selected date changes
        binding.mainCalendar.setOnDateChangeListener { calView: CalendarView, year: Int, month: Int, dayOfMonth: Int ->
            //check to make sure we aren't entering information on date change
            //if we are - pull back to the default input layout
            if(binding.sleepEntry.visibility == View.VISIBLE)
                toggleSleepInput()

            if(binding.stepEntry.visibility == View.VISIBLE)
                toggleStepInput()

            if(binding.moodEntry.visibility == View.VISIBLE)
                toggleMoodInput()

            //Create a calendar instance and set it's date to what was selected
            val calendar: Calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            calView.setDate(calendar.timeInMillis, true, true)

            //build label text
            sb.clear()
            sb.append(getString(R.string.date_label)).append(" ").append(labelSdf.format(binding.mainCalendar.date))

            //format the selected date and update the label accordingly
            binding.homeDate.text = sb.toString()
            selectedDate = sdf.format(binding.mainCalendar.date)

            val dateData = binding.root.context.getSharedPreferences(selectedDate.replace("/", ""), MODE_PRIVATE)

            //check if we have data for this date
            if(dateData.contains("sleep"))
                binding.homeSleepData.text = dateData.getInt("sleep", -1).toString()
            else
                binding.homeSleepData.text = "0"

            if(dateData.contains("steps"))
                binding.homeStepData.text = dateData.getInt("steps", -1).toString()
            else
                binding.homeStepData.text = "0"

            if(dateData.contains("mood")) {
                if (dateData.getInt("mood", -1) == 1) {
                    binding.homeMood.setImageResource(R.drawable.mood_frown)
                } else if (dateData.getInt("mood", -1) == 2) {
                    binding.homeMood.setImageResource(R.drawable.mood_average)
                } else {
                    binding.homeMood.setImageResource(R.drawable.mood_smile)
                }
            } else {
                binding.homeMood.setImageResource(it.beppi.knoblibrary.R.drawable.bg_circle)
            }
        }

        binding.homeSleepButton.setOnClickListener {
            toggleSleepInput()
        }

        binding.homeStepButton.setOnClickListener {
           toggleStepInput()
        }

        binding.homeMoodButton.setOnClickListener {
            toggleMoodInput()
        }

        binding.homeDiaryButton.setOnClickListener {
            toggleDiary()
        }

        binding.homeDiaryDone.setOnClickListener {
            toggleDiary()
        }

        binding.homeDiaryEdit.setOnClickListener {
            binding.homeDiaryEditor.visibility = View.VISIBLE
            binding.homeDiaryView.visibility = View.GONE

            val prefs = binding.root.context.getSharedPreferences(selectedDate.replace("/", ""), Context.MODE_PRIVATE)
            if(prefs.contains("diary")) {
                binding.homeDiaryEditor.setText(prefs.getString("diary", ""))
            } else {
                binding.homeDiaryEditor.setText("")
            }
        }

        //touch listener for our steps slider
        binding.sleepSlider.addOnSliderTouchListener(object: Slider.OnSliderTouchListener {
            //required for abstract class
            override fun onStartTrackingTouch(slider: Slider) {

            }

            //when a value is selected, hide the input
            override fun onStopTrackingTouch(slider: Slider) {
                toggleSleepInput()

                //update the sleep hours display
                binding.homeSleepData.text = slider.value.toInt().toString()

                //update the shared preferences
                val prefs = binding.root.context.getSharedPreferences(selectedDate.replace("/", ""), MODE_PRIVATE)
                val editor = prefs.edit()
                editor.putInt("sleep", slider.value.toInt())
                editor.apply()
            }
        })

        binding.moodSlider.addOnSliderTouchListener(object: Slider.OnSliderTouchListener {
            //required for abstract class
            override fun onStartTrackingTouch(slider: Slider) {

            }

            //when a value is selected, hide the input
            override fun onStopTrackingTouch(slider: Slider) {
                toggleMoodInput()

                //update the mood display
                //1 = bad, 2 = average, 3 = happy
                if(slider.value == 1.0F) {
                    binding.homeMood.setImageResource(R.drawable.mood_frown)
                } else if (slider.value == 2.0F) {
                    binding.homeMood.setImageResource(R.drawable.mood_average)
                } else {
                    binding.homeMood.setImageResource(R.drawable.mood_smile)
                }

                //update the shared preferences
                val prefs = binding.root.context.getSharedPreferences(selectedDate.replace("/", ""), MODE_PRIVATE)
                val editor = prefs.edit()
                editor.putInt("mood", slider.value.toInt())
                editor.apply()
            }
        })

        binding.sleepSlider.addOnChangeListener { slider, value, fromUSer ->
            //update the display
            binding.sleepDisplay.text = (slider.value).toString()
        }

        binding.moodSlider.addOnChangeListener { slider, value, fromUSer ->
            //update the display
            //1 = bad, 2 = average, 3 = happy
            if(slider.value == 1.0F) {
                binding.moodDisplay.setImageResource(R.drawable.mood_frown)
            } else if (slider.value == 2.0F) {
                binding.moodDisplay.setImageResource(R.drawable.mood_average)
            } else {
                binding.moodDisplay.setImageResource(R.drawable.mood_smile)
            }
        }

        binding.stepsKnob.setOnStateChanged(OnStateChanged {
            binding.stepDisplay.text = (binding.stepsKnob.state*1000).toString()
        })

        binding.homeSubmitSteps.setOnClickListener {
            toggleStepInput()
        }

    }

    /**
     * Called when the view is deconstructed.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Toggles the sleep input ui.
     */
    fun toggleSleepInput() {
        if(binding.sleepEntry.visibility == View.GONE) {
            binding.inputLayout.visibility = View.GONE
            binding.sleepEntry.visibility = View.VISIBLE
            binding.sleepSlider.value = 0.0F
        } else {
            binding.inputLayout.visibility = View.VISIBLE
            binding.sleepEntry.visibility = View.GONE
        }
    }

    /**
     * Toggles the step input ui.
     */
    fun toggleStepInput() {
        if(binding.stepEntry.visibility == View.GONE) {
            binding.inputLayout.visibility = View.GONE
            binding.stepEntry.visibility = View.VISIBLE
        } else {
            //update the display
            binding.homeStepData.text = binding.stepDisplay.text

            //update the storage
            val prefs = binding.root.context.getSharedPreferences(selectedDate.replace("/", ""), MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putInt("steps", (binding.stepsKnob.state*1000))
            editor.apply()

            //change visibilities
            binding.inputLayout.visibility = View.VISIBLE
            binding.stepEntry.visibility = View.GONE
        }
    }

    /**
     * Toggles the mood input ui.
     */
    fun toggleMoodInput() {
        if(binding.moodEntry.visibility == View.GONE) {
            binding.inputLayout.visibility = View.GONE
            binding.moodEntry.visibility = View.VISIBLE
        } else {
            binding.inputLayout.visibility = View.VISIBLE
            binding.moodEntry.visibility = View.GONE
        }
    }

    /**
     * Toggles the diary ui.
     */
    fun toggleDiary() {
        val prefs = binding.root.context.getSharedPreferences(selectedDate.replace("/", ""), Context.MODE_PRIVATE)
        if(binding.diaryLayout.visibility == View.GONE) {
            //update the date display
            val sb = StringBuilder()
            binding.homeDiaryDateLabel.text = "Viewing journal for:"
            binding.homeDiaryDateLabel.text = sb.append(binding.homeDiaryDateLabel.text).append(" ").append(selectedDate).toString()

            //update the text box if we have any data saved
            if(prefs.contains("diary") && !prefs.getString("diary", "").equals("")) {
                binding.homeDiaryView.text = prefs.getString("diary", "")
            } else {
                binding.homeDiaryView.text = "There is currently no journal entry for this day."
            }
            binding.homeScrollLayout.visibility = View.GONE
            binding.diaryLayout.visibility = View.VISIBLE
        } else {
            //save our data
            prefs.edit().putString("diary", binding.homeDiaryEditor.text.toString()).apply()

            binding.homeDiaryEditor.visibility = View.GONE
            binding.homeDiaryView.visibility = View.VISIBLE
            binding.homeScrollLayout.visibility = View.VISIBLE
            binding.diaryLayout.visibility = View.GONE

            //hide keyboard
            val imm = binding.root.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view?.windowToken, 0)
        }
    }

}