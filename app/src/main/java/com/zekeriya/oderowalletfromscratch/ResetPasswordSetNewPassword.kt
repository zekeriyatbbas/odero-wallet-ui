package com.zekeriya.oderowalletfromscratch

import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zekeriya.oderowalletfromscratch.databinding.FragmentResetPasswordSetNewPasswordBinding

class ResetPasswordSetNewPassword : Fragment() {
    private lateinit var binding: FragmentResetPasswordSetNewPasswordBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResetPasswordSetNewPasswordBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backButtonSetNewPass.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        var isPassVisible = false
        var isReenterPassVisible = false

        binding.eyeIconPass.setOnClickListener {
            isPassVisible = !isPassVisible
            if (isPassVisible) {
                binding.eyeIconPass.setImageResource(R.drawable.eye_blocked)
                binding.PassText.inputType = InputType.TYPE_CLASS_NUMBER
            } else {
                binding.eyeIconPass.setImageResource(R.drawable.eye)
                binding.PassText.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD
            }
            binding.PassText.setSelection(binding.PassText.text.length)
        }

        binding.eyeIconPassReenter.setOnClickListener {
            isReenterPassVisible = !isReenterPassVisible
            if (isReenterPassVisible) {
                binding.eyeIconPassReenter.setImageResource(R.drawable.eye_blocked)
                binding.PassTextReenter.inputType = InputType.TYPE_CLASS_NUMBER
            } else {
                binding.eyeIconPassReenter.setImageResource(R.drawable.eye)
                binding.PassTextReenter.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD
            }
            binding.PassTextReenter.setSelection(binding.PassTextReenter.text.length)
        }
    }
}