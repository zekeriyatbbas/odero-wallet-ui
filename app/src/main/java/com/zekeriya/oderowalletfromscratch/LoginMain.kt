package com.zekeriya.oderowalletfromscratch

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.zekeriya.oderowalletfromscratch.databinding.FragmentLoginMainBinding
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.method.DigitsKeyListener
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView


class LoginMain : Fragment() {
    private lateinit var binding: FragmentLoginMainBinding
    private val loginErrorTypesDialog = listOf<String>("wrong_info","wrong_password","id_not_given","password_not_given","account_blocked")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding = FragmentLoginMainBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        binding.tcInput.setOnClickListener {
            binding.tcTextInput.post {
                binding.tcTextInput.requestFocus()
                Log.d("Focus", "Has focus: ${binding.tcTextInput.hasFocus()}")
                val immTc = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                immTc.showSoftInput(binding.tcTextInput, InputMethodManager.SHOW_IMPLICIT)
            }
        }
        binding.tcTextInput.keyListener = DigitsKeyListener.getInstance("0123456789")

        binding.passwordInput.setOnClickListener {
            binding.passwordTextInput.post {
                binding.passwordTextInput.requestFocus()
                Log.d("Focus", "Has focus: ${binding.tcTextInput.hasFocus()}")
                val immPass = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                immPass.showSoftInput(binding.passwordTextInput, InputMethodManager.SHOW_IMPLICIT)
            }
        }
        binding.passwordTextInput.keyListener = DigitsKeyListener.getInstance("0123456789")
        binding.loginButton.setOnClickListener {
            // a coroutine that deals with the user-password verification will be placed here ????
            var isRegistered = true
            val typedID = binding.tcTextInput.text.toString()
            val typedPassword = binding.passwordTextInput.text.toString()
            if (typedID.length != 11){
                this.createErrorDialog(loginErrorTypesDialog[2])
            }else{
                isRegistered = true
            }
            if (typedPassword.length != 6){
                isRegistered = false
                this.createErrorDialog(loginErrorTypesDialog[3])
            }else{
                isRegistered = true
            }

            if (isRegistered) {
                // as we proceed with the registered user, I didn't add the LoginMain fragment to the backstack
                parentFragmentManager.beginTransaction().replace(R.id.Splash, RegisteredUser()).commit()
            }
        }

        //this.createErrorDialog("wrong_password")
        //this.createErrorDialog("id_not_given")
        //this.createErrorDialog("password_not_given")
        //this.createErrorDialog("account_blocked")

        binding.sifremiUnuttumButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.Splash, ResetPassword())
                .addToBackStack("LoginMain").commit()
        }
    }

    fun createErrorDialog(loginErrorType: String) {
        if (!this.loginErrorTypesDialog.contains(loginErrorType)){
            Log.i("createErrorDialog method","Invalid error type")
            return
        }
        if(loginErrorType == this.loginErrorTypesDialog[4]){
            // The dialog here shows up when the account is blocked
            val dialogViewAccountBlocked = layoutInflater.inflate(R.layout.blocked_account_login,null)
            val dialogAccountBlocked = AlertDialog.Builder(requireContext()).setView(dialogViewAccountBlocked).create()
            dialogAccountBlocked.setCanceledOnTouchOutside(false)
            dialogAccountBlocked.setCancelable(false)
            dialogAccountBlocked.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialogViewAccountBlocked.findViewById<Button>(R.id.passwordResetButton).setOnClickListener {
                // navigate to the password reset page
                dialogAccountBlocked.dismiss()
                parentFragmentManager.beginTransaction().replace(R.id.Splash, ResetPassword()).addToBackStack("LoginMain").commit()
            }
            dialogViewAccountBlocked.findViewById<TextView>(R.id.dismissText).setOnClickListener {
                dialogAccountBlocked.dismiss()
            }
            dialogAccountBlocked.show()
            return
        }

        // the other 4 types of errors are handled here
        val dialogViewWrongInfo = layoutInflater.inflate(R.layout.wrong_information_login, null)
        val dialogWrongInfo = AlertDialog.Builder(requireContext()).setView(dialogViewWrongInfo).create()
        dialogWrongInfo.setCanceledOnTouchOutside(false)
        dialogWrongInfo.setCancelable(false)
        dialogWrongInfo.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        when(loginErrorType) {
            this.loginErrorTypesDialog[0] -> {
                // The dialog here shows up when wrong information from user is taken, and here it is empty as default
            }
            this.loginErrorTypesDialog[1] -> {
                // The dialog here shows up when wrong password were given
                dialogViewWrongInfo.findViewById<TextView>(R.id.mainErrorMessage).setText("Şifren hatalı")
            }
            this.loginErrorTypesDialog[2] -> {
                // The dialog here shows up when ID Number (TC Kimlik numarası) is not given
                dialogViewWrongInfo.findViewById<TextView>(R.id.mainErrorMessage).setText("Lütfen TC kimlik numaranı gir")
                dialogViewWrongInfo.findViewById<LinearLayout>(R.id.mainFrame).removeView(dialogViewWrongInfo.findViewById<TextView>(R.id.additionalInfo))
            }
            this.loginErrorTypesDialog[3] -> {
                // The dialog here shows up when password is not given
                dialogViewWrongInfo.findViewById<TextView>(R.id.mainErrorMessage).setText("Lütfen şifreni gir")
                dialogViewWrongInfo.findViewById<LinearLayout>(R.id.mainFrame).removeView(dialogViewWrongInfo.findViewById<TextView>(R.id.additionalInfo))
            }
        }
        dialogViewWrongInfo.findViewById<Button>(R.id.dismissButton).setOnClickListener {
            dialogWrongInfo.dismiss()
        }
        dialogWrongInfo.show()
    }
}