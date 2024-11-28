package com.gundogar.learnconnect

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.gundogar.learnconnect.databinding.FragmentMyCoursesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyCoursesFragment : BaseFragment<FragmentMyCoursesBinding>() {

    private val viewModel: MyCoursesViewModel by viewModels()
    private var adapter: MyCoursesAdapter? = null

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMyCoursesBinding {
        return FragmentMyCoursesBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvMyCourses.adapter = adapter
        binding.rvMyCourses.layoutManager = LinearLayoutManager(requireContext())


        launchAndRepeatOnLifecycle {
            viewModel.myCourses.collect { courses ->
                adapter = MyCoursesAdapter(courses, itemClickListener = {
                    watchVideo(it)

                })
                binding.rvMyCourses.adapter = adapter
            }

        }


    }

    private fun watchVideo(course: RemoteCourseModel) {
        val action = MyCoursesFragmentDirections.actionMyCoursesFragmentToExoplayerFragment(course)
        findNavController().navigate(action)

    }


}