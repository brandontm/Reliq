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

package com.brandontm.reliq.ui.contacts.list

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.brandontm.reliq.R
import com.brandontm.reliq.base.BaseFragment
import com.brandontm.reliq.data.model.entities.Contact
import com.brandontm.reliq.data.model.entities.Result
import com.brandontm.reliq.di.viewModel.ViewModelProviderFactory
import com.brandontm.reliq.ui.contacts.add.AddContactDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.contact_list_fragment.*
import kotlinx.android.synthetic.main.layout_contact_card.*
import javax.inject.Inject

class ContactListFragment : BaseFragment() {

    @Inject
    lateinit var vmProviderFactory: ViewModelProviderFactory

    private lateinit var viewModel: ContactListViewModel

    private val adapter = ContactListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.contact_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this, vmProviderFactory)
            .get(ContactListViewModel::class.java)

        viewModel.user.observe(this) { // TODO: Move session to other activity/fragment
            viewModel.retrieveContacts()

            setupViews()
            loadObservers()
        }
    }

    private fun loadObservers() {
        viewModel.contacts.observe(this) {
            swipe_contacts_refresh.isRefreshing = (it is Result.Loading)

            when(it) {
                is Result.Success -> {
                    if(it.data.isEmpty()) {
                        hideContactList()
                    } else {
                        showContactList()
                    }

                    adapter.updateItems(it.data)
                }
            }
        }
        viewModel.saveContactsStatus.observe(this) {
            viewModel.retrieveContacts()
        }
    }

    private fun setupViews() {
        setupContactsRecyclerView()

        fab_add_contact.setOnClickListener {

                fragmentManager?.run {
                    val fragment = AddContactDialogFragment()
                    fragment.setOnAddContactListener { contact ->
                        viewModel.addContact(contact)
                    }

                    fragment.show(this, null)
                }

        }
    }

    private fun setupContactsRecyclerView() {
        rv_contacts.layoutManager = LinearLayoutManager(context)

        adapter.onContactClicked = { contact ->
            navigateToDetail(contact)
        }

        adapter.onItemLongClick = { _, position ->
            requireActivity().startActionMode(object : ActionMode.Callback {
                override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                    return false
                }

                override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                    adapter.onItemSelectedChanged = { _, position, isSelected ->

                        if(adapter.getSelectedItems().isEmpty()) {
                            mode?.finish()
                        } else {
                            mode?.title = adapter.getSelectedItems().size.toString()
                        }
                    }

                    adapter.isSelectable = true
                    adapter.itemSwitchSelected(position)

                    menu?.add("Delete")?.apply {
                        icon = ContextCompat.getDrawable(
                            requireContext(), R.drawable.ic_delete_24dp
                        )?.apply { setTint(ContextCompat.getColor(requireContext(), android.R.color.white)) }

                    }?.setOnMenuItemClickListener {
                        mode?.let { getConfirmDeleteContactDialog(it).show() }
                        true
                    }
                    fab_add_contact.hide()

                    return true
                }

                override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                    return false
                }

                override fun onDestroyActionMode(mode: ActionMode?) {
                    adapter.isSelectable = false
                    chk_selected.visibility = View.GONE

                    fab_add_contact.show()
                }

            })
        }

        swipe_contacts_refresh.setOnRefreshListener {
            viewModel.retrieveContacts()
        }

        rv_contacts.adapter = adapter
    }

    private fun getConfirmDeleteContactDialog(mode: ActionMode): AlertDialog {
        return MaterialAlertDialogBuilder(requireContext())
            .setTitle(
                resources.getQuantityString(
                    R.plurals.confirm_delete_items,
                    adapter.getSelectedItems().size)
            )
            .setPositiveButton(R.string.delete) { _, _ ->
                viewModel.deleteContacts(adapter.getSelectedItems().values.toList())
                mode.finish()
            }
            .setNegativeButton(android.R.string.cancel, null)
            .create()
    }

    private fun showContactList() {
        rv_contacts.visibility = View.VISIBLE
        empty_contact_list_view.visibility = View.GONE
    }

    private fun hideContactList() {
        rv_contacts.visibility = View.GONE
        empty_contact_list_view.visibility = View.VISIBLE
    }

    private fun navigateToDetail(contact: Contact) {
        val action = ContactListFragmentDirections.actionMainFragmentToDetailFragment(contact)
        this.findNavController().navigate(action)
    }
}
