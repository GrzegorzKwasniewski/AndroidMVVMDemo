package com.example.grzegorz.androidmvvm.mainView.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.grzegorz.androidmvvm.helpers.*
import com.example.grzegorz.androidmvvm.mainView.model.CoinModel
import com.example.grzegorz.androidmvvm.mainView.network.ApiService
import com.example.grzegorz.androidmvvm.mainView.network.ApiServiceInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(
    private val apiService: ApiServiceInterface = ApiService()
): ViewModel() {

    // Internal Properties

    // Public is default
    internal val coins = MutableLiveData<List<CoinModel>>()
    internal val progress = MutableLiveData<Boolean>(false)
    internal val errors = MutableLiveData<ErrorMessage>()


    internal val coinsCount: Int
        get() = coins.value?.count() ?: 0

    // Private Properties

    private var disposable: Disposable? = null

    // Internal Methods

    internal fun getCoinsData() {

        disposable?.dispose()

        disposable = apiService.getAllCoins()
            .subscribeOnIOThread()
            .observeOnMainThread()
            .withProgress(progress)
            .showErrorMessages(errors)
            .subscribe {
                coins.value = it
            }
    }

    override fun onCleared() {
        disposable?.dispose()
        super.onCleared()
    }
}