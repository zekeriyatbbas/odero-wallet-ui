package com.zekeriya.oderowalletfromscratch

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.Layout
import android.text.TextWatcher
import android.view.Gravity
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
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

        var frequencyChoice = "No choice"
        binding.TextFieldFreqCon.setOnClickListener {
            val frequencyDialogView = layoutInflater.inflate(R.layout.password_reset_frequency_dialog,null)
            val frequencyDialog = AlertDialog.Builder(requireContext())
                .setView(frequencyDialogView)
                .create()
            frequencyDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            frequencyDialog.window?.setGravity(Gravity.BOTTOM)
            val freqViews = listOf<TextView>(frequencyDialogView.findViewById<TextView>(R.id.frequency01),
                frequencyDialogView.findViewById<TextView>(R.id.frequency03),
                frequencyDialogView.findViewById<TextView>(R.id.frequency06))
            for (freqView in freqViews){
                freqView.setOnClickListener {
                    frequencyChoice = freqView.text.toString()
                    binding.FreqText.setText(frequencyChoice)
                    frequencyDialog.dismiss()
                }
            }
            frequencyDialog.show()
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

        var isPasswordSixDigitAndNumber = false
        var hasPasswordFourSameDigits = false
        var hasPasswordSequentialDigits = false
        var isPasswordSameOneOfLastThree = false

        binding.PassText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val currentEnteredPassword = s.toString()
                if (currentEnteredPassword.length == 6){
                    isPasswordSixDigitAndNumber = true
                    binding.circle1.setImageResource(R.drawable.checkmark)
                }

                val passwordNumbers = IntArray(10)
                hasPasswordFourSameDigits = false
                for (number in currentEnteredPassword) {
                    val numericValue = number.digitToInt()
                    passwordNumbers[numericValue]++
                    if (passwordNumbers[numericValue] > 3) {
                        hasPasswordFourSameDigits = true
                        break
                    }
                }

                // how we will behave to the constraint that forbids sequential digits ??

                // we need database of passwords to manage the constraints for isPasswordSameOneOfLastThree

            }
        })
        binding.PassText.setOnKeyListener { v, keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_DEL && event.action == KeyEvent.ACTION_DOWN) {
                isPasswordSixDigitAndNumber = false
                binding.circle1.setImageResource(R.drawable.circle)
                false
            }else{
                false
            }
        }
        binding.SubmitButtonCon.setOnClickListener {
            if (frequencyChoice == "No choice"){
                val noChoiceView = layoutInflater.inflate(R.layout.wrong_information_login,null)
                val noChoiceDialog = AlertDialog.Builder(requireContext()).setView(noChoiceView).create()
                noChoiceDialog.setCanceledOnTouchOutside(false)
                noChoiceDialog.setCancelable(false)
                noChoiceDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                noChoiceView.findViewById<TextView>(R.id.mainErrorMessage).setText("Şifre değiştirme sıklığı seçmedin")
                noChoiceView.findViewById<TextView>(R.id.additionalInfo).setText("Lütfen şifre değiştirme sıklığını seç")
                noChoiceView.findViewById<Button>(R.id.dismissButton).setOnClickListener {
                    noChoiceDialog.dismiss()
                }
                noChoiceDialog.show()
            }

            val doPasswordsMatch = binding.PassText.text.toString() == binding.PassTextReenter.text.toString()
            if (!doPasswordsMatch){
                val passMatchView = layoutInflater.inflate(R.layout.wrong_information_login,null)
                val passMatchDialog = AlertDialog.Builder(requireContext()).setView(passMatchView).create()
                passMatchDialog.setCanceledOnTouchOutside(false)
                passMatchDialog.setCancelable(false)
                passMatchDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                passMatchView.findViewById<TextView>(R.id.mainErrorMessage).setText("Girdiğin şifreler uyuşmuyor")
                passMatchView.findViewById<TextView>(R.id.additionalInfo).setText("Lütfen şifreni kontrol et")
                passMatchView.findViewById<Button>(R.id.dismissButton).setOnClickListener {
                    passMatchDialog.dismiss()
                }
                passMatchDialog.show()
            }
            if (isPasswordSixDigitAndNumber && !isPasswordSameOneOfLastThree &&
                !hasPasswordFourSameDigits && !hasPasswordSequentialDigits &&
                frequencyChoice != "No choice" && doPasswordsMatch) {
                val passwordResetSuccessView = layoutInflater.inflate(R.layout.password_reset_success,null)
                val passwordResetSuccessDialog = AlertDialog.Builder(requireContext())
                    .setView(passwordResetSuccessView).create()
                passwordResetSuccessDialog.setCancelable(false)
                passwordResetSuccessDialog.setCanceledOnTouchOutside(false)
                passwordResetSuccessDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                passwordResetSuccessDialog.show()
                passwordResetSuccessView.findViewById<Button>(R.id.navigateToLoginButton).setOnClickListener {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.Splash, LoginMain())
                        .commit()
                    passwordResetSuccessDialog.dismiss()
                }

            }
        }
    }
}
