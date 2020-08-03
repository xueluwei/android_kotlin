package com.example.xlwapp.recyclerview.gdg

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.xlwapp.databinding.ListItemBinding
import com.example.xlwapp.network.gdg.GdgChapter
import com.example.xlwapp.network.gdg.NewChapter
import com.example.xlwapp.network.gdg.NewGdgResponse

class GdgListAdapter(val clickListener: GdgClickListener): ListAdapter<NewChapter, GdgListAdapter.GdgListViewHolder>(DiffCallback){
    companion object DiffCallback : DiffUtil.ItemCallback<NewChapter>() {
        override fun areItemsTheSame(oldItem: NewChapter, newItem: NewChapter): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: NewChapter, newItem: NewChapter): Boolean {
            return oldItem == newItem
        }
    }

    class GdgListViewHolder(private var binding: ListItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(listener: GdgClickListener, gdgChapter: NewChapter) {
            binding.chapter = gdgChapter
            binding.clickListener = listener
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): GdgListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemBinding.inflate(layoutInflater, parent, false)
                return GdgListViewHolder(binding)
            }
        }
    }

    /**
     * Part of the RecyclerView adapter, called when RecyclerView needs a new [ViewHolder].
     *
     * A ViewHolder holds a view for the [RecyclerView] as well as providing additional information
     * to the RecyclerView such as where on the screen it was last drawn during scrolling.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GdgListViewHolder {
        return GdgListViewHolder.from(parent)

    }

    /**
     * Part of the RecyclerView adapter, called when RecyclerView needs to show an item.
     *
     * The ViewHolder passed may be recycled, so make sure that this sets any properties that
     * may have been set previously.
     */
    override fun onBindViewHolder(holder: GdgListViewHolder, position: Int) {
        holder.bind(clickListener, getItem(position))
    }
}

class GdgClickListener(val clickListener: (chapter: NewChapter) -> Unit) {
    fun onClick(chapter: NewChapter) = clickListener(chapter)
}
