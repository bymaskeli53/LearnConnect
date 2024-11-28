package com.gundogar.learnconnect.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.gundogar.learnconnect.ui.adapter.CoursesAdapter
import com.gundogar.learnconnect.ui.viewmodel.CoursesViewModel
import com.gundogar.learnconnect.R
import com.gundogar.learnconnect.model.RemoteCourseModel
import com.gundogar.learnconnect.databinding.FragmentCoursesBinding
import com.gundogar.learnconnect.util.launchAndRepeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoursesFragment : BaseFragment<FragmentCoursesBinding>() {


    private val viewModel: CoursesViewModel by viewModels()


    private var adapter: CoursesAdapter? = null

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCoursesBinding {
        return FragmentCoursesBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.rvCourses.adapter = adapter
        binding.rvCourses.layoutManager = LinearLayoutManager(requireContext())

        launchAndRepeatOnLifecycle {
            viewModel.courses.collect { courses ->
               adapter = CoursesAdapter(courses, itemClickListener = {
                   purchaseCourse(it)
                   Toast.makeText(
                       requireContext(),
                       getString(R.string.you_bought_the_course), Toast.LENGTH_SHORT
                   ).show()

               })
                binding.rvCourses.adapter = adapter
            }

        }
    }

    private fun purchaseCourse(course: RemoteCourseModel) {
        viewModel.purchaseCourse(course)
    }

}