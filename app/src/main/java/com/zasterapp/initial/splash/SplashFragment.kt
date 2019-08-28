package com.zasterapp.initial.splash

import android.os.Bundle
import android.os.Handler
import android.view.View
import com.zasterapp.R
import com.zasterapp.common.base.BaseFragment

class SplashFragment : BaseFragment() {

    override fun layoutResourceId() = R.layout.fragment_splash

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fakeLoading()
    }

    private fun fakeLoading() {
        Handler().postDelayed({ navController.navigate(R.id.registerFragment) }, 2000)
    }
}
