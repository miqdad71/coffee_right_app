package com.istekno.coffeebreakapp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel

abstract class BaseFragmentViewModel<FragmentBinding: ViewDataBinding, FragmentViewModel: ViewModel>: Fragment()  {

    private lateinit var binding: FragmentBinding
    private lateinit var viewModel: FragmentViewModel
    protected var setLayout = 0
    protected var setViewModel: FragmentViewModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, setLayout, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = setViewModel!!
    }
}