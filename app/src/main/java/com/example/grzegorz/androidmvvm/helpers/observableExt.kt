package com.example.grzegorz.androidmvvm.helpers

import android.view.View
import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable

fun <T> Observable<T>.withProgress(
    mutableLiveData: MutableLiveData<Boolean>
): Observable<T> {

    return compose {
        it.doOnSubscribe { _ ->
            mutableLiveData.postValue(true)
        }.doAfterTerminate {
            mutableLiveData.postValue(false)
        }
    }
}

fun View.show(show: Boolean) {
    if (show) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.INVISIBLE
    }
}