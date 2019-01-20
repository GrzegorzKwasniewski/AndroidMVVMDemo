package com.example.grzegorz.androidmvvm.mainView.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.grzegorz.androidmvvm.helpers.withProgress
import com.example.grzegorz.androidmvvm.mainView.model.CoinModel
import com.example.grzegorz.androidmvvm.mainView.network.ApiServiceInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(
    private val apiService: ApiServiceInterface
): ViewModel() {

    // Internal Properties

    // Public is default
    internal val coins = MutableLiveData<List<CoinModel>>()
    internal val progress = MutableLiveData<Boolean>(false)

    // Private Properties

    private var disposable: Disposable? = null

    // Internal Methods

    internal fun getCoinsData() {

        disposable?.dispose()

        disposable = apiService.getAllCoins()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .withProgress(progress)
            .subscribe {
                coins.value = it
            }
    }

    override fun onCleared() {
        disposable?.dispose()
        super.onCleared()
    }
}