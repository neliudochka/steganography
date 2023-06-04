package com.example.steganography

import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.steganography.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNavController()
        initToolbar()
        initBottomNavBar()
    }

    private fun initNavController() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        navController = navHostFragment.navController
    }
    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setTitleTextColor(Color.WHITE)
    }

    private fun initBottomNavBar(){
        binding.bottomNavigationView.menu.children.forEach { it ->
            it.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.encodeSection -> {
                        check(it)
                        if (navController.isFragmentInBackStack(R.id.encodeResultFragment)) {
                            navController.navigate(R.id.encodeResultFragment)
                        } else {
                            navController.navigate(R.id.encodeSetupFragment)
                        }
                    }
                    R.id.decodeSection -> {
                        check(it)
                        if (navController.isFragmentInBackStack(R.id.decodeResultFragment)) {
                            navController.navigate(R.id.decodeResultFragment)
                        } else {
                            navController.navigate(R.id.decodeSetupFragment)
                        }
                    }
                }
                true
            }
        }

    }

    private fun check(item: MenuItem): Boolean {
        item.isChecked = !item.isChecked
        return true
    }

    private fun NavController.isFragmentInBackStack(destinationId: Int) =
        try {
            getBackStackEntry(destinationId)
            true
        } catch (e: Exception) {
            false
        }
}