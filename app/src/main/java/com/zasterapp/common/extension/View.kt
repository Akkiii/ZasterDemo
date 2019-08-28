package co.asisten.android.common.extension

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding


inline fun <reified T : ViewDataBinding> View.createBinding(): T? = DataBindingUtil.bind(this)
