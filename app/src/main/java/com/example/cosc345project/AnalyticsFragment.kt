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

        // create graphView for Sleep
        val sleepGraph = binding.analyticsSleepGraph

        //formatting and initialising for weekArray
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val calendar = Calendar.getInstance()
        val weekArray = Array(7) {calendar.time}

        // inputs dates into array, from 7 days back to current date
        for (i in 0..6) {
            weekArray[6-i] = calendar.time
            calendar.add(Calendar.DATE, -1)
        }

        val seriesSleep = LineGraphSeries<DataPoint>()

        // obtains all sleep data for corresponding days, reformats then adds to the data series
        for (i in 0..6) {
            val date = sdf.format(weekArray[i]).replace("/", "")
            val dateData = binding.root.context.getSharedPreferences(date,MODE_PRIVATE)
            val sleepData = dateData.getInt("sleep", 0)
            seriesSleep.appendData(DataPoint(weekArray[i], sleepData.toDouble()),
                true, 7)
        }

        sleepGraph.addSeries(seriesSleep)

        // date label formatter to set it as MM/dd/yy
        sleepGraph.gridLabelRenderer.labelFormatter = DateAsXAxisLabelFormatter(activity)
        sleepGraph.gridLabelRenderer.numHorizontalLabels = 4

        // setting x axis divisions
        sleepGraph.viewport.setMinX(weekArray[0].time.toDouble())
        sleepGraph.viewport.setMaxX(weekArray[6].time.toDouble())
        sleepGraph.viewport.isXAxisBoundsManual
        sleepGraph.gridLabelRenderer.setHumanRounding(false)

        // create graphView for Steps
        val stepsGraph = binding.analyticsStepsGraph

        val seriesSteps = LineGraphSeries<DataPoint>()

        // obtains all steps data for corresponding days, reformats to Key:date's required structure
        // then adds to the data series
        for (i in 0..6) {
            val date = sdf.format(weekArray[i]).replace("/", "")
            val dateData = binding.root.context.getSharedPreferences(date,MODE_PRIVATE)
            val stepsData = dateData.getInt("steps", 0)
            seriesSteps.appendData(DataPoint(weekArray[i], stepsData.toDouble()),
                true, 7)
        }

        stepsGraph.addSeries(seriesSteps)

        // date label formatter to set it as MM/dd/yy
        stepsGraph.gridLabelRenderer.labelFormatter = DateAsXAxisLabelFormatter(activity)
        stepsGraph.gridLabelRenderer.numHorizontalLabels = 4

        // setting x axis divisions
        stepsGraph.viewport.setMinX(weekArray[0].time.toDouble())
        stepsGraph.viewport.setMaxX(weekArray[6].time.toDouble())
        stepsGraph.viewport.isXAxisBoundsManual
        stepsGraph.gridLabelRenderer.setHumanRounding(false)

    }

    /**
     * Called when the view is deconstructed.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}