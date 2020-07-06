package com.example.class1.recyclerview.sleeptrack

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.class1.R
import com.example.class1.database.trackmysleep.entity.SleepNight
import com.example.class1.databinding.GridItemSleepNightBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.ClassCastException

//grid和item点击监听
class SleepNightGridAdapter(
        private val clickListener: SleepNightListener
) : ListAdapter<DataItem, RecyclerView.ViewHolder>(SleepNightDiffCallback()){

    //因为fragment有Observer监听adapter变化，所以放到了Main中调度，本应在Default中调度
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

    class ViewHolder private constructor(val binding: GridItemSleepNightBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: SleepNight, clickListener: SleepNightListener) {
            binding.sleep = item
            binding.listener = clickListener
            binding.executePendingBindings()
        }

        companion object {

            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = GridItemSleepNightBinding.inflate(layoutInflater, parent, false)
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
                holder.bind(item.sleepNight, clickListener)
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
