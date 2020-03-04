package com.example.lichet.di.component

import com.example.lichet.CustomApplication
import com.example.lichet.di.component.ActivityComponent
import com.example.lichet.di.module.ActivityModule
import com.example.lichet.di.module.AndroidModule
import com.example.lichet.di.module.ApplicationModule
import com.example.lichet.di.module.HttpClientModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class, AndroidModule::class, HttpClientModule::class))
interface ApplicationComponent {
    fun inject(application: CustomApplication)
    fun plus(module: ActivityModule): ActivityComponent
}