package com.example.lichet.view

import android.app.Activity
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.widget.ArrayAdapter
import com.example.lichet.BaseActivity
import com.example.lichet.CustomApplication
import com.example.lichet.R
import com.example.lichet.di.module.ActivityModule
import com.example.lichet.presenter.MainPresenter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import org.jetbrains.anko.longToast
import javax.inject.Inject

interface MainView {
    fun showProgress()
    fun hideProgress()
    fun showToast(toastMessage: String)

    class MainActivity : BaseActivity(), MainView {

        @Inject
        lateinit var presenter: MainPresenter

        private lateinit var appBarConfiguration: AppBarConfiguration

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)
            setSupportActionBar(toolbar)

            //Injection
            (application as CustomApplication).component.plus(ActivityModule()).inject(this)

            presenter.takeView(this)

            initView()
        }

        private fun initView(){
            fab.setOnClickListener { view ->
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }

            val navController = findNavController(R.id.nav_host_fragment)
            appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.nav_home,
                    R.id.nav_gallery,
                    R.id.nav_slideshow,
                    R.id.nav_tools,
                    R.id.nav_share,
                    R.id.nav_send
                ), drawer_layout
            )
            setupActionBarWithNavController(navController, appBarConfiguration)
            nav_view.setupWithNavController(navController)

            val spinnerAdapter = ArrayAdapter<Int>(this, android.R.layout.simple_spinner_item)
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            (1.. 100).toList().forEach {
                spinnerAdapter.add(it)
            }
            spinner.adapter = spinnerAdapter

            btn.setOnClickListener {
                val selectedValue = spinner.selectedItem as Int
                presenter.onClickBtn(selectedValue)
            }
        }


        override fun onCreateOptionsMenu(menu: Menu): Boolean {
            // Inflate the menu; this adds items to the action bar if it is present.
            menuInflater.inflate(R.menu.main, menu)
            return true
        }

        override fun onSupportNavigateUp(): Boolean {
            val navController = findNavController(R.id.nav_host_fragment)
            return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
        }

        override fun showProgress() {
            if (!progressDialog.isShowing){
                progressDialog.show()
            }
        }

        override fun hideProgress() {
            progressDialog.dismiss()
        }

        override fun showToast(toastMessage: String) {
            longToast(toastMessage)
        }

        override fun onDestroy() {
            super.onDestroy()
            presenter.onDestroy()
        }
    }
}