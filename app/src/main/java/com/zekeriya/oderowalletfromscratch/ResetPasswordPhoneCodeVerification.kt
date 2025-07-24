package com.zekeriya.oderowalletfromscratch

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
import com.zekeriya.oderowalletfromscratch.databinding.ActivityMainBinding
import com.zekeriya.oderowalletfromscratch.databinding.FragmentResetPasswordPhoneCodeVerificationBinding

class ResetPasswordPhoneCodeVerification : Fragment() {
    private lateinit var binding: FragmentResetPasswordPhoneCodeVerificationBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding = FragmentResetPasswordPhoneCodeVerificationBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backButtonPhoneVer.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        val OTPInputs = listOf(
            binding.OTP01,
            binding.OTP02,
            binding.OTP03,
            binding.OTP04,
            binding.OTP05,
            binding.OTP06
        )

        for (i in OTPInputs.indices) {
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
        //////

        binding.OTP06.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Optional: called before text is changed
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Called as the text is being changed
            }

            override fun afterTextChanged(s: Editable?) {
                // Optional: called after text is changed
                parentFragmentManager.beginTransaction().replace(R.id.Splash,
                    ResetPasswordEmailVerification())
                    .addToBackStack("ResetPasswordPhoneVerification")
                    .commit()
            }
        })
    }

    fun areAllOTPsFilled(OTPInputs: List<EditText>) : Boolean{
        for (otpInput in OTPInputs){
            if (otpInput.text.toString().length != 1) { return false }
        }
        return true
    }
}