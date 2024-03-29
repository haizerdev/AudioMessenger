package com.fantastic.audiomessanger.ui.activites

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.fantastic.audiomessanger.R
import com.fantastic.audiomessanger.interfaces.MainNavigator
import com.fantastic.audiomessanger.ui.fragments.AddConversationFragment
import com.fantastic.audiomessanger.ui.fragments.ConversationFragment
import com.fantastic.audiomessanger.ui.fragments.MainFragment
import com.fantastic.audiomessanger.viewModel.MainViewModel

class MainActivity : AppCompatActivity(), MainNavigator, MainFragment.OpenNewFragment,AddConversationFragment.DestroyFragment {

    private val fragmentMain = MainFragment.newInstance()
    private val fragmentConversation = ConversationFragment.newInstance()
    private val fragmentAddConversation = AddConversationFragment.newInstance()

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        if (savedInstanceState == null) {
            startFragment(fragmentMain,false)
        }

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    override fun openFragment() {
       startFragment(fragmentConversation,true)

    }

    override fun startFragment(fragment: Fragment, hide: Boolean) {
        if(hide){
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragmentAddConversation)
                .hide(fragmentMain)
                .commitNow()
            Log.d("Open","Fragment")
        }else{
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commitNow()
        }

    }

    override fun destroyAddConversation() {
        supportFragmentManager.beginTransaction()
            .remove(fragmentAddConversation)
            .replace(R.id.container, fragmentMain)
            .show(fragmentMain)
            .commitNow()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.deleteAll -> {
                Toast.makeText(applicationContext, "Delete", Toast.LENGTH_LONG).show()
                viewModel.deleteAllConversations()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
