package com.gooduckrefactoring.adapter

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gooduckrefactoring.R
import com.gooduckrefactoring.databinding.ItemTagBinding


class TagRecyclerviewAdapter(val type : String): ListAdapter <String, TagRecyclerviewAdapter.ItemViewHolder>(differ) {

    inner class ItemViewHolder(val binding: ItemTagBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("ResourceAsColor")
        fun bind(item: String) {

            binding.tagTxt.text = item
            if(type == "recommend"){
                binding.tagItemBackground.setBackgroundResource(R.drawable.r15_lightprimary_solid)
                binding.tagTxt.setTextColor(R.color.gooduckPrimary)
            }



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