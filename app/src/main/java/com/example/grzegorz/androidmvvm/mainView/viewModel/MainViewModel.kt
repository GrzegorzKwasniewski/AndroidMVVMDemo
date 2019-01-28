package com.example.grzegorz.androidmvvm.mainView.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.grzegorz.androidmvvm.helpers.*
import com.example.grzegorz.androidmvvm.mainView.model.CoinModel
import com.example.grzegorz.androidmvvm.mainView.network.ApiService
import com.example.grzegorz.androidmvvm.mainView.network.ApiServiceInterface
import io.reactivex.disposables.Disposable

interface MainViewModelInterface {
    val coins: LiveData<List<CoinModel>>
    val progress: LiveData<Boolean>
    val errors: LiveData<ErrorMessage>
    val coinsCount: Int

    fun getCoinsData()
}

class MainViewModel(
    private val apiService: ApiServiceInterface = ApiService()
): ViewModel(), MainViewModelInterface {

    // Public Properties

    override val coins: LiveData<List<CoinModel>>
        get() = coinsData

    override val progress: LiveData<Boolean>
        get() = progressData

    override val errors: LiveData<ErrorMessage>
        get() = errorsData

    override val coinsCount: Int
        get() = coinsData.value?.count() ?: 0

    // Private Properties

    private val coinsData = MutableLiveData<List<CoinModel>>()
    private val progressData = MutableLiveData<Boolean>(false)
    private val errorsData = MutableLiveData<ErrorMessage>()

    private var disposable: Disposable? = null

    // Internal Methods

    override fun getCoinsData() {

        disposable?.dispose()

        disposable = apiService.getAllCoins()
            .subscribeOnIOThread()
            .observeOnMainThread()
            .withProgress(progressData)
            .showErrorMessages(errorsData)
            .subscribe {
                coinsData.value = it
            }
    }

    override fun onCleared() {
        disposable?.dispose()
        super.onCleared()
    }
}