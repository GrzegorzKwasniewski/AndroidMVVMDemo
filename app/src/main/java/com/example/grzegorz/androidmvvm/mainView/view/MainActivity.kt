package com.example.grzegorz.androidmvvm.mainView.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.grzegorz.androidmvvm.R
import com.example.grzegorz.androidmvvm.helpers.getViewModel
import com.example.grzegorz.androidmvvm.helpers.subscribe
import com.example.grzegorz.androidmvvm.mainView.model.CoinModel
import com.example.grzegorz.androidmvvm.mainView.viewModel.MainViewModel
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // View Model

    private val viewModel by lazy { getViewModel<MainViewModel>() }

    // Private Properties

    private var disposable: Disposable? = null

    // View Life Cycle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindUIData()
        bindUIGestures()
    }

    override fun onPause() {
        disposable?.dispose()
        super.onPause()
    }

    // Private Methods

    private fun bindUIData() {
        viewModel.coins.subscribe(this, this::showAllCoins)
    }

    private fun showAllCoins(coins: List<CoinModel>) {

    }

    private fun bindUIGestures() {
        disposable = downloadButton.clicks()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                viewModel.getCoinsData()
            }
    }
}
