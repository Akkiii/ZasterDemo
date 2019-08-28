package com.zasterapp.initial

import android.os.Bundle
import com.zasterapp.R
import com.zasterapp.common.base.BaseActivity


class InitialActivity : BaseActivity() {

    override val navigationHostId = R.id.fragmentInitialNavHost

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_initial)
    }

}
