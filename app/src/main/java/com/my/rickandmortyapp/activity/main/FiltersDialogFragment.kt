package com.my.rickandmortyapp.activity.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.my.rickandmortyapp.databinding.DialogFiltersBinding

class FiltersDialogFragment : DialogFragment() {

    private var _binding: DialogFiltersBinding? = null
    private val binding get() = _binding!!

    var onApplyFilters: ((status: String?, gender: String?) -> Unit)? = null

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = DialogFiltersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpClickListeners()
    }

    private fun setUpClickListeners() {
        binding.btnApplyFilters.setOnClickListener {
            val status = when (binding.rgStatus.checkedRadioButtonId) {
                binding.rbStatusAlive.id -> "Alive"
                binding.rbStatusDead.id -> "Dead"
                binding.rbStatusUnknown.id -> "unknown"
                else -> null
            }
            val gender = when (binding.rgGender.checkedRadioButtonId) {
                binding.rbGenderMale.id -> "Male"
                binding.rbGenderFemale.id -> "Female"
                binding.rbGenderUnknown.id -> "unknown"
                else -> null
            }
            onApplyFilters?.invoke(status, gender)
            dismiss()
        }
        binding.btnClearFilters.setOnClickListener {
            binding.rgStatus.clearCheck()
            binding.rgGender.clearCheck()
            onApplyFilters?.invoke(null, null)
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}