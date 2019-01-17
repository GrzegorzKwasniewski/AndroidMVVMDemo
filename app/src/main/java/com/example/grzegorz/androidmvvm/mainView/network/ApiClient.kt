package com.example.grzegorz.androidmvvm.mainView.network

import com.example.grzegorz.androidmvvm.mainView.model.CoinModel
import io.reactivex.Observable
import retrofit2.http.GET

interface ApiClient {

    @GET("v1/ticker/")
    fun getAllCoins(): Observable<List<CoinModel>>
}