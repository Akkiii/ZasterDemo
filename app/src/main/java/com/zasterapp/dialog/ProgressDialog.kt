package com.zasterapp.dialog

import android.app.Dialog
import android.content.Context
import android.view.WindowManager
import com.zasterapp.R

class ProgressDialog(val mContext: Context) : Dialog(mContext, R.style.CustomDialog) {
    init {
        setContentView(R.layout.dialog_progress)
    }
}