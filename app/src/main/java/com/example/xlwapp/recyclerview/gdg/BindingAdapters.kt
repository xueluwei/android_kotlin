package com.example.xlwapp.recyclerview.gdg

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.xlwapp.network.gdg.GdgChapter
import com.example.xlwapp.network.gdg.NewChapter

/**
 * When there is no Mars property data (data is null), hide the [RecyclerView], otherwise show it.
 */
@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<NewChapter>?) {
    val adapter = recyclerView.adapter as GdgListAdapter
    adapter.submitList(data)
}

@BindingAdapter("showOnlyWhenEmpty")
fun View.showOnlyWhenEmpty(data: List<NewChapter>?) {
    visibility = when {
        data == null || data.isEmpty() -> View.VISIBLE
        else -> View.GONE
    }
}