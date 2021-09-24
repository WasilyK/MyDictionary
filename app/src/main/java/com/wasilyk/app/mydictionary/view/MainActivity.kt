package com.wasilyk.app.mydictionary.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.wasilyk.app.mydictionary.R
import com.wasilyk.app.mydictionary.databinding.ActivityMainBinding
import com.wasilyk.app.mydictionary.di.module.FAVORITE_FRAGMENT_SCREEN
import com.wasilyk.app.mydictionary.di.module.HISTORY_FRAGMENT_SCREEN
import com.wasilyk.app.mydictionary.di.module.MAIN_FRAGMENT_SCREEN
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named

class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding
    private val navigator = AppNavigator(this, R.id.fragment_container)
    private val navigatorHolder: NavigatorHolder by inject()
    private val router: Router by inject()
    private val mainFragmentScreen: Screen by inject(named(MAIN_FRAGMENT_SCREEN))
    private val historyFragmentScreen: Screen by inject(named(HISTORY_FRAGMENT_SCREEN))
    private val favoriteFragmentScreen: Screen by inject(named(FAVORITE_FRAGMENT_SCREEN))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val fragment = supportFragmentManager.findFragmentById(viewBinding.fragmentContainer.id)
        if(fragment == null) {
            router.newRootScreen(mainFragmentScreen)
        }
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
                    router.navigateTo(historyFragmentScreen, true)
                }
                true
            }
            R.id.favorite -> {
                val fragment = supportFragmentManager.findFragmentById(viewBinding.fragmentContainer.id)
                if(fragment !is FavoriteFragment) {
                    router.navigateTo(favoriteFragmentScreen, true)
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