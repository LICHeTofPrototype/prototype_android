package com.example.lichet.presenter

import android.util.Log
import com.example.lichet.api.request.HeartBeatRequest
import com.example.lichet.view.main.MainView
import com.example.lichet.di.scope.ActivityScope
import com.example.lichet.exception.AppException
import com.example.lichet.usecase.MainUseCase
import com.example.lichet.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@ActivityScope
class MainPresenter  @Inject constructor(
    private val compositeDisposable: CompositeDisposable,
    private val schedulerProvider: SchedulerProvider,
    private val useCase: MainUseCase){

    val TAG = MainPresenter::class.java.simpleName

    var view: MainView? = null
    private set

    fun onCreate(){}

    fun onDestroy(){
        compositeDisposable.clear()
    }

    fun takeView(view: MainView) {
        this.view = view
    }

    fun dropView() {
        this.view = null
    }

    val isViewAttached: Boolean
        get() = view != null

    fun onClickBtn(requestIndex: Int){
        getHeartBeats(requestIndex)
    }

    private fun getHeartBeats(requestIndex: Int){
        val view = view?:return
        useCase.getHeartBeats(HeartBeatRequest(2, requestIndex))
            .doOnSubscribe { view.showProgress() }
            .observeOn(schedulerProvider.ui())
            .subscribe({heartBeats ->
                view.hideProgress()
                view.showToast("success")

                view.relpaceFragment(heartBeats)

            },{
                view.hideProgress()
                if (it is AppException) {
                    it.userMessage?.let {
                        view.showToast(it)
                    }
                } else {
                    Log.e(TAG, it.toString())
                }
            }).also { compositeDisposable.add(it) }

    }

}