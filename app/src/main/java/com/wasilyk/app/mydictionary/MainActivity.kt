package com.wasilyk.app.mydictionary

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
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
        App.instance.appComponent.inject(this)

        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val fragment = supportFragmentManager.findFragmentById(viewBinding.fragmentContainer.id)
        if(fragment == null) {
            router.newRootScreen(FragmentScreen { MainFragment.newInstance() } )
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