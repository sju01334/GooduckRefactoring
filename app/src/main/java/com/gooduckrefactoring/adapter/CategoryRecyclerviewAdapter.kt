package com.gooduckrefactoring.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gooduckrefactoring.R
import com.gooduckrefactoring.databinding.ItemCategoryBinding
import com.gooduckrefactoring.databinding.ItemProductBinding
import com.gooduckrefactoring.util.MyItemDecoration
import com.nepplus.gooduck.models.Category


class CategoryRecyclerviewAdapter(
    val context : Context
): ListAdapter <Category, CategoryRecyclerviewAdapter.ItemViewHolder>(differ) {

    lateinit var smallAdapter: CategorySmallRecyclerviewAdapter

    inner class ItemViewHolder(val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Category) {
            binding.bigCategoryTxt.text  = item.name.replace("구독", "")

            setPicture(item)

            binding.smallCategoryRecyclerview.apply {
                smallAdapter = CategorySmallRecyclerviewAdapter()
                adapter = smallAdapter
                layoutManager = GridLayoutManager(context, 5)
                addItemDecoration(MyItemDecoration())
            }

            smallAdapter.submitList(item.smallCategories)


        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

        val differ = object : DiffUtil.ItemCallback<Category>() {
            override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
                return oldItem == newItem
            }

        }
    }

    fun setPicture(item: Category){
        when( item.id){
            1 -> {
                item.smallCategories[0].image  = R.drawable.category_ham
                item.smallCategories[1].image = R.drawable.category_egg
                item.smallCategories[2].image =R.drawable.category_snack
                item.smallCategories[3].image =R.drawable.category_fruits
                item.smallCategories[4].image =R.drawable.category_cereal
            }
            2 -> {
                item.smallCategories[0].image =R.drawable.category_socks
                item.smallCategories[1].image =R.drawable.category_underwear
                item.smallCategories[2].image =R.drawable.category_man
                item.smallCategories[3].image =R.drawable.category_woman
                item.smallCategories[4].image =R.drawable.category_baby
            }
            3 -> {
                item.smallCategories[0].image =R.drawable.category_kitchen
                item.smallCategories[1].image =R.drawable.category_laundry
                item.smallCategories[2].image =R.drawable.category_cloth
                item.smallCategories[3].image =R.drawable.category_bath
                item.smallCategories[4].image =R.drawable.category_medical
            }
        }
    }


}