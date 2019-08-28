package com.zasterapp.common.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel<Event, State> : ViewModel(), CoroutineScope {

    protected open val job = Job()

    private val _state = MutableLiveData<State>()
    val state: LiveData<State>
        get() = _state

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    protected fun pushState(state: State) {
        _state.value = state
    }

    abstract fun onEventReceived(event: Event)
}
