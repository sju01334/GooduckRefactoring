package com.gooduckrefactoring.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gooduckrefactoring.databinding.ItemProductFullBinding
import com.gooduckrefactoring.databinding.ItemProductHorizonBinding
import com.nepplus.gooduck.models.Product
import com.nepplus.gooduck.models.SmallCategory


class ProductFullRecyclerviewAdapter(
    val onClick : (Product) -> Unit
): ListAdapter <Product, ProductFullRecyclerviewAdapter.ItemViewHolder>(differ) {

    var onClickCart : ((Product) -> Unit)? = null

    inner class ItemViewHolder(val binding: ItemProductFullBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Product) {
            Glide.with(itemView)
                .load(item.imageUrl)
                .centerCrop()
                .into(binding.itemImg)

            binding.itemName.text = item.name
            binding.itemPrice.text = "${ item.price }원"


            binding.itemImg.setOnClickListener {
                onClick(item)
            }

            binding.addCartBtn.setOnClickListener {
                onClickCart?.invoke(item)
            }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemProductFullBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

        val differ = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem == newItem
            }

        }
    }



}