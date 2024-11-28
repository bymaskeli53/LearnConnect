package com.gundogar.learnconnect.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.gundogar.learnconnect.ui.viewmodel.AuthViewModel
import com.gundogar.learnconnect.databinding.FragmentProfileBinding
import com.gundogar.learnconnect.util.launchAndRepeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>() {


   private val viewModel: AuthViewModel by viewModels()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProfileBinding {
        return FragmentProfileBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCurrentUserEmail()

        launchAndRepeatOnLifecycle {
            viewModel.currentUserEmail.collect{email->
                binding.tvEmail.text = email
            }

        }
    }




}