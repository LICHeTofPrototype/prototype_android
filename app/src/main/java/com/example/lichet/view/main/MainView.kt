package com.example.lichet.view.main

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import android.view.Menu
import android.view.View
import android.widget.ArrayAdapter
import com.example.lichet.BaseActivity
import com.example.lichet.CustomApplication
import com.example.lichet.R
import com.example.lichet.api.response.HeartBeatResponse
import com.example.lichet.di.module.ActivityModule
import com.example.lichet.presenter.MainPresenter
import com.example.lichet.util.Const.Companion.TAG_BACK_PRESSED
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import org.jetbrains.anko.longToast
import javax.inject.Inject

interface MainView {
    fun showProgress()
    fun hideProgress()
    fun showToast(toastMessage: String)
    fun relpaceFragment(listHeartBeatResponse: List<HeartBeatResponse>)

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

            setSpinner()
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

            btn.setOnClickListener {
                val selectedValue = spinner.selectedItem as Int
                presenter.onClickBtn(selectedValue)
            }
        }

        private fun setSpinner(){
            val spinnerAdapter = ArrayAdapter<Int>(this, android.R.layout.simple_spinner_item)
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            (0.. 99).toList().forEach {
                spinnerAdapter.add(it)
            }
            spinner.adapter = spinnerAdapter
        }

        override fun relpaceFragment(listHeartBeatResponse: List<HeartBeatResponse>){
            val fragment = MainFragment().newInstance(listHeartBeatResponse)
            val fragmentManager = supportFragmentManager
            val trasnaction = fragmentManager.beginTransaction()
            trasnaction.replace(R.id.heartBeatFragment, fragment, TAG_BACK_PRESSED).commit()

            ll_select.visibility = View.GONE
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

        override fun onBackPressed() {
            val fragment = supportFragmentManager.findFragmentByTag(TAG_BACK_PRESSED)
            if (fragment is OnBackKeyPressedListener) {
                (fragment as OnBackKeyPressedListener).onBackPressed()
            } else {
                super.onBackPressed()
            }
        }

        interface OnBackKeyPressedListener {
            fun onBackPressed()
        }
    }
}