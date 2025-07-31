package com.zekeriya.oderowalletfromscratch

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.zekeriya.oderowalletfromscratch.databinding.ActivityMainBinding
import com.zekeriya.oderowalletfromscratch.databinding.FragmentSignUpContactInformationBinding
import java.util.regex.Pattern

class SignUpContactInformation : Fragment() {
    private lateinit var binding: FragmentSignUpContactInformationBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpContactInformationBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // for underlined text
        val aydinlatmaMetniText = binding.PersonalDataText
        var modifiedText = aydinlatmaMetniText.text.toString()
        var spannable = SpannableString(modifiedText)
        var wordToStyle = "Aydınlatma Metni"
        var start = modifiedText.indexOf(wordToStyle)
        var end = start + wordToStyle.length
        val oderoGreenColor = ContextCompat.getColor(requireContext(),R.color.odero_color_green)
        spannable.setSpan(UnderlineSpan(), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable.setSpan(ForegroundColorSpan(oderoGreenColor), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        aydinlatmaMetniText.text = spannable

        val cerceveSozlesmesiText = binding.cbText03
        modifiedText = cerceveSozlesmesiText.text.toString()
        spannable = SpannableString(modifiedText)
        wordToStyle = "Çerçeve Sözleşmesi"
        start = modifiedText.indexOf(wordToStyle)
        end = start + wordToStyle.length
        spannable.setSpan(UnderlineSpan(), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable.setSpan(ForegroundColorSpan(oderoGreenColor), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        cerceveSozlesmesiText.text = spannable


        val checkBoxes = mutableListOf<Pair<ImageView, Boolean>>(Pair(binding.cb01,false),
            Pair(binding.cb02,false),Pair(binding.cb03,false))
        for (i in checkBoxes.indices) {
            checkBoxes[i].first.setOnClickListener {
                if (!checkBoxes[i].second){
                    checkBoxes[i].first.setImageResource(R.drawable.checkbox_filled)
                    checkBoxes[i] = Pair(checkBoxes[i].first,true)
                }else{
                    checkBoxes[i].first.setImageResource(R.drawable.checkbox_empty)
                    checkBoxes[i] = Pair(checkBoxes[i].first,false)
                }
            }
        }
        binding.backButtonSignUpContactInfo.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        binding.OuterShell.setOnClickListener {
            var areAllCheckboxesApproved = true
            for (checkBox in checkBoxes){
                if (!checkBox.second){
                    areAllCheckboxesApproved = false
                    break
                }
            }
            val isPhoneInputTenDigit = binding.PhoneNumberInput.text.toString().length == 10
            val isEmailInputValid = isValidEmail(binding.EmailInput.text.toString())
            if (areAllCheckboxesApproved && isPhoneInputTenDigit && isEmailInputValid){
                parentFragmentManager.beginTransaction().replace(R.id.Splash,
                    SignUpPhoneVerification())
                    .addToBackStack("SignUpContactInformation").commit()
                return@setOnClickListener
            }
            val errorDialogView = layoutInflater.inflate(R.layout.wrong_information_login,null)
            val errorDialog = AlertDialog.Builder(requireContext()).setView(errorDialogView).create()
            errorDialog.setCancelable(false)
            errorDialog.setCanceledOnTouchOutside(false)
            errorDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            errorDialogView.findViewById<Button>(R.id.dismissButton).setOnClickListener {
                errorDialog.dismiss()
            }
            if (areAllCheckboxesApproved){
                errorDialogView.findViewById<TextView>(R.id.additionalInfo).text = "Telefon numaran veya e-posta adresin hatalı"
            } else{
                errorDialogView.findViewById<TextView>(R.id.mainErrorMessage).text = "Hata"
                errorDialogView.findViewById<TextView>(R.id.additionalInfo).text = "Gerekli şartları kabul etmedin"
            }
            errorDialog.show()
        }

    }

    fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$"
        val pattern = Pattern.compile(emailRegex)
        return pattern.matcher(email).matches()
    }
}