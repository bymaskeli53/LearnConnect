package com.gundogar.learnconnect.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.gundogar.learnconnect.ui.adapter.MyCoursesAdapter
import com.gundogar.learnconnect.ui.viewmodel.MyCoursesViewModel
import com.gundogar.learnconnect.model.RemoteCourseModel
import com.gundogar.learnconnect.databinding.FragmentMyCoursesBinding
import com.gundogar.learnconnect.util.launchAndRepeatOnLifecycle
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
        val action = MyCoursesFragmentDirections.Companion.actionMyCoursesFragmentToExoplayerFragment(course)
        findNavController().navigate(action)

    }


}