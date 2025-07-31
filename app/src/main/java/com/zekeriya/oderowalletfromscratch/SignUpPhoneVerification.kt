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
import com.zekeriya.oderowalletfromscratch.databinding.FragmentSignUpPhoneVerificationBinding

class SignUpPhoneVerification : Fragment() {
    private lateinit var binding: FragmentSignUpPhoneVerificationBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpPhoneVerificationBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backButtonSignUpPhoneVer.setOnClickListener {
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

        OTPInputs[0].isFocusable = true
        OTPInputs[0].isFocusableInTouchMode = true
        OTPInputs[0].requestFocus()

        for (i in OTPInputs.indices) {
            OTPInputs[i].addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (!s.isNullOrEmpty() && i < OTPInputs.size - 1) {
                        OTPInputs[i].isFocusable = false
                        OTPInputs[i].isFocusableInTouchMode = false
                        OTPInputs[i+1].isFocusable = true
                        OTPInputs[i+1].isFocusableInTouchMode = true
                        OTPInputs[i + 1].requestFocus()
                    }
                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun afterTextChanged(s: Editable?) {

                }
            })
            OTPInputs[i].setOnKeyListener { v, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_DEL && event.action == KeyEvent.ACTION_DOWN && OTPInputs[i].text.isEmpty() && i > 0) {
                    OTPInputs[i].isFocusable = false
                    OTPInputs[i].isFocusableInTouchMode = false
                    OTPInputs[i-1].isFocusable = true
                    OTPInputs[i-1].isFocusableInTouchMode = true
                    OTPInputs[i - 1].requestFocus()
                    false
                }
                false
            }
            OTPInputs[i].setOnFocusChangeListener{
                    _, hasFocus ->
                if (hasFocus) {
                    (OTPInputs[i].background as? GradientDrawable)?.setStroke(2,resources.getColor(R.color.odero_color_green))
                } else {
                    (OTPInputs[i].background as? GradientDrawable)?.setStroke(2,resources.getColor(R.color.odero_surface_border))
                }
            }
        }
        binding.OuterShell.setOnClickListener {
            if (areAllOTPsFilled(OTPInputs)){
                parentFragmentManager.beginTransaction().replace(R.id.Splash,
                    SignUpEmailVerification()).addToBackStack("SignUpPhoneVerification").commit()
            }
        }
    }
    fun areAllOTPsFilled(OTPInputs: List<EditText>) : Boolean{
        for (otpInput in OTPInputs){
            if (otpInput.text.toString().length != 1) {
                return false
            }
        }
        return true
    }
}