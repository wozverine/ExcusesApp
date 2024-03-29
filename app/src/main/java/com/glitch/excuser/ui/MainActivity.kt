package com.glitch.excuser.ui

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.glitch.excuser.R
import com.glitch.excuser.databinding.ActivityMainBinding
import com.glitch.excuser.ui.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
	private lateinit var appBarConfiguration: AppBarConfiguration
	private lateinit var binding: ActivityMainBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)
		setSupportActionBar(binding.toolbar)

		val navHostFragment =
			supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
		val navController = navHostFragment.navController

		val callback = object : OnBackPressedCallback(true /* enabled by default */) {
			override fun handleOnBackPressed() {
				val currentFragment = navHostFragment.childFragmentManager.primaryNavigationFragment
				if (currentFragment is HomeFragment) {
					finish()
				} else {
					navController.navigateUp()
				}
			}
		}
		onBackPressedDispatcher.addCallback(this, callback)

		appBarConfiguration = AppBarConfiguration(navController.graph)
		setupActionBarWithNavController(navController, appBarConfiguration)
		supportActionBar?.hide()
	}
}