package com.gooduckrefactoring.dialog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.gooduckrefactoring.R
import com.gooduckrefactoring.databinding.CustomBottomDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nepplus.gooduck.models.Product
import java.text.DecimalFormat

class CustomBottomDialog(private val mProduct: Product, private val buttonClick: () -> Unit) :
    BottomSheetDialogFragment(),
    View.OnClickListener {

    lateinit var binding: CustomBottomDialogBinding
    val mTag = "커스텀 다이얼로그"
    var sum = 0
    val dec = DecimalFormat("#,###")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.custom_bottom_dialog, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sum = mProduct.price
        binding.addCart.text = "${dec.format(sum)}원 장바구니 담기"
        binding.cartItemPrice.text = "${dec.format(sum)}원"
        binding.cartItemTitle.text = mProduct.name

        Glide.with(this)
            .load(mProduct.imageUrl)
            .centerCrop()
            .into(binding.imgUrl)

        binding.plusCnt.setOnClickListener(this)
        binding.minusCnt.setOnClickListener(this)
        binding.addCart.setOnClickListener {
            buttonClick()
            dialog?.dismiss()
        }
    }

    override fun onClick(v: View?) {

        val cartCnt = Integer.parseInt(binding.cnt.text.toString())

        when (v?.id) {
            R.id.plusCnt -> {
                binding.cnt.text = (cartCnt + 1).toString()
            }
            R.id.minusCnt -> {
                if (cartCnt != 1) {
                    binding.cnt.text = (cartCnt - 1).toString()
                }
            }
        }
        sum = mProduct.price * Integer.parseInt(binding.cnt.text.toString())
        binding.addCart.text = "${dec.format(sum)}원 장바구니 담기"
    }

}