package com.example.cosc345project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cosc345project.databinding.SettingsFragmentBinding

/**
 * The fragment class containing the framework for the Settings page.
 * @author Damien Miller
 */
class SettingsFragment : Fragment() {
    private var _binding: SettingsFragmentBinding? = null

    private val binding get() = _binding!!

    /**
     * Create the Settings page and inflate the layout.
     * @param inflater The layout inflater.
     * @param container The view container.
     * @param savedInstanceState -
     * @return The outermost view from the layout file.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = SettingsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Called immediately after the Settings page is created.
     * @param view The Settings view.
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