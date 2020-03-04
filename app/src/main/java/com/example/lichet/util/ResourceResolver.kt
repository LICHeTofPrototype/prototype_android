package com.example.lichet.util

import android.content.Context

import androidx.annotation.StringRes
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class ResourceResolver @Inject constructor(private val context: Context){

    fun getString(@StringRes resId: Int): String {
        return context.getString(resId)
    }

    fun getString(@StringRes resId: Int, vararg formatArgs: Any): String {
        return context.getString(resId, formatArgs)
    }
    companion object {
        val TAG = ResourceResolver::class.java.simpleName
    }
}