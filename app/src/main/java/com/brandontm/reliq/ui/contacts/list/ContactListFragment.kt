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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import kotlinx.android.synthetic.main.contact_list_fragment.*
import javax.inject.Inject

class ContactListFragment : BaseFragment() {

    @Inject
    lateinit var vmProviderFactory: ViewModelProviderFactory

    private lateinit var viewModel: ContactListViewModel

    private val adapter = ContactListAdapter()


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
        adapter.onContactSelected = { contact ->
            navigateToDetail(contact)
        }
        swipe_contacts_refresh.setOnRefreshListener {
            viewModel.retrieveContacts()
        }


        rv_contacts.adapter = adapter
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
