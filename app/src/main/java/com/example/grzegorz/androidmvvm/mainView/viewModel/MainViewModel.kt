package com.example.grzegorz.androidmvvm.mainView.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.grzegorz.androidmvvm.mainView.model.CoinModel
import com.example.grzegorz.androidmvvm.mainView.network.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(
    private val apiService: ApiService
): ViewModel() {

    // Internal Properties

    // Public is default
    internal val coins = MutableLiveData<List<CoinModel>>()

    // Private Properties

    private var disposable: Disposable? = null

    // Internal Methods

    internal fun getCoinsData() {

        disposable?.dispose()

        disposable = apiService.getAllCoins()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                coins.value = it
            }
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}