package com.example.reminders

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import com.example.reminders.databinding.DialogEditReminderBinding
import com.example.reminders.databinding.FragmentGeneralBinding
import com.example.reminders.databinding.FragmentPasswordsBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class GeneralInfoFragment: Fragment() {

    lateinit var binding: FragmentPasswordsBinding
    private val preferences by lazy {
        requireActivity().getSharedPreferences("passwords", Context.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPasswordsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayValues()
        binding.cardViewWifi.setOnClickListener { showEditDialog(PREF_WIFI) }
        binding.cardViewTabletPin.setOnClickListener { showEditDialog(PREF_TABLET_PIN) }
        binding.cardViewBikeLock.setOnClickListener { showEditDialog(PREF_BIKE_LOCK) }
    }

    private fun displayValues() {
        binding.textViewWifiValue.text = preferences.getString(PREF_WIFI, "")
        binding.textViewTabletPinValue.text = preferences.getString(PREF_TABLET_PIN, "")
        binding.textViewBikeLockValue.text = preferences.getString(PREF_BIKE_LOCK, "")
    }

    private fun showEditDialog(preferenceKey: String) {
        val dialogBinding = DialogEditReminderBinding.inflate(requireActivity().layoutInflater).apply {
            editTextValue.setText(preferences.getString(preferenceKey, ""))
            editTextValue.setSelection(editTextValue.text?.length ?: 0)
        }

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Update value")
            .setView(dialogBinding.root)
            .setPositiveButton("Save") { _, _ ->
                preferences.edit(commit = true) {
                    putString(preferenceKey, dialogBinding.editTextValue.text?.toString())
                }
                displayValues()
            }
            .setNegativeButton("Cancel") { _, _ ->

            }
            .show()
    }

    companion object {
        const val PREF_WIFI = "pref_wifi"
        const val PREF_TABLET_PIN = "pref_tablet_pin"
        const val PREF_BIKE_LOCK = "pref_bike_lock"
    }

}