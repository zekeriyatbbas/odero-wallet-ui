package com.zekeriya.oderowalletfromscratch

import android.os.Bundle
import android.text.method.DigitsKeyListener
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zekeriya.oderowalletfromscratch.databinding.ActivityMainBinding
import com.zekeriya.oderowalletfromscratch.databinding.FragmentLoginMainBinding
import com.zekeriya.oderowalletfromscratch.databinding.FragmentResetPasswordBinding

class ResetPassword : Fragment() {
    private lateinit var binding: FragmentResetPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding = FragmentResetPasswordBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tcKimlikInput.keyListener = DigitsKeyListener.getInstance("0123456789")
        binding.Button.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.Splash,ResetPasswordPhoneCodeVerification())
                .addToBackStack("ResetPasswordIDVerification")
                .commit()
        }

    }
}