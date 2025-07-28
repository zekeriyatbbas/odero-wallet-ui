package com.zekeriya.oderowalletfromscratch

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import androidx.core.view.isVisible
import com.zekeriya.oderowalletfromscratch.databinding.FragmentOnBoarding01Binding

class OnBoarding01 : Fragment() {
    private lateinit var binding: FragmentOnBoarding01Binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding = FragmentOnBoarding01Binding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val languageFrameView = layoutInflater.inflate(R.layout.language_frame,null)
        val languageFrameDialog = AlertDialog.Builder(requireContext())
            .setView(languageFrameView)
            .create()
        languageFrameDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        languageFrameDialog.window?.setGravity(Gravity.BOTTOM)
        languageFrameView.findViewById<LinearLayout>(R.id.turkishContainer).setOnClickListener {
            languageFrameView.findViewById<RadioButton>(R.id.turkishRadioButton).isChecked = true
            languageFrameView.findViewById<RadioButton>(R.id.englishRadioButton).isChecked = false
            binding.flag.setImageResource(R.drawable.flag_turkey)
            binding.languageText.text = "TR"
            languageFrameView.findViewById<TextView>(R.id.dil).text = "Dil"
            languageFrameDialog.dismiss()
        }
        languageFrameView.findViewById<LinearLayout>(R.id.englishContainer).setOnClickListener {
            languageFrameView.findViewById<RadioButton>(R.id.englishRadioButton).isChecked = true
            languageFrameView.findViewById<RadioButton>(R.id.turkishRadioButton).isChecked = false
            binding.flag.setImageResource(R.drawable.flag_uk)
            binding.languageText.text = "EN"
            languageFrameView.findViewById<TextView>(R.id.dil).text = "Language"
            languageFrameDialog.dismiss()
        }
        binding.Input.setOnClickListener {
            languageFrameDialog.show()
        }


        binding.signupButton.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.Splash, SignUpMain())
                .addToBackStack("OnBoarding01")
                .commit()
        }
        binding.loginButton.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.Splash, LoginMain())
                .addToBackStack("OnBoarding01")
                .commit()
        }
    }
}