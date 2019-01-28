package com.example.grzegorz.androidmvvm.mainView.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.grzegorz.androidmvvm.R
import com.example.grzegorz.androidmvvm.helpers.ErrorMessage
import com.example.grzegorz.androidmvvm.helpers.observeOnMainThread
import com.example.grzegorz.androidmvvm.helpers.show
import com.example.grzegorz.androidmvvm.helpers.subscribe
import com.example.grzegorz.androidmvvm.mainView.model.CoinModel
import com.example.grzegorz.androidmvvm.mainView.viewModel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity: AppCompatActivity() {

    // Private Properties
    private val viewModel by lazy { ViewModelProviders.of(this).get(MainViewModel::class.java) }

    private var disposable: Disposable? = null
    private var linearLayoutManager = LinearLayoutManager(this)

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
        viewModel.coins.subscribe(this, ::showAllCoins)
        viewModel.progress.subscribe(this, ::updateProgress)
        viewModel.errors.subscribe(this, ::showErrorMessage)
    }

    private fun bindUIGestures() {
        disposable = downloadButton.clicks()
            .observeOnMainThread()
            .subscribe {
                viewModel.getCoinsData()
            }
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = linearLayoutManager
    }

    // View Model Binding

    private fun showAllCoins(coins: List<CoinModel>) {
        recyclerView.adapter = RecyclerAdapter(coins)
    }

    private fun updateProgress(isDownloading: Boolean) {
        progressBar.show(isDownloading)
    }

    private fun showErrorMessage(error: ErrorMessage) {
        Snackbar.make(
            rootLayout,
            error.getMessage(),
            Snackbar.LENGTH_SHORT
        ).show()
    }
}
