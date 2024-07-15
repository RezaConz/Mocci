package com.example.mocci

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.mocci.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var host: NavHostFragment
    private lateinit var graph: NavGraph
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        host = supportFragmentManager.findFragmentById(R.id.mainNavHostFragment) as NavHostFragment
        graph = host.navController.navInflater.inflate(R.navigation.navigation_graph)
        graph.setStartDestination(R.id.movie)

        navController = host.navController

        val navView: BottomNavigationView = binding.navView
        NavigationUI.setupWithNavController(navView, navController)
    }

    override fun onResume() {
        super.onResume()
        graph.setStartDestination(R.id.movie)
        host.navController.graph = graph
    }
}