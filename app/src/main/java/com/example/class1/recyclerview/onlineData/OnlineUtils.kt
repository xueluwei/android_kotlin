package com.example.xlwapp.recyclerview.onlineData

import android.annotation.SuppressLint
import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.xlwapp.R
import com.example.xlwapp.network.MarsProperty
import com.example.xlwapp.viewmodel.onlinedata.MarsApiStatus

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = Uri.parse(imgUrl).buildUpon().scheme("https").build()
        Glide.with(imgView.context)
                .load(imgUri)
                .apply(RequestOptions()
                        .placeholder(R.drawable.loading_animation) //加载中动画
                        .error(R.drawable.ic_broken_image) //加载失败图片
                )
                .into(imgView)
    }
}

@BindingAdapter("listData")
fun bindRecyclerView(
        recyclerView: RecyclerView,
        dataList: List<MarsProperty>?
){
    val adapter = recyclerView.adapter as PhotoGridAdapter
    adapter.submitList(dataList)
}

@BindingAdapter("marsApiStatus")
fun bindStatus(
        statusImageView: ImageView,
        marsApiStatus: MarsApiStatus?
){
    when(marsApiStatus){
        MarsApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        MarsApiStatus.DONE ->{
            statusImageView.visibility = View.GONE
        }
        MarsApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
    }
}

class OnlineDiffCallback: DiffUtil.ItemCallback<MarsProperty>() {
    override fun areItemsTheSame(oldItem: MarsProperty, newItem: MarsProperty): Boolean {
        return oldItem.id == newItem.id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: MarsProperty, newItem: MarsProperty): Boolean {
        return oldItem == newItem
    }

}

class OnClickListener(val clickListener: (marsProperty: MarsProperty) -> Unit){
    fun onClick(marsProperty: MarsProperty) = clickListener(marsProperty)
}