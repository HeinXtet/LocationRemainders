package com.udacity.project4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.udacity.project4.geofence.createChannel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initNavController()
    }

    private fun initNavController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        val appBarConfiguration =
            AppBarConfiguration.Builder(R.id.remaindersFragment, R.id.loginFragment)
                .build()
        setupActionBarWithNavController(navController, appBarConfiguration)
        createChannel(this)
//
//        if (intent.hasExtra(GeofenceUtils.GEOFENCE_EXTRA)) {
//            val placeId = intent.getStringExtra(GeofenceUtils.GEOFENCE_EXTRA)
//            if (!placeId.isNullOrEmpty()) {
//                navController.safeNavigate()
//            }
//        }
    }

    override fun onNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
}
