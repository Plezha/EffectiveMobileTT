package com.plezha.effectivemobilett.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.plezha.effectivemobilett.R
import com.plezha.effectivemobilett.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainActivityViewModel: MainActivityViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavView: BottomNavigationView = binding.navView

        val badge = bottomNavView.getOrCreateBadge(R.id.favorites_screen_nav_graph)
        badge.backgroundColor = applicationContext.getColor(com.plezha.ui.R.color.red)
        badge.badgeTextColor = applicationContext.getColor(com.plezha.ui.R.color.white)

        lifecycleScope.launch {
            mainActivityViewModel.favoritesAmount.collect {
                badge.number = it
            }
        }


        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        bottomNavView.setupWithNavController(navController)
    }
}