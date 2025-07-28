package com.zekeriya.oderowalletfromscratch

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zekeriya.oderowalletfromscratch.databinding.ActivityMainBinding
import com.zekeriya.oderowalletfromscratch.databinding.FragmentSignUpIDInformationBinding

class SignUpIDInformation : Fragment() {
    private lateinit var binding: FragmentSignUpIDInformationBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        this.binding = FragmentSignUpIDInformationBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}