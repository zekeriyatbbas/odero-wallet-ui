package com.zekeriya.oderowalletfromscratch

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.method.DigitsKeyListener
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
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
        binding.tcKimlikInput.requestFocus()
        binding.backButtonResetID.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        binding.Button.setOnClickListener {
            val IDInputLength = binding.tcKimlikInput.text.length
            if (IDInputLength == 11) {
                parentFragmentManager.beginTransaction().replace(R.id.Splash,ResetPasswordPhoneCodeVerification())
                    .addToBackStack("ResetPasswordIDVerification")
                    .commit()
            }else{
                val invalidIDDialogView = layoutInflater.inflate(R.layout.wrong_information_login,null)
                val invalidIDDialog = AlertDialog.Builder(requireContext())
                    .setView(invalidIDDialogView)
                    .create()
                invalidIDDialog.setCancelable(false)
                invalidIDDialog.setCanceledOnTouchOutside(false)
                invalidIDDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                invalidIDDialogView.findViewById<TextView>(R.id.additionalInfo).text = "TC kimlik numaran 11 haneli olmalı"
                invalidIDDialogView.findViewById<Button>(R.id.dismissButton).setOnClickListener {
                    invalidIDDialog.dismiss()
                }
                invalidIDDialog.show()
            }
        }
    }
}