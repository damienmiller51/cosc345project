package com.example.cosc345project

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cosc345project.databinding.AnalyticsFragmentBinding
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import java.text.SimpleDateFormat
import java.util.*


/**
 * The fragment class containing the framework for the Analytics page.
 * @author Damien Miller
 */
class AnalyticsFragment : Fragment() {

    private var _binding: AnalyticsFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    /**
     * Create the Analytics page and inflate the layout.
     * @param inflater The layout inflater.
     * @param container The view container.
     * @param savedInstanceState -
     * @return The outermost view from the layout file.
     */
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = AnalyticsFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    /**
     * Called immediately after the Analytics page is created.
     * @param view The Analytics view.
     * @param savedInstanceState -
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //formatting and initialising for weekArray
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val calendar = Calendar.getInstance()
        val weekArray = Array(7) {calendar.time}

        // inputs dates into array, from 7 days back to current date
        for (i in 0..6) {
            weekArray[6-i] = calendar.time
            calendar.add(Calendar.DATE, -1)
        }

        // GRAPHVIEW SLEEP

        val sleepGraph = binding.analyticsSleepGraph
        val seriesSleep = LineGraphSeries<DataPoint>()
        var sleepDailyAverage = 0

        // obtains all sleep data for corresponding days, reformats then adds to the data series
        for (i in 0..6) {
            val date = sdf.format(weekArray[i]).replace("/", "")
            val dateData = binding.root.context.getSharedPreferences(date,MODE_PRIVATE)
            val sleepData = dateData.getInt("sleep", 0)
            sleepDailyAverage += sleepData
            seriesSleep.appendData(DataPoint(weekArray[i], sleepData.toDouble()),
                true, 7)
        }

        sleepDailyAverage = sleepDailyAverage/7
        sleepGraph.addSeries(seriesSleep)


        // date label formatter to set it as MM/dd/yy
        sleepGraph.gridLabelRenderer.labelFormatter = DateAsXAxisLabelFormatter(activity,
            SimpleDateFormat("dd"))
        sleepGraph.gridLabelRenderer.numHorizontalLabels = 7
        sleepGraph.gridLabelRenderer.horizontalAxisTitle = "Date"
        sleepGraph.gridLabelRenderer.setHumanRounding(false)

        // setting x axis divisions
        sleepGraph.viewport.isXAxisBoundsManual
        sleepGraph.viewport.setMinX(weekArray[0].time.toDouble())
        sleepGraph.viewport.setMaxX(weekArray[6].time.toDouble())

        // GRAPHVIEW STEPS

        val stepsGraph = binding.analyticsStepsGraph
        val seriesSteps = LineGraphSeries<DataPoint>()
        var stepsDailyAverage = 0

        // obtains all steps data for corresponding days, reformats to Key:date's required structure
        // then adds to the data series
        for (i in 0..6) {
            val date = sdf.format(weekArray[i]).replace("/", "")
            val dateData = binding.root.context.getSharedPreferences(date,MODE_PRIVATE)
            val stepsData = dateData.getInt("steps", 0)
            stepsDailyAverage += stepsData
            seriesSteps.appendData(DataPoint(weekArray[i], stepsData.toDouble()),
                true, 7)
        }

        stepsDailyAverage = stepsDailyAverage/7
        stepsGraph.addSeries(seriesSteps)

        // date label formatter to set it as MM/dd/yy
        stepsGraph.gridLabelRenderer.labelFormatter = DateAsXAxisLabelFormatter(activity,
            SimpleDateFormat("dd"))
        stepsGraph.gridLabelRenderer.numHorizontalLabels = 7
        stepsGraph.gridLabelRenderer.horizontalAxisTitle = "Date"
        stepsGraph.gridLabelRenderer.setHumanRounding(false)
        stepsGraph.gridLabelRenderer.labelVerticalWidth

        // setting x axis divisions
        stepsGraph.viewport.isXAxisBoundsManual
        stepsGraph.viewport.setMinX(weekArray[0].time.toDouble())
        stepsGraph.viewport.setMaxX(weekArray[6].time.toDouble())

        // GRAPHVIEW MOOD

        val moodGraph = binding.analyticsMoodGraph
        val seriesMood = LineGraphSeries<DataPoint>()
        var moodDailyAverage = 0

        // obtains all mood data for corresponding days, reformats to Key:date's required structure
        // then adds to the data series
        for (i in 0..6) {
            val date = sdf.format(weekArray[i]).replace("/", "")
            val dateData = binding.root.context.getSharedPreferences(date,MODE_PRIVATE)
            val moodData = dateData.getInt("mood", 0)
            moodDailyAverage += moodData
            seriesMood.appendData(DataPoint(weekArray[i], moodData.toDouble()),
                true, 7)
        }

        moodDailyAverage = moodDailyAverage/7
        moodGraph.addSeries(seriesMood)

        // date label formatter to set it as MM/dd/yy
        moodGraph.gridLabelRenderer.labelFormatter = DateAsXAxisLabelFormatter(activity,
            SimpleDateFormat("dd"))
        moodGraph.gridLabelRenderer.numHorizontalLabels = 7
        moodGraph.gridLabelRenderer.horizontalAxisTitle = "Date"
        moodGraph.gridLabelRenderer.setHumanRounding(false)

        // setting x axis divisions
        moodGraph.viewport.isXAxisBoundsManual
        moodGraph.viewport.setMinX(weekArray[0].time.toDouble())
        moodGraph.viewport.setMaxX(weekArray[6].time.toDouble())

        // TEXTVIEW DAILY AVERAGES

        val dailyAverages = binding.analyticsDailyAverages
        val sb = StringBuilder()

        val dSleepText = "Daily Sleep (Hours): "
        val dSleepValue = sleepDailyAverage

        val dStepsText = "Daily Steps: "
        val dStepsValue = stepsDailyAverage

        val dMoodText = "Daily Mood: "
        val dMoodValue = moodDailyAverage

        dailyAverages.text = (sb.append(dSleepText).append(dSleepValue).append("\n")
            .append(dStepsText).append(dStepsValue).append("\n")
            .append(dMoodText).append(dMoodValue))
    }

    /**
     * Called when the view is deconstructed.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}