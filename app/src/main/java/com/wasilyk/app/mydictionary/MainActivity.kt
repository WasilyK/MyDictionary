package com.wasilyk.app.mydictionary

import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.AnticipateInterpolator
import android.widget.SimpleAdapter
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.wasilyk.app.favorite.FavoriteFragment
import com.wasilyk.app.history.HistoryFragment
import com.wasilyk.app.main.MainFragment
import com.wasilyk.app.mydictionary.databinding.ActivityMainBinding
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding
    @Inject lateinit var navigatorHolder: NavigatorHolder
    @Inject lateinit var router: Router
    private val navigator = AppNavigator(this, R.id.fragment_container)

    override fun onCreate(savedInstanceState: Bundle?) {

        setDefaultSplashScreen()

        App.instance.appComponent.inject(this)

        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val fragment = supportFragmentManager.findFragmentById(viewBinding.fragmentContainer.id)
        if(fragment == null) {
            router.newRootScreen(FragmentScreen { MainFragment.newInstance() } )
        }
    }

    private fun setDefaultSplashScreen() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            setSplashScreenHideAnimation()
        }

        setSplashScreenDuration()
    }

    @RequiresApi(31)
    private fun setSplashScreenHideAnimation() {
        splashScreen.setOnExitAnimationListener { splashScreenView ->
            val slideLeft = ObjectAnimator.ofFloat(
                splashScreenView,
                View.TRANSLATION_X,
                0f,
                -splashScreenView.height.toFloat()
            )
            slideLeft.interpolator = AnticipateInterpolator()
            slideLeft.duration = 1000L
            slideLeft.doOnEnd { splashScreenView.remove() }
            slideLeft.start()
        }
    }

    private fun setSplashScreenDuration() {
        var isHideSplashScreen = false
        object : CountDownTimer(2000, 1000) {
            override fun onTick(millisUntilFinished: Long) { }
            override fun onFinish() { isHideSplashScreen = true }
        }.start()

        val content = findViewById<View>(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    return if(isHideSplashScreen) {
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else {
                        false
                    }
                }
            }
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.history -> {
                val fragment = supportFragmentManager.findFragmentById(viewBinding.fragmentContainer.id)
                if(fragment !is HistoryFragment) {
                    router.navigateTo(FragmentScreen { HistoryFragment.newInstance() })
                }
                true
            }
            R.id.favorite -> {
                val fragment = supportFragmentManager.findFragmentById(viewBinding.fragmentContainer.id)
                if(fragment !is FavoriteFragment) {
                    router.navigateTo(FragmentScreen { FavoriteFragment.newInstance() })
                }
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }
}