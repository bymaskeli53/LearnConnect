package com.gundogar.learnconnect

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.gundogar.learnconnect.databinding.FragmentExoplayerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExoplayerFragment : BaseFragment<FragmentExoplayerBinding>() {

    private val viewModel: MyCoursesViewModel by viewModels()

    private val navArgs: ExoplayerFragmentArgs by navArgs()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentExoplayerBinding {
        return FragmentExoplayerBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.watchVideo(binding.exoPlayerView,navArgs.course.courseId)



    }

    override fun onPause() {
        super.onPause()
        val videoId = navArgs.course.courseId
        viewModel.saveVideoProgress(videoId)
    }




}