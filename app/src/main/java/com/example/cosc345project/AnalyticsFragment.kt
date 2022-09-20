package com.example.cosc345project

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.cosc345project.databinding.AnalyticsFragmentBinding

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
    }

    /**
     * Called when the view is deconstructed.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}