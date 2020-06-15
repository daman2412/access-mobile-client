package org.iota.access.ui.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.android.AndroidInjection
import org.iota.access.MainActivity
import org.iota.access.R
import org.iota.access.ui.auth.login.LoginActivity
import org.iota.access.user.UserManager
import javax.inject.Inject

class SplashActivity : AppCompatActivity(R.layout.activity_splash) {
    companion object {
        init {
            System.loadLibrary("native-lib")
        }
    }

    @Inject
    lateinit var mUserManager: UserManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)

        // check if user has already logged in
        if (mUserManager.isUserLoggedIn) {
            // user has logged in
            startActivity(MainActivity.newIntent(this))
        } else {
            // user has NOT logged in
            startActivity(LoginActivity.newIntent(this))
        }
        finish()
    }
}
