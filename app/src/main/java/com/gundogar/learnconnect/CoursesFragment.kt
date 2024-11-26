package com.gundogar.learnconnect


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.gundogar.learnconnect.databinding.FragmentCoursesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoursesFragment : BaseFragment<FragmentCoursesBinding>() {

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCoursesBinding {
        return FragmentCoursesBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = CoursesAdapter(getDummyCourses())
        binding.recyclerViewCourses.adapter = adapter
        binding.recyclerViewCourses.layoutManager = LinearLayoutManager(requireContext())

    }
}