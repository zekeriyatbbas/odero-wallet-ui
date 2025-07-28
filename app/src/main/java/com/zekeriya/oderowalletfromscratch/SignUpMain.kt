package com.zekeriya.oderowalletfromscratch

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zekeriya.oderowalletfromscratch.databinding.ActivityMainBinding
import com.zekeriya.oderowalletfromscratch.databinding.FragmentLoginMainBinding
import com.zekeriya.oderowalletfromscratch.databinding.FragmentSignUpMainBinding


class SignUpMain : Fragment() {

    private lateinit var binding: FragmentSignUpMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding = FragmentSignUpMainBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backButtonSignUpMain.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.StartButton.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.Splash, SignUpIDInformation())
                .addToBackStack("SignUpMain")
                .commit()
        }

    }

}