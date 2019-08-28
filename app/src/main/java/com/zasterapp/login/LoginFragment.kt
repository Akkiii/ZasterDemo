package com.zasterapp.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProviders
import co.asisten.android.common.extension.doAfterTextChanged
import co.asisten.android.common.extension.observe
import com.zasterapp.R
import com.zasterapp.common.base.BaseFragment
import com.zasterapp.dialog.ProgressDialog
import com.zasterapp.login.viewmodel.LoginViewModel
import com.zasterapp.network.model.UserModel
import kotlinx.android.synthetic.main.fragment_login.*
import org.json.JSONException
import org.json.JSONObject

class LoginFragment : BaseFragment() {

    private lateinit var progressDialog: ProgressDialog
    private val viewModel by lazy { ViewModelProviders.of(this).get(LoginViewModel::class.java) }

    override fun showBackButton(): Boolean? = true

    override fun layoutResourceId() = R.layout.fragment_login

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindViewModel()
        configureViewEvents()

        viewModel.onEventReceived(LoginViewModel.Event.ViewCreated)
    }

    private fun bindViewModel() {
        observe(viewModel.state) {
            when (it) {
                is LoginViewModel.State.NextButtonEnabled -> {
                    buttonLogin.isEnabled = it.enable
                    if (it.enable) {
                        buttonLogin.setBackgroundColor(context?.resources?.getColor(R.color.grey)!!)
                    }
                }
                is LoginViewModel.State.EmailValid -> {
                    textViewEmailError.visibility = if (it.valid) View.GONE else View.VISIBLE
                }
                is LoginViewModel.State.PasswordValid -> {
                    textViewPasswordError.visibility = if (it.valid) View.GONE else View.VISIBLE
                }
                is LoginViewModel.State.UserResponse -> {
                    hideProgress()
                    if (it.resStatus != 0) {
                        if (it.resStatus in 200..206) {
                            val res = it.obj as UserModel
                            Toast.makeText(context, "User Login Successfully", Toast.LENGTH_LONG)
                                .show()
                            navController.navigate(R.id.dashboardFragment)
                        } else {
                            val jObj = JSONObject(it.message)
                            try {
                                val errorMessage = jObj.getString("alert")
                                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                            } catch (e: JSONException) {
                                val errorMessage = jObj.getString("body")
                                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                            }
                        }

                    }
                }
            }
        }
    }

    private fun configureViewEvents() {
        editTextEmailAddress.doAfterTextChanged {
            viewModel.onEventReceived(LoginViewModel.Event.EmailFieldChanged(it.toString()))
        }

        editTextPassword.doAfterTextChanged {
            viewModel.onEventReceived(LoginViewModel.Event.PasswordFieldChanged(it.toString()))
        }

        editTextEmailAddress.setOnFocusChangeListener { _, hasFocus ->
            viewModel.onEventReceived(LoginViewModel.Event.PasswordFocusChanged(hasFocus))
        }

        editTextPassword.setOnFocusChangeListener { _, hasFocus ->
            viewModel.onEventReceived(LoginViewModel.Event.EmailFieldFocusChanged(hasFocus))
        }

        buttonLogin.setOnClickListener {
            showProgress()
            viewModel.onEventReceived(LoginViewModel.Event.ValidateUser)
        }
    }

    private fun showProgress() {
        progressDialog = ProgressDialog(context!!)
        progressDialog.show()
    }

    private fun hideProgress() {
        if (progressDialog != null)
            progressDialog.cancel()
    }
}