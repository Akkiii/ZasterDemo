package com.zasterapp.common.base

import android.content.Intent
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController

abstract class BaseActivity : AppCompatActivity() {

    protected val navController by lazy { findNavController(navigationHostId) }

    abstract val navigationHostId: Int

    override fun onOptionsItemSelected(item: MenuItem?) = when (item?.itemId) {
        android.R.id.home -> navController.popBackStack()
        else -> super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
}
