package com.example.grzegorz.androidmvvm.mainView.network

import com.example.grzegorz.androidmvvm.mainView.model.CoinModel
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

interface ApiServiceInterface {
    fun getAllCoins(): Observable<List<CoinModel>>
}

class ApiService(
    private val baseUrl: String = "https://api.coinmarketcap.com/"
): ApiServiceInterface {

    // Public Methods

    override fun getAllCoins(): Observable<List<CoinModel>> {

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(baseUrl).build()

        val postsApi = retrofit.create(ApiClient::class.java)

        return postsApi.getAllCoins()
    }
}