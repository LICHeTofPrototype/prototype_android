package com.example.lichet.usecase

import android.content.Context
import com.example.lichet.api.Client
import com.example.lichet.api.response.HeartBeat
import com.example.lichet.di.module.ApplicationModule
import com.example.lichet.di.scope.ActivityScope
import com.example.lichet.util.SchedulerProvider
import io.reactivex.Single
import javax.inject.Inject


@ActivityScope
class MainUseCase @Inject constructor(
    private val apiClient: Client,
    private val defaultPrefsWrapper: ApplicationModule.DefaultPrefsWrapper,
    private val schedulerProvider: SchedulerProvider,
    private val context: Context){

    fun getHeartBeats(userId: Int, measureMentId: Int, requestIndex: Int): Single<List<HeartBeat>> {
        return apiClient.getHearBeats(userId, measureMentId, requestIndex)
    }
}