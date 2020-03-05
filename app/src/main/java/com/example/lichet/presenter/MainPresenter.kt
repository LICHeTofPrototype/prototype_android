package com.example.lichet.presenter

import android.util.Log
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

    val TAG = MainPresenter::class.java.simpleName + ": "

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
        useCase.getHeartBeats(1,1, requestIndex)
            .doOnSubscribe { view.showProgress() }
            .observeOn(schedulerProvider.ui())
            .subscribe({heartBeats ->
                view.hideProgress()
                view.showToast("結果をLogに出力")
                heartBeats.forEach {heartBeat ->
                    Log.i(TAG, heartBeat.pnn_time)
                    Log.i(TAG, heartBeat.pnn?:"null")
                }

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