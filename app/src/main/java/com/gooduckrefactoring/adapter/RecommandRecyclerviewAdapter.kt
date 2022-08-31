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
import com.gooduckrefactoring.databinding.ItemTagBinding
import com.nepplus.gooduck.models.Banner


class RecommandRecyclerviewAdapter: ListAdapter <String, RecommandRecyclerviewAdapter.ItemViewHolder>(differ) {

    inner class ItemViewHolder(val binding: ItemTagBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: String) {




        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemTagBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

        val differ = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

        }
    }


}