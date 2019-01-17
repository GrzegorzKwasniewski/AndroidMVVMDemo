package com.example.grzegorz.androidmvvm.mainView.network

import com.example.grzegorz.androidmvvm.mainView.model.CoinModel
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiService {

    val baseUrl = "https://api.coinmarketcap.com/"

    fun getAllCoins(): Observable<List<CoinModel>> {

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(baseUrl).build()

        val postsApi = retrofit.create(ApiClient::class.java)

        return postsApi.getAllCoins()
    }
}