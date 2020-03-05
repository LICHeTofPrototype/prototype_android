package com.example.lichet.di.component

import com.example.lichet.view.main.MainView
import com.example.lichet.di.module.ActivityModule
import com.example.lichet.di.scope.ActivityScope
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = arrayOf(ActivityModule::class))
interface ActivityComponent {
    fun inject(activity: MainView.MainActivity)
}