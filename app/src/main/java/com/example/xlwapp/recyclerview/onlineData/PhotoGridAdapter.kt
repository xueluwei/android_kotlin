package com.example.xlwapp.recyclerview.onlineData

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter

import androidx.recyclerview.widget.RecyclerView
import com.example.xlwapp.databinding.GridViewItemBinding
import com.example.xlwapp.network.MarsProperty

class PhotoGridAdapter(private val onClickListener: OnClickListener) : ListAdapter<MarsProperty, RecyclerView.ViewHolder>(OnlineDiffCallback()){
    private class ViewHolder constructor(val binding: GridViewItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(marsProperty: MarsProperty){
            binding.property = marsProperty
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder{
                val inflater = LayoutInflater.from(parent.context)
                val binding = GridViewItemBinding.inflate(inflater,parent,false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val marsProperty = getItem(position)
        val mHolder = holder as ViewHolder
        mHolder.itemView.setOnClickListener{
            onClickListener.onClick(marsProperty)
        }
        mHolder.bind(marsProperty)
    }
}