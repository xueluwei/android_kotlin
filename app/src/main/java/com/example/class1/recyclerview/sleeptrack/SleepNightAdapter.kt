package com.example.class1.recyclerview.sleeptrack

import android.icu.util.DateInterval
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.class1.R
import com.example.class1.database.trackmysleep.entity.SleepNight
import com.example.class1.databinding.ListItemSleepNightBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.ClassCastException

//RecyclerList加Head
class SleepNightAdapter() : ListAdapter<DataItem, RecyclerView.ViewHolder>(SleepNightDiffCallback()){

    private val dataScope = CoroutineScope(Dispatchers.Main)

    class HeadViewHolder(view: View) : RecyclerView.ViewHolder(view){
        companion object{
            fun from(parent: ViewGroup): HeadViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.list_header_sleep_night, parent,false)
                return HeadViewHolder(view)
            }
        }
    }

    class ViewHolder private constructor(val binding: ListItemSleepNightBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: SleepNight) {
            binding.sleep = item
            binding.executePendingBindings()
        }

        companion object {

            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemSleepNightBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            DataItem.ITEM_VIEW_TYPE_HEADER -> HeadViewHolder.from(parent)
            DataItem.ITEM_VIEW_TYPE_ITEM -> ViewHolder.from(parent)
            else -> throw ClassCastException("Unknown View Type ${viewType}")
        }
    }

    fun addHeaderAndSubmit(list:List<SleepNight>?){
        dataScope.launch {
            val items = when (list) {
                null -> listOf(DataItem.Header)
                else -> listOf(DataItem.Header) + list.map{DataItem.SleepNighetItem(it) }
            }
            withContext(Dispatchers.Main){
                submitList(items)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ViewHolder -> {
                val item = getItem(position) as DataItem.SleepNighetItem
                holder.bind(item.sleepNight)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)){
            is DataItem.Header -> DataItem.ITEM_VIEW_TYPE_HEADER
            is DataItem.SleepNighetItem -> DataItem.ITEM_VIEW_TYPE_ITEM
        }
    }

}

