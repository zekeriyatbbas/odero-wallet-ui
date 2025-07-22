package com.zekeriya.oderowalletfromscratch

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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

        binding.languageFrame.visibility = View.GONE
        binding.buttonContainer.visibility = View.VISIBLE

        binding.Input.setOnClickListener {
            binding.buttonContainer.visibility = View.GONE
            binding.languageFrame.visibility = View.VISIBLE
        }
        binding.turkishContainer.setOnClickListener {
            binding.englishRadioButton.isChecked = false
            binding.turkishRadioButton.isChecked = true
            binding.flag.setImageResource(R.drawable.flag_turkey)
            binding.languageText.text = "TR"
            binding.dil.text = "Dil"
            binding.languageFrame.visibility = View.GONE
            binding.buttonContainer.visibility = View.VISIBLE
        }
        binding.englishContainer.setOnClickListener {
            binding.turkishRadioButton.isChecked = false
            binding.englishRadioButton.isChecked = true
            binding.flag.setImageResource(R.drawable.flag_uk)
            binding.languageText.text = "EN"
            binding.dil.text = "Language"
            binding.languageFrame.visibility = View.INVISIBLE
            binding.buttonContainer.visibility = View.VISIBLE
        }

        binding.signupButton.setOnClickListener {
            // navigate to the signup fragment
        }
        binding.loginButton.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.Splash, LoginMain()).addToBackStack("OnBoarding01").commit()
        }
    }
}