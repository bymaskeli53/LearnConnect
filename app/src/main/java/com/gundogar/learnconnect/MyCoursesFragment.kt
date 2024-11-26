package com.gundogar.learnconnect

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gundogar.learnconnect.databinding.FragmentMyCoursesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyCoursesFragment : BaseFragment<FragmentMyCoursesBinding>() {

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMyCoursesBinding {
        return FragmentMyCoursesBinding.inflate(inflater, container, false)
    }
}