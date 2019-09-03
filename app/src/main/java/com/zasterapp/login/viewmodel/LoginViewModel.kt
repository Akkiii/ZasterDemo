package com.zasterapp.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.asisten.android.common.extension.isValidEmail
import com.google.gson.JsonObject
import com.zasterapp.dialog.ProgressDialog
import com.zasterapp.network.RestClient
import com.zasterapp.network.model.UserModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel(), Callback<UserModel> {
    sealed class State {
        data class NextButtonEnabled(val enable: Boolean) : State()

        data class EmailValid(val valid: Boolean) : State()

        data class PasswordValid(val valid: Boolean) : State()

        data class UserResponse(val resStatus: Int, val obj: Any?,val message : String) : State()
    }

    sealed class Event {
        object ViewCreated : Event()

        data class EmailFieldChanged(val email: String) : Event()

        data class PasswordFieldChanged(val name: String) : Event()

        data class EmailFieldFocusChanged(val focus: Boolean) : Event()

        data class PasswordFocusChanged(val focus: Boolean) : Event()

        object ValidateUser : Event()

    }

    val state: LiveData<State>
        get() = _state

    private val _state = MutableLiveData<State>()
    private var serviceResponseListener: ServiceResponseListener? = null
    var userLogin: MutableLiveData<UserModel>? = null
    private var email = ""
    private var password = ""

    fun onEventReceived(event: Event) {
        when (event) {
            Event.ViewCreated -> checkFieldCompletion()
            Event.ValidateUser -> loginUser()
            is Event.EmailFieldChanged -> onEmailFieldChanged(event.email)
            is Event.PasswordFieldChanged -> onPasswordFieldChanged(event.name)
            is Event.EmailFieldFocusChanged -> {
                if (!event.focus) onEmailFieldChanged(email)
            }
            is Event.PasswordFocusChanged -> {
                if (!event.focus) onPasswordFieldChanged(password)
            }
        }
    }

    private fun loginUser() {

        /**
         * We can pass data in Json like below**/
        val obj = JsonObject()
        obj.addProperty("email", email)
        obj.addProperty("password", password)
        obj.addProperty("isSocial", "0")

        RestClient.retrofitCallBack().create(RestClient.NetworkCall::class.java)
            .loginUser(email, password).enqueue(this)
    }

    override fun onResponse(call: Call<UserModel>, response: Response<UserModel>?) {
        _state.value = State.UserResponse(response?.code()!!, response.body(),
            response.errorBody()?.string()!!
        )
    }

    override fun onFailure(call: Call<UserModel>, t: Throwable?) {
        _state.value = State.UserResponse(0, null, t?.message.toString())
    }

    private fun onEmailFieldChanged(email: String) {
        _state.value = State.EmailValid(email.isValidEmail())
        this.email = email
        checkFieldCompletion()
    }

    private fun onPasswordFieldChanged(name: String) {
        _state.value = State.PasswordValid(name.isNotEmpty())
        password = name
        checkFieldCompletion()
    }

    private fun checkFieldCompletion() {
        val areFieldsValid = email.isNotEmpty() && password.isNotEmpty()
        _state.value = State.NextButtonEnabled(areFieldsValid)
    }

    interface ServiceResponseListener {
        fun serviceResponse(resStatus: Int, obj: Any?)
    }
}
