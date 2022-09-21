package com.example.cosc345project

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.cosc345project.databinding.SettingsFragmentBinding
import java.io.File

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

        //toggle button depending on what we have saved
        val settings = binding.root.context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        if(settings.contains("format")) {
            if(settings.getString("format", "").equals("MM/dd/yyyy"))
                binding.settingsToggleDate.toggle()
        }

        if(settings.contains("theme")) {
            if(settings.getString("theme", "").equals("dark"))
                binding.settingsToggleTheme.toggle()
        }

        //onClick listener for the clear button
        binding.settingsClearButton.setOnClickListener {
            clearUserData()
        }

        //onClick listener for the date format button
        binding.settingsToggleDate.setOnClickListener {
            changeDateFormat(binding.settingsToggleDate.isChecked)
        }

        //onClick listener for the change theme button
        binding.settingsToggleTheme.setOnClickListener {
            changeTheme(binding.settingsToggleTheme.isChecked)
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
     * Clears the data users have stored in the app.
     */
    fun clearUserData(){
        val sharedPreferencesPath = File(binding.root.context.filesDir.parentFile!!.absolutePath + File.separator + "shared_prefs")
        sharedPreferencesPath.listFiles()?.forEach { file ->
            //check to make sure the data is date data and not anything else
            if(file.nameWithoutExtension.length == 8 && file.nameWithoutExtension[4].equals('2'))
                binding.root.context.getSharedPreferences(file.nameWithoutExtension, Context.MODE_PRIVATE).edit().clear().apply()
        }
    }

    /**
     * Changes the way the app displays dates.
     * @param checked Whether the button clicked on is currently checked or not.
     */
    fun changeDateFormat(checked: Boolean) {
        if(checked) {
            val prefs = binding.root.context.getSharedPreferences("settings", Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putString("format", "MM/dd/yyyy").apply()
        } else {
            val prefs = binding.root.context.getSharedPreferences("settings", Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putString("format", "dd/MM/yyyy").apply()
        }
    }

    /**
     * Changes the theme of the app between light and dark.
     * @param checked Whether the button clicked on is currently checked or not.
     */
    fun changeTheme(checked: Boolean) {
        if(checked) {
            val prefs = binding.root.context.getSharedPreferences("settings", Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putString("theme", "dark").apply()
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            val prefs = binding.root.context.getSharedPreferences("settings", Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putString("theme", "light").apply()
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

}