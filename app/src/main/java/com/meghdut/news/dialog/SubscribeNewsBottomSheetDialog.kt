package com.meghdut.news.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.meghdut.news.R
import com.meghdut.news.databinding.LayoutSubscribeDialogBinding
import com.meghdut.news.helper.Common
import com.meghdut.news.view.ActDeshboardScreen

class SubscribeNewsBottomSheetDialog() : BottomSheetDialogFragment() {
    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.DialogStyle)
    }

    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)


        val layoutSubscribeDialogBinding: LayoutSubscribeDialogBinding = DataBindingUtil.inflate(
            LayoutInflater.from(dialog.context), R.layout.layout_subscribe_dialog, null, false
        )

        layoutSubscribeDialogBinding.txtCancel.setOnClickListener {
            dialog.cancel()
        }

        layoutSubscribeDialogBinding.txtSubscribe.setOnClickListener {

            if (layoutSubscribeDialogBinding.edtName.text.toString().length == 0) {
                Common.ShowToast(activity!!, getString(R.string.please_enter_name))
                return@setOnClickListener
            } else if (layoutSubscribeDialogBinding.edtEmail.text.toString().length == 0) {
                Common.ShowToast(activity!!, getString(R.string.please_enter_email))
                return@setOnClickListener
            } else if (!Common.isValidEmail(layoutSubscribeDialogBinding.edtEmail.text.toString())) {
                Common.ShowToast(activity!!, getString(R.string.please_enter_valid_email))
                return@setOnClickListener
            }
            dialog.cancel()
            (activity as ActDeshboardScreen).SubscribeNews(
                layoutSubscribeDialogBinding.edtName.text.toString(),
                layoutSubscribeDialogBinding.edtEmail.text.toString()
            )
        }

        dialog.getWindow()?.setGravity(Gravity.BOTTOM);
        dialog.setCancelable(false)
        dialog.setContentView(layoutSubscribeDialogBinding.root)
    }


}