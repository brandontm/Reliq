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

package com.brandontm.reliq.ui.contacts.detail

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.brandontm.reliq.R
import com.brandontm.reliq.base.BaseFragment
import com.brandontm.reliq.di.viewModel.ViewModelProviderFactory
import com.brandontm.reliq.ext.showKeyboard
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.detail_fragment.*
import javax.inject.Inject

class DetailFragment : BaseFragment() {
    @Inject
    lateinit var viewModelProvider: ViewModelProviderFactory

    private lateinit var viewModel: DetailViewModel

    private val args: DetailFragmentArgs by navArgs()

    private var isModifying: Boolean = false

    private val onBackPressedCallback = {
        if(isModifying) promptDiscardChanges() else findNavController().popBackStack()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            onBackPressedCallback()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this, viewModelProvider)
            .get(DetailViewModel::class.java)



        txt_contact_name.setText(args.contact.name)
        txt_contact_name.doOnTextChanged { _, _, _, _ ->
            input_contact_name.error.let {
                if(!it.isNullOrEmpty()) {
                    input_contact_name.error = null
                    input_contact_name.endIconDrawable
                        ?.setTint(ContextCompat.getColor(requireContext(), R.color.secondaryColor))
                }
            }
        }
        txt_contact_name.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                switchModifying()

                return@OnKeyListener true
            }
            false
        })
        txt_contact_name.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus && isModifying) {
                switchModifying()
            }
        }
        input_contact_name.setEndIconOnClickListener {
            switchModifying()
        }

        viewModel.updateContactStatus.observe(this) {
            if(it) {
                Toast.makeText(requireContext(), R.string.contact_updated, Toast.LENGTH_SHORT)
                    .show()
            } else {
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle(android.R.string.dialog_alert_title)
                    .setIconAttribute(android.R.attr.alertDialogIcon)
                    .setMessage(R.string.error_updating_contact)
                    .setPositiveButton(android.R.string.ok, null)
                    .show()
            }
        }
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.menu_contact_detail, menu)
//    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            android.R.id.home -> {
                requireActivity().onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun promptDiscardChanges() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.ask_discard_changes)
            .setPositiveButton(R.string.discard) { _, _ ->
                findNavController().popBackStack()
            }
            .setNegativeButton(android.R.string.cancel, null)
            .show()
    }

    private fun switchModifying() {
        if(isModifying && txt_contact_name.text.isNullOrBlank()) {

            input_contact_name.error = getString(R.string.warning_contact_name_empty)
            return
        }

        isModifying = !isModifying

        txt_contact_name.isEnabled = isModifying

        if(isModifying) {
            txt_contact_name.showKeyboard()
            input_contact_name.endIconDrawable
                ?.setTint(ContextCompat.getColor(requireContext(), R.color.secondaryColor))

        } else {
            val contact = args.contact.apply { name = txt_contact_name.text.toString() }
            viewModel.updateContact(contact)
        }
    }
}
