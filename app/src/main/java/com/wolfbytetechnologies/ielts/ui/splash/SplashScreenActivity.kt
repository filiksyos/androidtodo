package com.wolfbytetechnologies.ielts.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.wolfbytetechnologies.ielts.MainActivity

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Install splash screen
        val splashScreen = installSplashScreen()

        // Keep splash screen visible while performing any initialization
        splashScreen.setKeepOnScreenCondition {
            // Add a condition to keep the splash screen visible (e.g., until initialization is done)
            false // Use `false` if there's no initialization
        }

        // Start MainActivity after a slight delay to ensure smooth transition
        splashScreen.setOnExitAnimationListener { splashScreenView ->
            splashScreenView.remove() // Remove splash screen animation
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}

