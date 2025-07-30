package com.zekeriya.oderowalletfromscratch

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.zekeriya.oderowalletfromscratch.databinding.ActivityMainBinding
import com.zekeriya.oderowalletfromscratch.databinding.FragmentSignUpIDInformationBinding

class SignUpIDInformation : Fragment() {
    private lateinit var binding: FragmentSignUpIDInformationBinding
    private var IDInformationErrorTypes = listOf<String>("No name", "No surname", "ID length")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        this.binding = FragmentSignUpIDInformationBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.OuterShell.setOnClickListener {
            val nameText = binding.NameInput.text.toString()
            val surnameText = binding.SurnameInput.text.toString()
            val IDText = binding.TCInput.text.toString()
            if (IDText.length == 11 && nameText.length > 0 && surnameText.length > 0){
                parentFragmentManager.beginTransaction().replace(R.id.Splash,
                    SignUpContactInformation()).addToBackStack("SignUpIDInformation").commit()
            }
            if (nameText.length == 0){
                this.createErrorDialog(IDInformationErrorTypes[0])
            }else if (surnameText.length == 0){
                this.createErrorDialog(IDInformationErrorTypes[1])
            }else if (IDText.length != 11){
                this.createErrorDialog(IDInformationErrorTypes[2])
            }
        }

    }
    private fun createErrorDialog(errorType: String){
        if (!IDInformationErrorTypes.contains(errorType)){
            return
        }
        val errorDialogView = layoutInflater.inflate(R.layout.wrong_information_login,null)
        val errorDialog = AlertDialog.Builder(requireContext()).setView(errorDialogView).create()
        errorDialog.setCancelable(false)
        errorDialog.setCanceledOnTouchOutside(false)
        errorDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        errorDialogView.findViewById<Button>(R.id.dismissButton).setOnClickListener {
            errorDialog.dismiss()
        }
        when(errorType){
            IDInformationErrorTypes[0] ->
                errorDialogView.findViewById<TextView>(R.id.additionalInfo).text = "Lütfen adını gir"
            IDInformationErrorTypes[1] ->
                errorDialogView.findViewById<TextView>(R.id.additionalInfo).text = "Lütfen soyadını gir"
            IDInformationErrorTypes[2] ->
                errorDialogView.findViewById<TextView>(R.id.additionalInfo).text = "TC kimlik numaran 11 haneli olmalı"
        }
        errorDialog.show()
    }
}