package com.example.xlwapp.recyclerview.sleeptrack

import android.annotation.SuppressLint
import android.graphics.Color
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.xlwapp.R
import com.example.xlwapp.database.trackmysleep.entity.SleepNight
import com.example.xlwapp.utils.convertDurationToFormatted
import com.example.xlwapp.utils.convertNumericQualityToString


@BindingAdapter("sleepDurationFormatted")
fun TextView.setSleepDurationFormatted(item: SleepNight?){
    item?.let {
        text = convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, context.resources)
    }
}

@BindingAdapter("sleepQualityString")
fun TextView.setSleepQualityString(item: SleepNight?){
    item?.let {
        text = convertNumericQualityToString(item.sleepQuality, context.resources)
    }
}

@BindingAdapter("sleepImage")
fun ImageView.setSleepImage(item: SleepNight?){
    item?.let {
        setImageResource(getItemImageRes(item))
    }
}


@BindingAdapter("backgroundColor")
fun ConstraintLayout.setBackgroundColor(item: SleepNight?){
    item?.let {
        setBackgroundColor(getItemColor(item))
    }
}

fun getItemImageRes(item: SleepNight): Int {
    return when (item.sleepQuality) {
        0 -> R.drawable.ic_sleep_0
        1 -> R.drawable.ic_sleep_1
        2 -> R.drawable.ic_sleep_2
        3 -> R.drawable.ic_sleep_3
        4 -> R.drawable.ic_sleep_4
        5 -> R.drawable.ic_sleep_5
        else -> R.drawable.ic_sleep_active
    }
}

fun getItemColor(item: SleepNight): Int {
    return when (item.sleepQuality) {
        0, 1 -> Color.RED
        2, 3 -> Color.YELLOW
        4, 5 -> Color.GREEN
        else -> Color.BLUE
    }
}




//用于代替notifyDataSetChanged（），notifyDataSetChanged（）方法替换全部list
//DiffUtil只比较替换不同的，效率更高
class SleepNightDiffCallback: DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }

}

class SleepNightListener(val clickListener: (sleepId: Long) -> Unit){
    fun onClick(night: SleepNight) = clickListener(night.nightId)
}


sealed class DataItem{

    companion object{
        val ITEM_VIEW_TYPE_HEADER = 0
        val ITEM_VIEW_TYPE_ITEM = 1
    }

    abstract val id:Long

    data class SleepNighetItem(val sleepNight: SleepNight): DataItem() {
        override val id: Long
            get() = sleepNight.nightId
    }

    object Header: DataItem() {
        override val id: Long
            get() = Long.MIN_VALUE
    }
}

