package com.example.grzegorz.androidmvvm.mainView.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.grzegorz.androidmvvm.helpers.*
import com.example.grzegorz.androidmvvm.mainView.model.CoinModel
import com.example.grzegorz.androidmvvm.mainView.network.ApiService
import com.example.grzegorz.androidmvvm.mainView.network.ApiServiceInterface
import io.reactivex.disposables.Disposable

interface MainViewModelInterface {
    val coins: MutableLiveData<List<CoinModel>>
    val progress: MutableLiveData<Boolean>
    val errors: MutableLiveData<ErrorMessage>
    val coinsCount: Int

    fun getCoinsData()
}

class MainViewModel(
    private val apiService: ApiServiceInterface = ApiService()
): ViewModel(), MainViewModelInterface {

    // Public Properties

    override val coins = MutableLiveData<List<CoinModel>>()
    override val progress = MutableLiveData<Boolean>(false)
    override val errors = MutableLiveData<ErrorMessage>()

    override val coinsCount: Int
        get() = coins.value?.count() ?: 0

    // Private Properties

    private var disposable: Disposable? = null

    // Internal Methods

    override fun getCoinsData() {

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