package com.example.grzegorz.androidmvvm.mainView.view

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.grzegorz.androidmvvm.R
import com.example.grzegorz.androidmvvm.helpers.inflate
import com.example.grzegorz.androidmvvm.mainView.model.CoinModel

class RecyclerAdapter(private val menuItems: List<CoinModel>) : RecyclerView.Adapter<RecyclerAdapter.CoinHolder>() {

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.CoinHolder {
        val inflatedView = parent.inflate(R.layout.recyclerview_item_row, false)
        return CoinHolder(inflatedView)
    }

    override fun getItemCount() = menuItems.size

    override fun onBindViewHolder(holder: RecyclerAdapter.CoinHolder, position: Int) {
        val itemPhoto = menuItems[position]
        holder.bindCoin(itemPhoto)
    }

    class CoinHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {

        private var view: View = v
        private var coin: CoinModel? = null

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val context = itemView.context
        }

        fun bindCoin(coin: CoinModel) {

        }
    }
}