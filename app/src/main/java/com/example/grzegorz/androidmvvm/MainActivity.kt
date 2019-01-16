package com.example.grzegorz.androidmvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        disposable = downloadButton.clicks()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                println("click")
            }
    }

    override fun onPause() {
        disposable?.dispose()
        super.onPause()
    }
}
