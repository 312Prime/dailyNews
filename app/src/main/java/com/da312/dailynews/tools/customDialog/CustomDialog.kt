package com.da312.dailynews.tools.customDialog

import android.app.Dialog
import android.content.Context
import com.da312.dailynews.databinding.LayoutDialogBinding

class CustomDialog(context: Context) {
    private val dialog = Dialog(context)
    private val binding by lazy { LayoutDialogBinding.inflate(dialog.layoutInflater) }

    fun show(title: String, content: String) {
        dialog.setContentView(binding.root)

        if (title != "") {
            binding.dialogTitle.text = title
        }
        if (content != "") {
            binding.dialogContent.text = content
        }

        initView()
        dialog.show()
    }

    private fun initView() {
        binding.btnConfirm.setOnClickListener {
            android.os.Process.killProcess(android.os.Process.myPid());  //앱 종료
        }

        binding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }
    }
}