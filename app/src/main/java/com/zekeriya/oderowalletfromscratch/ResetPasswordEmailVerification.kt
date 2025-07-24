package com.zekeriya.oderowalletfromscratch

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.zekeriya.oderowalletfromscratch.databinding.FragmentResetPasswordBinding
import com.zekeriya.oderowalletfromscratch.databinding.FragmentResetPasswordEmailVerificationBinding
import com.zekeriya.oderowalletfromscratch.databinding.FragmentResetPasswordPhoneCodeVerificationBinding


class ResetPasswordEmailVerification : Fragment() {
    private lateinit var binding: FragmentResetPasswordEmailVerificationBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        this.binding = FragmentResetPasswordEmailVerificationBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backButtonEmailVer.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        val OTPInputs = listOf(
            binding.OTP01EmailVer,
            binding.OTP02EmailVer,
            binding.OTP03EmailVer,
            binding.OTP04EmailVer,
            binding.OTP05EmailVer,
            binding.OTP06EmailVer
        )
        for (i in OTPInputs.indices) {
            (OTPInputs[i].background as? GradientDrawable)?.setStroke(2,resources.getColor(R.color.odero_surface_border))
            OTPInputs[i].addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (!s.isNullOrEmpty() && i < OTPInputs.size - 1) {
                        OTPInputs[i + 1].requestFocus()
                    }
                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    if (areAllOTPsFilled(OTPInputs)){
                        parentFragmentManager.beginTransaction().replace(R.id.Splash,
                            ResetPasswordSetNewPassword()).addToBackStack("ResetPasswordEmailVerification")
                            .commit()
                    }
                }
            })
            OTPInputs[i].setOnKeyListener { v, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_DEL && event.action == KeyEvent.ACTION_DOWN && OTPInputs[i].text.isEmpty() && i > 0) {
                    OTPInputs[i - 1].requestFocus()
                    true
                } else {
                    false
                }
            }
            OTPInputs[i].setOnFocusChangeListener{
                    _, hasFocus ->
                if (hasFocus) {
                    // On focus: change to focused color
                    (OTPInputs[i].background as? GradientDrawable)?.setStroke(2,resources.getColor(R.color.odero_color_green))
                } else {
                    // On blur: revert to original color
                    (OTPInputs[i].background as? GradientDrawable)?.setStroke(2,resources.getColor(R.color.odero_surface_border))
                }
            }
        }

    }

    fun areAllOTPsFilled(OTPInputs: List<EditText>) : Boolean{
        for (otpInput in OTPInputs){
            if (otpInput.text.toString().length != 1) { return false }
        }
        return true
    }
}