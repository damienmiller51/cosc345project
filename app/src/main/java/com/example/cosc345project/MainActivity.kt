package com.example.cosc345project

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.cosc345project.databinding.ActivityMainBinding

/**
 * The Activity responsible for dealing with the app while running.
 * @author Damien Miller
 */
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    /**
     * Called on creation of MainActivity.
     * @param savedInstanceState -
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        //check to see if we should be in night mode
        val settings = binding.root.context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        if(settings.contains("theme")) {
            if (settings.getString("theme", "").equals("dark"))
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        // creates notification channel, then sends an notification on startup
        createNotificationChannel()
        val service = MoodNotificationService(applicationContext)
        service.showNotification()
    }

    /**
     * Creates notification channel
     */
    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(MoodNotificationService.MOOD_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

        }
    }

    /**
     * Inflates the options bar.
     * @param menu The menu to be inflated as the options bar.
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)

        return true
    }

    /**
     * Deals with the selection of the options bar.
     * @param item The MenuItem to handle.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //option actions
        val id = item.itemId
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        if(id == R.id.action_item_home)
            navController.navigate(R.id.HomeFragment)
        if(id == R.id.action_item_analytics)
            navController.navigate(R.id.AnalyticsFragment)
        if(id == R.id.action_item_settings)
            navController.navigate(R.id.SettingsFragment)


        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_item_settings -> true
            R.id.action_item_analytics -> true
            R.id.action_item_home -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * Handles the back button navigation - figures out what page to navigate back to.
     */
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}