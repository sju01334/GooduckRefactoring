package com.gooduckrefactoring.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.gooduckrefactoring.databinding.CustomAlertDialogBinding

class CustomAlertDialog(val mContext : Context) {

    val dialog = Dialog(mContext)
    lateinit var binding : CustomAlertDialogBinding

    fun myDialog(onClickListener : ButtonClickListener) {
        binding = CustomAlertDialogBinding.inflate(LayoutInflater.from(mContext))
        dialog.setContentView(binding.root)

        dialog.window?.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(InsetDrawable(ColorDrawable(Color.TRANSPARENT), 40))

        dialog.setCancelable(true)

        dialog.show()

        binding.positiveBtn.setOnClickListener {
            onClickListener.positiveBtnClicked()
        }

        binding.negativeBtn.setOnClickListener {
            onClickListener.negativeBtnClicked()
            dialog.dismiss()
        }

        dialog.show()
    }

    interface ButtonClickListener {
        fun positiveBtnClicked()
        fun negativeBtnClicked()
    }

}