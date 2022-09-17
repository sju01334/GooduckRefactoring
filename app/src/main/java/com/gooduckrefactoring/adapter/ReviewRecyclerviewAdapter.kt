package com.gooduckrefactoring.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gooduckrefactoring.databinding.ItemBannerBinding
import com.gooduckrefactoring.databinding.ItemReviewBinding
import com.nepplus.gooduck.models.Banner
import com.nepplus.gooduck.models.Review


class ReviewRecyclerviewAdapter: ListAdapter <Review, ReviewRecyclerviewAdapter.ItemViewHolder>(differ) {

    inner class ItemViewHolder(val binding: ItemReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Review) {
            Glide.with(itemView)
                .load(item.user.profileImg)
                .centerCrop()
                .into(binding.profileImg)
            binding.createdAt.text = item.createdAt
            binding.userNick.text = item.user.nickname
            binding.content.text = item.content

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    companion object {

        val differ = object : DiffUtil.ItemCallback<Review>() {
            override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
                return oldItem == newItem
            }

        }
    }


}