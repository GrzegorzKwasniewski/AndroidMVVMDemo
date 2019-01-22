package com.example.grzegorz.androidmvvm.helpers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.grzegorz.androidmvvm.R
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

fun <T> Observable<T>.showErrorMessages(
    mutableLiveData: MutableLiveData<ErrorMessage>
): Observable<T> {

    return compose {
        it.doOnError { error ->

            mutableLiveData.postValue(
                ErrorMessage(error.localizedMessage)
            )
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

inline fun <T> LiveData<T>.subscribe(lifecycle: LifecycleOwner, crossinline onChanged: (T) -> Unit) {
    observe(lifecycle, Observer { it?.run(onChanged) })
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}