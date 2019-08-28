package com.zasterapp.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseFragment : Fragment(), CoroutineScope {

    protected val job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    abstract fun layoutResourceId(): Int

    protected open fun menuResourceId(): Int? = null

    protected open fun getToolbar(): Toolbar? = null

    protected open fun getTitle(): String? = null

    protected open fun showBackButton(): Boolean? = null

    protected open fun onToolbarMenuItemClicked(item: MenuItem): Boolean = true

    protected val navController: NavController by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(layoutResourceId(), container, false)
        setHasOptionsMenu(menuResourceId() != null)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
    }

    private fun setupToolbarMenu() {
        menuResourceId()?.let {
            getToolbar()?.run {
                inflateMenu(it)
                setOnMenuItemClickListener { menu ->
                    onToolbarMenuItemClicked(menu)
                }
            }
        }
    }

    private fun setupToolbar() {
        (activity as? AppCompatActivity)?.run {
            setSupportActionBar(getToolbar())
            title = this@BaseFragment.getTitle()
            showBackButton()?.let { supportActionBar?.setDisplayHomeAsUpEnabled(it) }
            setupToolbarMenu()
        }
    }

}
