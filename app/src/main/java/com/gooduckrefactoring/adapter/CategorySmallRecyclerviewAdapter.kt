package com.gooduckrefactoring.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gooduckrefactoring.databinding.ItemCategorySmallBinding
import com.nepplus.gooduck.models.SmallCategory


class CategorySmallRecyclerviewAdapter : ListAdapter<SmallCategory, CategorySmallRecyclerviewAdapter.ItemViewHolder>(differ) {

    inner class ItemViewHolder(val binding: ItemCategorySmallBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SmallCategory) {

            binding.itemImg.setBackgroundResource(item.image)
            binding.itemName.text = item.name.replace("품", "")

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemCategorySmallBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

        val differ = object : DiffUtil.ItemCallback<SmallCategory>() {
            override fun areItemsTheSame(oldItem: SmallCategory, newItem: SmallCategory): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: SmallCategory, newItem: SmallCategory): Boolean {
                return oldItem == newItem
            }

        }
    }


}