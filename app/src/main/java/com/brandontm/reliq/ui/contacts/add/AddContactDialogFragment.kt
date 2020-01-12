/*
 * Copyright (C) 2019  Brandon Tirado
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.brandontm.reliq.ui.contacts.add

import android.app.Dialog
import android.os.Bundle
import android.view.WindowManager
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.DialogFragment
import com.brandontm.reliq.R
import com.brandontm.reliq.data.model.entities.Contact
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import java.util.*

class AddContactDialogFragment : DialogFragment() {
    private var listener: ((contact: Contact) -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(
            requireContext(), R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog
        ).setTitle("Add Contact")
            .setView(R.layout.add_contact_dialog_fragment)
            .create()
    }

    override fun onResume() {
        super.onResume()
        showKeyboard()

        val btnAddContact = dialog?.findViewById<MaterialButton>(R.id.btn_add_contact)
        val btnCancel = dialog?.findViewById<MaterialButton>(R.id.btn_cancel)
        val inputContactName = dialog?.findViewById<TextInputLayout>(R.id.input_contact_name)
        val txtContactName = dialog?.findViewById<TextView>(R.id.txt_contact_name)

        btnAddContact?.setOnClickListener {
            val id: String = UUID.randomUUID().toString()
            val name: String? = txtContactName?.text?.toString()
            if(name.isNullOrBlank()) {
                inputContactName?.error =
                    getString(R.string.warning_contact_name_empty)
            } else {
                val contact = Contact(id, name, 0)

                // Trigger listener
                listener?.invoke(contact)
                dismiss()
            }
        }

        btnCancel?.setOnClickListener {
            dismiss()
        }

        txtContactName?.doOnTextChanged { _, _, _, _ ->
            inputContactName?.error = ""
        }
    }

    fun setOnAddContactListener(onAddContact: (contact: Contact) -> Unit) {
        listener = onAddContact
    }

    private fun showKeyboard() {
        val window = dialog?.window
        window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
        window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
    }
}
