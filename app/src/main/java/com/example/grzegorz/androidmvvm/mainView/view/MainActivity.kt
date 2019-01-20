package com.example.grzegorz.androidmvvm.mainView.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.grzegorz.androidmvvm.R
import com.example.grzegorz.androidmvvm.helpers.show
import com.example.grzegorz.androidmvvm.helpers.subscribe
import com.example.grzegorz.androidmvvm.mainView.model.CoinModel
import com.example.grzegorz.androidmvvm.mainView.network.ApiService
import com.example.grzegorz.androidmvvm.mainView.viewModel.MainViewModel
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // View Model

    private val viewModel = MainViewModel(ApiService())

    // Private Properties

    private var disposable: Disposable? = null
    private lateinit var linearLayoutManager: LinearLayoutManager

    // View Life Cycle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindUIData()
        bindUIGestures()
        setupRecyclerView()
    }

    override fun onPause() {
        disposable?.dispose()
        super.onPause()
    }

    // Private Methods

    private fun bindUIData() {
        viewModel.coins.subscribe(this, this::showAllCoins)
        viewModel.progress.subscribe(this, this::updateProgress)
    }

    private fun bindUIGestures() {
        disposable = downloadButton.clicks()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                viewModel.getCoinsData()
            }
    }

    private fun setupRecyclerView() {
        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
    }

    // View Model Binding

    private fun showAllCoins(coins: List<CoinModel>) {
        recyclerView.adapter = RecyclerAdapter(coins)
    }

    private fun updateProgress(isDownloading: Boolean) {
        progressBar.show(isDownloading)
    }
}
