package com.example.grzegorz.androidmvvm.mainView.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.grzegorz.androidmvvm.mainView.model.CoinModel
import com.example.grzegorz.androidmvvm.mainView.network.ApiService
import io.reactivex.disposables.Disposable

class MainViewModel(
    private val apiService: ApiService
): ViewModel() {

    // Internal Properties

    internal val coins = MutableLiveData<List<CoinModel>>()

    // Private Properties

    private var disposable: Disposable? = null

    // Internal Methods

    internal fun getCoinsData() {

        disposable?.dispose()

        disposable = apiService.getAllCoins()
            .subscribe {

            }
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}